package com.dianping.cat.consumer.storage;

import com.dianping.cat.config.server.ServerCommonConfigManager;
import com.dianping.cat.configuration.common.config.entity.LogviewStoreType;
import com.dianping.cat.message.spi.MessageTree;
import com.google.common.eventbus.Subscribe;
import org.unidal.lookup.annotation.Inject;

/**
 * @author mort
 * @Description
 * @date 2021/9/29
 **/
public class LogViewLongPeriodFileStore implements LogViewLongPeriodStore {

    public static final String ID = "LogViewLongPeriodFileStore";

    @Inject
    private ServerCommonConfigManager m_serverCommonConfigManager;

    @Override
    @Subscribe
    public void store(MessageTree messageTree) {
        LogviewStoreType storeType = m_serverCommonConfigManager.getCommonConfig().getLogviewStoreTypeMap().get("file");
        if(null == storeType){
            return;
        }

    }
}
