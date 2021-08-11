package com.tujia.myssm.bean;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * activity_participant
 *
 * @author
 */
@Data
public class ActivityParticipant implements Serializable {
    private static final long serialVersionUID = -4109925055839192586L;
    /**
     * 主键id
     */
    private Long id;

    /**
     * 活动配置编码
     */
    private String activityCode;

    /**
     * 活动配置状态 1=未处理 2=已处理
     */
    private int status;

    /**
     * 房屋ID列表，按照升序排列的房屋列表
     */
    @Deprecated
    private String unitIds = "";

    /**
     * MD5值
     */
    private String md5;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最后修改时间
     */
    private Date updateTime;

    /**
     * 版本：初始值为1
     */
    private Integer version;

    /**
     * 字节数据：最大16M
     */
    private byte[] data;

    public String byteToString() {
        return new String(this.data);
    }
}