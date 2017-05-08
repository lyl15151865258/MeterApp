package com.metter.app.fragment;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.metter.app.AppContext;
import com.metter.app.R;
import com.metter.app.adapter.MyBaseExpandableListAdapter;
import com.metter.app.api.remote.JsMetterApi;
import com.metter.app.base.BaseFragment;
import com.metter.app.bean.DoorPlatelist;
import com.metter.app.bean.FindUserList;
import com.metter.app.bean.ListEntity;
import com.metter.app.bean.User;
import com.metter.app.ui.MainActivity;
import com.metter.app.ui.bean.GroupItemContext;
import com.metter.app.ui.bean.HieChildItem;
import com.metter.app.ui.empty.EmptyLayout;
import com.metter.app.util.StringUtils;
import com.metter.app.util.StringUtils.JSON_TYPE;
import com.metter.app.util.XmlUtils;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.loopj.android.http.AsyncHttpResponseHandler;

/**
 * 查找用户界面
 * 
 * @author FireAnt（http://my.oschina.net/LittleDY）
 * @version 创建时间：2014年12月8日 下午3:50:20
 * 
 */

public class HierarchyInfoFragment extends BaseFragment implements
	OnItemClickListener {
	AppContext appcontext;
    ListView hieInfoListView;
   ViewGroup hieInfoTitleViewGroup;
   public static ArrayList<DoorPlatelist> arraylist ;
   public static DoorPlateAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	    Bundle savedInstanceState) {
		//View view = inflater.inflate(R.layout.hierarchy_info_layout, null);
			View rootView = inflater.inflate(R.layout.hierarchy_info_layout, null);
//    	ViewGroup parent = (ViewGroup) rootView.getParent();
//    	if(parent!=null)
//    		parent.removeView(rootView);
		//ButterKnife.inject(this, view);
		setHasOptionsMenu(true);
		initData();
		initView(rootView);
		
		return rootView;
    }
    
    @Override
    public void initData() {
    	 appcontext = AppContext.getInstance();
    	arraylist = new ArrayList<DoorPlatelist>(); 
    	adapter = new DoorPlateAdapter(getActivity().getApplicationContext(), arraylist);
    }
    
	@Override
    public void initView(View view) {
		hieInfoListView = (ListView) view.findViewById(R.id.hieInfoListView);
		hieInfoTitleViewGroup = (ViewGroup) view.findViewById(R.id.hieInfoTitleViewGroup);
    	hieInfoListView.setAdapter(adapter);	
    	hieInfoListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				
				appcontext.defaultValue.setDefaultCollector(arraylist.get(arg2).getList5());
				appcontext.defaultValue.setDefaultMeter(arraylist.get(arg2).getList6());
				appcontext.saveDefOptValue();
				MainActivity mainActivity = (MainActivity) getActivity();
				mainActivity.updateMeterIdAndImei();
				AppContext.showToast("选中"+arraylist.get(arg2).getList1()+"_"+arraylist.get(arg2).getList2()+"_的水表！");
				
				return false;
			}
		});
    	
	}




    @Override
    public void onClick(View v) {
    }

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		
	}
}
