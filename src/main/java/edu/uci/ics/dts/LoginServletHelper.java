package edu.uci.ics.dts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginServletHelper {

    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final String forwardPage;

    public LoginServletHelper(HttpServletRequest request, HttpServletResponse response, String forwardPage) {
        this.request = request;
        this.response = response;
        this.forwardPage = forwardPage;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public String getForwardPage() {
        return forwardPage;
    }

}
