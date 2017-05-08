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
import com.metter.app.ui.FragmentReportPage;
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

public class HierarchyDataFragment extends BaseFragment implements
	OnItemClickListener {
   AppContext appcontext;
   ListView hieDataListView;
   ViewGroup hieDataTitleViewGroup;
   public static ArrayList<Reportlist> arraylist ;
   public static ReportAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	    Bundle savedInstanceState) {
		//View view = inflater.inflate(R.layout.hierarchy_data_layout, null);
		//if(rootView==null)
    	View rootView = inflater.inflate(R.layout.hierarchy_data_layout, null);
//    	ViewGroup parent = (ViewGroup) rootView.getParent();
//    	if(parent!=null)
//    		parent.removeView(rootView);
		//ButterKnife.inject(this, view);
		setHasOptionsMenu(true);
		initData();
		initView(rootView);
		
		return rootView;
    }
    
    MainActivity mActivity;
	
	@Override
	public void onAttach(Activity activity) {
		mActivity = (MainActivity) activity;
		mActivity.setfHieDataHandler(mHandler);

		super.onAttach(activity);
	}

    
    @Override
    public void initData() {
    	appcontext = AppContext.getInstance();
    	arraylist = new ArrayList<Reportlist>(); 
    	adapter = new ReportAdapter(getActivity().getApplicationContext(), arraylist);
    }
    
	@Override
    public void initView(View view) {
    	hieDataListView = (ListView) view.findViewById(R.id.hieDataListView);
    	hieDataTitleViewGroup = (ViewGroup) view.findViewById(R.id.hieDataTitleViewGroup);
    	hieDataListView.setAdapter(adapter);	
    	hieDataListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				appcontext.defaultValue.setDefaultCollector(arraylist.get(arg2).getList5());
				appcontext.defaultValue.setDefaultMeter(arraylist.get(arg2).getList6());
				appcontext.saveDefOptValue();
				MainActivity mainActivity = (MainActivity) getActivity();
				mainActivity.updateMeterIdAndImei();
				//AppContext.showToast("选中"+arraylist.get(arg2).getList1()+"_"+arraylist.get(arg2).getList2()+"_的水表！");
				return false;
			}
		});
    	
	}
	private void cmdReadMeters(String vals){
		JsMetterApi.exeReadMetersCmd(appcontext.remote.getServiceIp(), appcontext.remote.getServicePort(),
				appcontext.remote.getServiceName(),appcontext.user.getLoginName(),"entranceId",vals,
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
						if (response.equals("\"-2\"")){
							AppContext.showToast("服务未启动，或数据开关(关闭)"); 
		   				} else if (response.equals("\"-1\"")){
		   					AppContext.showToast("TCP服务未启动，请稍后再试"); 
	    				} else if (response.equals("\"0\"")) { 
	    					AppContext.showToast("服务器繁忙，请稍后再试"); 
	    			    } else if (response.equals("\"1\"")) {  
	    			    	AppContext.showToast("服务器正在处理，请稍查看报表"); 
	    			    }
					}});
	}

	
	private void getMeterData(String vals){
		 JsMetterApi.getMeterDataList(appcontext.remote.getServiceIp(), appcontext.remote.getServicePort(),
				 appcontext.remote.getServiceName(), "entranceId",vals,
					new AsyncHttpResponseHandler(){

						@Override
						public void onFailure(int arg0, Header[] arg1,
								byte[] arg2, Throwable arg3) {
							AppContext.showToast(R.string.tip_no_internet);
							
						}

						@Override
						public void onSuccess(int arg0, Header[] arg1,
								byte[] arg2) {
							try {
				            	String recString=new String( arg2 );
				            	if(JSON_TYPE.JSON_TYPE_ARRAY== StringUtils.getJSONType(recString)){
				            			JSONArray  jsarr = new JSONArray(recString);
				            			if(arraylist==null)
				            				arraylist = new ArrayList<Reportlist>(); 
				            			arraylist.clear();
				            			if(jsarr.length()>0){
				            				for(int i =0;i<jsarr.length();i++){
					            				JSONObject o  = jsarr.getJSONObject(i);
					            				arraylist.add(new Reportlist(String.valueOf(i+1),o.getString("entrance"),o.getString("doorPlate"),
					            						o.getString("productType"),o.getString("imei"),o.getString("meterId"),
					            						o.getString("valveStatus"),o.getString("pressure"),o.getString("total"),
					            						o.getString("flowRate"),o.getString("t1Inp"),
					            						o.getString("status"),o.getString("timeInP"),o.getString("createTime")));
					            				//arraylist.add(new Reportlist(String.valueOf(i+1),o.getString("entrance"),o.getString("doorPlate"),o.getString("productType"),o.getString("imei"),o.getString("meterId"),o.getString("valveStatus"),o.getString("sumHeat")+"kW*h",o.getString("total")+"m³",o.getString("flowRate")+"m³/h",o.getString("t1Inp")+"℃",o.getString("t2Inp")+"℃",o.getString("status"),o.getString("createTime")));
						            		}
				            				adapter.notifyDataSetChanged();
				            				FragmentReportPage.rootView.setCurrentItem(2);
				            			}
				            			else{
				            				AppContext.showToast(R.string.tip_login_nodata);
				            			}
				            	}
				            }catch (Exception e) {
				                e.printStackTrace();
				                onFailure(arg0, arg1, arg2, e);
				            }
							
						}
			 
		 });
	}
	
	public Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				String msgtype = (String) msg.obj;
				//AppContext.showToast(msgtype);
				cmdReadMeters(msgtype);
				break;
			case 2:
				
				 msgtype = (String) msg.obj;
				 arraylist.clear();
				 adapter.notifyDataSetChanged();
				 getMeterData(msgtype);
				
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
