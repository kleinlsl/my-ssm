/*
 * Copyright (c) 2017 tujia.com. All Rights Reserved.
 */
package com.tujia.myssm.core.diff.render;

import java.util.Iterator;
import com.tujia.myssm.core.diff.visitor.bean.DiffPath;

/**
 * @author huachen created on 2/21/14 3:16 PM
 * @version $Id$
 */
public class SimpleDiffPathFormatter implements DiffPathFormatter {
    private OperationRender operationRender = new OperationRender();

    @Override
    public String format(DiffPath diffPath) {
        return format0(diffPath, "");
    }

    private String format0(DiffPath diffPath, String prefix) {
        if (diffPath == null) {
            return "没有变化";
        }
        StringBuilder resultString = new StringBuilder();
        if (diffPath.getName() != null) {
            resultString.append(diffPath.getName());
        }
        if (diffPath.isLeaf()) {
            resultString.append(":").append(operationRender.render(diffPath.getModifyValue()));
        } else if (diffPath.getChildren().size() > 0) {
            Iterator<DiffPath> iterator = diffPath.getChildren().iterator();
            while (iterator.hasNext()) {
                DiffPath child = iterator.next();
                resultString.append("\n").append(prefix);
                if (iterator.hasNext()) {
                    resultString.append("├──");
                } else {
                    resultString.append("└──");
                }
                resultString.append(format0(child, iterator.hasNext() ? prefix + "│    " : prefix + "     "));
            }
        }

        return resultString.toString();
    }

}
