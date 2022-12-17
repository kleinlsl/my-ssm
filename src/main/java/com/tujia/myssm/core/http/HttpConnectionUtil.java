package com.tujia.myssm.core.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.Map;
import org.apache.http.HttpHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.collect.Maps;

/**
 * Created by lizuju on 2017/10/24.
 */
public class HttpConnectionUtil {

    public final static String ENC_UTF8 = "UTF-8";
    private static final Logger logger = LoggerFactory.getLogger(HttpConnectionUtil.class);
    private static final int DEFAULT_CONNECTTIMEOUT = 10000;
    private static final int DEFAULT_READTIMEOUT = 10000;
    private static final String METHOD_POST = "POST";
    private static final String METHOD_GET = "GET";

    /**
     * http Get请求
     * @param urlString 请求url
     * @return
     * @throws IOException
     */
    public static String httpGet(String urlString) throws IOException {
        return httpGet(urlString, DEFAULT_CONNECTTIMEOUT, DEFAULT_READTIMEOUT);
    }

    /**
     *http Get请求
     * @param urlString 请求url
     * @param connectTimeout 连接超时
     * @param readTimeout 读取超时
     * @return
     * @throws IOException
     */
    public static String httpGet(String urlString, int connectTimeout, int readTimeout) throws IOException {
        return httpRequest(urlString, METHOD_GET, connectTimeout, readTimeout, null, null, ENC_UTF8, ENC_UTF8);
    }

    /**
     * http Post请求
     * @param urlString 请求url
     * @param reqParams 请求参数
     * @return
     * @throws IOException
     */
    public static String httpPost(String urlString, Map<String, String> reqParams) throws IOException {
        return httpPost(urlString, DEFAULT_CONNECTTIMEOUT, DEFAULT_READTIMEOUT, reqParams);
    }

    /**
     * http Post请求
     * @param urlString 请求url
     * @param connectTimeout 连接超时
     * @param readTimeout 读取超时
     * @param reqParams 请求参数
     * @return
     * @throws IOException
     */
    public static String httpPost(String urlString, int connectTimeout, int readTimeout, Map<String, String> reqParams) throws IOException {
        Map<String, String> headParams = Maps.newHashMapWithExpectedSize(1);
        headParams.put(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");
        return httpRequest(urlString, METHOD_POST, connectTimeout, readTimeout, reqParams, headParams, ENC_UTF8, ENC_UTF8);
    }

    /**
     * http请求
     *
     * @param urlString    请求url
     * @param method       GET/POST
     * @param reqParams    请求参数
     * @param headParams   header setting
     * @param sendEncoding 发送字符集
     * @param receiveEncoding 接收字符集
     * @return
     */
    public static String httpRequest(String urlString, String method, Map<String, String> reqParams, Map<String, String> headParams,
                                     String sendEncoding, String receiveEncoding) throws IOException {
        return httpRequest(urlString, method, DEFAULT_CONNECTTIMEOUT, DEFAULT_READTIMEOUT, reqParams, headParams, sendEncoding, receiveEncoding);
    }

    /**
     * http请求
     *
     * @param urlString      请求url
     * @param method         GET/POST
     * @param connectTimeout 连接超时
     * @param readTimeout    读取超时
     * @param reqParams      请求参数
     * @param headParams     header setting
     * @param sendEncoding   发送字符集
     * @param receiveEncoding 接收字符集
     * @return
     */
    public static String httpRequest(String urlString, String method, int connectTimeout, int readTimeout, Map<String, String> reqParams,
                                     Map<String, String> headParams, String sendEncoding, String receiveEncoding) throws IOException {
        return httpRequest(urlString, method, connectTimeout, readTimeout, reqParams, headParams, sendEncoding, receiveEncoding, null, 0);
    }

    public static String sendAndReceive(String urlString, String method, int connectTimeout, int readTimeout, Map<String, String> reqParams,
                                        Map<String, String> headParams, String sendEncoding, String receiveEncoding, byte[] body, String proxyIp,
                                        int proxyPort) throws IOException {
        InputStream is = null;
        try {
            String param = getParams(reqParams);
            if (body == null) {
                if (method.equalsIgnoreCase(METHOD_GET) && param.length() > 0) {
                    urlString += "?" + param;
                }
            }
            URL url = new URL(urlString);
            HttpURLConnection conn = null;
            if (proxyIp != null) {
                Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyIp, proxyPort));
                conn = (HttpURLConnection) url.openConnection(proxy);
            } else {
                conn = (HttpURLConnection) url.openConnection();
            }
            conn.setRequestMethod(method);
            conn.setConnectTimeout(connectTimeout);
            conn.setReadTimeout(readTimeout);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            setHeader(conn, headParams);
            conn.connect();
            if (method.equalsIgnoreCase(METHOD_POST) && param.length() > 0) {
                conn.getOutputStream().write(param.getBytes(sendEncoding));
                conn.getOutputStream().flush();
                conn.getOutputStream().close();
            } else if (body != null) {
                conn.getOutputStream().write(body);
                conn.getOutputStream().flush();
                conn.getOutputStream().close();
            }
            is = conn.getInputStream();
            int iLen = -1;
            byte[] bytes = new byte[4096];
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((iLen = is.read(bytes)) >= 0) {
                baos.write(bytes, 0, iLen);
            }
            return new String(baos.toByteArray(), receiveEncoding);
        } finally {
            closeQuietly(urlString, is);
        }
    }

    /**
     * http请求
     *
     * @param urlString      请求url
     * @param method         GET/POST
     * @param connectTimeout 连接超时
     * @param readTimeout    读取超时
     * @param reqParams      请求参数
     * @param headParams     header setting
     * @param sendEncoding   发送字符集
     * @param receiveEncoding 接收字符集
     * @param proxyIp        代理ip，不用代理时置为null
     * @param proxyPort        代理端口，不用代理时置为0
     * @return
     */
    public static String httpRequest(String urlString, String method, int connectTimeout, int readTimeout, Map<String, String> reqParams,
                                     Map<String, String> headParams, String sendEncoding, String receiveEncoding, String proxyIp, int proxyPort)
            throws IOException {
        return sendAndReceive(urlString, method, connectTimeout, readTimeout, reqParams, headParams, sendEncoding, receiveEncoding, null, proxyIp,
                              proxyPort);
    }

    /**
     * http post请求，请求体方式发送数据
     * @param urlString 请求url
     * @param connectTimeout 连接超时
     * @param readTimeout 读取超时
     * @param headParams header setting
     * @param body 请求体数据
     * @param sendEncoding 发送字符集
     * @param receiveEncoding 接收字符集
     * @param proxyIp 代理ip，不用代理时置为null
     * @param proxyPort 代理端口，不用代理时置为0
     * @return
     * @throws IOException
     */
    public static String httpPostAsBody(String urlString, int connectTimeout, int readTimeout, Map<String, String> headParams, String body,
                                        String sendEncoding, String receiveEncoding, String proxyIp, int proxyPort) throws IOException {
        return sendAndReceive(urlString, METHOD_POST, connectTimeout, readTimeout, null, headParams, ENC_UTF8, ENC_UTF8, body.getBytes(sendEncoding),
                              proxyIp, proxyPort);
    }

    /**
     * http post请求，请求体方式发送数据（JSON格式）
     * @param urlString 请求url
     * @param body 请求体数据
     * @return
     */
    public static String httpPostAsJsonBody(String urlString, String body) throws IOException {
        return httpPostAsJsonBody(urlString, body, DEFAULT_CONNECTTIMEOUT, DEFAULT_READTIMEOUT);
    }

    /**
     * http post请求，请求体方式发送数据（JSON格式）
     * @param urlString 请求url
     * @param body 请求体数据
     * @param connectTimeout 连接超时
     * @param readTimeout 读取超时
     * @return
     */
    public static String httpPostAsJsonBody(String urlString, String body, int connectTimeout, int readTimeout) throws IOException {
        Map<String, String> headParams = Maps.newHashMapWithExpectedSize(1);
        headParams.put(HttpHeaders.CONTENT_TYPE, "application/json");
        return httpPostAsBody(urlString, connectTimeout, readTimeout, headParams, body, ENC_UTF8, ENC_UTF8, null, 0);
    }

    private static void setHeader(HttpURLConnection conn, Map<String, String> headParams) {
        if (headParams == null) {
            return;
        }
        for (String key : headParams.keySet()) {
            conn.addRequestProperty(key, headParams.get(key));
        }
    }

    private static String getParams(Map<String, String> reqParams) {
        StringBuffer param = new StringBuffer();
        if (reqParams != null) {
            int i = 0;
            for (Map.Entry<String, String> entry : reqParams.entrySet()) {
                if (i > 0) {
                    param.append("&");
                }
                param.append(entry.getKey()).append("=").append(entry.getValue());
                i++;
            }
        }
        return param.toString();
    }

    private static void closeQuietly(String urlString, InputStream input) {
        try {
            if (input != null) {
                input.close();
            }
        } catch (IOException ioe) {
            logger.error("close stream error,url={}", urlString, ioe);
        }
    }

}
