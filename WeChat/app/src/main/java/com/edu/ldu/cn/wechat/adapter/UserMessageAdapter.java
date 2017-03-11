package com.edu.ldu.cn.wechat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.edu.ldu.cn.wechat.R;
import com.edu.ldu.cn.wechat.bean.UserMessage;

import java.util.List;

/**
 * Created by mic on 2016/12/22.
 */

public class UserMessageAdapter extends BaseAdapter {
    private List<UserMessage> mData;
    private Context mContext;

    public UserMessageAdapter(){

    }

    public UserMessageAdapter(List<UserMessage> mData,Context mContext){
        this.mData = mData;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        UserMessage u = this.mData.get(position);
        //布局加载器
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewHolder holder = null;
        if (convertView == null){//如果没有可用的，生成
            //根据消息的不同来源指定消息的界面
            if (u.getFlag() == 0){
                convertView = inflater.inflate(R.layout.item_msg_send,null,false);
            }else{
                convertView = inflater.inflate(R.layout.item_msg_get,null,false);
            }
            holder = new ViewHolder();
            holder.imgRes = (ImageView)convertView.findViewById(R.id.userhead);
            holder.tv_send = (TextView)convertView.findViewById(R.id.tv_send);
            holder.now_time = (TextView)convertView.findViewById(R.id.nowtime);
            convertView.setTag(holder);
        }else {//如果有，直接取缓存的
            holder = (ViewHolder)convertView.getTag();
        }
        holder.now_time.setText(u.getTime());
        holder.tv_send.setText(u.getMsg());
        holder.imgRes.setImageResource(u.getImgRes());
        return convertView;
    }
    class ViewHolder{
        public TextView now_time;
        public TextView tv_send;
        public ImageView imgRes;
    }
}
