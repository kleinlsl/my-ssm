package com.tujia.myssm.service.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.stereotype.Service;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.tujia.myssm.api.model.InvoiceInfo;
import com.tujia.myssm.service.InvoiceService;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author: songlinl
 * @create: 2022/01/05 13:15
 */
@Slf4j
@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Override
    public InvoiceInfo parserInvoiceInfo(String filePath) {
        try {
            List<BufferedImage> imageList = extractImage(new File(filePath));
            if (imageList.isEmpty()) {
                log.info("pdf中未解析出图片，返回空");
                return null;
            }
            Result result = getResult(imageList.get(0));
            // 读取到的信息为 ： 01，发票类型，发票代码，发票号码，发票金额，开票日期，校验码，随机产生的摘要信息
            String[] infos = result.getText().split(",");
            if (infos.length != 8) {
                log.info("pdf中的第一张图片解析出的字符串数组长度不为8，返回空。");
                return null;
            }

            //            InvoiceInfo invoiceInfo = new InvoiceInfo();
            //            invoiceInfo.setInvoiceType(infos[1]); //发票类型
            //            invoiceInfo.setInvoiceCode(infos[2]); //发票代码
            //            invoiceInfo.setInvoiceNo(infos[3]); // 发票号码
            //            invoiceInfo.setAmount(new BigDecimal(infos[4])); // 发票金额
            //            invoiceInfo.setInvoiceDate(DateUtils.parseDate(infos[5], "yyyyMMdd")); //开票日期
            //            invoiceInfo.setCheckCode(infos[6]); // 校验码

            return null;
        } catch (Exception e) {
            log.info("解析pdf中的二维码出现异常", e);
            return null;
        }
    }

    private Result getResult(BufferedImage image) throws NotFoundException {
        MultiFormatReader formatReader = new MultiFormatReader();
        //正常解析出来有3张图片，第一张是二维码，其他两张图片是发票上盖的章
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)));
        Map<DecodeHintType, Object> hints = new HashMap<>();
        hints.put(DecodeHintType.CHARACTER_SET, "utf-8");
        Result result = formatReader.decode(binaryBitmap, hints);
        log.info("从电子发票中识别出的信息为：{}", result.getText());
        return result;
    }

    /**
     * 提取电子发票里面的图片
     * @param pdfFile 电子发票文件对象
     * @return pdf中解析出的图片列表
     * @throws Exception
     */
    private List<BufferedImage> extractImage(File pdfFile) throws Exception {
        List<BufferedImage> imageList = new ArrayList<BufferedImage>();

        PDDocument document = PDDocument.load(pdfFile);
        PDPage page = document.getPage(0); //电子发票只有一页
        PDResources resources = page.getResources();

        for (COSName name : resources.getXObjectNames()) {
            if (resources.isImageXObject(name)) {
                PDImageXObject obj = (PDImageXObject) resources.getXObject(name);
                imageList.add(obj.getImage());
            }
        }
        return imageList;
    }
}
