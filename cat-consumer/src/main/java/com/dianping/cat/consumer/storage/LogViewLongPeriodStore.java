package com.dianping.cat.consumer.storage;

import com.dianping.cat.message.spi.MessageTree;

/**
 * @author mort
 * @Description
 * @date 2021/9/29
 **/
public interface LogViewLongPeriodStore {

    void store(MessageTree messageTree);

}
