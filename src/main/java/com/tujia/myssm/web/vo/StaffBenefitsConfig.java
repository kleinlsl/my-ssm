package com.tujia.myssm.web.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 员工福利配置信息
 *
 * @author: songlinl
 * @create: 2023/7/5 15:54
 */

@Getter
@Setter
public class StaffBenefitsConfig implements Serializable {


    private static final long serialVersionUID = 5914795522803807023L;

    /**
     * 长租券，民宿券
     */
    private String activityName;

    /**
     * 活动code
     */
    private String activityCode;

    /**
     * 员工赠券总次数
     */
    private int totalCount;

    /**
     * 使用规则描述
     */
    private String ruleDesc;


}
