package com.edu.ldu.cn.wechat;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.edu.ldu.cn.wechat.adapter.UserMessageAdapter;
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
import java.util.List;

public class FriendActivity extends Activity {
    private TextView txtTile;
    private EditText et_message;
    private Button btn_send;
    private ListView msgList;
    private List<UserMessage> mData;
    private UserMessageAdapter adapter;
    private SharedPreferences info;
    private Socket toPersonSocket = null;
    private BufferedReader in;  //声明网络输入流
    private PrintWriter out; //声明网络输出流
    private String userName;
    private Thread recv;
    private ImageView imgBack;
    private Handler handler = null;
    private Handler mHandler = new Handler();
    private String friendName;
    private String friendUserName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_friend);
        if (Looper.myLooper() != Looper.getMainLooper()){
            Log.w("当前UI不是：", "主线程！");
        }else{
            Log.w("当前UI是：", "主线程！");
        }
        Intent intent = getIntent();
        friendName = intent.getStringExtra("friendName");
        friendUserName = intent.getStringExtra("friendUserName");
        info = getSharedPreferences("INFO",0);
        userName = info.getString("userName","");
        Log.w("当前用户：",userName);
        toPersonSocket = Params.Users.get(userName);
        initView();
        txtTile.setText(friendName);
        msgList.setVerticalScrollBarEnabled(true);
        //mData1 = new ArrayList<UserMessage>();
        mData = Params.Records.get(friendUserName);
        //loadData();
        adapter = new UserMessageAdapter(mData,this);
        msgList.setAdapter(adapter);
        msgList.postInvalidate();
        try {
            in=new BufferedReader(new InputStreamReader(toPersonSocket.getInputStream(),"UTF-8"));
            out=new PrintWriter(new OutputStreamWriter(toPersonSocket.getOutputStream(),"UTF-8"),true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent();
                intent.setClass(getApplication(),MainActivity.class);
                startActivity(intent);*/
                finish();
            }
        });
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserMessage um = new UserMessage();
                um.setTime(getNowTime());
                um.setUserName(userName);
                um.setMsg(et_message.getText().toString());
                if(userName.equals("jia")){
                    um.setImgRes(R.drawable.f1);
                }else{
                    um.setImgRes(R.drawable.f2);
                }
                um.setFlag(0);
                mData.add(um);
                adapter.notifyDataSetChanged();
                try {
                    User user = new User();
                    user.setUserName(userName);
                    user.setFromPerson(userName);
                    user.setToPerson(friendUserName);
                    user.setMessage(et_message.getText().toString());
                    user.setType("MSG");//MSG代表发送的是消息类型
                    user.setFlag(0);
                    Gson gson = new Gson();
                    String msg = gson.toJson(user);
                    out.println(msg);
                    Log.w("消息发送结果：", msg + "成功发送！");
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                }
                et_message.setText("");
            }
        });

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 0x123){
                    Log.w("消息通知已经接收到！", "" + 123456);
                    User user = (User) msg.obj;
                    UserMessage um = new UserMessage();
                    um.setTime(getNowTime());
                    um.setUserName(user.getUserName());
                    um.setMsg(user.getMessage());
                    if(user.getUserName().equals("jia")){
                        um.setImgRes(R.drawable.f2);
                    }else{
                        um.setImgRes(R.drawable.f1);
                    }
                    um.setFlag(0);
                    mData.add(um);
                    System.out.println("mData的大小是：" + mData.size());
                    System.out.println("adapter的大小是：" + adapter.getCount());
                    Log.w("mData的大小是：",mData.size() + "大小");
                    Log.w("adapter的大小是：",adapter.getCount() + "大小");
                    adapter.notifyDataSetChanged();
                    Log.w("已更新！：",user.getMessage());
                }
            }
        };
        recv = new Thread(new ReceiveMessage());
        recv.start();//开启接收消息的线程
        mHandler.removeCallbacks(runnable);
        mHandler.postDelayed(runnable,100);
    }
    private Runnable runnable = new Runnable() {
        public void run () {
            adapter.notifyDataSetChanged();
            mHandler.postDelayed(this,100);
        }
    };

    @Override
    protected void onPause() {
        mHandler.removeCallbacks(runnable);
        super.onPause();
    }



    private void initView() {
        et_message = (EditText)findViewById(R.id.et_message);
        btn_send = (Button)findViewById(R.id.btn_send);
        msgList = (ListView)findViewById(R.id.list);
        imgBack = (ImageView)findViewById(R.id.img_back);
        txtTile = (TextView)findViewById(R.id.txt_title);
    }
    private String getNowTime(){
        SimpleDateFormat format = new SimpleDateFormat("MM月dd日 HH:mm");
        Date date=new Date();
        return format.format(date);
    }
    class ReceiveMessage implements Runnable {
        @Override
        public void run() {
            try {
                String msg = "";
                while((msg = in.readLine()) != null){
                    Log.w("消息读取成功：", msg);
                    Gson gson = new Gson();
                    final User user = gson.fromJson(msg,User.class);
                    Log.w("对象已生成：", user.getMessage());
                    //把得到的对象传给handler,用于更新UI界面
                    new Thread() {
                        public void run() {
                            //这儿是耗时操作，完成之后更新UI；
                            runOnUiThread(new Runnable(){
                                @Override
                                public void run() {
                                    //更新UI
                                    UserMessage um = new UserMessage();
                                    um.setTime(getNowTime());
                                    um.setUserName(user.getUserName());
                                    um.setMsg(user.getMessage());
                                    if(user.getUserName().equals("jia")){
                                        um.setImgRes(R.drawable.f1);
                                    }else{
                                        um.setImgRes(R.drawable.f2);
                                    }
                                    um.setFlag(0);
                                    System.out.println("mData的大小是：" + mData.size());
                                    System.out.println("adapter的大小是：" + adapter.getCount());
                                    mData.add(um);
                                    adapter.notifyDataSetChanged();
                                    Log.w("已更新！：",user.getMessage());
                                }
                            });
                        }
                    }.start();
                }
                Log.w("循环中断！：","");
            }catch (Exception e){

            }
        }
    }

}
