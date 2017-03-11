package com.edu.ldu.cn.wechat.bean;

public class User {
	private String userName;// 用户名
	private String password;//密码
	private String fromPerson;
	private String toPerson;
	private String message;
	private int flag = 0;//定义消息是从哪里来的
	private int imgId;
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getImgId() {
		return imgId;
	}
	public User(){

	}
	public void setImgId(int imgId) {
		this.imgId = imgId;
	}

	public User(int imgId,String userName,String fromPerson){
		this.imgId = imgId;
		this.userName = userName;
		this.fromPerson = fromPerson;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFromPerson() {
		return fromPerson;
	}

	public void setFromPerson(String fromPerson) {
		this.fromPerson = fromPerson;
	}

	public String getToPerson() {
		return toPerson;
	}

	public void setToPerson(String toPerson) {
		this.toPerson = toPerson;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
