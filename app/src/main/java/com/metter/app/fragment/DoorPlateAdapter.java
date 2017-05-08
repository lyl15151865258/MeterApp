package com.metter.app.fragment;

import java.util.List;  

import com.metter.app.R;
import com.metter.app.bean.DoorPlatelist;

import android.content.Context;   
import android.view.LayoutInflater;
import android.view.View;  
import android.view.ViewGroup;  
import android.widget.BaseAdapter;   
import android.widget.TextView;  

public class DoorPlateAdapter extends BaseAdapter {
    private List<DoorPlatelist> list;
    private LayoutInflater inflater;
    
    public DoorPlateAdapter(Context context, List<DoorPlatelist> list){
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    	DoorPlatelist doorplatelist = (DoorPlatelist) this.getItem(position);
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.report_info_listitem, null);//group_item_layout
            viewHolder.mTextl1 = (TextView) convertView.findViewById(R.id.text_l1);
            viewHolder.mTextl2 = (TextView) convertView.findViewById(R.id.text_l2);
            viewHolder.mTextl3 = (TextView) convertView.findViewById(R.id.text_l3);
            viewHolder.mTextl4 = (TextView) convertView.findViewById(R.id.text_l4);
            viewHolder.mTextl5 = (TextView) convertView.findViewById(R.id.text_l5);
            viewHolder.mTextl6 = (TextView) convertView.findViewById(R.id.text_l6);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        
        viewHolder.mTextl1.setText(doorplatelist.getList1());
        viewHolder.mTextl2.setText(doorplatelist.getList2());
        viewHolder.mTextl3.setText(doorplatelist.getList3());
        viewHolder.mTextl4.setText(doorplatelist.getList4());
        viewHolder.mTextl5.setText(doorplatelist.getList5());
        viewHolder.mTextl6.setText(doorplatelist.getList6());
        
        return convertView;
    }
    
    public static class ViewHolder{
        public TextView mTextl1;
        public TextView mTextl2;
        public TextView mTextl3;
        public TextView mTextl4;
        public TextView mTextl5;
        public TextView mTextl6;
    }

}