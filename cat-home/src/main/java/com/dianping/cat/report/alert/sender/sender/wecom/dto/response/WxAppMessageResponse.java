/*
 * Copyright (c) 2020. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.dianping.cat.report.alert.sender.sender.wecom.dto.response;

import com.google.gson.annotations.SerializedName;

public class WxAppMessageResponse extends WxAppResponse {

    private static final long serialVersionUID = -4906854517395052764L;

    @SerializedName("invaliduser")
    private String invalidUser;

    @SerializedName("invalidparty")
    private String invalidParty;

    @SerializedName("invalidtag")
    private String invalidTag;

    public String getInvalidUser() {
        return invalidUser;
    }

    public void setInvalidUser(String invalidUser) {
        this.invalidUser = invalidUser;
    }

    public String getInvalidParty() {
        return invalidParty;
    }

    public void setInvalidParty(String invalidParty) {
        this.invalidParty = invalidParty;
    }

    public String getInvalidTag() {
        return invalidTag;
    }

    public void setInvalidTag(String invalidTag) {
        this.invalidTag = invalidTag;
    }

    @Override
    public String toString() {
        return "WxAppMessageResponse{" +
                "invalidUser='" + invalidUser + '\'' +
                ", invalidParty='" + invalidParty + '\'' +
                ", invalidTag='" + invalidTag + '\'' +
                "} " + super.toString();
    }
}
