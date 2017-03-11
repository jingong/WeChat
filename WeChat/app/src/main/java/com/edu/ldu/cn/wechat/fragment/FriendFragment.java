package com.edu.ldu.cn.wechat.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.edu.ldu.cn.wechat.FriendActivity;
import com.edu.ldu.cn.wechat.R;
import com.edu.ldu.cn.wechat.SmileActivity;
import com.edu.ldu.cn.wechat.adapter.FriendAdapter;
import com.edu.ldu.cn.wechat.bean.Params;
import com.edu.ldu.cn.wechat.bean.User;

public class FriendFragment extends Fragment
{	private SharedPreferences info;
	private String nowUser;
	@Override
	public View onCreateView(final LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_friend, container, false);
		info = getActivity().getSharedPreferences("INFO",0);
		nowUser = info.getString("userName","");
		initData();
		ListView friendList = (ListView)view.findViewById(R.id.lvContact);
		FriendAdapter adapter = new FriendAdapter(getActivity());
		friendList.setAdapter(adapter);
		friendList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				String name = Params.UserInfos.get(position).getUserName();
				String userName = Params.UserInfos.get(position).getFromPerson();
				Log.w("当前您要聊天的好友是：", name);
				if (!userName.equals(nowUser)){
					Intent intent = new Intent();
					intent.setClass(getActivity(), FriendActivity.class);
					intent.putExtra("friendName",name);
					intent.putExtra("friendUserName",userName);
					startActivity(intent);
				}else {
					Intent intent = new Intent();
					intent.setClass(getActivity(), SmileActivity.class);
					startActivity(intent);
				}
			}
		});
		return view;
	}

	private void initData() {
		Params.UserInfos.add(new User(R.drawable.f1,"莎莎","sa"));
		Params.UserInfos.add(new User(R.drawable.f2,"贾","jia"));
	}
}
