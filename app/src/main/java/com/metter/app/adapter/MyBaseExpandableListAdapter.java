package com.metter.app.adapter;

import java.util.List;
import java.util.Map;





import com.metter.app.AppContext;
import com.metter.app.R;
import com.metter.app.ui.bean.HieChildItem;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 *
 */
public class MyBaseExpandableListAdapter extends BaseExpandableListAdapter implements OnClickListener {
	
	private Context mContext;
	private List<String> groupTitle;
	private Map<Integer, List<HieChildItem>> childMap;
	private Button groupButton;//
	private int mChildPosition;
	
	public MyBaseExpandableListAdapter(Context context, List<String> groupTitle, Map<Integer, List<HieChildItem>> childMap) {
		this.mContext = context;
		this.groupTitle = groupTitle;
		this.childMap = childMap;
	}
	/*
	 *  Gets the data associated with the given child within the given group
	 */
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return childMap.get(groupPosition).get(childPosition).getTitle();
	}
	/*  
	 */
	@Override
	public long getChildId(int groupPosition, int childPosition) {		
		return childPosition;
	}
	/* 
	 *  Gets a View that displays the data for the given child within the given group
	 */
	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, 
			ViewGroup parent) {
		ChildHolder childHolder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.hie_child_item, null);
			childHolder = new ChildHolder();
			childHolder.childImg = (ImageView) convertView.findViewById(R.id.img_child);
			childHolder.childText = (TextView) convertView.findViewById(R.id.tv_child_text);
			convertView.setTag(childHolder);
		}else {
			childHolder = (ChildHolder) convertView.getTag();
		}
		childHolder.childImg.setBackgroundResource(childMap.get(groupPosition).get(childPosition).getMarkerImgId());
		childHolder.childText.setText(childMap.get(groupPosition).get(childPosition).getTitle());
		
		// 设置控件内容
		//childHolder.childText.setText(childMap.get(groupPosition).get(childPosition).getTitle());
		if (mChildPosition == childPosition) {
			childHolder.childText.setTextColor(mContext.getResources().getColor(
					R.color.actionbar_background));
			convertView.setBackgroundColor(mContext.getResources().getColor(
					R.color.group_item_pressed_bg));
		} else {
			childHolder.childText.setTextColor(mContext.getResources().getColor(
					R.color.text_dark));
			convertView.setBackgroundColor(mContext.getResources().getColor(
					R.color.group_item_bg));
		}
		
		
		
		return convertView;
	}

	/* 
	 */
	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return childMap.get(groupPosition).size();
	}

	/**
	 */
	@Override
	public Object getGroup(int groupPosition) {
		return groupTitle.get(groupPosition);
	}
		
	/**
	 */
	@Override
	public int getGroupCount() {
		return groupTitle.size();
	}

	/**
	 */
	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}
	/* 
	 *Gets a View that displays the given group
	 *return: the View corresponding to the group at the specified position 
	 */
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		GroupHolder groupHolder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.hie_group_item, null);
			groupHolder = new GroupHolder();
			groupHolder.groupImg = (ImageView) convertView.findViewById(R.id.img_indicator);
			groupHolder.groupText = (TextView) convertView.findViewById(R.id.tv_group_text);
			convertView.setTag(groupHolder);
		}else {
			groupHolder = (GroupHolder) convertView.getTag();
		}
		if (isExpanded) {
			groupHolder.groupImg.setBackgroundResource(R.drawable.downarrow);
		}else {
			groupHolder.groupImg.setBackgroundResource(R.drawable.rightarrow);
		}
		groupHolder.groupText.setText(groupTitle.get(groupPosition));
		
		//groupButton = (Button) convertView.findViewById(R.id.btn_group_function);
		//groupButton.setOnClickListener(this);
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		// Indicates whether the child and group IDs are stable across changes to the underlying data
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// Whether the child at the specified position is selectable
		return true;
	}
	/**
	 * show the text on the child and group item
	 */	
	private class GroupHolder
	{
		ImageView groupImg;
		TextView groupText;
	}
	private class ChildHolder
	{
		ImageView childImg;
		TextView childText;
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		//case R.id.btn_group_function:
			//Log.d("MyBaseExpandableListAdapter", "group button");			
		//AppContext.showToast(message);
		default:
			break;
		}
		
	}	
	
	public void setChildSelectedPosition(int position) {
		this.mChildPosition = position;
	}

}
