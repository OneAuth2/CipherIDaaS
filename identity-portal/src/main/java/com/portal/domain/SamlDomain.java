package com.portal.domain;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

/**
 * TODO:
 * create by shizhao at 2019-12-12
 *
 * @author: shizhao
 * @since: 2019-12-12 10:15
 */
public class SamlDomain implements Serializable {

    private String samlString;

    private HttpServletRequest request;

    private HttpServletResponse response;

    public SamlDomain(String samlString, HttpServletRequest request, HttpServletResponse response) {
        this.samlString = samlString;
        this.request = request;
        this.response = response;
    }

    public SamlDomain() {
    }

    public String getSamlString() {
        return samlString;
    }

    public void setSamlString(String samlString) {
        this.samlString = samlString;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }
}
