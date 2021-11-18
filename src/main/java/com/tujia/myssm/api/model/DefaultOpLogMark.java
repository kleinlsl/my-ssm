package com.tujia.myssm.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author: songlinl
 * @create: 2021/11/18 14:30
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DefaultOpLogMark extends OpLogMark {
    private static final long serialVersionUID = -5746172758809165154L;
    /**
     * 备注
     */
    private String mark;
}
