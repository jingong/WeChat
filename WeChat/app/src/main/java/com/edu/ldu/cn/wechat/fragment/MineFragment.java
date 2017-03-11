package com.edu.ldu.cn.wechat.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.edu.ldu.cn.wechat.R;

public class MineFragment extends Fragment
{
	private SharedPreferences info;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_mine, container, false);
		info = getActivity().getSharedPreferences("INFO",0);
		String userName = info.getString("userName","");
		TextView txtName = (TextView)view.findViewById(R.id.name);
		txtName.setText(userName);
		return view;
	}
}
