package com.dianping.cat.demo;

import java.util.concurrent.CountDownLatch;

import org.junit.Test;
import org.unidal.helper.Threads;
import org.unidal.helper.Threads.Task;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Event;
import com.dianping.cat.message.Message;
import com.dianping.cat.message.Transaction;
import com.dianping.cat.message.spi.MessageTree;
import com.dianping.cat.message.spi.internal.DefaultMessageTree;

public class TestBusinessMessage {
	private static final String Puma = "PumaServer";

	private static final String PayOrder = "PayOrder";

	@Test
	public void testMutilThead() throws Exception {
		int total = 10;
		CountDownLatch latch = new CountDownLatch(total);

		for (int i = 0; i < 10; i++) {
			Threads.forGroup("cat").start(new CatThread(latch, i));
		}

		Thread.sleep(10000);
	}

	public class CatThread implements Task {

		private CountDownLatch m_latch;

		private int m_count;

		public CatThread(CountDownLatch latch, int count) {
			m_latch = latch;
			m_count = count;
		}

		@Override
		public void run() {
			m_latch.countDown();
			try {
				m_latch.await();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			for (int i = 0; i < 100; i++) {
				Transaction transaction = Cat.newTransaction("test", "test" + m_count);

				transaction.setStatus(Message.SUCCESS);
				transaction.complete();
			}
		}

		@Override
		public String getName() {
			return "cat-test-thread";
		}

		@Override
		public void shutdown() {
		}

	}

	@Test
	public void testEvent() throws Exception {
		for (int i = 0; i < 1000; i++) {
			Cat.logError(new NullPointerException());
		}

		Thread.sleep(10000);
	}

	@Test
	public void testMetric() throws Exception {
		for (int i = 0; i < 1000; i++) {
			Transaction t = Cat.newTransaction("URL", "/index");
			Cat.logEvent("RemoteLink", "sina", Event.SUCCESS, "http://sina.com.cn/");
			t.addData("channel=channel" + i % 5);

			Cat.logMetricForCount("Receipt Verify Success");
			Cat.logMetricForCount("Receipt Verify Success 2", 2);
			Cat.logMetricForDuration("Receipt Verify Druation", 10);
			Cat.logMetricForSum("sum Value", 20);
			Cat.logMetricForSum("sum Value2", 20, 2);

			t.complete();
		}
		Thread.sleep(1000);
	}

	@Test
	public void test() throws Exception {
		for (int i = 0; i < 1000; i++) {
			Transaction t = Cat.newTransaction("URL", "/index");
			Cat.logEvent("RemoteLink", "sina", Event.SUCCESS, "http://sina.com.cn/");
			t.addData("channel=channel" + i % 5);

			Cat.logMetricForCount("Receipt Verify Success");
			Cat.logMetricForCount("Receipt Verify Success 2", 2);
			Cat.logMetricForDuration("Receipt Verify Druation", 10);
			Cat.logMetricForSum("sum Value", 20);
			Cat.logMetricForSum("sum Value2", 20, 2);

			t.complete();
		}

		for (int i = 0; i < 900; i++) {
			Transaction t = Cat.newTransaction("URL", "/detail");
			MessageTree tree = (MessageTree) Cat.getManager().getThreadLocalMessageTree();

			tree.setDomain(Puma);
			t.addData("channel=channel" + i % 5);
			t.complete();
		}

		for (int i = 0; i < 500; i++) {
			Transaction t = Cat.newTransaction("URL", "/order/submitOrder");
			MessageTree tree = (MessageTree) Cat.getManager().getThreadLocalMessageTree();

			tree.setDomain(PayOrder);
			Cat.logMetric("order", "quantity", 1, "channel", "channel" + i % 5);
			Cat.logMetric("payment.pending", "amount", i, "channel", "channel" + i % 5);
			Cat.logMetric("payment.success", "amount", i, "channel", "channel" + i % 5);
			t.addData("channel=channel" + i % 5);
			t.complete();
		}

		for (int i = 0; i < 1000; i++) {
			Transaction t = Cat.newTransaction("URL", "t");
			Cat.logEvent("RemoteLink", "sina", Event.SUCCESS, "http://sina.com.cn/");
			t.complete();
		}
		for (int i = 0; i < 900; i++) {
			Transaction t = Cat.newTransaction("URL", "e");
			t.complete();
		}
		for (int i = 0; i < 500; i++) {
			Transaction t = Cat.newTransaction("URL", "home");
			Cat.logMetric("order", "quantity", 1, "channel", "channel" + i % 5);
			Cat.logMetric("payment.pending", "amount", i, "channel", "channel" + i % 5);
			Cat.logMetric("payment.success", "amount", i, "channel", "channel" + i % 5);
			t.complete();
		}

		Thread.sleep(1000);
	}

	@Test
	public void test2() throws Exception {
		while (true) {

			for (int i = 0; i < 1000; i++) {
				Transaction t = Cat.newTransaction("URL", "/index");
				Cat.logEvent("RemoteLink", "sina", Event.SUCCESS, "http://sina.com.cn/");
				t.addData("channel=channel" + i % 5);

				t.complete();
			}
			for (int i = 0; i < 900; i++) {
				Transaction t = Cat.newTransaction("URL", "/detail");
				t.addData("channel=channel" + i % 5);
				t.complete();
			}
			for (int i = 0; i < 500; i++) {
				Transaction t = Cat.newTransaction("URL", "/order/submitOrder");
				Cat.logMetric("order", "quantity", 1, "channel", "channel" + i % 5);
				Cat.logMetric("payment.pending", "amount", i, "channel", "channel" + i % 5);
				Cat.logMetric("payment.success", "amount", i, "channel", "channel" + i % 5);
				t.addData("channel=channel" + i % 5);
				t.complete();
			}

			Thread.sleep(1000);
			break;
		}
	}

	@Test
	public void test3() throws InterruptedException {
		for (int i = 0; i < 500; i++) {
			Transaction t = Cat.newTransaction("test", "test");

			Cat.logMetricForCount("MemberCardSuccess");
			Cat.logMetricForCount("MemberCardFail", 2);

			MessageTree tree = Cat.getManager().getThreadLocalMessageTree();
			((DefaultMessageTree) tree).setDomain("MobileMembercardMainApiWeb");
			t.complete();
		}
		Thread.sleep(100000);
	}

	public void sample() {
		String pageName = "";
		String serverIp = "";

		Transaction t = Cat.newTransaction("URL", pageName); // ????????????Transaction

		try {
			// ??????????????????
			Cat.logEvent("URL.Server", serverIp, Event.SUCCESS, "ip=" + serverIp + "&...");
			// ?????????????????????????????????????????????
			Cat.logMetricForCount("OrderCount");
			// ?????????????????????????????????????????????
			Cat.logMetricForCount("PayCount");

			yourBusiness();// ??????????????????

			t.setStatus(Transaction.SUCCESS);// ????????????
		} catch (Exception e) {
			t.setStatus(e);// ??????????????????
		} finally {
			t.complete();// ??????Transaction
		}
	}

	private void yourBusiness() {

	}

}
