package com.edu.ldu.cn.wechat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.edu.ldu.cn.wechat.R;
import com.edu.ldu.cn.wechat.bean.Params;
import com.edu.ldu.cn.wechat.bean.User;

public class FriendAdapter extends BaseAdapter{
	private Context mContext;
	public FriendAdapter(Context mContext) {

		this.mContext = mContext;
	}

	@Override
	public int getCount() {
		return Params.UserInfos.size();
	}

	@Override
	public Object getItem(int position) {
		return Params.UserInfos.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		User user = Params.UserInfos.get(position);
		//布局加载器
		LayoutInflater inflater = LayoutInflater.from(mContext);
		ViewHolder holder = null;
		if (convertView == null){//如果没有可用的，生成
			convertView = inflater.inflate(R.layout.friend_item,null,false);
			holder = new ViewHolder();
			holder.friendImg = (ImageView)convertView.findViewById(R.id.friendImg);
			holder.userName = (TextView)convertView.findViewById(R.id.username);
			convertView.setTag(holder);
		}else {//如果有，直接取缓存的
			holder = (ViewHolder)convertView.getTag();
		}
		//通过holder赋值
		holder.userName.setText(user.getUserName());
		holder.friendImg.setImageResource(user.getImgId());

		return convertView;
	}
	class ViewHolder{
		public TextView userName;
		public ImageView friendImg;
	}
}
