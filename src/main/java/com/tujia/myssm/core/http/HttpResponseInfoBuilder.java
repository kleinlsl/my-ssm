package com.tujia.myssm.core.http;

/**
 * Created by lizuju on 2017/9/21.
 */
class HttpResponseInfoBuilder {

    private String responseText;
    private Registry<String> headerRegistry;
    private int statuscode;

    private HttpResponseInfoBuilder() {}

    static HttpResponseInfoBuilder newBuilder() {
        return new HttpResponseInfoBuilder();
    }

    HttpResponseInfo build() {
        HttpResponseInfo httpResponse = new HttpResponseInfo();
        httpResponse.setStatuscode(statuscode);
        httpResponse.setResponseText(responseText);
        httpResponse.setHeaderRegistry(headerRegistry);
        return httpResponse;
    }

    public String getResponseText() {
        return responseText;
    }

    public HttpResponseInfoBuilder setResponseText(String responseText) {
        this.responseText = responseText;
        return this;
    }

    public Registry<String> getHeaderRegistry() {
        return headerRegistry;
    }

    public HttpResponseInfoBuilder setHeaderRegistry(Registry<String> headerRegistry) {
        this.headerRegistry = headerRegistry;
        return this;
    }

    public int getStatuscode() {
        return statuscode;
    }

    public HttpResponseInfoBuilder setStatuscode(int statuscode) {
        this.statuscode = statuscode;
        return this;
    }
}
