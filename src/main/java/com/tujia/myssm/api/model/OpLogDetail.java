package com.tujia.myssm.api.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author: songlinl
 * @create: 2021/11/04 17:22
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OpLogDetail implements Serializable {
    private static final long serialVersionUID = -696447714714231739L;
    /**
     * 日志摘要
     */
    private String summary;

    /**
     * 旧值
     */
    private String oldValue;

    /**
     * 新值
     */
    private String newValue;
}
