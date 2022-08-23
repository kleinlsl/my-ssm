package com.tujia.myssm.http;

/**
 * Created by lizuju on 2017/9/21.
 */
public class HttpResponseInfo {

    private String responseText;
    private Registry<String> headerRegistry;
    private int statuscode;

    public String getResponseText() {
        return responseText;
    }

    void setResponseText(String responseText) {
        this.responseText = responseText;
    }

    public Registry<String> getHeaderRegistry() {
        return headerRegistry;
    }

    void setHeaderRegistry(Registry<String> headerRegistry) {
        this.headerRegistry = headerRegistry;
    }

    public int getStatuscode() {
        return statuscode;
    }

    void setStatuscode(int statuscode) {
        this.statuscode = statuscode;
    }
}
