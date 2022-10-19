package com.st.studentregistration.models.message;

public class Message {
    private String msgTitle;
    private String msgDescription;
    private String msgFlag;

    public Message(String msgTitle, String msgDescription, String msgFlag) {
        this.msgTitle = msgTitle;
        this.msgDescription = msgDescription;
        this.msgFlag = msgFlag;
    }


    public String getMsgTitle() {
        return msgTitle;
    }

    public String getMsgDescription() {
        return msgDescription;
    }

    public String getMsgFlag() {
        return msgFlag;
    }


}
