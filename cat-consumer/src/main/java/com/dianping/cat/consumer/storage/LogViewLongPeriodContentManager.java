package com.dianping.cat.consumer.storage;

import com.dianping.cat.Cat;
import com.dianping.cat.CatConstants;
import com.dianping.cat.message.spi.MessageTree;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import org.codehaus.plexus.logging.LogEnabled;
import org.codehaus.plexus.logging.Logger;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.Initializable;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.InitializationException;
import org.unidal.helper.Threads;
import org.unidal.lookup.ContainerHolder;

import java.util.Map;
import java.util.concurrent.*;

/**
 * @author mort
 * @Description
 * @date 2021/9/29
 **/
public class LogViewLongPeriodContentManager extends ContainerHolder implements Initializable, LogEnabled {


    private BlockingQueue<MessageTree> m_messageBlocks = new LinkedBlockingQueue<MessageTree>(2000);

    private long m_queueOverflow;

    private Logger m_logger;


    @Override
    public void initialize() throws InitializationException {


        final EventBus eventBus = new AsyncEventBus(Executors.newFixedThreadPool(10, new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
//                thread.setName("");
                return thread;
            }
        }));

        Map<String, LogViewLongPeriodStore> map = lookupMap(LogViewLongPeriodStore.class);
        for (final LogViewLongPeriodStore logViewLongPeriodStore : map.values()) {
//            Threads.forGroup("cat-" + logViewLongPeriodStore.getClass().getSimpleName()).start(new Runnable() {
//                @Override
//                public void run() {
////                    logViewLongPeriodStore.store();
//                }
//            });

            eventBus.register(logViewLongPeriodStore);
        }


        Threads.forGroup("cat-" + LogViewLongPeriodContentManager.class.getSimpleName()).start(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        MessageTree messageTree = m_messageBlocks.poll(100, TimeUnit.MILLISECONDS);
                        if (null != messageTree) {
                            eventBus.post(messageTree);
                        }
                    }
                } catch (Exception e) {
                    Cat.logError(e);
                }
            }
        });
    }

    public boolean enqueue(MessageTree tree) {
        boolean result = m_messageBlocks.offer(tree);

        if (!result) { // trace queue overflow
            m_queueOverflow++;

            if (m_queueOverflow % (10 * CatConstants.ERROR_COUNT) == 0) {
                m_logger.warn(LogViewLongPeriodContentManager.class.getSimpleName() + " queue overflow number " + m_queueOverflow);
            }
        }
        return result;
    }

    @Override
    public void enableLogging(Logger logger) {
        m_logger = logger;
    }
}
