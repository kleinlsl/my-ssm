package com.tujia.myssm.http.aop;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.DelegatingServletInputStream;
import org.springframework.util.Assert;
import org.springframework.util.StreamUtils;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.Lists;
import lombok.Getter;
import static org.apache.http.entity.ContentType.APPLICATION_ATOM_XML;
import static org.apache.http.entity.ContentType.APPLICATION_JSON;
import static org.apache.http.entity.ContentType.APPLICATION_SVG_XML;
import static org.apache.http.entity.ContentType.APPLICATION_XHTML_XML;
import static org.apache.http.entity.ContentType.APPLICATION_XML;
import static org.apache.http.entity.ContentType.TEXT_HTML;
import static org.apache.http.entity.ContentType.TEXT_PLAIN;
import static org.apache.http.entity.ContentType.TEXT_XML;

/**
 * local servlet request wrapper
 * @author leyuan.lv
 * @create 2022-08-15 16:48
 */
public final class BufferingHttpServletRequestWrapper extends HttpServletRequestWrapper {
    public static final String UTF_8 = "UTF-8";
    private static final Logger LOG = LoggerFactory.getLogger(BufferingHttpServletRequestWrapper.class);
    private static final List<ContentType> CONTENT_TYPES = Lists.newArrayList(APPLICATION_ATOM_XML, APPLICATION_JSON, APPLICATION_SVG_XML,
                                                                              APPLICATION_XHTML_XML, APPLICATION_XML, TEXT_HTML, TEXT_PLAIN,
                                                                              TEXT_XML);

    /**
     * request buffer
     */
    private final byte[] buffer;

    @Getter
    private final Supplier<String> memoizedRequest;

    public BufferingHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
        buffer = read(request);
        Assert.notNull(buffer);
        memoizedRequest = Suppliers.memoize(() -> {
            try {
                return new String(buffer, UTF_8);
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        ByteArrayInputStream delegate = new ByteArrayInputStream(buffer);
        return new DelegatingServletInputStream(delegate);
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(new ByteArrayInputStream(buffer), getCharacterEncoding()));
    }

    private byte[] read(HttpServletRequest request) {
        String requestURI = StringUtils.removeEnd(request.getRequestURI(), "/");
        final byte[] buffer;
        try {
            final ServletInputStream inputStream;
            switch (HttpMethod.valueOf(request.getMethod())) {
                case POST:
                    inputStream = isRequestBodyPost(request) ? request.getInputStream() : getInputStreamFromParameters(request);
                    break;

                default:
                    inputStream = getInputStreamFromParameters(request);
            }
            buffer = StreamUtils.copyToByteArray(inputStream);
            //            TMonitor.recordOne("request_read_success");
            return buffer;
        } catch (Exception e) {
            //            TMonitor.recordOne("request_read_error");
            LOG.error("requestURI:" + requestURI + ", request_read_error", e);
        } finally {
            try {
                IOUtils.closeQuietly(request.getInputStream());
            } catch (IOException e) {
                //                TMonitor.recordOne("request_stream_close_error");
                LOG.error("requestURI:" + requestURI + ", request_stream_close_error", e);
            }
        }
        throw new IllegalArgumentException("requestURI:" + requestURI + ", request_body_read_error");
    }

    private ServletInputStream getInputStreamFromParameters(HttpServletRequest request) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        Writer writer = new OutputStreamWriter(bos, UTF_8);
        try {
            Map<String, String[]> form = request.getParameterMap();
            for (Iterator<Map.Entry<String, String[]>> entryIterator = form.entrySet().iterator(); entryIterator.hasNext(); ) {
                Map.Entry<String, String[]> entry = entryIterator.next();
                String paramName = entry.getKey();

                String[] paramVals = entry.getValue();
                for (Iterator<String> valueIterator = Arrays.stream(paramVals).iterator(); valueIterator.hasNext(); ) {
                    String paramVal = valueIterator.next();
                    if (paramVal != null) {
                        writer.write(paramName);
                        writer.write('=');
                        writer.write(paramVal);
                        if (valueIterator.hasNext()) {
                            writer.write('&');
                        }
                    }
                }

                if (entryIterator.hasNext()) {
                    writer.append('&');
                }
            }

        } finally {
            try {
                writer.flush();
                writer.close();
            } catch (IOException e) {
                // ignore
            }
        }

        return new DelegatingServletInputStream(new ByteArrayInputStream(bos.toByteArray()));
    }

    private boolean isRequestBodyPost(HttpServletRequest request) {
        return request.getContentType() != null && !StringUtils.containsIgnoreCase(request.getContentType(),
                                                                                   ContentType.APPLICATION_FORM_URLENCODED.getMimeType()) &&
                HttpMethod.POST.name().equalsIgnoreCase(request.getMethod());
    }

}