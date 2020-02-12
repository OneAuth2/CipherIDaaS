package com.portal.utils.httpclient;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * TODO:
 * create by liuying at 2019/10/14
 *
 * @author liuying
 * @since 2019/10/14 14:46
 */
public class URLBuilder {

    private String baseUrl;
    private StringBuilder builder;
    private String encoding;
    private boolean initProcessed;

    public URLBuilder(String baseUrl) {
        this(baseUrl, "UTF-8");
    }

    public URLBuilder(String baseUrl, String encoding) {
        this.initProcessed = false;
        this.baseUrl = baseUrl;
        this.encoding = encoding;
        this.builder = new StringBuilder(baseUrl);
    }

    public URLBuilder addParam(String name, int value) {
        return this.addParam(name, String.valueOf(value));
    }

    public URLBuilder addParam(String name, long value) {
        return this.addParam(name, String.valueOf(value));
    }

    public URLBuilder addParam(String name, boolean value) {
        return this.addParam(name, String.valueOf(value));
    }

    public URLBuilder addParam(String name, String value) {
        if (value == null) {
            return this;
        } else {
            if (!this.initProcessed) {
                if (this.baseUrl.contains("?")) {
                    this.builder.append("&");
                } else {
                    this.builder.append("?");
                }

                this.initProcessed = true;
            } else {
                this.builder.append("&");
            }

            try {
                this.builder.append(name).append("=").append(URLEncoder.encode(value, this.encoding));
                return this;
            } catch (Exception var4) {
                try {
                    throw new Exception(var4);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public String toString() {
        return this.builder.toString();
    }
}
