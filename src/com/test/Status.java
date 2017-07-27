package com.test;

import java.io.Serializable;

/**
 * Created by hdy on 2017/6/23. 状态模型类
 */
public class Status implements Serializable {

	private int status;
	private String msg;

	public Status() {
		// TODO Auto-generated constructor stub
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Status(int status, String message) {
		this.status = status;
		this.msg = message;
	}

	public String getMessage() {
		return msg;
	}

	public void setMessage(String message) {
		this.msg = message;
	}

	@Override
	public String toString() {
		return "Status{" + "status=" + status + ", message='" + msg + '\'' + '}';
	}
}
