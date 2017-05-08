package com.metter.app.fragment;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.metter.app.AppContext;
import com.metter.app.R;
import com.metter.app.adapter.MyBaseExpandableListAdapter;
import com.metter.app.adapter.ViewPageFragmentAdapter;
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

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
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

public class HierarchyFragment extends BaseFragment implements
	OnItemClickListener {
	
    ExpandableListView expandList;
    
    GroupAdapter entranceAdapter = null;
	ListView entranceListView;
	public static Map<Integer, List<HieChildItem>> buildingData;//child的数据源
	public static MyBaseExpandableListAdapter myAdapter;
	public static List<String> supplierList;//group的数据源
	public static List<String> buildingList;//group的数据源
	public static List<GroupItemContext> supplierListContext ;
	public static Map<Integer,List<GroupItemContext>> buildingMapContext ;
	List<GroupItemContext> entranceListContext;
	AppContext appcontext;
	private int curSupplierIndex;
	public static List<String> entranceList ;
	public static String lastEntranceId = "" ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	    Bundle savedInstanceState) {
    	
    	View rootView = inflater.inflate(R.layout.hierarchy_layout, null);
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
    	
    	supplierListContext = new ArrayList<GroupItemContext>();
		supplierList = new ArrayList<String>();
		buildingMapContext = new HashMap<Integer,List<GroupItemContext>>();
		buildingList = new ArrayList<String>();
		entranceList = new ArrayList<String>();
		entranceListContext = new ArrayList<GroupItemContext>();
		
		buildingData = new HashMap<Integer, List<HieChildItem>>();
		myAdapter = new MyBaseExpandableListAdapter(this.getApplication(), supplierList, buildingData);
		entranceAdapter = new GroupAdapter(this.getActivity().getApplicationContext(), entranceList);
    }
    
    @Override
    public void initView(View view) {
    	expandList = (ExpandableListView) view.findViewById(R.id.hieExpListView);
		expandList.setOnGroupClickListener(new OnGroupClickListener() {
			
			@Override
			public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition,
					long id) {
				
			
				 boolean expanded = parent.isGroupExpanded(groupPosition);  
				 curSupplierIndex = groupPosition;
			     if (!expanded) {  
			    	 if(entranceList!=null&&entranceList.size()>0){
			    		 entranceList.clear();
			    		 entranceListContext.clear();
			    	 }
			        // AppContext.showToast("正在加载楼栋数据。。。");
			         JsMetterApi.getTreeList(appcontext.remote.getServiceIp(), appcontext.remote.getServicePort(),
			        		 appcontext.remote.getServiceName(), "buildingId",String.valueOf(supplierListContext.get(groupPosition).getId()),
								new AsyncHttpResponseHandler(){

									@Override
									public void onFailure(int arg0,
											Header[] arg1, byte[] arg2,
											Throwable arg3) {
										AppContext.showToast(R.string.tip_no_internet);
									}

									@Override
									public void onSuccess(int arg0,
											Header[] arg1, byte[] arg2) {
										 try {
								            	String recString=new String( arg2 );
								            	if(JSON_TYPE.JSON_TYPE_OBJECT== StringUtils.getJSONType(recString)){
									            	JSONObject jsobj =new JSONObject(recString);
									            	if(jsobj.has("fieldName")){
									            		String strDataType = jsobj.getString("fieldName");
									            		if("buildingId".equals(strDataType)){
											            	JSONArray  jsonArray = new JSONObject(recString).getJSONArray("list");
											            	List<HieChildItem> childItems = new ArrayList<HieChildItem>();
											            	List<GroupItemContext> childItemsData = new ArrayList<GroupItemContext>();
											            	for(int i=0;i<jsonArray.length();i++){
											            		JSONObject jsonObject = jsonArray.getJSONObject(i);
											            		childItems.add(new HieChildItem(jsonObject.getString("text"), R.drawable.hie_building));
											            		childItemsData.add(new GroupItemContext(jsonObject.getInt("id"), jsonObject.getString("text")));
											            	}
											            	buildingMapContext.put(curSupplierIndex, childItemsData);
											            	buildingData.put(curSupplierIndex, childItems);
											            	myAdapter.notifyDataSetChanged();
											            	expandList.expandGroup(curSupplierIndex);
										            	}
								            	}
							            	}
							            } catch (Exception e) {
							                e.printStackTrace();
							                onFailure(arg0, arg1, arg2, e);
							            }
									}
			        	 
			         });
			        return true;  
			     }  

				return false;
			}
		});
		
		//在drawable文件夹下新建了indicator.xml，下面这个语句也可以实现group伸展收缩时的indicator变化
		//expandList.setGroupIndicator(this.getResources().getDrawable(R.drawable.indicator));
		expandList.setGroupIndicator(null);//这里不显示系统默认的group indicator
		expandList.setAdapter(myAdapter);
		registerForContextMenu(expandList);//给ExpandListView添加上下文菜单	
		//child子项的单击事件
		expandList.setOnChildClickListener(new OnChildClickListener() {
			
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				myAdapter.setChildSelectedPosition(childPosition);
				myAdapter.notifyDataSetChanged();
				 JsMetterApi.getTreeList(appcontext.remote.getServiceIp(), appcontext.remote.getServicePort(),
						 appcontext.remote.getServiceName(), "entranceId",String.valueOf(buildingMapContext.get(groupPosition).get(childPosition).getId()),
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
										if(JSON_TYPE.JSON_TYPE_OBJECT== StringUtils.getJSONType(recString)){
							            	JSONObject jsobj =new JSONObject(recString);
							            	if(jsobj.has("fieldName")){
							            		String strDataType = jsobj.getString("fieldName");
								            	 if("entranceId".equals(strDataType)){
								            		JSONArray  jsonArray = new JSONObject(recString).getJSONArray("list");
								            		entranceList.clear();
								            		entranceListContext.clear();
									            	for(int i=0;i<jsonArray.length();i++){
									            		JSONObject jsonObject = jsonArray.getJSONObject(i);
									            		entranceList.add(jsonObject.getString("text"));
									            		entranceListContext.add(new GroupItemContext(jsonObject.getInt("id"), jsonObject.getString("text")));
									            	}
									            	entranceAdapter.notifyDataSetChanged();
								            	}
							            	}
										}
									} catch (Exception e) {
						                e.printStackTrace();
						                onFailure(arg0, arg1, arg2, e);
						            }
									
								}
				 });
				
				return true;
			}
		});	
		expandList.setOnGroupExpandListener(new OnGroupExpandListener() {
			@Override
			public void onGroupExpand(int groupPosition) {
				for (int i = 0; i < supplierList.size(); i++) {
					if (i != groupPosition) {
						expandList.collapseGroup(i);
					}
				}
			}
		});
		entranceListView = (ListView) view.findViewById(R.id.entranceListView);
		entranceListView.setAdapter(entranceAdapter);
		entranceListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				//entranceAdapter.setSelectedPosition(arg2);
				entranceAdapter.setSelectedPosition(arg2);
				entranceAdapter.notifyDataSetChanged();
				lastEntranceId  = String.valueOf(entranceListContext.get(arg2).getId());
				// AppContext.showToast("正在加载单元数据。。。");
				 JsMetterApi.getMeterInfoList(appcontext.remote.getServiceIp(), appcontext.remote.getServicePort(),
						 appcontext.remote.getServiceName(), "entranceId",lastEntranceId,
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
						            			if(HierarchyInfoFragment.arraylist==null)
						            				HierarchyInfoFragment.arraylist = new ArrayList<DoorPlatelist>(); 
						            			HierarchyInfoFragment.arraylist.clear();
						            			if(jsarr.length()>0){
						            				for(int i =0;i<jsarr.length();i++){
							            				JSONObject o  = jsarr.getJSONObject(i);
							            				HierarchyInfoFragment.arraylist.add(new DoorPlatelist(String.valueOf(i+1),o.getString("doorPlate"),o.getString("userName"),o.getString("productType"),o.getString("imei"),o.getString("meterId")));
							            			}
							            			HierarchyInfoFragment.adapter.notifyDataSetChanged();
							            			FragmentReportPage.adapter.mViewPager.setCurrentItem(1);
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
				 JsMetterApi.getMeterDataList(appcontext.remote.getServiceIp(), appcontext.remote.getServicePort(),
						 appcontext.remote.getServiceName(), "entranceId",String.valueOf(entranceListContext.get(arg2).getId()),
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
						            			if(HierarchyDataFragment.arraylist==null)
						            				HierarchyDataFragment.arraylist = new ArrayList<Reportlist>(); 
						            			HierarchyDataFragment.arraylist.clear();
						            			if(jsarr.length()>0){
						            				for(int i =0;i<jsarr.length();i++){
							            				JSONObject o  = jsarr.getJSONObject(i);
							            				HierarchyDataFragment.arraylist.add(new Reportlist(String.valueOf(i+1),o.getString("entrance"),o.getString("doorPlate"),
							            						o.getString("productType"),o.getString("imei"),o.getString("meterId"),
							            						o.getString("valveStatus"),o.getString("pressure"),o.getString("total"),
							            						o.getString("flowRate"),o.getString("t1Inp"),o.getString("status"),o.getString("timeInP"),
							            						o.getString("createTime")));
							            				//HierarchyDataFragment.arraylist.add(new Reportlist(String.valueOf(i+1),o.getString("entrance"),o.getString("doorPlate"),o.getString("productType"),o.getString("imei"),o.getString("meterId"),o.getString("valveStatus"),o.getString("sumHeat")+"kW*h",o.getString("total")+"m³",o.getString("flowRate")+"m³/h",o.getString("t1Inp")+"℃",o.getString("t2Inp")+"℃",o.getString("status"),o.getString("createTime")));
							            				//HierarchyDataFragment.arraylist.add(new Reportlist(String.valueOf(i+1),o.getString("entrance"),o.getString("doorPlate"),o.getString("productType"),o.getString("imei"),o.getString("meterId"),o.getString("valveStatus"),o.getString("sumHeat")+"kW*h",o.getString("total")+"m³",o.getString("flowRate")+"m³/h",o.getString("t1Inp")+"℃",o.getString("t2Inp")+"℃",o.getString("status"),o.getString("createTime")));
								            			
						            				}
						            				HierarchyDataFragment.adapter.notifyDataSetChanged();
							            			//FragmentReportPage.adapter.mViewPager.setCurrentItem(1);
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
		});
		
	}



	 public final AsyncHttpResponseHandler mHandler = new AsyncHttpResponseHandler() {

	        @Override
	        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
	            try {
	            	String recString=new String( arg2 );
	            	if(JSON_TYPE.JSON_TYPE_ARRAY== StringUtils.getJSONType(recString)){
	            			JSONArray  jsarr = new JSONArray(recString);
	            			if(HierarchyInfoFragment.arraylist==null)
	            				HierarchyInfoFragment.arraylist = new ArrayList<DoorPlatelist>(); 
	            			for(int i =0;i<jsarr.length();i++){
	            				JSONObject o  = jsarr.getJSONObject(i);
	            				HierarchyInfoFragment.arraylist.add(new DoorPlatelist(String.valueOf(i+1),o.getString("doorPlate"),o.getString("userName"),o.getString("productType"),o.getString("imei"),o.getString("meterId")));
	            			}
	            			HierarchyInfoFragment.adapter.notifyDataSetChanged();
	            			FragmentReportPage.adapter.mViewPager.setCurrentItem(1);
		            	
	            	}else if(JSON_TYPE.JSON_TYPE_OBJECT== StringUtils.getJSONType(recString)){
		            	JSONObject jsobj =new JSONObject(recString);
		            	if(jsobj.has("fieldName")){
		            		String strDataType = jsobj.getString("fieldName");
			            	if("buildingId".equals(strDataType)){
				            	JSONArray  jsonArray = new JSONObject(recString).getJSONArray("list");
				            	List<HieChildItem> childItems = new ArrayList<HieChildItem>();
				            	List<GroupItemContext> childItemsData = new ArrayList<GroupItemContext>();
				            	for(int i=0;i<jsonArray.length();i++){
				            		JSONObject jsonObject = jsonArray.getJSONObject(i);
				            		childItems.add(new HieChildItem(jsonObject.getString("text"), R.drawable.icon_explore_event));
				            		childItemsData.add(new GroupItemContext(jsonObject.getInt("id"), jsonObject.getString("text")));
				            	}
				            	buildingMapContext.put(curSupplierIndex, childItemsData);
				            	buildingData.put(curSupplierIndex, childItems);
				            	HierarchyFragment.myAdapter.notifyDataSetChanged();
				            	expandList.expandGroup(curSupplierIndex);
			            	}else if("entranceId".equals(strDataType)){
			            		JSONArray  jsonArray = new JSONObject(recString).getJSONArray("list");
			            		entranceList.clear();
			            		entranceListContext.clear();
				            	for(int i=0;i<jsonArray.length();i++){
				            		JSONObject jsonObject = jsonArray.getJSONObject(i);
				            		entranceList.add(jsonObject.getString("text"));
				            		entranceListContext.add(new GroupItemContext(jsonObject.getInt("id"), jsonObject.getString("text")));
				            	}
				            	entranceAdapter.notifyDataSetChanged();
			            	}
		            	}
	            	}
	            } catch (Exception e) {
	                e.printStackTrace();
	                onFailure(arg0, arg1, arg2, e);
	            }
	        }

	        @Override
	        public void onFailure(int arg0, Header[] arg1, byte[] arg2,
	                Throwable arg3) {
	            AppContext.showToast(R.string.tip_no_internet);
	        }
	    };
	

    @Override
    public void onClick(View v) {
    }

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		
	}
	
//	@Override
//    protected void executeOnLoadDataSuccess(List<News> data) {
//        if (mCatalog == NewsList.CATALOG_WEEK
//                || mCatalog == NewsList.CATALOG_MONTH) {
//            mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
//            if (mState == STATE_REFRESH)
//                mAdapter.clear();
//            mAdapter.addData(data);
//            mState = STATE_NOMORE;
//            mAdapter.setState(ListBaseAdapter.STATE_NO_MORE);
//            return;
//        }
//        super.executeOnLoadDataSuccess(data);
//    }
}
