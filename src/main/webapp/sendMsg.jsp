<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@ page import="com.tujia.myssm.common.utils.JsonUtils"%>
<%@page session="false"%>
<%@page	contentType="text/plain;charset=UTF-8" language="java"%>
<%
    try {
        ApplicationContext ctx = WebApplicationContextUtils
                .getRequiredWebApplicationContext(request.getSession().getServletContext());
        out.println(JsonUtils.toJson(request));
        //out.println(JsonUtils.toJson(request.getParameterMap()));
//        String params = request.getParameter("params");

       // Map<String,Object> paramsMap = JsonUtils.readValue(params,Map.class);
//        out.println("paramsMap:"+JsonUtils.toJson(paramsMap));
    } catch (Exception e) {
        out.println("发生错误：e：" + e.getMessage());
    }
%>