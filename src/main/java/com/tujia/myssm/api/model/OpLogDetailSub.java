package com.tujia.myssm.api.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author: songlinl
 * @create: 2021/11/18 10:49
 */
@Builder
@Getter
@Setter
public class OpLogDetailSub extends OpLogDetail {
    private static final long serialVersionUID = -1054788230617830325L;

    /**
     * sub
     */
    private String sub;
}
