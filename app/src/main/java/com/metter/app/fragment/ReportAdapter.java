package com.metter.app.fragment;

import java.util.List;  

import com.metter.app.R;

import android.content.Context;   
import android.view.LayoutInflater;
import android.view.View;  
import android.view.ViewGroup;  
import android.widget.BaseAdapter;   
import android.widget.TextView;  

public class ReportAdapter extends BaseAdapter {
    private List<Reportlist> list;
    private LayoutInflater inflater;
    
    public ReportAdapter(Context context, List<Reportlist> list){
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
    	Reportlist reportlist = (Reportlist) this.getItem(position);
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.report_data_listitem, null);
            viewHolder.mTextl1  = (TextView) convertView.findViewById(R.id.text_l1);
            viewHolder.mTextl2  = (TextView) convertView.findViewById(R.id.text_l2);
            viewHolder.mTextl3  = (TextView) convertView.findViewById(R.id.text_l3);
            viewHolder.mTextl4  = (TextView) convertView.findViewById(R.id.text_l4);
            viewHolder.mTextl5  = (TextView) convertView.findViewById(R.id.text_l5);
            viewHolder.mTextl6  = (TextView) convertView.findViewById(R.id.text_l6);
            viewHolder.mTextl7  = (TextView) convertView.findViewById(R.id.text_l7);
            viewHolder.mTextl8  = (TextView) convertView.findViewById(R.id.text_l8);
            viewHolder.mTextl9  = (TextView) convertView.findViewById(R.id.text_l9);
            viewHolder.mTextl10 = (TextView) convertView.findViewById(R.id.text_l10);
            viewHolder.mTextl11 = (TextView) convertView.findViewById(R.id.text_l11);
            viewHolder.mTextl12 = (TextView) convertView.findViewById(R.id.text_l12);
            viewHolder.mTextl13 = (TextView) convertView.findViewById(R.id.text_l13);
            viewHolder.mTextl14 = (TextView) convertView.findViewById(R.id.text_l14);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        
        viewHolder.mTextl1.setText(reportlist.getList1());
        viewHolder.mTextl2.setText(reportlist.getList2());
        viewHolder.mTextl3.setText(reportlist.getList3());
        viewHolder.mTextl4.setText(reportlist.getList4());
        viewHolder.mTextl5.setText(reportlist.getList5());
        viewHolder.mTextl6.setText(reportlist.getList6());
        viewHolder.mTextl7.setText(reportlist.getList7());
        viewHolder.mTextl8.setText(reportlist.getList8());
        viewHolder.mTextl9.setText(reportlist.getList9());
        viewHolder.mTextl10.setText(reportlist.getList10());
        viewHolder.mTextl11.setText(reportlist.getList11());
        viewHolder.mTextl12.setText(reportlist.getList12());
        viewHolder.mTextl13.setText(reportlist.getList13());
        viewHolder.mTextl14.setText(reportlist.getList14());
        return convertView;
    }
    
    public static class ViewHolder{
        public TextView mTextl1;
        public TextView mTextl2;
        public TextView mTextl3;
        public TextView mTextl4;
        public TextView mTextl5;
        public TextView mTextl6;
        public TextView mTextl7;
        public TextView mTextl8;
        public TextView mTextl9;
        public TextView mTextl10;
        public TextView mTextl11;
        public TextView mTextl12;
        public TextView mTextl13;
        public TextView mTextl14;
    }

}