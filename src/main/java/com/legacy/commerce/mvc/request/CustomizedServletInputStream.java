package com.legacy.commerce.mvc.request;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import java.io.IOException;
import java.io.InputStream;

public class CustomizedServletInputStream extends ServletInputStream {

    InputStream inputStream;

    public CustomizedServletInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override public int read() throws IOException {
        return inputStream.read();
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
