package com.dianping.cat.report.alert.sender.sender.wecom.dto.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OaUserInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    @SerializedName("loginId")
    private String loginId;
    @SerializedName("nickName")
    private String nickName;
    @SerializedName("status")
    private Integer status;
    @SerializedName("workcode")
    private String workCode;

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getWorkCode() {
        return workCode;
    }

    public void setWorkCode(String workCode) {
        this.workCode = workCode;
    }
}
