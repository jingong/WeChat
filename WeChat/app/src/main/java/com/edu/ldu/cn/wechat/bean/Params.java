package com.edu.ldu.cn.wechat.bean;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class Params {
	public static Map<String, Socket> Users = new HashMap<String, Socket>();
	public static List<User> UserInfos = new ArrayList<User>();// 好友信息
	public static Map<String,ArrayList<UserMessage>> Records = new HashMap<String,ArrayList<UserMessage>>();
}
