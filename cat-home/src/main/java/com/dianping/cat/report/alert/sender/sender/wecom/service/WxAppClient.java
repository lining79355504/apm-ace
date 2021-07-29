package com.dianping.cat.report.alert.sender.sender.wecom.service;


import com.dianping.cat.common.utils.HttpRequestUtil;
import com.dianping.cat.Cat;
import com.dianping.cat.report.alert.sender.sender.wecom.IWxAppClient;
import com.dianping.cat.report.alert.sender.sender.wecom.config.WxAppConfig;
import com.dianping.cat.report.alert.sender.sender.wecom.config.WxAppConfigManager;
import com.dianping.cat.report.alert.sender.sender.wecom.dto.request.WxAppMessage;
import com.dianping.cat.report.alert.sender.sender.wecom.dto.response.*;
import com.dianping.cat.report.alert.sender.sender.wecom.enums.WxAppMessageType;
import com.google.gson.Gson;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.Initializable;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.InitializationException;
import org.unidal.lookup.annotation.Inject;

import java.util.List;


public class WxAppClient implements IWxAppClient, Initializable {

    // 调用企业微信API接口的凭证
    private volatile String accessToken;

    @Inject
    private WxAppConfigManager wxAppConfigManager;

    public void initialize() throws InitializationException {
        refreshAccessToken();
    }

    public String refreshAccessToken() {
        WxAppConfig wxAppConfig = wxAppConfigManager.getWxAppConfig();
        String url = generatorUrl(wxAppConfig.getAccessTokenUrl(), wxAppConfig.getCorpId(), wxAppConfig.getAppSecret());

        WxAppAccessToken response = doGet(url, WxAppAccessToken.class);
        accessToken = response.getAccessToken();
        return accessToken;
    }

    public String getUserId(String authCode) throws Exception {
        WxAppConfig wxAppConfig = wxAppConfigManager.getWxAppConfig();
        String url = generatorUrl(wxAppConfig.getGetUserIdUrl(), accessToken, authCode);
        WxAppUserAuthResponse response = doGet(url, WxAppUserAuthResponse.class);
        return response.getUserId();
    }

    public WxAppUserInfo getUserInfo(String userId) throws Exception {
        WxAppConfig wxAppConfig = wxAppConfigManager.getWxAppConfig();
        String url = generatorUrl(wxAppConfig.getGetUserInfoUrl(), accessToken, userId);
        return doGet(url, WxAppUserInfo.class);
    }

    public WxAppMessageResponse sendTextMessage(List<String> toUserIds, String content) throws Exception {
        String userList = org.apache.commons.lang.StringUtils.join(toUserIds.toArray(), "|");
        return sendTextMessage(content, userList);
    }

    public WxAppMessageResponse sendTextMessage(String content, String userList) {
        WxAppConfig wxAppConfig = wxAppConfigManager.getWxAppConfig();
        WxAppMessage.Text text = new WxAppMessage().new Text(content);
        WxAppMessage message = new WxAppMessage();
        message.setToUser(userList);
        message.setMsgType(WxAppMessageType.TEXT.getCode());
        message.setAgentId(wxAppConfig.getAppId());
        message.setText(text);
        String url = generatorUrl(wxAppConfig.getSendMessageUrl(), accessToken);
        WxAppMessageResponse wxAppMessageResponse = doPost(url, new Gson().toJson(message), WxAppMessageResponse.class);
        if (0 != wxAppMessageResponse.getErrCode()) {
            Cat.logEvent("CONVERT_OA_NAME", "wx_send_error", "1", wxAppMessageResponse.toString() + message.toString() + url);
        }
        return wxAppMessageResponse;
    }

    public WxAppMessageResponse sendTextCard(List<String> toUserIds, String title, String content, String url) throws Exception {
        String userList = org.apache.commons.lang.StringUtils.join(toUserIds.toArray(), "|");
        return sendTextCard(title, content, url, userList);
    }

    public WxAppMessageResponse sendTextCard(String title, String content, String url, String userList) throws Exception {
        WxAppConfig wxAppConfig = wxAppConfigManager.getWxAppConfig();
        WxAppMessage.TextCard textCard = new WxAppMessage().new TextCard(title, content, url);
        WxAppMessage message = new WxAppMessage();
        message.setToUser(userList);
        message.setMsgType(WxAppMessageType.TEXT_CARD.getCode());
        message.setAgentId(wxAppConfig.getAppId());
        message.setTextCard(textCard);
        String sendUrl = generatorUrl(wxAppConfig.getSendMessageUrl(), accessToken);
        return doPost(sendUrl, new Gson().toJson(message), WxAppMessageResponse.class);
    }

    private <T extends WxAppResponse> T doGet(String url, Class<T> responseType) {

        WxAppConfig wxAppConfig = wxAppConfigManager.getWxAppConfig();
        WxAppResponse response = null;
        try {
            String result = HttpRequestUtil.doGet(null, url, null);
            response = new Gson().fromJson(result, responseType);
            if (wxAppConfig.getWxAppAccessTokenErrorCode().contains(response.getErrCode())) {
                refreshAccessToken();
                url = generatorUrl(wxAppConfig.getSendMessageUrl(), accessToken);
                result = HttpRequestUtil.doGet(null, url, null);
                response = new Gson().fromJson(result, responseType);
            }
        } catch (Exception e) {
            Cat.logError(e);
        }
        return (T) response;
    }

    private <T extends WxAppResponse> T doPost(String url, String body, Class<T> responseType) {

        WxAppConfig wxAppConfig = wxAppConfigManager.getWxAppConfig();
        WxAppResponse response = null;
        try {
            String result = HttpRequestUtil.doPost(null, body, url, null);
            response = new Gson().fromJson(result, responseType);
            if (wxAppConfig.getWxAppAccessTokenErrorCode().contains(response.getErrCode())) {
                refreshAccessToken();
                url = generatorUrl(wxAppConfig.getSendMessageUrl(), accessToken);
                result = HttpRequestUtil.doGet(null, url, null);
                response = new Gson().fromJson(result, responseType);
            }
        } catch (Exception e) {
            Cat.logError(e);
        }
        return (T) response;
    }

    private String updateUrlAccessToken (String url) throws Exception {
        
        String tokenParam = "access_token=" + refreshAccessToken();
        return url.replaceFirst("access_token=(?<t>[^&]+)", tokenParam);
    }

    private String generatorUrl (String path, Object... args) {
        WxAppConfig wxAppConfig = wxAppConfigManager.getWxAppConfig();
        return String.format(wxAppConfig.getBaseUrl().concat(path), args);
    }
}
