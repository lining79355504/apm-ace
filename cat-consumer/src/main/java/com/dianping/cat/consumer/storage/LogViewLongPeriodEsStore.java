package com.dianping.cat.consumer.storage;

import com.dianping.cat.message.spi.MessageTree;
import com.google.common.eventbus.Subscribe;

/**
 * @author mort
 * @Description
 * @date 2021/9/29
 **/
public class LogViewLongPeriodEsStore implements LogViewLongPeriodStore {

    public static final String ID = "LogViewLongPeriodEsStore";

    @Override
    @Subscribe
    public void store(MessageTree messageTree) {

    }
}