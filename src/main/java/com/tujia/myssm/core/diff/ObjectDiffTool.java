package com.tujia.myssm.core.diff;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.tujia.myssm.core.diff.annotation.Diff;
import com.tujia.myssm.core.diff.annotation.DiffIgnore;
import com.tujia.myssm.core.diff.danielbechler.diff.ObjectDiffer;
import com.tujia.myssm.core.diff.danielbechler.diff.ObjectDifferBuilder;
import com.tujia.myssm.core.diff.danielbechler.diff.identity.IdentityStrategy;
import com.tujia.myssm.core.diff.danielbechler.diff.inclusion.Inclusion;
import static com.tujia.myssm.core.diff.danielbechler.diff.inclusion.Inclusion.DEFAULT;
import static com.tujia.myssm.core.diff.danielbechler.diff.inclusion.Inclusion.EXCLUDED;
import com.tujia.myssm.core.diff.danielbechler.diff.node.DiffNode;
import com.tujia.myssm.core.diff.extend.CollectionIdentityStrategy;
import com.tujia.myssm.core.diff.render.DiffPathBuilder;
import com.tujia.myssm.core.diff.render.DiffPathFormatter;
import com.tujia.myssm.core.diff.render.SimpleDiffPathFormatter;
import com.tujia.myssm.core.diff.visitor.TnsVisitor;
import com.tujia.myssm.core.diff.visitor.bean.DiffPath;

/**
 * @author huachen created on 2/20/14 3:07 PM
 * @version $Id$
 * <p>说明：于2017-10-24修正，修复一些bug
 * <p>參考：https://github.com/SQiShER/java-object-diff
 *
 * @Diff("TModel")
 * public class TModel {
 *     public TModel() {
 *     }
 *
 *     public TModel(Integer id, String name, String ee) {
 *         this.id = id;
 *         this.name = name;
 *         this.ee = ee;
 *     }
 *
 *     @DiffIgnore
 *     private Integer id = 0;
 *     @Diff("名字")
 *     private String name = "";
 *     // 用于测试diff string 中包含该字段，diff map中不包含该字段
 *     @Diff("EEE")
 *     private String ee = "";
 *
 *     @DiffIgnore
 *     public Integer getId() {
 *         return id;
 *     }
 *
 *     public void setId(Integer id) {
 *         this.id = id;
 *     }
 *
 *     public String getName() {
 *         return name;
 *     }
 *
 *     public void setName(String name) {
 *         this.name = name;
 *     }
 *
 *     @DiffIgnore
 *     public String getEe() {
 *         return ee;
 *     }
 *
 *     public void setEe(String ee) {
 *         this.ee = ee;
 *     }
 *
 *     @Override
 *     public boolean equals(Object o) {
 *         if (this == o) return true;
 *         if (o == null || getClass() != o.getClass()) return false;
 *
 *         TModel tModel = (TModel) o;
 *
 *         if (id != null ? !id.equals(tModel.id) : tModel.id != null) return false;
 *         if (name != null ? !name.equals(tModel.name) : tModel.name != null) return false;
 *         return ee != null ? ee.equals(tModel.ee) : tModel.ee == null;
 *     }
 *
 *     @Override
 *     public int hashCode() {
 *         int result = id != null ? id.hashCode() : 0;
 *         result = 31 * result + (name != null ? name.hashCode() : 0);
 *         result = 31 * result + (ee != null ? ee.hashCode() : 0);
 *         return result;
 *     }
 * }
 */
public class ObjectDiffTool {
    private static Logger logger = LoggerFactory.getLogger(ObjectDiffTool.class);

    /**
     * 反斜杠
     */
    private static String BACKSLASH = "/";
    /**
     * 替换 反斜杠 符号，默认使用 '.'
     */
    private static String POINT = ".";
    /**
     * 替换第一个 反斜杠 的符号，默认使用 空值
     */
    private static String EMPTY = "";

    /**
     * diff 2个对象(使用默认格式化结果工具{@link SimpleDiffPathFormatter})
     * <p>
     * diff结果中不包含：字段上包含 {@link DiffIgnore}的注解
     * diff结果中包含：  字段上 不 包含 {@link DiffIgnore}的注解
     * 1）字段上包含 {@link Diff}的注解，如果Diff注解中包含说明(如Diff("测试"))刚使用该说明替换字段名
     * 2）字段上不包含{@link Diff}的注解，使用字段名输出diff结果
     *
     * @param base    老对象
     * @param working 新对象
     * @return String 格式化的字符串
     */
    public static String diff(Object base, Object working) {
        return diff(base, working, new SimpleDiffPathFormatter());
    }

    /**
     * diff 2个对象(使用自定义格式化结果工具{@link SimpleDiffPathFormatter})
     * <p>
     * diff结果中不包含：字段上包含 {@link DiffIgnore}的注解
     * diff结果中包含：  字段上 不 包含 {@link DiffIgnore}的注解
     * 1）字段上包含 {@link Diff}的注解，如果Diff注解中包含说明(如Diff("测试"))刚使用该说明替换字段名
     * 2）字段上不包含{@link Diff}的注解，使用字段名输出diff结果
     *
     * @param base              老对象
     * @param working           新对象
     * @param diffPathFormatter 自定义diff格式化工具
     * @return String 格式化的字符串
     */
    public static String diff(Object base, Object working, DiffPathFormatter diffPathFormatter) {
        return diff(base, working, diffPathFormatter, null);
    }

    /**
     * diff 2个对象(使用默认格式化结果工具{@link SimpleDiffPathFormatter},同时使用了新的diff 集合对象的策略,
     * 具体见:{@link CollectionIdentityStrategy})
     * <p>
     * diff结果中不包含：字段上包含 {@link DiffIgnore}的注解
     * diff结果中包含：  字段上 不 包含 {@link DiffIgnore}的注解
     * 1）字段上包含 {@link Diff}的注解，如果Diff注解中包含说明(如Diff("测试"))刚使用该说明替换字段名
     * 2）字段上不包含{@link Diff}的注解，使用字段名输出diff结果
     *
     * @param base    老对象
     * @param working 新对象
     * @return String 格式化的字符串
     */
    public static String diffWithList(Object base, Object working) {
        return diff(base, working, new SimpleDiffPathFormatter(), CollectionIdentityStrategy.INSTANCE);
    }

    /**
     * diff 2个对象(使用自定义格式化结果工具{@link SimpleDiffPathFormatter})
     * <p>
     * diff结果中不包含：字段上包含 {@link DiffIgnore}的注解
     * diff结果中包含：  字段上 不 包含 {@link DiffIgnore}的注解
     * 1）字段上包含 {@link Diff}的注解，如果Diff注解中包含说明(如Diff("测试"))刚使用该说明替换字段名
     * 2）字段上不包含{@link Diff}的注解，使用字段名输出diff结果
     *
     * @param base             老对象
     * @param working          新对象
     * @param identityStrategy 自定义的集合对象标识策略提供类
     * @return String 格式化的字符串
     */
    public static String diff(Object base, Object working, IdentityStrategy identityStrategy) {
        return diff(base, working, new SimpleDiffPathFormatter(), identityStrategy);
    }

    /**
     * diff 2个对象(使用自定义格式化结果工具{@link SimpleDiffPathFormatter})
     * <p>
     * diff结果中不包含：字段上包含 {@link DiffIgnore}的注解
     * diff结果中包含：  字段上 不 包含 {@link DiffIgnore}的注解
     * 1）字段上包含 {@link Diff}的注解，如果Diff注解中包含说明(如Diff("测试"))刚使用该说明替换字段名
     * 2）字段上不包含{@link Diff}的注解，使用字段名输出diff结果
     *
     * @param base              老对象
     * @param working           新对象
     * @param diffPathFormatter 自定义diff格式化工具
     * @return String 格式化的字符串
     */
    public static String diff(Object base, Object working, DiffPathFormatter diffPathFormatter, IdentityStrategy identityStrategy) {
        try {
            // start of java-object-diff module
            ObjectDifferBuilder differBuilder = ObjectDifferBuilder.startBuilding();
            if (identityStrategy != null) {
                differBuilder.identity().setDefaultCollectionItemIdentityStrategy(identityStrategy);
            }
            ObjectDiffer objectDiffer = differBuilder.build();
            final DiffNode root = objectDiffer.compare(working, base);
            final TnsVisitor visitor = new TnsVisitor(working, base);
            root.visit(visitor);
            // end of java-object-diff module

            DiffPath diffPath = new DiffPathBuilder(visitor).createDiffPath();
            if (diffPath == null) {
                return "";
            }
            return diffPathFormatter.format(diffPath);
        } catch (Exception e) {
            logger.info("diff对象失败,msg = {}", e.getMessage(), e);
            return "";
        }
    }

    /**
     * diff2个对象生成 Map<{@link String}, {@link DiffValues}>
     *
     * diff结果中不包含：字段对应的get方法上包含 {@link DiffIgnore}的注解
     * diff结果中包含：字段对应的get方法上 不 包含 {@link DiffIgnore}的注解
     *
     * @param base    老对象
     * @param working 新对象
     * @return Map<String, DiffValues>
     */
    public static Map<String, DiffValues> generateDiffMap(Object base, Object working) {
        return generateDiffMap(base, working, EMPTY, POINT);
    }

    /**
     * diff2个对象生成 Map<{@link String}, {@link DiffValues}>
     *
     * 不需要diff结果时在字段对应的get方法上添加 @ObjectDiffProperty(inclusion = Inclusion.EXCLUDED)的注解
     *
     * @param base                  老对象
     * @param working               新对象
     * @param replaceFirstBackslash 替换第一个 反斜杠 的符号，默认使用 空值
     * @param replaceAllBackslash   替换 反斜杠 符号，默认使用 '.'
     * @return Map<String, DiffValues>
     */
    public static Map<String, DiffValues> generateDiffMap(Object base, Object working, String replaceFirstBackslash, String replaceAllBackslash) {
        try {
            DiffNode root = ObjectDifferBuilder.buildDefault().compare(working, base);

            String finalReplaceFirstBackslash = Strings.isNullOrEmpty(replaceFirstBackslash) ? EMPTY : replaceFirstBackslash;
            String finalReplaceAllBackslash = Strings.isNullOrEmpty(replaceAllBackslash) ? POINT : replaceAllBackslash;

            Map<String, DiffValues> diffValuesMap = Maps.newHashMap();
            root.visit((diffNode, visit) -> {
                if (diffNode.hasChanges() && !diffNode.hasChildren() && getInclusion(diffNode) != EXCLUDED) {
                    diffValuesMap.put(diffNode.getPath()
                                              .toString()
                                              .replaceFirst(BACKSLASH, finalReplaceFirstBackslash)
                                              .replace(BACKSLASH, finalReplaceAllBackslash),
                                      new DiffValues(diffNode.canonicalGet(base), diffNode.canonicalGet(working)));
                }
            });
            return diffValuesMap;
        } catch (Exception e) {
            logger.info("diff对象失败,msg = {}", e.getMessage(), e);
            return Maps.newHashMap();
        }
    }

    /**
     * 读取字段对应的get方法上的注解，如果包含{@link DiffIgnore} 刚在生成diff map时忽略该节点
     *
     * @param node {@link DiffNode}
     * @return {@link Inclusion}
     */
    private static Inclusion getInclusion(final DiffNode node) {
        final DiffIgnore propertyAnnotation = node.getPropertyAnnotation(DiffIgnore.class);
        if (propertyAnnotation != null) {
            return EXCLUDED;
        }

        return DEFAULT;
    }
}