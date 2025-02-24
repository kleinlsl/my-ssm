package com.tujia.myssm.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: songlinl
 * @create: 2023/11/26 18:16
 */
@Slf4j
@Controller
@RequestMapping("/model/center/")
public class ModelAndV {

    private String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>";

    @RequestMapping(value = "/getfullhotelinfo", produces = "application/xml")
    @ResponseBody
    public Object downExcelTemplate(@RequestParam("type") int type, HttpServletRequest request,
                                    HttpServletResponse response) {
        if (type == 1) {
            return xml;
        }
        ModelAndView view = new ModelAndView();
        view.setViewName("/WEB-INF/xml/index.xml");
        return view;
    }

}
