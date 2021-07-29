package com.dianping.cat.report.alert.sender.sender.wecom.dto.request;

import com.google.gson.annotations.SerializedName;

public class WxAppMessage {

    @SerializedName("touser")
    private String toUser;

    @SerializedName("toparty")
    private String toParty;

    @SerializedName("totag")
    private String toTag;

    @SerializedName("msgtype")
    private String msgType;

    @SerializedName("agentid")
    private Integer agentId;

    private Text text;

    @SerializedName("textcard")
    private TextCard textCard;

    private Integer safe;

    // 文本类型
    public class Text {

        private String content;

        public Text (String content) {
            this.content = content;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        @Override
        public String toString() {
            return "Text{" +
                    "content='" + content + '\'' +
                    '}';
        }
    }

    // 文本卡片类型
    public class TextCard {

        private String title;

        private String description;

        private String url;

        @SerializedName("btntxt")
        private String btnTxt;

        public TextCard (String title, String description, String url) {

            this.title = title;
            this.description = description;
            this.url = url;
        }

        public TextCard (String title, String description, String url, String btnTxt) {

            this.title = title;
            this.description = description;
            this.url = url;
            this.btnTxt = btnTxt;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getBtnTxt() {
            return btnTxt;
        }

        public void setBtnTxt(String btnTxt) {
            this.btnTxt = btnTxt;
        }

        @Override
        public String toString() {
            return "TextCard{" +
                    "title='" + title + '\'' +
                    ", description='" + description + '\'' +
                    ", url='" + url + '\'' +
                    ", btnTxt='" + btnTxt + '\'' +
                    '}';
        }
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getToParty() {
        return toParty;
    }

    public void setToParty(String toParty) {
        this.toParty = toParty;
    }

    public String getToTag() {
        return toTag;
    }

    public void setToTag(String toTag) {
        this.toTag = toTag;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }

    public TextCard getTextCard() {
        return textCard;
    }

    public void setTextCard(TextCard textCard) {
        this.textCard = textCard;
    }

    public Integer getSafe() {
        return safe;
    }

    public void setSafe(Integer safe) {
        this.safe = safe;
    }

    @Override
    public String toString() {
        return "WxAppMessage{" +
                "toUser='" + toUser + '\'' +
                ", toParty='" + toParty + '\'' +
                ", toTag='" + toTag + '\'' +
                ", msgType='" + msgType + '\'' +
                ", agentId=" + agentId +
                ", text=" + text +
                ", textCard=" + textCard +
                ", safe=" + safe +
                '}';
    }
}
