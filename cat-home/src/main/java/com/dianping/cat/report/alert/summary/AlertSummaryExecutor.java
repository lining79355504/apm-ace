package com.dianping.cat.report.alert.summary;

import java.text.SimpleDateFormat;
import java.util.*;

import org.unidal.helper.Splitters;
import org.unidal.lookup.annotation.Inject;

import com.dianping.cat.Cat;
import com.dianping.cat.helper.TimeHelper;
import com.dianping.cat.message.Transaction;
import com.dianping.cat.report.alert.sender.AlertChannel;
import com.dianping.cat.report.alert.sender.AlertMessageEntity;
import com.dianping.cat.report.alert.sender.sender.SenderManager;
import com.dianping.cat.report.alert.summary.build.RelatedSummaryBuilder;
import com.dianping.cat.report.alert.summary.build.AlterationSummaryBuilder;
import com.dianping.cat.report.alert.summary.build.FailureSummaryBuilder;
import com.dianping.cat.report.alert.summary.build.SummaryBuilder;

public class AlertSummaryExecutor {

	@Inject(type = SummaryBuilder.class, value = RelatedSummaryBuilder.ID)
	private SummaryBuilder m_relatedBuilder;

	@Inject(type = SummaryBuilder.class, value = FailureSummaryBuilder.ID)
	private SummaryBuilder m_failureBuilder;

	@Inject(type = SummaryBuilder.class, value = AlterationSummaryBuilder.ID)
	private SummaryBuilder m_alterationBuilder;

	@Inject
	private SenderManager m_sendManager;

	public static final long SUMMARY_DURATION = 5 * TimeHelper.ONE_MINUTE;

	public static final long ALTERATION_DURATION = 30 * TimeHelper.ONE_MINUTE;

	private List<String> builderReceivers(String str) {
		List<String> result = new ArrayList<String>();

		if (str != null) {
			result.addAll(Splitters.by(",").noEmptyItem().split(str));
		}

		return result;
	}

	private String buildMailTitle(String domain, Date date) {
		StringBuilder builder = new StringBuilder();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

		builder.append("[统一告警] [项目 ").append(domain).append("]");
		builder.append("[时间 ").append(dateFormat.format(date)).append("]");
		return builder.toString();
	}

	public String execute(String domain, Date date) {
		Transaction t = Cat.newTransaction("Summary", domain);

		date = normalizeDate(date);
		try {
			StringBuilder builder = new StringBuilder();

			Map<Object, Object> dataMap = m_failureBuilder.generateModel(domain, date);
//			builder.append();
//			builder.append();
			builder.append(dataMap);
//			builder.append(m_relatedBuilder.generateHtml(domain, date));
//			builder.append(m_failureBuilder.generateHtml(domain, date));
//			builder.append(m_alterationBuilder.generateHtml(domain, date));

			t.setStatus(Transaction.SUCCESS);
			return builder.toString();
		} catch (Exception e) {
			t.setStatus(e);
			Cat.logError("generate alert summary fail:" + domain + " " + date, e);
		} finally {
			t.complete();
		}
		return null;
	}

	public String execute(String domain, Date date, String receiverStr) {
		String content = execute(domain, date);
		
		if (content == null || "".equals(content)) {
			return null;
		} else {
			String title = buildMailTitle(domain, date);
			List<String> receivers = builderReceivers(receiverStr);
			AlertMessageEntity message = new AlertMessageEntity(domain, title, "alertSummary", content, receivers);

			if (receivers.size() > 0) {
				m_sendManager.sendAlert(AlertChannel.MAIL, message);
			}
		}

		return content;
	}

	private Date normalizeDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		return cal.getTime();
	}

}
