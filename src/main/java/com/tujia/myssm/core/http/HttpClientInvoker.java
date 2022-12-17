package com.tujia.myssm.core.http;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.config.SocketConfig;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.collect.Lists;

/**
 * Created by lizuju on 2017/9/21.
 */
public class HttpClientInvoker {

    private static final Logger logger = LoggerFactory.getLogger(HttpClientInvoker.class);
    private static final ConcurrentHashMap<CloseableHttpClient, PoolingHttpClientConnectionManager> connectionManagers = new ConcurrentHashMap<>();

    static {
        new Thread(() -> {
            while (true) {
                try {
                    for (Map.Entry<CloseableHttpClient, PoolingHttpClientConnectionManager> entry : connectionManagers.entrySet()) {
                        PoolingHttpClientConnectionManager cm = entry.getValue();
                        cm.closeExpiredConnections();
                        cm.closeIdleConnections(60, TimeUnit.SECONDS);
                    }
                    TimeUnit.SECONDS.sleep(10);
                } catch (Throwable e) {
                    logger.error("recycleIdleResource error", e);
                }
            }
        }, "httpclient-recycle-thread").start();
    }

    private CloseableHttpClient httpClient;

    public HttpClientInvoker(int maxTotal, int defaultMaxPerRoute) {
        SocketConfig socketConfig = SocketConfig.custom().setTcpNoDelay(true).setSoTimeout(2000).setSoKeepAlive(true).build();
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(maxTotal);
        connectionManager.setDefaultMaxPerRoute(defaultMaxPerRoute);
        connectionManager.setDefaultSocketConfig(socketConfig);
        httpClient = HttpClients.custom().setConnectionManager(connectionManager).build();
        connectionManagers.put(httpClient, connectionManager);
    }

    private static String formatUrlPath(String path) {
        try {
            return StringUtils.replaceEach(path, new String[] { "/", ".", "-" }, new String[] { "_", "_", "_" });
        } catch (Exception e) {
            logger.error("formatUrlPath error", e);
            return "UNKNOWN";
        }
    }

    public void distroy() {
        connectionManagers.remove(httpClient);
    }

    public String httpGet(String url) throws IOException {
        HttpResponseInfo httpResponse = doRequest(HttpRequestInfoBuilder.newBuilder().setUrl(url).setHttpMethod(HttpMethod.GET).build());
        return httpResponse.getResponseText();
    }

    public String httpGet(String url, int socketTimeout, int connectTimeout) throws IOException {
        HttpResponseInfo httpResponse = doRequest(HttpRequestInfoBuilder.newBuilder().setUrl(url).setHttpMethod(HttpMethod.GET).setSocketTimeout(
                socketTimeout).setConnectTimeout(connectTimeout).build());
        return httpResponse.getResponseText();
    }

    public String httpPost(String url, Map<String, String> paramMap) throws IOException {
        Registry<String> headRegistry = RegistryBuilder.<String>createDefault().register(HttpHeaders.CONTENT_TYPE,
                                                                                         "application/x-www-form-urlencoded").register(
                HttpHeaders.CONNECTION, "close").build();
        HttpResponseInfo httpResponse = doRequest(HttpRequestInfoBuilder.newBuilder().setUrl(url).setHttpMethod(HttpMethod.POST).setParamsRegistry(
                RegistryBuilder.<String>createDefault().registerAll(paramMap).build()).setHeaderRegistry(headRegistry).build());
        return httpResponse.getResponseText();
    }

    public String httpPost(String url, Map<String, String> paramMap, int socketTimeout, int connectTimeout) throws IOException {
        Registry<String> headRegistry = RegistryBuilder.<String>createDefault().register(HttpHeaders.CONTENT_TYPE,
                                                                                         "application/x-www-form-urlencoded").register(
                HttpHeaders.CONNECTION, "close").build();
        HttpResponseInfo httpResponse = doRequest(HttpRequestInfoBuilder.newBuilder()
                                                                        .setUrl(url)
                                                                        .setHttpMethod(HttpMethod.POST)
                                                                        .setParamsRegistry(
                                                                                RegistryBuilder.<String>createDefault().registerAll(paramMap).build())
                                                                        .setHeaderRegistry(headRegistry)
                                                                        .setSocketTimeout(socketTimeout)
                                                                        .setConnectTimeout(connectTimeout)
                                                                        .build());
        return httpResponse.getResponseText();
    }

    public String httpPostAsJsonBody(String url, String postBody) throws IOException {
        Registry<String> headRegistry = RegistryBuilder.<String>createDefault().register(HttpHeaders.CONTENT_TYPE, "application/json").build();
        HttpResponseInfo httpResponse = doRequest(HttpRequestInfoBuilder.newBuilder()
                                                                        .setUrl(url)
                                                                        .setHttpMethod(HttpMethod.POST)
                                                                        .setPostBody(postBody)
                                                                        .setHeaderRegistry(headRegistry)
                                                                        .build());
        return httpResponse.getResponseText();
    }

    public String httpPostAsJsonBody(String url, String postBody, int socketTimeout, int connectTimeout) throws IOException {
        Registry<String> headRegistry = RegistryBuilder.<String>createDefault().register(HttpHeaders.CONTENT_TYPE, "application/json").build();
        HttpResponseInfo httpResponse = doRequest(HttpRequestInfoBuilder.newBuilder()
                                                                        .setUrl(url)
                                                                        .setHttpMethod(HttpMethod.POST)
                                                                        .setPostBody(postBody)
                                                                        .setHeaderRegistry(headRegistry)
                                                                        .setSocketTimeout(socketTimeout)
                                                                        .setConnectTimeout(connectTimeout)
                                                                        .build());
        return httpResponse.getResponseText();
    }

    public HttpResponseInfo doRequest(HttpRequestInfo httpRequest) throws IOException {
        RequestConfig.Builder configBuilder = RequestConfig.custom().setSocketTimeout(httpRequest.getSocketTimeout()).setConnectTimeout(
                httpRequest.getConnectTimeout()).setConnectionRequestTimeout(1000).setCookieSpec(CookieSpecs.IGNORE_COOKIES);
        HttpProxyInfo proxyInfo = httpRequest.getProxyInfo();
        if (proxyInfo != null) {
            HttpHost httpHost = new HttpHost(proxyInfo.getHostName(), proxyInfo.getPort());
            configBuilder.setProxy(httpHost);
        }
        RequestConfig requestConfig = configBuilder.build();
        CloseableHttpResponse _httpResponse = null;
        HttpResponseInfoBuilder httpResponseBuilder = HttpResponseInfoBuilder.newBuilder();
        try {
            if (httpRequest.getHttpMethod() == HttpMethod.POST) {
                _httpResponse = doPost(httpRequest, requestConfig);
            } else if (httpRequest.getHttpMethod() == HttpMethod.GET) {
                _httpResponse = doGet(httpRequest, requestConfig);
            } else {
                throw new UnsupportedOperationException(String.format("can not support httpMethod[%s]", httpRequest.getHttpMethod()));
            }
            int statuscode = _httpResponse.getStatusLine().getStatusCode();
            HeaderIterator respHeaderIterator = _httpResponse.headerIterator();
            RegistryBuilder<String> respRegistryBuilder = RegistryBuilder.createRepeatable();
            while (respHeaderIterator.hasNext()) {
                Header header = (Header) respHeaderIterator.next();
                respRegistryBuilder.register(header.getName(), header.getValue());
            }
            httpResponseBuilder.setStatuscode(statuscode);
            httpResponseBuilder.setHeaderRegistry(respRegistryBuilder.build());
            HttpEntity entity = _httpResponse.getEntity();
            httpResponseBuilder.setResponseText(EntityUtils.toString(entity, httpRequest.getResponseEncoding()));
            EntityUtils.consume(entity);
        } catch (Exception e) {
            if (_httpResponse != null) {
                _httpResponse.close();
            }
            throw e;
        }
        try {
            if (_httpResponse != null) {
                _httpResponse.close();
            }
        } catch (Exception e) {
            logger.warn("dup close");
        }

        return httpResponseBuilder.build();
    }

    private CloseableHttpResponse doGet(HttpRequestInfo httpRequest, RequestConfig requestConfig) throws IOException {
        HttpGet httpGet = new HttpGet(httpRequest.getUrl());
        initHttpRequestBase(httpGet, requestConfig, httpRequest.getHeaderRegistry());
        return httpClient.execute(httpGet);
    }

    private CloseableHttpResponse doPost(HttpRequestInfo httpRequest, RequestConfig requestConfig) throws IOException {
        HttpPost httpPost = new HttpPost(httpRequest.getUrl());
        initHttpRequestBase(httpPost, requestConfig, httpRequest.getHeaderRegistry());
        if (StringUtils.isNotEmpty(httpRequest.getPostBody())) {
            httpPost.setEntity(new StringEntity(httpRequest.getPostBody(), httpRequest.getRequestEncoding()));
        } else {
            Registry<String> paramsRegistry = httpRequest.getParamsRegistry();
            if (paramsRegistry != null) {
                List<NameValuePair> formparams = Lists.newArrayListWithExpectedSize(paramsRegistry.size());
                Iterator<Map.Entry<String, String>> paramsIterator = paramsRegistry.iterator();
                while (paramsIterator.hasNext()) {
                    Map.Entry<String, String> entry = paramsIterator.next();
                    formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
                httpPost.setEntity(new UrlEncodedFormEntity(formparams, httpRequest.getRequestEncoding()));
            }
        }

        long st = System.currentTimeMillis();
        try {
            return httpClient.execute(httpPost);
        } finally {

        }
    }

    private void initHttpRequestBase(HttpRequestBase httpRequestBase, RequestConfig requestConfig, Registry<String> headerRegistry) {
        httpRequestBase.setConfig(requestConfig);
        if (headerRegistry != null) {
            Iterator<Map.Entry<String, String>> headerIterator = headerRegistry.iterator();
            while (headerIterator.hasNext()) {
                Map.Entry<String, String> entry = headerIterator.next();
                httpRequestBase.addHeader(entry.getKey(), entry.getValue());
            }
        }
    }
}
