package com.dianping.cat.report.alert.sender.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.codehaus.plexus.personality.plexus.lifecycle.phase.Initializable;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.InitializationException;
import org.codehaus.plexus.util.StringUtils;
import org.unidal.dal.jdbc.DalNotFoundException;
import org.unidal.lookup.annotation.Inject;

import com.dianping.cat.Cat;
import com.dianping.cat.config.content.ContentFetcher;
import com.dianping.cat.core.config.Config;
import com.dianping.cat.core.config.ConfigDao;
import com.dianping.cat.core.config.ConfigEntity;
import com.dianping.cat.home.sender.entity.Par;
import com.dianping.cat.home.sender.entity.Sender;
import com.dianping.cat.home.sender.entity.SenderConfig;
import com.dianping.cat.home.sender.transform.DefaultSaxParser;

public class SenderConfigManager implements Initializable {

	@Inject
	private ConfigDao m_configDao;

	@Inject
	private ContentFetcher m_fetcher;

	private int m_configId;

	private SenderConfig m_senderConfig;

	private static final String CONFIG_NAME = "senderConfig";

	private static  Map<String, List<String>> greyParConfig = new ConcurrentHashMap<String, List<String>>();

	public SenderConfig getConfig() {
		return m_senderConfig;
	}

	@Override
	public void initialize() throws InitializationException {
		try {
			Config config = m_configDao.findByName(CONFIG_NAME, ConfigEntity.READSET_FULL);
			String content = config.getContent();

			m_senderConfig = DefaultSaxParser.parse(content);
			m_configId = config.getId();
			buildGreyProject(m_senderConfig);
		} catch (DalNotFoundException e) {
			try {
				String content = m_fetcher.getConfigContent(CONFIG_NAME);
				Config config = m_configDao.createLocal();

				config.setName(CONFIG_NAME);
				config.setContent(content);
				m_configDao.insert(config);

				m_senderConfig = DefaultSaxParser.parse(content);
				m_configId = config.getId();
			} catch (Exception ex) {
				Cat.logError(ex);
			}
		} catch (Exception e) {
			Cat.logError(e);
		}
		if (m_senderConfig == null) {
			m_senderConfig = new SenderConfig();
		}
	}

	public boolean insert(String xml) {
		try {
			m_senderConfig = DefaultSaxParser.parse(xml);

			return storeConfig();
		} catch (Exception e) {
			Cat.logError(e);
			return false;
		}
	}

	public boolean insert(Sender sender) {
		m_senderConfig.getSenders().put(sender.getId(), sender);

		return storeConfig();
	}

	public boolean remove(String id) {
		m_senderConfig.removeSender(id);

		return storeConfig();
	}

	public Sender querySender(String id) {
		return m_senderConfig.getSenders().get(id);
	}

	public String queryParString(Sender sender) {
		List<Par> pars = sender.getPars();
		String[] s = new String[pars.size()];
		int i = 0;

		for (Par par : pars) {
			s[i++] = par.getId();
		}
		return StringUtils.join(s, "&");
	}

	private boolean storeConfig() {
		synchronized (this) {
			try {
				Config config = m_configDao.createLocal();

				config.setId(m_configId);
				config.setKeyId(m_configId);
				config.setName(CONFIG_NAME);
				config.setContent(m_senderConfig.toString());
				m_configDao.updateByPK(config, ConfigEntity.UPDATESET_FULL);
			} catch (Exception e) {
				Cat.logError(e);
				return false;
			}
		}
		return true;
	}

	private void buildGreyProject(SenderConfig senderConfig) {
		for (Map.Entry<String, Sender> senderEntry : senderConfig.getSenders().entrySet()) {
			String id = senderEntry.getKey();
			Sender sender = senderEntry.getValue();
			greyParConfig.put(sender.getId(), buildGreyProject(sender));
		}
	}

	public List<String> buildGreyProject(Sender sender) {
		List<String> result = new ArrayList<String>();
		List<Par> pars = sender.getPars();
		for (Par par : pars) {
			String[] split = par.getId().split("_");
			if (2 == split.length && split[0].equals("grey")) {
				result.add(split[1]);
			}
		}
		return result;
	}

	public Map<String, List<String>> getGreyParConfig() {
		return greyParConfig;
	}

	public List<String> getGreyParConfig(String sendId) {
		return greyParConfig.get(sendId);
	}
}
