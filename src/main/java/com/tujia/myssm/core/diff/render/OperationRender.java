/*
 * Copyright (c) 2017 tujia.com. All Rights Reserved.
 */
package com.tujia.myssm.core.diff.render;

import com.tujia.myssm.core.diff.visitor.bean.ModifyValue;

/**
 * @author huachen created on 2/21/14 12:25 PM
 * @version $Id$
 */
public class OperationRender {
    public String render(ModifyValue modifyValue) {
        if (modifyValue.isAdded()) {
            return renderAdd(modifyValue);
        }
        if (modifyValue.isRemoved()) {
            return renderRemoved(modifyValue);
        }
        if (modifyValue.isChanged()) {
            return renderModified(modifyValue);
        }

        return null;
    }

    private String renderAdd(ModifyValue modifyValue) {
        //        if (modifyValue.getWorking() instanceof PrimitiveDiffValue) {
        //            return String.format("添加 %s", modifyValue.getWorking());
        //        } else {
        //            return String.format("添加 %s", modifyValue.getWorking());
        //        }
        return String.format("添加 '%s'", modifyValue.getWorking());
    }

    private String renderRemoved(ModifyValue modifyValue) {
        //        if (modifyValue.getBase() instanceof PrimitiveDiffValue) {
        //            return String.format("%s 删除", modifyValue.getBase());
        //        } else {
        //            return String.format("删除 %s", modifyValue.getBase());
        //        }
        return String.format("删除 '%s'", modifyValue.getBase());
    }

    private String renderModified(ModifyValue modifyValue) {
        return String.format("'%s'->'%s'", modifyValue.getBase(), modifyValue.getWorking());
    }

}
