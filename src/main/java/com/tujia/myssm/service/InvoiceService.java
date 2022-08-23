package com.tujia.myssm.service;

import com.tujia.myssm.api.model.InvoiceInfo;

/**
 *
 * @author: songlinl
 * @create: 2022/01/05 13:12
 */
public interface InvoiceService {

    /**
     * ss
     * @param filePath
     * @return
     */
    InvoiceInfo parserInvoiceInfo(String filePath);
}
