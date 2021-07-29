package com.dianping.cat.report.alert.sender.sender.wecom.config;

import java.util.List;


public class WxAppConfig {

    private String corpId;

    private String appSecret;

    private Integer appId;

    private String baseUrl;

    private String accessTokenUrl;

    private String getUserIdUrl;

    private String getUserInfoUrl;

    private String sendMessageUrl;

    private List<Integer> wxAppAccessTokenErrorCode;

    private Integer wxAppCookieMaxAge;

    private List<String> userList;

    private List<String> innerUserList;

    private String oaTokenUrl;

    private String oaUserConvertIdUrl;

    public String getCorpId() {
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getAccessTokenUrl() {
        return accessTokenUrl;
    }

    public void setAccessTokenUrl(String accessTokenUrl) {
        this.accessTokenUrl = accessTokenUrl;
    }

    public String getGetUserIdUrl() {
        return getUserIdUrl;
    }

    public void setGetUserIdUrl(String getUserIdUrl) {
        this.getUserIdUrl = getUserIdUrl;
    }

    public String getGetUserInfoUrl() {
        return getUserInfoUrl;
    }

    public void setGetUserInfoUrl(String getUserInfoUrl) {
        this.getUserInfoUrl = getUserInfoUrl;
    }

    public String getSendMessageUrl() {
        return sendMessageUrl;
    }

    public void setSendMessageUrl(String sendMessageUrl) {
        this.sendMessageUrl = sendMessageUrl;
    }

    public List<Integer> getWxAppAccessTokenErrorCode() {
        return wxAppAccessTokenErrorCode;
    }

    public void setWxAppAccessTokenErrorCode(List<Integer> wxAppAccessTokenErrorCode) {
        this.wxAppAccessTokenErrorCode = wxAppAccessTokenErrorCode;
    }

    public Integer getWxAppCookieMaxAge() {
        return wxAppCookieMaxAge;
    }

    public void setWxAppCookieMaxAge(Integer wxAppCookieMaxAge) {
        this.wxAppCookieMaxAge = wxAppCookieMaxAge;
    }

    public List<String> getUserList() {
        return userList;
    }

    public void setUserList(List<String> userList) {
        this.userList = userList;
    }

    public List<String> getInnerUserList() {
        return innerUserList;
    }

    public void setInnerUserList(List<String> innerUserList) {
        this.innerUserList = innerUserList;
    }

    public String getOaTokenUrl() {
        return oaTokenUrl;
    }

    public void setOaTokenUrl(String oaTokenUrl) {
        this.oaTokenUrl = oaTokenUrl;
    }

    public String getOaUserConvertIdUrl() {
        return oaUserConvertIdUrl;
    }

    public void setOaUserConvertIdUrl(String oaUserConvertIdUrl) {
        this.oaUserConvertIdUrl = oaUserConvertIdUrl;
    }
}
