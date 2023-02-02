package com.tujia.myssm.utils.excel;

import java.util.List;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.google.common.collect.Lists;
import com.tujia.myssm.api.model.excel.SimpleExcelModel;

/**
 *
 * @author: songlinl
 * @create: 2022/06/08 11:10
 */
public class SimpleExcelUtils {

    public static List<SimpleExcelModel> simpleRead(String fileName) {
        // 有个很重要的点 DemoDataListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
        // 写法1：
        List<SimpleExcelModel> excelList = Lists.newArrayList();
        EasyExcel.read(fileName, SimpleExcelModel.class, new AnalysisEventListener<SimpleExcelModel>() {

            @Override
            public void invoke(SimpleExcelModel data, AnalysisContext context) {
                if (data != null) {
                    excelList.add(data);
                }
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {

            }
        }).sheet().doRead();

        return excelList;
    }
}
