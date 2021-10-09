package com.dianping.cat.consumer.storage;

import com.dianping.cat.Cat;
import com.dianping.cat.consumer.dump.LocalMessageBucketManager;
import com.dianping.cat.core.dal.Logviewlongperiodcontent;
import com.dianping.cat.core.dal.LogviewlongperiodcontentDao;
import com.dianping.cat.message.spi.MessageCodec;
import com.dianping.cat.message.spi.MessageTree;
import com.dianping.cat.message.spi.codec.PlainTextMessageCodec;
import com.google.common.eventbus.Subscribe;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import org.unidal.lookup.annotation.Inject;

import java.util.Date;

/**
 * @author mort
 * @Description
 * @date 2021/9/29
 **/
public class LogViewLongPeriodDBStore  implements LogViewLongPeriodStore {

    public static final String ID = "LogViewLongPeriodDBStore";

    @Inject
    private LocalMessageBucketManager m_bucketManager;

    @Inject
    private LogviewlongperiodcontentDao logviewlongperiodcontentDao;

    @Inject(PlainTextMessageCodec.ID)
    private MessageCodec messageCodec;

    @Override
    @Subscribe
    public void store(MessageTree tree) {
            //add long period duration logView store
            try {
//			MessageTree messageTree =  m_bucketManager.loadMessage(tree.getMessageId());
                ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
                messageCodec.encode(tree, buf);
                try {
                    buf.readInt(); // get rid of length
//				return buf.toString(Charset.forName("utf-8"));
                } catch (Exception e) {
                    // ignore it
                }
                byte[] bytes = new byte[buf.readableBytes()];
                buf.readBytes(bytes);

                Logviewlongperiodcontent content = logviewlongperiodcontentDao.createLocal();
                content.setMessageId(tree.getMessageId());
                content.setDomain(tree.getDomain());
                //分区字段 防止messageId重复 设置为消息的产生时间
                content.setCtime(new Date(tree.getMessage().getTimestamp()));
                content.setMtime(new Date());
                content.setContent(bytes);

                logviewlongperiodcontentDao.insertIgnore(content);
            } catch (Exception e) {
                Cat.logError(e);
            }
    }
}
