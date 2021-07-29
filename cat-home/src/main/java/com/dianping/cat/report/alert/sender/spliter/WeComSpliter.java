package com.dianping.cat.report.alert.sender.spliter;

import com.dianping.cat.report.alert.sender.AlertChannel;

/**
 * @author mort
 * @Description
 * @date 2021/1/14
 **/
public class WeComSpliter  implements Spliter {

    public static final String ID = AlertChannel.WECOM.getName();

    public String process(String content) {
        return content;
    }

    public String getID() {
        return ID;
    }
}
