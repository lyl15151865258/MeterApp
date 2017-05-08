package com.metter.app.fragment;

import java.util.List;  

import com.metter.app.R;
import com.metter.app.bean.GPRSlist;

import android.content.Context;   
import android.view.LayoutInflater;
import android.view.View;  
import android.view.ViewGroup;  
import android.widget.BaseAdapter;   
import android.widget.TextView;  

public class GPRSAdapter extends BaseAdapter {
    private List<GPRSlist> list;
    private LayoutInflater inflater;
    
    public GPRSAdapter(Context context, List<GPRSlist> list){
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
    	GPRSlist gprslist = (GPRSlist) this.getItem(position);
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.gprs_info_listitem, null);
            viewHolder.mTextl1 = (TextView) convertView.findViewById(R.id.text_l1);
            viewHolder.mTextl2 = (TextView) convertView.findViewById(R.id.text_l2);
            viewHolder.mTextl3 = (TextView) convertView.findViewById(R.id.text_l3);
            viewHolder.mTextl4 = (TextView) convertView.findViewById(R.id.text_l4);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        
        viewHolder.mTextl1.setText(gprslist.getList1());
        viewHolder.mTextl2.setText(gprslist.getList2());
        viewHolder.mTextl3.setText(gprslist.getList3());
        viewHolder.mTextl4.setText(gprslist.getList4());
        
        return convertView;
    }
    
    public static class ViewHolder{
        public TextView mTextl1;
        public TextView mTextl2;
        public TextView mTextl3;
        public TextView mTextl4;
    }

}