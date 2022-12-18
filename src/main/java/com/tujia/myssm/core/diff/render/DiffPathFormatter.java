/*
 * Copyright (c) 2017 tujia.com. All Rights Reserved.
 */
package com.tujia.myssm.core.diff.render;

import com.tujia.myssm.core.diff.visitor.bean.DiffPath;

/**
 * @author huachen created on 2/21/14 9:37 PM
 * @version $Id$
 */
public interface DiffPathFormatter {
    String format(DiffPath diffPath);
}
