package com.dianping.cat.report.alert.sender.sender.wecom.dto.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OaResponse<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    @SerializedName("code")
    private Integer code;

    @SerializedName("msg")
    private String msg;

    @SerializedName("data")
    private T data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
