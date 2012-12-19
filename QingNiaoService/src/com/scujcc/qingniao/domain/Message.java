package com.scujcc.qingniao.domain;

import java.util.Date;

public class Message {
	private String sendUser;
	private String recordUser;
	private Date sendDate;
	private Date recordDate;
	private String MessText;

	

	public String getSendUser() {
		return sendUser;
	}

	public void setSendUser(String sendUser) {
		this.sendUser = sendUser;
	}

	public String getRecordUser() {
		return recordUser;
	}

	public void setRecordUser(String recordUser) {
		this.recordUser = recordUser;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public Date getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}

	public String getMessText() {
		return MessText;
	}

	public void setMessText(String messText) {
		MessText = messText;
	}

}
