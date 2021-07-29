package com.dianping.cat.system.page.router.config;

import com.dianping.cat.Cat;
import com.dianping.cat.config.content.ContentFetcher;
import com.dianping.cat.core.config.Config;
import com.dianping.cat.core.config.ConfigDao;
import com.dianping.cat.core.config.ConfigEntity;
import com.dianping.cat.sample.entity.Domain;
import com.dianping.cat.sample.entity.SampleConfig;
import com.dianping.cat.sample.transform.DefaultSaxParser;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.Initializable;
import org.unidal.dal.jdbc.DalException;
import org.unidal.dal.jdbc.DalNotFoundException;
import org.unidal.lookup.annotation.Inject;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.Date;

/**
 * Created by wangbolong on 2018/11/25.
 */
public class SampleConfigManager implements Initializable {

    @Inject
    private ConfigDao m_configDao;

    @Inject
    private ContentFetcher m_fetcher;

    private SampleConfig m_config;

    private Date m_modifiedDate;

    private int m_configId;

    private static final String CONFIG_NAME = "sampleConfig";

    public Domain createDomain(Domain domain) throws DalException {
        do {
            if (m_config.getDomains().containsKey(domain.getId())) {
                throw new DalException("appkey 已经存在");
            }
            m_config.addDomain(domain);
            if (storeConfig()) {
                return domain;
            } else {
                refreshConfig();
            }
        } while (true);
    }

    public SampleConfig getSampleConfig() {
        return m_config;
    }

    @Override
    public void initialize() {
        try {
            Config config = m_configDao.findByName(CONFIG_NAME,
                    ConfigEntity.READSET_FULL);
            String content = config.getContent();

            m_configId = config.getId();
            m_config = DefaultSaxParser.parse(content);
            m_modifiedDate = config.getModifyDate();
        } catch (DalNotFoundException e) {
            try {
                String content = m_fetcher.getConfigContent(CONFIG_NAME);
                Config config = m_configDao.createLocal();
                Date now = new Date();

                config.setName(CONFIG_NAME);
                config.setContent(content);
                config.setModifyDate(now);
                m_configDao.insert(config);

                m_configId = config.getId();
                m_config = DefaultSaxParser.parse(content);
                m_modifiedDate = now;
            } catch (Exception ex) {
                Cat.logError(ex);
            }
        } catch (Exception e) {
            Cat.logError(e);
        }
        if (m_config == null) {
            m_config = new SampleConfig();
        }
    }

    private Config loadConfig() {
        do {
            try {
                Config config = m_configDao.findByName(CONFIG_NAME, ConfigEntity.READSET_FULL);
                m_configId = config.getId();
                m_modifiedDate = config.getModifyDate();
                return config;
            } catch (DalNotFoundException e) {
                Date now = new Date();
                String content = m_fetcher.getConfigContent(CONFIG_NAME);
                Config config = m_configDao.createLocal();
                config.setName(CONFIG_NAME);
                config.setContent(content);
                config.setModifyDate(now);
                try {
                    m_configDao.insert(config);
                    continue;
                } catch (DalException e1) {
                    Cat.logError(e1);
                }
            } catch (DalException e) {
                Cat.logError(e);
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                return null;
            }
        } while (true);
    }

    public boolean isSample(String domain) {
        Domain d = m_config.findDomain(domain);
        return d != null && d.getSample() < 1.0;
    }

    public double querySampleByDomain(String domain) {
        Domain d = m_config.findDomain(domain);
        if (d != null) {
            return d.getSample();
        } else {
            return 1.0;
        }
    }

    public boolean insert(String xml) {
        try {
            m_config = DefaultSaxParser.parse(xml);
            boolean result = storeConfig();

            return result;
        } catch (Exception e) {
            Cat.logError(e);
            return false;
        }
    }

    public void refreshConfig() {
        Config config = loadConfig();
        synchronized (this) {
            String content = config.getContent();
            try {
                m_config = DefaultSaxParser.parse(content);
            } catch (SAXException e) {
                Cat.logError(e);
            } catch (IOException e) {
                Cat.logError(e);
            }
        }
    }

    public Domain updateDomain(Domain domain) throws DalException {
        do {
            m_config.addDomain(domain);
            if (storeConfig()) {
                return domain;
            } else {
                refreshConfig();
            }
        } while (true);
    }

    private boolean storeConfig() {
        synchronized (this) {
            try {
                Config config = m_configDao.createLocal();

                config.setId(m_configId);
                config.setKeyId(m_configId);
                config.setName(CONFIG_NAME);
                config.setContent(m_config.toString());
                m_configDao.updateByPK(config, ConfigEntity.UPDATESET_FULL);
            } catch (Exception e) {
                Cat.logError(e);
                return false;
            }
        }
        return true;
    }
}
