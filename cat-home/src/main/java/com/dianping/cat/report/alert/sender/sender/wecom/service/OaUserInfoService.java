package com.dianping.cat.report.alert.sender.sender.wecom.service;

import com.dianping.cat.common.utils.HttpRequestUtil;
import com.dianping.cat.Cat;
import com.dianping.cat.report.alert.sender.sender.wecom.config.WxAppConfigManager;
import com.dianping.cat.report.alert.sender.sender.wecom.dto.response.OaResponse;
import com.dianping.cat.report.alert.sender.sender.wecom.dto.response.OaToken;
import com.dianping.cat.report.alert.sender.sender.wecom.dto.response.OaUserInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang.StringUtils;
import org.apache.http.message.BasicHeader;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.Initializable;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.InitializationException;
import org.unidal.lookup.annotation.Inject;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class OaUserInfoService implements Initializable {

    @Inject
    private WxAppConfigManager wxAppConfigManager;

    private volatile String accessToken;

    private Map<String, List<OaUserInfo>> oaInfoCache = new ConcurrentHashMap<String, List<OaUserInfo>>();

    public List<OaUserInfo> getOaUserInfo(String username) {
        if (null == username) {
            return Collections.emptyList();
        }

        List<OaUserInfo> oaCacheUserInfos = oaInfoCache.get(username.trim());
        if (null != oaCacheUserInfos) {
            return oaCacheUserInfos;
        }

        String userUrl = wxAppConfigManager.getWxAppConfig().getOaUserConvertIdUrl();
        String url = userUrl.concat(username);
        if (StringUtils.isEmpty(accessToken)) {
            refreshAccessToken();
        }

        String result = HttpRequestUtil.doGet(null, url, new BasicHeader("authorization", accessToken));
        OaResponse<List<OaUserInfo>> userResponse = null;
        try {
            userResponse = new Gson().fromJson(result, new TypeToken<OaResponse<List<OaUserInfo>>>() {
            }.getType());
        } catch (Exception e) {
            Cat.logError(e);
            refreshAccessToken();
            userResponse = new Gson().fromJson(result, new TypeToken<OaResponse<List<OaUserInfo>>>() {
            }.getType());
            Cat.logEvent("CONVERT_OA_NAME", "convert", "1", result);
        }

        if (null != userResponse.getData()) {
            oaInfoCache.put(username, userResponse.getData());
        }
        return userResponse.getData();
    }

    public String refreshAccessToken() {
        String tokenUrl = wxAppConfigManager.getWxAppConfig().getOaTokenUrl();
        String result = HttpRequestUtil.doGet(null, tokenUrl, null);
        try {
            OaResponse<OaToken> tokenResponse = new Gson().fromJson(result, new TypeToken<OaResponse<OaToken>>() {
            }.getType());
            accessToken = String.format("Bearer %s", tokenResponse.getData().getToken());
        } catch (Exception e) {
            Cat.logError(e);
            Cat.logEvent("CONVERT_OA_NAME", "refresh_token", "1", result + tokenUrl);
        }
        return accessToken;
    }

    public void initialize() throws InitializationException {
        try {
            refreshAccessToken();
        }catch (Exception e){
            Cat.logError(e);
        }
    }
}
