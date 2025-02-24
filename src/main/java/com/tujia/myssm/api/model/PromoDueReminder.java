package com.tujia.myssm.api.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import com.tujia.framework.datetime.bean.ShortDate;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

/**
 * 红包到期提醒记录
 * @author: songlinl
 * @create: 2021/10/13 09:47
 */
@Getter
@Setter
@ToString
public class PromoDueReminder implements Serializable {
    private static final long serialVersionUID = 2766237106439684544L;

    /**
     * 主键id
     */
    private long id;

    /**
     * 会员id
     */
    private long memberId;
    /**
     * 券code
     */
    private String promoCode;
    /**
     * 券到期时间
     */
    private LocalDateTime toTime;
    /**
     * 提醒次数
     */
    private int number;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 最后修改时间
     */
    private LocalDateTime updateTime;

    public static void main(String[] args) {
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM月dd日 EEEE");
        String formattedDate = date.format(formatter);
        System.out.println(formattedDate);
    }

    @Data
    public static class A{
        ShortDate date;

        public A(ShortDate date) {
            this.date = date;
        }
    }

    @Data
    public static class B{
        Date date;

        public B(Date date) {
            this.date = date;
        }
    }
}
