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
import com.metter.app.bean.GPRSlist;
import com.metter.app.bean.ListEntity;
import com.metter.app.bean.User;
import com.metter.app.fragment.CmdMeterFragment.MyThread;
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
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

public class GprsInfoFragment extends BaseFragment implements
	OnItemClickListener {
	AppContext appcontext;
    ListView gprsInfoListView;
    ViewGroup gprsInfoTitleViewGroup;
    ArrayList<GPRSlist> arraylist ;
    GPRSAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	    Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.gprs_info_layout, null);
		setHasOptionsMenu(true);
		initData();
		initView(rootView);
		return rootView;
    }
    MainActivity mActivity;
    @Override
	public void onAttach(Activity activity) {
		mActivity = (MainActivity) activity;
		mActivity.setfGprsinfoHandler(mHandler);

		super.onAttach(activity);
	}
    
    @Override
    public void initData() {
    	 appcontext = AppContext.getInstance();
    	arraylist = new ArrayList<GPRSlist>(); 
    	adapter = new GPRSAdapter(getActivity().getApplicationContext(), arraylist);
    }
    
	@Override
    public void initView(View view) {
		gprsInfoListView = (ListView) view.findViewById(R.id.gprsInfoListView);
		gprsInfoTitleViewGroup = (ViewGroup) view.findViewById(R.id.gprsInfoTitleViewGroup);
		gprsInfoListView.setAdapter(adapter);	
		gprsInfoListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				
				return false;
			}
		});
    	
	}
	private void getGprsInfoList() {
		JsMetterApi.exeCmdGetGprsInfo(appcontext.remote.getServiceIp(), appcontext.remote.getServicePort(),
				appcontext.remote.getServiceName(),
				new AsyncHttpResponseHandler(){

					@Override
					public void onFailure(int arg0, Header[] arg1,
							byte[] arg2, Throwable arg3) {
						AppContext.showToast(R.string.tip_no_internet);
						
					}

					@Override
					public void onSuccess(int arg0, Header[] arg1,
							byte[] arg2) {
						String response=new String( arg2 );
						
						response = response.replace("[", "").replace("]", "").replace("\"","");
	    			    	String[] sarr = response.split(",");	
	    					try
	    					 {
	    				     	String s0,s1,s2="";
	    				     	String[] s ;
	    						for (int i=0;i<sarr.length;i++) {
	    							s =sarr[i].split("&");	
	    							try{
	    								s0=s[0];
	    							}catch(Exception e){
	    								s0="";
	    							}
	    							try{
	    								s1=s[1];
	    							}catch(Exception e){
	    								s1="";
	    							}
	    							try{
	    								s2=s[2];
	    							}catch(Exception e){
	    								s2="";
	    							}
	    							if (s0.length()!=0) {
	    								arraylist.add(new GPRSlist(String.valueOf(i+1),s0,s1,s2));
	    						    }
	    						}
	    						adapter.notifyDataSetChanged();
	    					 }catch(Exception e){
	    				     }
	    			    
					}});
	}

	public Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				String msgtype = (String) msg.obj;
				if("getgprsstate".equals(msgtype)){
					arraylist.clear();
					adapter.notifyDataSetChanged();
					getGprsInfoList();
				}
				break;
			}
		}

		
	};

    @Override
    public void onClick(View v) {
    }

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		
	}
}
