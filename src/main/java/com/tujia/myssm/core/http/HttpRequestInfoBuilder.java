package com.tujia.myssm.core.http;

import org.apache.commons.lang3.StringUtils;
import com.google.common.base.Preconditions;

/**
 * Created by lizuju on 2017/9/21.
 */
public class HttpRequestInfoBuilder {

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

    private HttpRequestInfoBuilder() {}

    public static HttpRequestInfoBuilder newBuilder() {
        return new HttpRequestInfoBuilder();
    }

    public HttpRequestInfo build() {
        Preconditions.checkArgument(StringUtils.isNotEmpty(url), "url can not be empty");
        Preconditions.checkArgument(httpMethod != null, "httpMethod must be assigned");
        HttpRequestInfo httpRequest = new HttpRequestInfo();
        httpRequest.setUrl(url);
        httpRequest.setPostBody(postBody);
        httpRequest.setParamsRegistry(paramsRegistry);
        httpRequest.setHeaderRegistry(headerRegistry);
        httpRequest.setHttpMethod(httpMethod);
        httpRequest.setProxyInfo(proxyInfo);
        httpRequest.setSocketTimeout(socketTimeout);
        httpRequest.setConnectTimeout(connectTimeout);
        httpRequest.setRequestEncoding(requestEncoding);
        httpRequest.setResponseEncoding(responseEncoding);
        return httpRequest;
    }

    public int getSocketTimeout() {
        return socketTimeout;
    }

    public HttpRequestInfoBuilder setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
        return this;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public HttpRequestInfoBuilder setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
        return this;
    }

    public String getRequestEncoding() {
        return requestEncoding;
    }

    public HttpRequestInfoBuilder setRequestEncoding(String requestEncoding) {
        this.requestEncoding = requestEncoding;
        return this;
    }

    public String getResponseEncoding() {
        return responseEncoding;
    }

    public HttpRequestInfoBuilder setResponseEncoding(String responseEncoding) {
        this.responseEncoding = responseEncoding;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public HttpRequestInfoBuilder setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getPostBody() {
        return postBody;
    }

    public HttpRequestInfoBuilder setPostBody(String postBody) {
        this.postBody = postBody;
        return this;
    }

    public Registry<String> getParamsRegistry() {
        return paramsRegistry;
    }

    public HttpRequestInfoBuilder setParamsRegistry(Registry<String> paramsRegistry) {
        this.paramsRegistry = paramsRegistry;
        return this;
    }

    public Registry<String> getHeaderRegistry() {
        return headerRegistry;
    }

    public HttpRequestInfoBuilder setHeaderRegistry(Registry<String> headerRegistry) {
        this.headerRegistry = headerRegistry;
        return this;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public HttpRequestInfoBuilder setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
        return this;
    }

    public HttpProxyInfo getProxyInfo() {
        return proxyInfo;
    }

    public HttpRequestInfoBuilder setProxyInfo(HttpProxyInfo proxyInfo) {
        this.proxyInfo = proxyInfo;
        return this;
    }
}
