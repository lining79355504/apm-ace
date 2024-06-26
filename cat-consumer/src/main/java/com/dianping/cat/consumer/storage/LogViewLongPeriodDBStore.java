package com.dianping.cat.consumer.storage;

import com.dianping.cat.Cat;
import com.dianping.cat.config.server.ServerCommonConfigManager;
import com.dianping.cat.configuration.common.config.entity.LogviewStoreType;
import com.dianping.cat.consumer.dump.LocalMessageBucketManager;
import com.dianping.cat.core.dal.Logviewlongperiodcontent;
import com.dianping.cat.core.dal.LogviewlongperiodcontentDao;
import com.dianping.cat.message.Transaction;
import com.dianping.cat.message.spi.MessageCodec;
import com.dianping.cat.message.spi.MessageTree;
import com.dianping.cat.message.spi.codec.PlainTextMessageCodec;
import com.google.common.eventbus.Subscribe;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import org.unidal.lookup.annotation.Inject;
import com.dianping.cat.common.utils.GzipUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @author mort
 * @Description
 * @date 2021/9/29
 **/
public class LogViewLongPeriodDBStore implements LogViewLongPeriodStore {

    public static final String ID = "LogViewLongPeriodDBStore";

    @Inject
    private LocalMessageBucketManager m_bucketManager;

    @Inject
    private LogviewlongperiodcontentDao logviewlongperiodcontentDao;

    @Inject(PlainTextMessageCodec.ID)
    private MessageCodec messageCodec;

    @Inject
    private ServerCommonConfigManager m_serverCommonConfigManager;

    private static final Object OBJECT_LOCK = new Object();

    private List<Logviewlongperiodcontent> contents = new ArrayList<Logviewlongperiodcontent>();

    @Override
    @Subscribe
    public void store(MessageTree tree) {
        //add long period duration logView store
        LogviewStoreType storeType = m_serverCommonConfigManager.getCommonConfig().getLogviewStoreTypeMap().get("db");
        if (null == storeType) {
            return;
        }
        Transaction transaction = Cat.newTransaction("LOGVIEW", ID);
        try {
//			MessageTree messageTree =  m_bucketManager.loadMessage(tree.getMessageId());
            ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
            messageCodec.encode(tree, buf);
            Cat.logEvent("ENCODE", ID + "_ENCODE");
            try {
                buf.readInt(); // get rid of length
//				return buf.toString(Charset.forName("utf-8"));
            } catch (Exception e) {
                // ignore it
            }
            byte[] bytes = new byte[buf.readableBytes()];
            buf.readBytes(bytes);
            byte[] dataBytes = bytes;
            Cat.logEvent("ENCODE", ID + "_GZIP_START");
            if (storeType.getGzip()) {
                //gzip 压缩
                dataBytes = GzipUtils.compress(bytes);
            }
            Cat.logEvent("ENCODE", ID + "_GZIP_DONE");
            Logviewlongperiodcontent content = logviewlongperiodcontentDao.createLocal();
            content.setMessageId(tree.getMessageId());
            content.setDomain(tree.getDomain());
            //分区字段 防止messageId重复 设置为消息的产生时间
            content.setCtime(new Date(tree.getMessage().getTimestamp()));
            content.setMtime(new Date());
            content.setContent(dataBytes);
            synchronized (OBJECT_LOCK) {
                contents.add(content);
                if (contents.size() > storeType.getBatchSize()) {
                    Logviewlongperiodcontent[] inertData = contents.toArray(new Logviewlongperiodcontent[0]);
                    logviewlongperiodcontentDao.insertIgnoreBatch(inertData);
                    contents.clear();
                }
            }
            transaction.setStatus("0");
        } catch (Exception e) {
            transaction.setStatus(e);
        } finally {
            transaction.complete();
        }
    }
}
