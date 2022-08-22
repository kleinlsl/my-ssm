package com.tujia.myssm.test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.TreeMap;
import org.junit.Test;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.protobuf.InvalidProtocolBufferException;
import com.tujia.myssm.api.model.proto.Book;
import com.tujia.myssm.api.model.proto.Page;
import com.tujia.myssm.api.model.proto.PayType;
import com.tujia.myssm.cache.BookModels;
import com.tujia.myssm.cache.CacheModels;
import com.tujia.myssm.utils.ByteUtils;
import com.tujia.myssm.utils.ProtoJsonUtils;
import com.tujia.myssm.utils.SerializationUtil;

/**
 *
 * @author: songlinl
 * @create: 2021/10/22 21:32
 */
public class testProtobuf {

    @Test
    public void testStudent() throws IOException {
        CacheModels.Student student = CacheModels.Student.newBuilder().setScore(CacheModels.BDecimal.newBuilder().setScale(1).build()).build();

        System.out.println("student = " + student);

        String json = ProtoJsonUtils.toJson(student);
        System.out.println("json = " + json);
    }

    @Test
    public void test() {
        Book book = new Book();
        book.setName("name");
        book.setId(1000L);
        book.setAmount(BigDecimal.ONE);
        String s = SerializationUtil.serializeToString(book);
        System.out.println("s = " + s);
    }

    @Test
    public void testA() throws InvalidProtocolBufferException {
        BookModels.Book book = BookModels.Book.newBuilder().putSm("1", "1").putSm("2", "2").setName("setName").setId(1000L).setAmount("0").addPages(
                "1").setPayType(BookModels.PaymentType.CASH).setPage(BookModels.Page.newBuilder().setNum(1).setName("page").build()).build();
        byte[] proto = book.toByteArray();
        System.out.println("book = " + book);

        Book b = SerializationUtil.deserializeFromByte(proto, Book.class);
        System.out.println("b = " + b);
        byte[] p = SerializationUtil.serializeToByte(b);

        System.out.println("protobuf = " + p);

        book = BookModels.Book.parseFrom(p);
        System.out.println("book = " + book);

        System.out.println("p1 = " + ByteUtils.toByteString(proto));
        System.out.println("p2 = " + ByteUtils.toByteString(p));
        System.out.println("ByteUtils.equals(proto, p) = " + ByteUtils.equals(proto, p));
    }

    @Test
    public void testB() throws InvalidProtocolBufferException {
        TreeMap<String, String> sm = Maps.newTreeMap();
        sm.put("1", "1");
        Book book = new Book().setName("setName").setId(1000L).setAmount(BigDecimal.ONE).setPages(Lists.newArrayList("1")).setType(2).setPayType(
                PayType.CASH).setPage(new Page().setNum(1).setName("page")).setSm(sm);
        byte[] proto = SerializationUtil.serializeToByte(book);

        System.out.println("protobuf = " + Arrays.toString(proto));

        Book book1 = SerializationUtil.deserializeFromByte(proto, Book.class);
        System.out.println("book1 = " + book1);

        BookModels.Book b = BookModels.Book.parseFrom(proto);

        System.out.println("b = " + b);
        System.out.println("p1 = " + ByteUtils.toByteString(proto));
        System.out.println("p2 = " + ByteUtils.toByteString(b.toByteArray()));
        System.out.println("ByteUtils.equals(proto, p) = " + ByteUtils.equals(proto, b.toByteArray()));

        System.out.println("b = " + SerializationUtil.deserializeFromByte(proto, Book.class));
    }

}
