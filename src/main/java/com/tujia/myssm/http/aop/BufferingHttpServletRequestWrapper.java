package com.tujia.myssm.http.aop;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Supplier;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.DelegatingServletInputStream;
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
            String req = new String(buffer, StandardCharsets.UTF_8);
            HttpMethod method = HttpMethod.valueOf(request.getMethod());
            if (method.equals(HttpMethod.GET)) {
                try {
                    req = getInputStreamFromParameters(request);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return req;
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
            return buffer;
        } catch (Exception e) {
            LOG.error("requestURI:" + requestURI + ", request_read_error", e);
        } finally {
            try {
                IOUtils.closeQuietly(request.getInputStream());
            } catch (IOException e) {
                LOG.error("requestURI:" + requestURI + ", request_stream_close_error", e);
            }
        }
        throw new IllegalArgumentException("requestURI:" + requestURI + ", request_body_read_error");
    }

    private String getInputStreamFromParameters(HttpServletRequest request) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        Writer writer = new OutputStreamWriter(bos, StandardCharsets.UTF_8);
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

        return bos.toString();
    }


}