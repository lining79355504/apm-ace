package com.dianping.cat.report.page;

import com.dianping.cat.report.alert.event.EventRuleConfigManager;
import com.dianping.cat.report.alert.exception.ExceptionRuleConfigManager;
import com.dianping.cat.report.alert.sender.sender.wecom.config.WxAppConfigManager;
import com.dianping.cat.report.alert.transaction.TransactionRuleConfigManager;
import com.dianping.cat.system.page.router.config.SampleConfigManager;
import org.unidal.helper.Threads.Task;
import org.unidal.lookup.annotation.Inject;

import com.dianping.cat.Cat;
import com.dianping.cat.config.server.BlackListManager;
import com.dianping.cat.consumer.config.AllReportConfigManager;
import com.dianping.cat.consumer.config.ProductLineConfigManager;
import com.dianping.cat.consumer.metric.MetricConfigManager;
import com.dianping.cat.helper.TimeHelper;
import com.dianping.cat.message.Transaction;
import com.dianping.cat.system.page.router.config.RouterConfigManager;

public class ConfigReloadTask implements Task {

    @Inject
    private ProductLineConfigManager m_productLineConfigManager;

    @Inject
    private MetricConfigManager m_metricConfigManager;

    @Inject
    private RouterConfigManager m_routerConfigManager;

    @Inject
    private SampleConfigManager m_sampleConfigManager;

    @Inject
    private BlackListManager m_blackListManager;

    @Inject
    private AllReportConfigManager m_allTransactionConfigManager;

    @Inject
    private WxAppConfigManager wxAppConfigManager;

    @Inject
    private TransactionRuleConfigManager transactionRuleConfigManager;

    @Inject
    private ExceptionRuleConfigManager exceptionRuleConfigManager;

    @Inject
    private EventRuleConfigManager eventRuleConfigManager;

    @Override
    public String getName() {
        return "Config-Reload";
    }

    @Override
    public void run() {
        boolean active = true;
        while (active) {
            try {
                m_productLineConfigManager.refreshConfig();
            } catch (Exception e) {
                Cat.logError(e);
            }
            try {
                m_metricConfigManager.refreshConfig();
            } catch (Exception e) {
                Cat.logError(e);
            }

            Transaction t = Cat.newTransaction("ReloadConfig", "router");
            try {
                m_routerConfigManager.refreshConfig();
                t.setStatus(Transaction.SUCCESS);
            } catch (Exception e) {
                Cat.logError(e);
                t.setStatus(e);
            } finally {
                t.complete();
            }

            try {
                m_sampleConfigManager.refreshConfig();
            } catch (Exception e) {
                Cat.logError(e);
            }

            try {
                m_blackListManager.refreshConfig();
            } catch (Exception e) {
                Cat.logError(e);
            }
            try {
                m_allTransactionConfigManager.refreshConfig();
            } catch (Exception e) {
                Cat.logError(e);
            }
            try {
                wxAppConfigManager.buildWeComConfig();
            } catch (Exception e){
                Cat.logError(e);
            }
            try {
                transactionRuleConfigManager.refresh();
            } catch (Exception e) {
                Cat.logError(e);
            }
            try {
                eventRuleConfigManager.refresh();
            } catch (Exception e) {
                Cat.logError(e);
            }
            try {
                exceptionRuleConfigManager.refresh();
            } catch (Exception e) {
                Cat.logError(e);
            }

            try {
                Thread.sleep(TimeHelper.ONE_MINUTE);
            } catch (InterruptedException e) {
                active = false;
            }
        }
    }

    @Override
    public void shutdown() {
    }

}
