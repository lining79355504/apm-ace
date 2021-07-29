/*
 * Copyright (c) 2020. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.dianping.cat.report.alert.sender.sender.wecom.enums;


public enum WxAppMessageType {

    TEXT("text", "文本"),
    TEXT_CARD("textcard", "文本卡片");

    private String code;

    private String desc;

    WxAppMessageType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static WxAppMessageType getByCode(String code) {
        for (WxAppMessageType bean : values()) {
            if (bean.getCode() == code) {
                return bean;
            }
        }
        return null;
    }
}
