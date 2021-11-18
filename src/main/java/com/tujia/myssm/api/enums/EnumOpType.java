package com.tujia.myssm.api.enums;

import java.util.Arrays;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

/**
 *
 * @author: songlinl
 * @create: 2021/11/03 18:28
 */
public enum EnumOpType {
    None(0, "None"),
    SQLError(1, "Sql错误"),
    SQLExecute(2, "Sql执行"),

    TaskDetailAdd(10001, "任务详情表:新增"),
    TaskDetailUpd(10002, "任务详情表:修改"),
    TaskDetailDel(10003, "任务详情表:删除"),
    TaskDetailUpdFlowStatus(10004, "任务详情表:修改流水状态"),
    TaskDetailCancelExpire(10005, "任务详情表:取消过期任务"),

    TaskConfigAdd(20001, "任务配置表:新增"),
    TaskConfigUpd(20002, "任务配置表:修改"),
    TaskConfigDel(20003, "任务配置表:删除"),
    TaskConfigUpdStatus(20005, "任务配置表:修改状态");

    private static final ImmutableMap<Integer, EnumOpType> INDEX = Maps.uniqueIndex(
            Arrays.stream(EnumOpType.values()).iterator(), EnumOpType::getId);

    private int id;

    private String desc;

    EnumOpType(int id, String desc) {
        this.id = id;
        this.desc = desc;
    }

    public static EnumOpType codeOf(int id) {
        return INDEX.get(id);
    }

    public int getId() {
        return id;
    }

    public String getDesc() {
        return desc;
    }
}
