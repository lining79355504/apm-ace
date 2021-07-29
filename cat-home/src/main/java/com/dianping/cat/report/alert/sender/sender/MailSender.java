package com.dianping.cat.report.alert.sender.sender;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import com.dianping.cat.Cat;
import com.dianping.cat.report.alert.sender.AlertChannel;
import com.dianping.cat.report.alert.sender.AlertMessageEntity;
import com.sun.mail.util.MailSSLSocketFactory;
import org.apache.commons.lang.StringUtils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSender extends AbstractSender {

	public static final String ID = AlertChannel.MAIL.getName();

	@Override
	public String getId() {
		return ID;
	}

	@Override
	public boolean send(AlertMessageEntity message) {
		com.dianping.cat.home.sender.entity.Sender sender = querySender();
		boolean batchSend = sender.isBatchSend();
		boolean result = false;

		if (batchSend) {
			String emails = message.getReceiverString();

			result = sendEmail(message, emails, null);
		} else {
			List<String> emails = message.getReceivers();

			for (String email : emails) {
				boolean success = sendEmail(message, email , null);
				result = result || success;
			}
		}
		return result;
	}

	private boolean sendEmail(AlertMessageEntity message, String receiver,
	      com.dianping.cat.home.sender.entity.Sender sender) {
		String title = message.getTitle().replaceAll(",", " ");
		String content = message.getContent().replaceAll(",", " ");
		String urlPrefix = sender.getUrl();
		String urlPars = m_senderConfigManager.queryParString(sender);
		String time = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());

		try {
			urlPars = urlPars.replace("${receiver}", receiver).replace("${title}", URLEncoder.encode(title, "utf-8"))
			      .replace("${content}", URLEncoder.encode(content, "utf-8"))
			      .replace("${time}", URLEncoder.encode(time, "utf-8"));

		} catch (Exception e) {
			Cat.logError(e);
		}

		return httpSend(sender.getSuccessCode(), sender.getType(), urlPrefix, urlPars);
	}


}
