package com.tujia.myssm.base.monitor;


/**
 * 监控总览
 *
 * @author bowen.ma
 * @date 2019/02/01 4:47 PM
 */
@SuppressWarnings({"AlibabaEnumConstantsMustHaveComment", "unused"})
public enum Monitors implements MRegistry {
    //查询活动详情

    SUCCESS("_Success"),
    ERROR("_Error"),
    EXECUTE("_Execute"),
    IGNORE("_Ignore"),

    BackDoorController_testBizTemplate("测试业务模板类"),
    BackDoorController_testBizTemplateA("测试业务模板类A")
    ;


    final private String desc;


    Monitors(String desc) {
        this.desc = desc;
    }

    @Override
    public String key() {
        return name();
    }

    @Override
    public String desc() {
        return desc;
    }
}
