package com.tujia.myssm.api.diff;

import com.tujia.framework.datetime.bean.ShortDate;
import com.tujia.myssm.api.diff.render.DiffPathBuilder;
import com.tujia.myssm.api.diff.render.DiffPathFormatter;
import com.tujia.myssm.api.diff.render.SimpleDiffPathFormatter;
import com.tujia.myssm.api.diff.visitor.TnsVisitor;
import com.tujia.myssm.api.diff.visitor.bean.DiffPath;
import de.danielbechler.diff.ObjectDiffer;
import de.danielbechler.diff.ObjectDifferBuilder;
import de.danielbechler.diff.node.DiffNode;

/**
 *
 * @author: songlinl
 * @create: 2023/02/22 19:48
 */
public class DiffTools {

    public static String diff(Object base, Object working) {
        return diff(base, working, new SimpleDiffPathFormatter());
    }

    public static String diff(Object base, Object working, DiffPathFormatter diffPathFormatter) {

        // start of java-object-diff module
        ObjectDiffer objectDiffer = ObjectDifferBuilder.startBuilding().build();
        final DiffNode root = objectDiffer.compare(working, base);
        final TnsVisitor visitor = new TnsVisitor(working, base);
        root.visit(visitor);
        // end of java-object-diff module

        DiffPath diffPath = new DiffPathBuilder(visitor).createDiffPath();
        return diffPathFormatter.format(diffPath);
    }

    public static void main(String[] args) {
        System.out.println(diff(ShortDate.valueOf("2014-01-01"), ShortDate.valueOf("2014-01-02")));
        System.out.println(diff(ShortDate.valueOf("2014-01-01"), ShortDate.valueOf("2014-01-01")));
    }

}
