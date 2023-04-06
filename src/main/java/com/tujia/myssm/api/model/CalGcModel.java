package com.tujia.myssm.api.model;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author: songlinl
 * @create: 2023/04/06 14:02
 */
@Getter
@Setter
public class CalGcModel implements Serializable {
    private static final long serialVersionUID = 4865912945797367406L;

    private Long before;
    private Long after;
    private Long heapBefore;
    private Long heapAfter;

    private Long gc;
    private Long heapGc;

    private Long diff;

    public void calGc() {
        if (before == null || after == null) {
            return;
        }
        this.gc = before - after;
    }

    public void calHGc() {
        if (heapBefore == null || heapAfter == null) {
            return;
        }
        this.heapGc = this.heapBefore - this.heapAfter;
    }

    public void calDiff() {
        if (gc == null || heapGc == null) {
            return;
        }
        this.diff = gc - heapGc;
    }

    public void cal() {
        calGc();
        calHGc();
        calDiff();
    }

}
