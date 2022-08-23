package com.tujia.myssm.api.model.json;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

/**
 *
 * @author: songlinl
 * @create: 2021/12/27 16:59
 */
@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.CUSTOM, include = JsonTypeInfo.As.PROPERTY, property = "type", visible = true)
@JsonSubTypes({ @JsonSubTypes.Type(value = JsonHuman.Man.class, name = "man"), @JsonSubTypes.Type(value = JsonHuman.Woman.class, name = "woman") })
public abstract class JsonHuman {
    private String district;

    @Data
    public static class Man extends JsonHuman {
        private String manField;
    }

    @Data
    public static class Woman extends JsonHuman {
        private String womanField;
    }

}
