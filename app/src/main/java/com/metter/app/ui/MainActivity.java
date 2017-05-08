package com.metter.app.ui;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.metter.app.AppManager;
import com.metter.app.AppConfig;
import com.metter.app.api.remote.JsMetterApi;
import com.metter.app.base.BaseActivity;
import com.metter.app.fragment.HierarchyFragment;
import com.metter.app.ui.bean.GroupItemContext;
import com.metter.app.AppContext;
import com.metter.app.R;
import com.metter.app.widget.MyFragmentTabHost;

import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.TabHost.TabContentFactory;
import android.widget.TabHost.TabSpec;

public class MainActivity extends BaseActivity implements ActionBar.OnNavigationListener, View.OnClickListener {

    private DoubleClickExitHelper mDoubleClickExit;
    //定义FragmentTabHost对象
    public static MyFragmentTabHost mTabHost;

    //定义一个布局
    private LayoutInflater layoutInflater;

    AppContext appcontext;

    View mAddBt;
    /**
     * The serialization (saved instance state) Bundle key representing the
     * current dropdown position.
     */
    private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";

    private Handler fCmdMeterHandler;
    private Handler fReportHandler;
    private Handler fDeviceHandler;
    private Handler fGprsinfoHandler;
    private Handler fHierarchyHandler;
    private Handler fHieDataHandler;
    private Handler fHieInfoHandler;


    public Handler getfHieDataHandler() {
        return fHieDataHandler;
    }


    public void setfHieDataHandler(Handler fHieDataHandler) {
        this.fHieDataHandler = fHieDataHandler;
    }


    public Handler getfHieInfoHandler() {
        return fHieInfoHandler;
    }


    public void setfHieInfoHandler(Handler fHieInfoHandler) {
        this.fHieInfoHandler = fHieInfoHandler;
    }


    public Handler getfHierarchyHandler() {
        return fHierarchyHandler;
    }


    public void setfHierarchyHandler(Handler fHierarchyHandler) {
        this.fHierarchyHandler = fHierarchyHandler;
    }


    public Handler getfGprsinfoHandler() {
        return fGprsinfoHandler;
    }


    public void setfGprsinfoHandler(Handler fGprsinfoHandler) {
        this.fGprsinfoHandler = fGprsinfoHandler;
    }


    public Handler getfReportHandler() {
        return fReportHandler;
    }


    public void setfReportHandler(Handler fReportHandler) {
        this.fReportHandler = fReportHandler;
    }


    public Handler getfDeviceHandler() {
        return fDeviceHandler;
    }


    public void setfDeviceHandler(Handler fDeviceHandler) {
        this.fDeviceHandler = fDeviceHandler;
    }


    public Handler getfCmdMeterHandler() {
        return fCmdMeterHandler;
    }


    public void setfCmdMeterHandler(Handler fCmdMeterHandler) {
        this.fCmdMeterHandler = fCmdMeterHandler;
    }


    @Override
    protected int getLayoutId() {
        // TODO Auto-generated method stub
        return R.layout.activity_main;
    }


    @Override
    public void initData() {
        // TODO Auto-generated method stub
        appcontext = AppContext.getInstance();

    }

    /**
     * 初始化组件
     */
    @Override
    public void initView() {

        // Set up the action bar to show a dropdown list.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        // Set up the dropdown list navigation in the action bar.
        actionBar.setListNavigationCallbacks(
                // Specify a SpinnerAdapter to populate the dropdown list.
                new ArrayAdapter<String>(actionBar.getThemedContext(), android.R.layout.simple_list_item_1, android.R.id.text1, getResources().getStringArray(
                        R.array.main_optionpage_arrays)), this);

        //	initView();
        AppManager.getAppManager().addActivity(this);

        mDoubleClickExit = new DoubleClickExitHelper(this);
        //实例化布局对象
        layoutInflater = LayoutInflater.from(this);

        //实例化TabHost对象，得到TabHost
        mTabHost = (MyFragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.container);
        MainTab[] tabs = MainTab.values();
        final int size = tabs.length;
        for (int i = 0; i < size; i++) {
            MainTab mainTab = tabs[i];
            TabSpec tab = mTabHost.newTabSpec(getString(mainTab.getResName()));
            View indicator = LayoutInflater.from(getApplicationContext()).inflate(R.layout.tab_indicator, null);
            TextView title = (TextView) indicator.findViewById(R.id.tab_title);
            Drawable drawable = this.getResources().getDrawable(mainTab.getResIcon());
            title.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
            if (i == 2) {
                indicator.setVisibility(View.INVISIBLE);
                mTabHost.setNoTabChangedTag(getString(mainTab.getResName()));
            }
            title.setText(getString(mainTab.getResName()));
            tab.setIndicator(indicator);
            tab.setContent(new TabContentFactory() {

                @Override
                public View createTabContent(String tag) {
                    return new View(MainActivity.this);
                }
            });
            mTabHost.addTab(tab, mainTab.getClz(), null);

        }

        mAddBt = findViewById(R.id.quick_option_iv);
        // 中间按键图片触发
        mAddBt.setOnClickListener(this);
    }


    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore the previously serialized current dropdown position.
        if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
            getSupportActionBar().setSelectedNavigationItem(
                    savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // Serialize the current dropdown position.
        outState.putInt(STATE_SELECTED_NAVIGATION_ITEM, getSupportActionBar()
                .getSelectedNavigationIndex());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

//		ActionBar actionBar = getSupportActionBar();
//        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
//        actionBar.setDisplayShowTitleEnabled(true);
        //actionBar.setTitle(mTitle);
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(int position, long id) {
        // When the given dropdown item is selected, show its contents in the
        // container view.
//		getSupportFragmentManager()
//				.beginTransaction()
//				.replace(R.id.container,
//						PlaceholderFragment.newInstance(position + 1)).commit();
        if (position == 1) {
            //	AppContext.showToast(R.string.unlogin);
            //UIHelper.showLoginActivity(this);
            Intent intent = new Intent(this, LoginActivity.class);
            //context.startActivity(intent);
            startActivityForResult(intent, AppConfig.LOGIN_CALLBACK);
            //Intent intent = new Intent(this, LoginActivity.class);
            // startActivity(intent);
        }
        //Toast.makeText(getApplicationContext(),"当前ID:"+position,Toast.LENGTH_SHORT).show();
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setSelectedNavigationItem(0);
        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case AppConfig.LOGIN_CALLBACK:
                //JsMetterApi.getHierarchy()
                //AppContext.showToast(AppContext.user.getLoginName());
                switch (mTabHost.getCurrentTab()) {
                    case 0:
                        if (AppContext.login) {
                            if (appcontext.user.getSupplierId() == -1)
                                JsMetterApi.getTreeList(appcontext.remote.getServiceIp(), appcontext.remote.getServicePort(),
                                        appcontext.remote.getServiceName(), "supplierId", "-1", mHandler);
                            else {
                                if (appcontext.user.getExchangStationId() != -1)
                                    JsMetterApi.getTreeList(appcontext.remote.getServiceIp(), appcontext.remote.getServicePort(),
                                            appcontext.remote.getServiceName(), "villageId", String.valueOf(appcontext.user.getExchangStationId()), mHandler);
                                else
                                    JsMetterApi.getTreeList(appcontext.remote.getServiceIp(), appcontext.remote.getServicePort(),
                                            appcontext.remote.getServiceName(), "exchangStationId", String.valueOf(appcontext.user.getSupplierId()), mHandler);
                            }
                        }
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    default:
                        break;
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    public final AsyncHttpResponseHandler mHandler = new AsyncHttpResponseHandler() {

        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            try {
                String recString = new String(arg2);
                //AppContext.showToast(recString);
                JSONArray jsonArray = new JSONObject(recString).getJSONArray("list");
                List<String> strArray = new ArrayList<String>();
                HierarchyFragment.supplierListContext.clear();
                HierarchyFragment.supplierList.clear();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    //strArray.add(jsonObject.getString("text")) ;
                    HierarchyFragment.supplierListContext.add(new GroupItemContext(jsonObject.getInt("id"), jsonObject.getString("text")));
                    HierarchyFragment.supplierList.add(jsonObject.getString("text"));
                }
                //HierarchyFragment.supplierList = strArray;
                //HierarchyFragment.myAdapter.
                //HierarchyFragment.groupAdapter.setChildData(strArray);
                HierarchyFragment.myAdapter.notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
                onFailure(arg0, arg1, arg2, e);
            }
        }

        @Override
        public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                              Throwable arg3) {
            AppContext.showToast(R.string.tip_login_error_for_network);
        }
    };


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container,
                    false);
            TextView textView = (TextView) rootView
                    .findViewById(R.id.section_label);
            textView.setText(Integer.toString(getArguments().getInt(
                    ARG_SECTION_NUMBER)));
            return rootView;
        }
    }


    public void updateMeterIdAndImei() {

        Message msg = new Message();
        msg.obj = "updatemeterid";
        msg.what = 1;
        if (fCmdMeterHandler != null)
            fCmdMeterHandler.sendMessage(msg);
        msg = new Message();
        msg.obj = "updatemeterid";
        msg.what = 1;
        if (fDeviceHandler != null)
            fDeviceHandler.sendMessage(msg);

        mTabHost.setCurrentTab(1);
    }


    private void showDialog() {
        if (HierarchyFragment.lastEntranceId != "") {
            AlertDialog dialog1 = new AlertDialog.Builder(MainActivity.this)
                    .setTitle("提示：")
                    .setMessage("请选择操作项！")
                    .setNeutralButton(R.string.user_sendtogprs,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    if (fHieDataHandler != null) {
                                        Message msg = new Message();
                                        msg.obj = HierarchyFragment.lastEntranceId;
                                        msg.what = 1;
                                        fHieDataHandler.sendMessage(msg);
                                    }
                                }
                            })
                    .setPositiveButton(R.string.user_getfromgprs,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    Message msg = new Message();
                                    msg.obj = HierarchyFragment.lastEntranceId;
                                    msg.what = 2;
                                    fHieDataHandler.sendMessage(msg);


                                } //第二个按钮设置
                            })
                    .setNegativeButton(R.string.cancle,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    //Toast.makeText(getApplicationContext(),"this software made by xiaoyan",Toast.LENGTH_SHORT).show();
                                } //第二个按钮设置
                            })
                    .create();  //创建对话框
            dialog1.show(); //显示对话框
        }

    }


    private void showQuickOption() {

        if (AppContext.login) {
            switch (mTabHost.getCurrentTab()) {
                case 0:
                    switch (FragmentReportPage.rootView.getCurrentItem()) {
                        case 0:
                            if (appcontext.user.getSupplierId() == -1)
                                JsMetterApi.getTreeList(appcontext.remote.getServiceIp(), appcontext.remote.getServicePort(),
                                        appcontext.remote.getServiceName(), "supplierId", "-1", mHandler);
                            else {
                                if (appcontext.user.getExchangStationId() != -1)
                                    JsMetterApi.getTreeList(appcontext.remote.getServiceIp(), appcontext.remote.getServicePort(),
                                            appcontext.remote.getServiceName(), "villageId", String.valueOf(appcontext.user.getExchangStationId()), mHandler);
                                else
                                    JsMetterApi.getTreeList(appcontext.remote.getServiceIp(), appcontext.remote.getServicePort(),
                                            appcontext.remote.getServiceName(), "exchangStationId", String.valueOf(appcontext.user.getSupplierId()), mHandler);
                            }
                            break;
                        case 1:
                            showDialog();
                            break;
                        case 2:
                            showDialog();
                            break;

                    }

                    break;
                case 1:
                    switch (FragmentDevicePage.curView.getCurrentItem()) {
                        case 0:
                            break;
                        case 1:
                            if (fCmdMeterHandler != null) {
                                Message msg = new Message();
                                msg.obj = "execmd";
                                msg.what = 1;
                                fCmdMeterHandler.sendMessage(msg);
                            }

                            break;
                    }

                    break;
                case 2:
                    break;
                case 3:
                    switch (FragmentCollectorPage.curView.getCurrentItem()) {
                        case 0:
                            if (fGprsinfoHandler != null) {
                                Message msg = new Message();
                                msg.obj = "getgprsstate";
                                msg.what = 1;
                                fGprsinfoHandler.sendMessage(msg);
                            }

                            break;
                        default:
                            break;
                    }
                    break;
                case 4:
                    break;
                default:
                    break;
            }
        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            //context.startActivity(intent);
            startActivityForResult(intent, AppConfig.LOGIN_CALLBACK);

        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            // 点击了快速操作按钮
            case R.id.quick_option_iv:
                showQuickOption();
                break;

            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 是否退出应用
            if (AppContext.get(AppConfig.KEY_DOUBLE_CLICK_EXIT, true)) {
                return mDoubleClickExit.onKeyDown(keyCode, event);
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}
