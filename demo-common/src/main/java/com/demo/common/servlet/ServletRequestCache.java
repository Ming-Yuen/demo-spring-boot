package com.demo.common.servlet;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class ServletRequestCache extends HttpServletRequestWrapper {

    private byte[] bodyByte;
    public ServletRequestCache(HttpServletRequest request) throws IOException {
        super(request);
        copyBody(request);
    }

    private void copyBody(HttpServletRequest request) throws IOException {
        try(BufferedReader reader = request.getReader()){
            StringBuffer buffer = new StringBuffer();
            String line;
            while((line = reader.readLine()) != null){
                buffer.append(line);
            }
            bodyByte = buffer.toString().getBytes(StandardCharsets.UTF_8);
        }
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }
    @Override
    public ServletInputStream getInputStream() throws IOException {
        try(ByteArrayInputStream bais = new ByteArrayInputStream(bodyByte)){
            return new ServletInputStream() {
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
                @Override
                public int read() throws IOException {
                    return bais.read();
                }
            };
        }
    }
}
