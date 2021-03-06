package com.dianping.cat.report.alert.transaction;

import org.codehaus.plexus.personality.plexus.lifecycle.phase.Initializable;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.InitializationException;
import org.unidal.dal.jdbc.DalException;
import org.unidal.dal.jdbc.DalNotFoundException;
import org.unidal.lookup.annotation.Inject;

import com.dianping.cat.Cat;
import com.dianping.cat.config.content.ContentFetcher;
import com.dianping.cat.core.config.Config;
import com.dianping.cat.core.config.ConfigEntity;
import com.dianping.cat.home.rule.entity.MonitorRules;
import com.dianping.cat.home.rule.transform.DefaultSaxParser;
import com.dianping.cat.report.alert.config.BaseRuleConfigManager;
import org.xml.sax.SAXException;

import java.io.IOException;

public class TransactionRuleConfigManager extends BaseRuleConfigManager implements Initializable {

	@Inject
	private ContentFetcher m_fetcher;

	private static final String CONFIG_NAME = "transactionRule";

	@Override
	protected String getConfigName() {
		return CONFIG_NAME;
	}

	@Override
	public void initialize() throws InitializationException {
		try {
			refresh();
		} catch (DalNotFoundException e) {
			try {
				String content = m_fetcher.getConfigContent(CONFIG_NAME);
				Config config = m_configDao.createLocal();

				config.setName(CONFIG_NAME);
				config.setContent(content);
				m_configDao.insert(config);

				m_configId = config.getId();
				m_config = DefaultSaxParser.parse(content);
			} catch (Exception ex) {
				Cat.logError(ex);
			}
		} catch (Exception e) {
			Cat.logError(e);
		}
		if (m_config == null) {
			m_config = new MonitorRules();
		}
	}

	public void refresh() throws DalException, SAXException, IOException {
		Config config = m_configDao.findByName(CONFIG_NAME, ConfigEntity.READSET_FULL);
		String content = config.getContent();

		m_configId = config.getId();
		m_config = DefaultSaxParser.parse(content);
	}

}
