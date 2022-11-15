package com.tujia.myssm.web.filter;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Supplier;
import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.util.Assert;
import org.springframework.util.StreamUtils;
import com.google.common.base.Suppliers;
import lombok.Getter;

/**
 * local servlet request wrapper
 * @author leyuan.lv
 * @create 2022-08-15 16:48
 */
public final class BufferingHttpServletRequestWrapper extends HttpServletRequestWrapper {
    private static final Logger LOG = LoggerFactory.getLogger(BufferingHttpServletRequestWrapper.class);

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
                if (isRequestBodyPost(request)) {
                    return new String(buffer, StandardCharsets.UTF_8);
                }
                return getFromParameters(request);
            } catch (Exception e) {
                //                TMonitor.recordOne("memoizedRequest_error");
                LOG.error("memoizedRequest_error", e);
                return "memoizedRequest_error";
            }
        })::get;
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
            final ServletInputStream inputStream = request.getInputStream();
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

    private String getFromParameters(HttpServletRequest request) {
        StringBuilder paramBuilder = new StringBuilder();

        Map<String, String[]> form = request.getParameterMap();
        for (Iterator<Map.Entry<String, String[]>> entryIterator = form.entrySet().iterator(); entryIterator.hasNext(); ) {
            Map.Entry<String, String[]> entry = entryIterator.next();
            String paramName = entry.getKey();

            String[] paramVals = entry.getValue();
            for (Iterator<String> valueIterator = Arrays.stream(paramVals).iterator(); valueIterator.hasNext(); ) {
                String paramVal = valueIterator.next();
                if (paramVal != null) {
                    paramBuilder.append(paramName);
                    paramBuilder.append('=');
                    paramBuilder.append(paramVal);
                    if (valueIterator.hasNext()) {
                        paramBuilder.append('&');
                    }
                }
            }

            if (entryIterator.hasNext()) {
                paramBuilder.append('&');
            }
        }

        return paramBuilder.toString();
    }

    private boolean isRequestBodyPost(HttpServletRequest request) {
        return request.getContentType() != null && !StringUtils.containsIgnoreCase(request.getContentType(),
                                                                                   ContentType.APPLICATION_FORM_URLENCODED.getMimeType()) &&
                HttpMethod.POST.name().equalsIgnoreCase(request.getMethod());
    }

    public class DelegatingServletInputStream extends ServletInputStream {
        private final InputStream sourceStream;

        public DelegatingServletInputStream(InputStream sourceStream) {
            Assert.notNull(sourceStream, "Source InputStream must not be null");
            this.sourceStream = sourceStream;
        }

        @Override
        public int read() throws IOException {
            return this.sourceStream.read();
        }

        @Override
        public void close() throws IOException {
            super.close();
            this.sourceStream.close();
        }

        @Override
        public boolean isFinished() {
            return false;
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public void setReadListener(ReadListener readListener) {

        }
    }

}