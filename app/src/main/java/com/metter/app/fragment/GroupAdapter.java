package com.metter.app.fragment;

import java.util.List;

import com.metter.app.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 父ListView适配器
 * 
 * @author zihao
 * 
 */
public class GroupAdapter extends BaseAdapter {

	Context mContext;// 上下文对象
	//String[] mGroupNameArr;// 父item标题数组
	List<String> mGroupNameArr;
	int mPosition = 0;// 选中的位置

	/**
	 * 构造方法
	 * 
	 * @param context
	 *            // 上下文对象
	 * @param groupArr
	 *            // item标题数组
	 */
	public GroupAdapter(Context context, String[] groupArr) {
		this.mContext = context;
		//this.mGroupNameArr = groupArr;
	}
	public GroupAdapter(Context context) {
		this.mContext = context;
	}
	public GroupAdapter(Context applicationContext, List<String> entranceList) {
		this.mContext = applicationContext;
		this.mGroupNameArr = entranceList;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;

		// 初始化布局控件
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.group_item_layout, null);
			holder.groupName = (TextView) convertView.findViewById(R.id.group_textView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		// 设置控件内容
		holder.groupName.setText(mGroupNameArr.get(position));
		if (mPosition == position) {
			holder.groupName.setTextColor(mContext.getResources().getColor(R.color.actionbar_background));
			//convertView.setBackgroundColor(mContext.getResources().getColor(R.color.group_item_pressed_bg));
		} else {
			holder.groupName.setTextColor(mContext.getResources().getColor(R.color.text_dark));
			//holder.groupName.setTextColor(mContext.getResources().getColor(android.R.color.black));
			//convertView.setBackgroundColor(mContext.getResources().getColor(R.color.group_item_bg));
		}

		return convertView;
	}

	/**
	 * 获取item总数
	 */
	@Override
	public int getCount() {
		return mGroupNameArr.size();
	}

	/**
	 * 获取某一个Item的内容
	 */
	@Override
	public Object getItem(int position) {
		return mGroupNameArr.get(position);
	}

	/**
	 * 获取当前item的ID
	 */
	@Override
	public long getItemId(int position) {
		return position;
	}

	static class ViewHolder {
		/** 父Item名称 **/
		TextView groupName;
	}

	/**
	 * 设置选择的位置
	 * 
	 * @param position
	 */
	public void setSelectedPosition(int position) {
		this.mPosition = position;
	}
//	public void setChildData(List<String> group1NameArray) {
//		// TODO Auto-generated method stub
//		this.mGroupNameArr = group1NameArray;
//	}

}
