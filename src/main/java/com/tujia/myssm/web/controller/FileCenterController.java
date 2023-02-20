package com.tujia.myssm.web.controller;

import java.io.IOException;
import java.net.URLEncoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.tujia.framework.api.APIResponse;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author: songlinl
 * @create: 2023/01/03 12:04
 */
@Slf4j
@Controller
@RequestMapping("/file/center/")
public class FileCenterController {

    /**
     * 下载Excel模板
     *
     * @param request HttpServletRequest
     * @return
     */
    @GetMapping(value = "/download/excel/template.htm")
    public APIResponse<String> downExcelTemplate(@RequestParam(value = "fileType") Integer fileType, HttpServletRequest request,
                                                 HttpServletResponse response) {
        long start = System.currentTimeMillis();
        String realPath = null;
        try {
            String fileName = URLEncoder.encode("test.xlsx", "utf-8");
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName);
            writerResponseStream(response, null);
            return APIResponse.returnSuccess();
        } catch (Exception e) {
            log.error("请求下载Excel模板异常，fileType:{}, realPath:{}, cost:{}", fileType, realPath, System.currentTimeMillis() - start, e);
            return APIResponse.returnFail(e.getMessage());
        }
    }

    private void writerResponseStream(HttpServletResponse response, byte[] body) throws IOException {
        response.getOutputStream().write(body);
    }

}
