package com.tujia.myssm.service;

import org.junit.Test;
import com.tujia.myssm.service.impl.InvoiceServiceImpl;

/**
 *
 * @author: songlinl
 * @create: 2022/01/05 13:21
 */
public class InvoiceServiceTest {

    private InvoiceService invoiceService = new InvoiceServiceImpl();

    @Test
    public void parserInvoiceInfo() {
        invoiceService.parserInvoiceInfo("C:\\Users\\songlinl\\OneDrive\\报销\\待报销\\011002000911_63446923-68.pdf");
    }
}