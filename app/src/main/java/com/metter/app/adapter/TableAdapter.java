package com.metter.app.adapter;

import java.util.List;  

import com.metter.app.R;
import com.metter.app.bean.Gridlist;

import android.content.Context;   
import android.view.LayoutInflater;
import android.view.View;  
import android.view.ViewGroup;  
import android.widget.BaseAdapter;   
import android.widget.TextView;  

public class TableAdapter extends BaseAdapter {
    private List<Gridlist> list;
    private LayoutInflater inflater;
    
    public TableAdapter(Context context, List<Gridlist> list){
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
    	Gridlist gridlist = (Gridlist) this.getItem(position);
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.cmd_meter_listitem, null);
            viewHolder.mTextl1 = (TextView) convertView.findViewById(R.id.text_l1);
            viewHolder.mTextl2 = (TextView) convertView.findViewById(R.id.text_l2);
            viewHolder.mTextl3 = (TextView) convertView.findViewById(R.id.text_l3);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        
        viewHolder.mTextl1.setText(gridlist.getList1());
        viewHolder.mTextl2.setText(gridlist.getList2());
        viewHolder.mTextl3.setText(gridlist.getList3());
        return convertView;
    }
    
    public static class ViewHolder{
        public TextView mTextl1;
        public TextView mTextl2;
        public TextView mTextl3;
    }

}