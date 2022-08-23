package com.tujia.myssm.api.model.excel;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * @author: songlinl
 * @create: 2022/05/16 11:21
 */
@Getter
@Setter
public class AmapFacilityMapping implements Serializable {

    private static final long serialVersionUID = 6709351538771533066L;

    /**
     * 高德设施 ID
     */
    private String amapCode;

    /**
     * 高德设施名称
     */
    private String amapName;

    /**
     * 途家设施ids (逗号隔开)
     */
    private String tujiaIds;

}
