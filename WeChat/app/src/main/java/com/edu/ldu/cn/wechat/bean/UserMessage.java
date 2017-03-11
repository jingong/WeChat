package com.edu.ldu.cn.wechat.bean;

/**
 * Created by mic on 2016/12/22.
 */

public class UserMessage {
    private String time;
    private String userName;
    private String msg;
    private int imgRes;
    private int flag = 0;//flag标志着消息是客户端发出的还是服务器传来的,0表示客户端发出的，1表示客户端收到的
    public UserMessage(){

    }
    public UserMessage(String time,String userName,String msg,int imgRes,int flag){
        this.time = time;
        this.userName = userName;
        this.msg = msg;
        this.imgRes = imgRes;
        this.flag = flag;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getImgRes() {
        return imgRes;
    }

    public void setImgRes(int imgRes) {
        this.imgRes = imgRes;
    }
}
