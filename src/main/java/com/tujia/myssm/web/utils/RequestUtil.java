package com.tujia.myssm.web.utils;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.DispatcherType;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.WebUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: songlinl
 * @create: 2021/09/02 15:27
 */
@Slf4j
public class RequestUtil {

    public static HttpServletRequest httpServletRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public static String serverDomain() {
        try {
            String urlHost = httpServletRequest().getServerName().toLowerCase();
            log.info("[RequestUtil.serverDomain] Request serverDomain:{}", urlHost);
            return urlHost;
        } catch (Exception e) {
            return "";
        }
    }

    public static String getRequestUri(HttpServletRequest request) {
        String uri = (String) request.getAttribute(WebUtils.INCLUDE_REQUEST_URI_ATTRIBUTE);
        if (uri == null) {
            uri = request.getRequestURI();
        }
        return uri;
    }

    public static String logRequest(HttpServletRequest request) {
        if (request == null) {
            return "";
        }
        String params = request.getParameterMap().entrySet().stream().map(
                entry -> entry.getKey() + ":" + Arrays.toString(entry.getValue())).collect(Collectors.joining(", "));
        String queryString = request.getQueryString();
        String queryClause = (StringUtils.hasLength(queryString) ? "?" + queryString : "");
        String dispatchType = (!request.getDispatcherType().equals(DispatcherType.REQUEST) ?
                "\"" + request.getDispatcherType().name() + "\" dispatch for " :
                "");
        String message = (dispatchType + request.getMethod() + " \"" + getRequestUri(request) + queryClause +
                "\", parameters={" + params + "}");

        List<String> values = Collections.list(request.getHeaderNames());
        String headers = values.stream().map(name -> name + ":" + Collections.list(request.getHeaders(name))).collect(
                Collectors.joining(", "));
        return message + ", headers={" + headers + "} ";
    }

    public static String getRequestInfo(HttpServletRequest req) throws ServletException, IOException {
        StringBuffer sb = new StringBuffer();
        sb.append("method:" + req.getMethod() + ",\r\n");
        sb.append("URI:" + req.getRequestURI() + "\r\n");
        sb.append("QueryString:" + req.getQueryString() + "\r\n");
        sb.append("ContentType:" + req.getContentType() + "\r\n");
        sb.append("ContentLength:" + req.getContentLength() + "\r\n");
        sb.append("Protocol:" + req.getProtocol() + "\r\n");
        sb.append("RemoteAddr:" + req.getRemoteAddr() + "\r\n");
        sb.append("RemoteHost:" + req.getRemoteHost() + "\r\n");

        //        BufferedReader in = null;
        //        try {
        //            // 读取输出内容
        //            in = new BufferedReader(new InputStreamReader(req.getInputStream(), "UTF-8"));
        //            String line;
        //            sb.append("Data:\r\n");
        //            while ((line = in.readLine()) != null) {
        //                sb.append(line);
        //            }
        //
        //            sb.append("header :\r\n");
        //            Enumeration<String> enumeration = req.getHeaderNames();
        //            while (enumeration.hasMoreElements()) {
        //                Object obj = enumeration.nextElement();
        //                sb.append(obj.toString() + ": " + req.getHeader(obj.toString()) + "; ");
        //                sb.append("\r\n");
        //            }
        //
        //        } catch (Exception ex) {
        //            throw ex;
        //        } finally {
        //            try {
        //                if (in != null) {
        //                    in.close();
        //                }
        //            } catch (Exception ex2) {
        //                throw ex2;
        //            }
        //        }
        return sb.toString();
    }

    public static String getLocalAddr() {
        return NetworkInterfaceManager.INSTANCE.getLocalHostAddress();
    }

    public synchronized static String getHexLocalAddr() {
        String ip = getLocalAddr();
        String[] addr = ip.split("\\.");
        StringBuilder result = new StringBuilder();
        for (String s : addr) {
            String hex = Integer.toHexString(Integer.parseInt(s));
            if (hex.length() == 1) {
                result.append("0");
            }
            result.append(hex);
        }
        return result.toString().toUpperCase();
    }

    public static String convert(String hexIp) {
        if (StringUtils.isEmpty(hexIp) || hexIp.length() != 8) {
            return "";
        }
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            String subIp = hexIp.substring(i << 1, (i + 1) << 1);
            if (i != 0) {
                result.append(".");
            }
            result.append(Integer.parseInt(subIp, 16));
        }
        return result.toString();
    }

    public static void main(String[] args) {
        String realIp = getLocalAddr();
        String hexIp = getHexLocalAddr();
        String ip = convert(hexIp);

        System.out.println("realIp = " + realIp);
        System.out.println("hexIp = " + hexIp);
        System.out.println("ip = " + ip);
        System.out.println("convert(\"0A5A382A\") = " + convert("0A5A382A"));
    }

}
