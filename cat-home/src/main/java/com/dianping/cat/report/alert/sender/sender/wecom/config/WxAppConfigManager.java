package com.dianping.cat.report.alert.sender.sender.wecom.config;

import com.dianping.cat.Cat;
import com.dianping.cat.config.content.ContentFetcher;
import com.dianping.cat.core.config.Config;
import com.dianping.cat.core.config.ConfigDao;
import com.dianping.cat.core.config.ConfigEntity;
import com.dianping.cat.core.dal.Task;
import com.dianping.cat.home.alert.policy.transform.DefaultSaxParser;
import com.google.gson.Gson;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.Initializable;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.InitializationException;
import org.unidal.helper.Threads;
import org.unidal.lookup.annotation.Inject;

/**
 * @author mort
 * @Description
 * @date 2021/1/14
 **/
public class WxAppConfigManager implements Initializable {

    private static final String CONFIG_NAME = "wx-config";

    @Inject
    private ConfigDao m_configDao;

    @Inject
    private ContentFetcher m_fetcher;

    private int m_configId;

    private volatile WxAppConfig wxAppConfig ;

    public void initialize() throws InitializationException {
        buildWeComConfig();
    }

    public void buildWeComConfig() {
        try {
            Config config = m_configDao.findByName(CONFIG_NAME, ConfigEntity.READSET_FULL);
            String content = config.getContent();
            m_configId = config.getId();
            wxAppConfig = new Gson().fromJson(content, WxAppConfig.class);
        } catch (Exception e) {
            Cat.logError(e);
        }
    }

    public WxAppConfig getWxAppConfig() {
        return wxAppConfig;
    }
}
