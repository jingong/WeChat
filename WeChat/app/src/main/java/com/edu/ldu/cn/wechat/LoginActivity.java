package com.edu.ldu.cn.wechat;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.edu.ldu.cn.wechat.bean.Params;
import com.edu.ldu.cn.wechat.bean.User;
import com.edu.ldu.cn.wechat.bean.UserMessage;
import com.google.gson.Gson;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class LoginActivity extends Activity {
    private EditText et_phoneNumber;
    private EditText et_password;
    private Button btn_login;

    private Socket clientSocket=null; //声明客户机套接字
    private BufferedReader in;  //声明网络输入流
    private PrintWriter out; //声明网络输出流
    private SharedPreferences info;
    ArrayList<UserMessage> mData1;
    ArrayList<UserMessage> mData2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        info = getSharedPreferences("INFO",0);
        initView();
        getConnettion();
        mData1 = new ArrayList<>();
        mData2 = new ArrayList<>();
        loadData();
        Params.Records.put("sa", mData1);
        Params.Records.put("jia", mData2);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String remoteName="192.168.49.10";
                            int remotePort=5000;
                            //构建一个Socket格式的地址
                            SocketAddress remoteAddr=new InetSocketAddress(InetAddress.getByName(remoteName),remotePort);
                            //1. 创建套接字clientSocket并连接到远程服务器
                            clientSocket=new Socket();
                            clientSocket.connect(remoteAddr);
                            Log.w("192.168.49.10", "成功");
                            //2. 创建绑定到套接字clientSocket上的网络输入流与输出流
                            out=new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream(),"UTF-8"),true);
                            in=new BufferedReader(new InputStreamReader(clientSocket.getInputStream(),"UTF-8"));
                            User user = new User();
                            user.setUserName(et_phoneNumber.getText().toString());
                            user.setPassword(et_password.getText().toString());
                            user.setType("LOGIN");
                            Gson gson = new Gson();
                            String u = gson.toJson(user);
                            //JSONObject
                            out.println(u);//把当前要登录的用户的姓名和密码发送到服务器
                            String flag = in.readLine();//服务器返回过来的用户信息是否正确
                            Log.w("192.168.49.10", flag);
                            if (flag.equals("1")){
                                Params.Users.put(user.getUserName(),clientSocket);
                                info.edit().putString("userName",user.getUserName()).commit();
                                Intent intent = new Intent();
                                intent.setClass(getApplication(),MainActivity.class);
                                startActivity(intent);
                            }else{
                                Log.w("192.168.49.10", "登录失败");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }finally {
                            try {
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
            }
        });
    }
    private void loadData() {
        mData1.add(new UserMessage(getNowTime(),"jia","最近干嘛呢？",R.drawable.f1,0));
        mData1.add(new UserMessage(getNowTime(),"sa","最近快考试了！呜呜...",R.drawable.f2,0));
        mData2.add(new UserMessage(getNowTime(),"jia","最近干嘛呢？",R.drawable.f1,0));
        mData2.add(new UserMessage(getNowTime(),"sa","最近快考试了！呜呜...",R.drawable.f2,0));
    }
    private String getNowTime(){
        SimpleDateFormat format = new SimpleDateFormat("MM月dd日 HH:mm");
        Date date=new Date();
        return format.format(date);
    }
    private void getConnettion() {

    }

    private void initView() {
        et_phoneNumber = (EditText)findViewById(R.id.phoneNumber);
        et_password = (EditText)findViewById(R.id.password);
        btn_login = (Button)findViewById(R.id.btn_login);
    }
}
