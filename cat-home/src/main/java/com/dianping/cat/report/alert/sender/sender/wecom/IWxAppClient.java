package com.dianping.cat.report.alert.sender.sender.wecom;

import com.dianping.cat.report.alert.sender.sender.wecom.dto.response.WxAppMessageResponse;
import com.dianping.cat.report.alert.sender.sender.wecom.dto.response.WxAppUserInfo;

import java.util.List;


public interface IWxAppClient {

    String refreshAccessToken() throws Exception;

    String getUserId(String authCode) throws Exception;

    WxAppUserInfo getUserInfo(String userId) throws Exception;

    WxAppMessageResponse sendTextMessage(List<String> toUserIds, String content) throws Exception;

    WxAppMessageResponse sendTextCard(List<String> toUserIds, String title, String content, String url) throws Exception;
}
