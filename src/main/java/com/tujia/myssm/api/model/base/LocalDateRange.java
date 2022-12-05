package com.tujia.myssm.api.model.base;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import static com.tujia.myssm.common.utils.Symbol.UNDERLINE;

/**
 *
 * @author: songlinl
 * @create: 2022/12/05 17:13
 */
public class LocalDateRange implements Serializable, Comparable<LocalDateRange> {
    private static final long serialVersionUID = 5669236618618467560L;

    private LocalDate st;
    private LocalDate et;

    public LocalDateRange() {
        st = LocalDate.now();
        et = LocalDate.now();
    }

    public LocalDateRange(LocalDate st, LocalDate et) {
        if (st.isAfter(et)) {
            this.st = et;
            this.et = st;
            return;
        }
        this.st = st;
        this.et = et;
    }

    public static LocalDateRange parse(String value) {
        if (value == null) {
            return null;
        }
        String[] arr = value.split(UNDERLINE);
        if (arr.length != 2) {
            return null;
        }
        return new LocalDateRange(LocalDate.parse(arr[0]), LocalDate.parse(arr[1]));
    }

    public LocalDate getSt() {
        return st;
    }

    public void setSt(LocalDate st) {
        this.st = st;
    }

    public LocalDate getEt() {
        return et;
    }

    public void setEt(LocalDate et) {
        this.et = et;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        LocalDateRange dateRange = (LocalDateRange) o;
        return Objects.equals(st, dateRange.st) && Objects.equals(et, dateRange.et);
    }

    @Override
    public int hashCode() {
        return Objects.hash(st, et);
    }

    @Override
    public String toString() {
        return st + UNDERLINE + et;
    }

    @Override
    public int compareTo(LocalDateRange o) {
        if (!this.st.equals(o.st)) {
            return this.st.compareTo(o.st);
        }
        return this.et.compareTo(o.et);
    }
}
