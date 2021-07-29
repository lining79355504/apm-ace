package com.dianping.cat.report.alert.sender.sender;

import com.dianping.cat.home.sender.entity.Sender;
import com.dianping.cat.report.alert.sender.AlertChannel;
import com.dianping.cat.report.alert.sender.AlertMessageEntity;
import com.dianping.cat.report.alert.sender.sender.wecom.dto.response.OaUserInfo;
import com.dianping.cat.report.alert.sender.sender.wecom.service.OaUserInfoService;
import com.dianping.cat.report.alert.sender.sender.wecom.service.WxAppClient;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.plexus.logging.Logger;
import org.unidal.lookup.annotation.Inject;

import java.util.Collections;
import java.util.List;

/**
 * @author mort
 * @Description
 * @date 2020/11/24
 **/
public class WeComSender extends AbstractSender {

    protected Logger m_logger;

    public static final String ID = AlertChannel.WECOM.getName();

    @Inject
    private OaUserInfoService oaUserInfoService;

    @Inject
    private WxAppClient wxAppClient;

    public String getId() {
        return ID;
    }

    /**
     * 告警策略 app 负责人 关注人
     * @param message
     * @return
     */
    public boolean send(AlertMessageEntity message) {
        Sender sender = m_senderConfigManager.querySender(ID);
        List<String> greyList = m_senderConfigManager.getGreyParConfig(getId());
        String receiverStr = message.getReceiverString();
        StringBuilder receiverIdBuilder = new StringBuilder();
        String[] split = receiverStr.split(",");
        for (String userName : split) {
            List<OaUserInfo> oaUserInfo = oaUserInfoService.getOaUserInfo(userName);
            if (CollectionUtils.isNotEmpty(oaUserInfo)) {
                receiverIdBuilder.append(oaUserInfo.get(0).getWorkCode()).append("|");
            }
        }
        String receiverIdStr = receiverIdBuilder.toString();

        if (StringUtils.isBlank(receiverIdStr)) {
            return true;
        }

        if (greyList.contains(message.getGroup())) {
            wxAppClient.sendTextMessage(message.getContent(), receiverIdStr);
            return true;
        }
        wxAppClient.sendTextMessage(message.getContent(), receiverIdStr);
        return true;
    }
}
