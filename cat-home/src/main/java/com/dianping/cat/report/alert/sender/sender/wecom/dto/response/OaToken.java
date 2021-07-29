package com.dianping.cat.report.alert.sender.sender.wecom.dto.response;


import java.io.Serializable;

public class OaToken implements Serializable {
    private static final long serialVersionUID = 1L;
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
