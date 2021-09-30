package com.dianping.cat.consumer.problem;

import com.dianping.cat.consumer.dump.LocalMessageBucketManager;
import com.dianping.cat.consumer.problem.model.entity.*;
import com.dianping.cat.consumer.storage.LogViewLongPeriodContentManager;
import com.dianping.cat.core.dal.LogviewlongperiodcontentDao;
import com.dianping.cat.message.Message;
import com.dianping.cat.message.spi.MessageCodec;
import com.dianping.cat.message.spi.MessageTree;
import com.dianping.cat.message.spi.codec.PlainTextMessageCodec;
import org.unidal.lookup.annotation.Inject;

import java.util.List;

public abstract class ProblemHandler {

	@Inject
	private LogViewLongPeriodContentManager logViewLongPeriodContentManager;

	@Inject
	private LocalMessageBucketManager m_bucketManager;

	@Inject
	private LogviewlongperiodcontentDao logviewlongperiodcontentDao;

	@Inject(PlainTextMessageCodec.ID)
	private MessageCodec messageCodec;

	public static final int MAX_LOG_SIZE = 60;

	protected Entity findOrCreateEntity(Machine machine, String type, String status) {
		String id = type + ":" + status;
		Entity entity = machine.findOrCreateEntity(id);
		entity.setType(type).setStatus(status);

		return entity;
	}

	protected int getSegmentByMessage(MessageTree tree) {
		Message message = tree.getMessage();
		long current = message.getTimestamp() / 1000 / 60;
		int min = (int) (current % (60));

		return min;
	}

	public abstract void handle(Machine machine, MessageTree tree);

	public void updateEntity(MessageTree tree, Entity entity, int value) {
		Duration duration = entity.findOrCreateDuration(value);
		List<String> messages = duration.getMessages();

		duration.incCount();
		if (messages.size() < MAX_LOG_SIZE) {
			messages.add(tree.getMessageId());
			//// TODO: 2021/9/23 可以改为每分钟采样数据
			logViewLongPeriodContentManager.enqueue(tree);
		}

		// make problem thread id = thread group name, make report small
		JavaThread thread = entity.findOrCreateThread(tree.getThreadGroupName());

		if (thread.getGroupName() == null) {
			thread.setGroupName(tree.getThreadGroupName());
		}
		if (thread.getName() == null) {
			thread.setName(tree.getThreadName());
		}

		Segment segment = thread.findOrCreateSegment(getSegmentByMessage(tree));
		List<String> segmentMessages = segment.getMessages();

		segment.incCount();
		if (segmentMessages.size() < MAX_LOG_SIZE) {
			segmentMessages.add(tree.getMessageId());
			//add long period duration logView store

			//add long period duration logView store
//			logviewLongDurationStore(tree);
			logViewLongPeriodContentManager.enqueue(tree);
		}
	}
}