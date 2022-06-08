package com.tujia.myssm.http;

/**
 * Created by lizuju on 2017/9/21.
 */
public class HttpRequestInfo {

    private String url;
    // post请求体
    private String postBody;
    // 请求参数
    private Registry<String> paramsRegistry;
    // 请求header
    private Registry<String> headerRegistry;
    private HttpMethod httpMethod;
    // 代理信息
    private HttpProxyInfo proxyInfo;
    // 请求获取数据的超时时间，单位毫秒
    private int socketTimeout = 5000;
    // 连接超时时间，毫秒单位
    private int connectTimeout = 5000;
    private String requestEncoding = "UTF-8";
    private String responseEncoding = "UTF-8";

    public int getSocketTimeout() {
        return socketTimeout;
    }

    void setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public String getRequestEncoding() {
        return requestEncoding;
    }

    void setRequestEncoding(String requestEncoding) {
        this.requestEncoding = requestEncoding;
    }

    public String getResponseEncoding() {
        return responseEncoding;
    }

    void setResponseEncoding(String responseEncoding) {
        this.responseEncoding = responseEncoding;
    }

    public String getUrl() {
        return url;
    }

    void setUrl(String url) {
        this.url = url;
    }

    public String getPostBody() {
        return postBody;
    }

    void setPostBody(String postBody) {
        this.postBody = postBody;
    }

    public Registry<String> getParamsRegistry() {
        return paramsRegistry;
    }

    void setParamsRegistry(Registry<String> paramsRegistry) {
        this.paramsRegistry = paramsRegistry;
    }

    public Registry<String> getHeaderRegistry() {
        return headerRegistry;
    }

    void setHeaderRegistry(Registry<String> headerRegistry) {
        this.headerRegistry = headerRegistry;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    void setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }

    public HttpProxyInfo getProxyInfo() {
        return proxyInfo;
    }

    void setProxyInfo(HttpProxyInfo proxyInfo) {
        this.proxyInfo = proxyInfo;
    }
}
