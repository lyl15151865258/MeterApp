package com.metter.app.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.metter.app.AppConfig;
import com.metter.app.AppContext;
import com.metter.app.R;
import com.metter.app.api.remote.JsMetterApi;
import com.metter.app.base.BaseFragment;
import com.metter.app.ui.LoginActivity;
import com.metter.app.ui.MainActivity;
import com.metter.app.util.StringUtils;

import org.apache.http.Header;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.loopj.android.http.AsyncHttpResponseHandler;

/**
 * 查找用户界面
 *
 * @author FireAnt（http://my.oschina.net/LittleDY）
 * @version 创建时间：2014年12月8日 下午3:50:20
 */

public class DeviceContolFragment extends BaseFragment implements OnItemClickListener {
    private int[] imageRes = {R.drawable.kaifa, R.drawable.guanfa,
            R.drawable.guanbimbus, R.drawable.xianshibiaohao, R.drawable.shuiliangjianding, R.drawable.xianshishijian,
            R.drawable.dispa1, R.drawable.dispa2, R.drawable.dispa3,
            R.drawable.xianshireliang, R.drawable.fenjiedian, R.drawable.banben
    };
    private GridView controlManuGridView;
    private EditText et_colection_no;
    private EditText et_meter_no;
    private String collector_no = "";
    private String meter_no = "";
    private AppContext appcontext;
    private MainActivity mActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.control_meter, null);
        setHasOptionsMenu(true);
        initData();
        initView(view);
        return view;
    }


    @Override
    public void onAttach(Activity activity) {
        mActivity = (MainActivity) activity;
        mActivity.setfDeviceHandler(mHandler);
        super.onAttach(activity);
    }

    @Override
    public void initData() {
        appcontext = AppContext.getInstance();
    }

    @Override
    public void initView(View view) {
        String[] itemNames = {this.getString(R.string.control_openvalve), this.getString(R.string.control_closevalve),
                this.getString(R.string.control_closembus), this.getString(R.string.control_showmeterid),
                this.getString(R.string.control_showtotal), this.getString(R.string.control_showtime),
                this.getString(R.string.control_showA1), this.getString(R.string.control_showA2),
                this.getString(R.string.control_showA3), this.getString(R.string.control_showheat),
                this.getString(R.string.control_showdiv), this.getString(R.string.control_showver)};
        et_colection_no = (EditText) view.findViewById(R.id.et_colection_no);
        et_meter_no = (EditText) view.findViewById(R.id.et_meter_no);
        et_colection_no.setText(appcontext.defaultValue.getDefaultCollector());
        et_meter_no.setText(appcontext.defaultValue.getDefaultMeter());

        controlManuGridView = (GridView) view.findViewById(R.id.controlManuGridView);
        List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
        int length = itemNames.length;
        for (int i = 0; i < length; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("ItemImageView", imageRes[i]);
            map.put("ItemTextView", itemNames[i]);
            data.add(map);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(),
                data, R.layout.control_manu_item, new String[]{"ItemImageView",
                "ItemTextView"}, new int[]{R.id.ItemImageView,
                R.id.ItemTextView});
        controlManuGridView.setAdapter(simpleAdapter);
        controlManuGridView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                String tx = "";
                switch (checkPostInfo()) {
                    case 0:
                        switch (arg2) {
                            case 0:
                                String openvalve = openValve(meter_no, "20", "001111");//开阀
                                remoteCmdControle(openvalve);
                                break;
                            case 1:
                                String closevalve = closeValve(meter_no, "20", "001111");//管阀
                                remoteCmdControle(closevalve);
                                break;
                            case 2:
                                tx = "6848111111110011110403A018996E16";//闭合MBUS
                                remoteCmdControle(tx);
                                break;
                            case 3:
                                String displayMeterId = "6820AAAAAAAAAAAAAA1A039A4F003416";//显示表号
                                remoteCmdControle(displayMeterId);
                                break;
                            case 4:
                                String waterverification = "6820AAAAAAAAAAAAAA1A039A7F006416";//水量检定
                                remoteCmdControle(waterverification);
                                break;
                            case 5:
                                tx = "6820AAAAAAAAAAAAAA1A039A1F000416";//显示时间
                                remoteCmdControle(tx);
                                break;
                            case 6:
                                tx = "6820AAAAAAAAAAAAAA3E03901F001E16";//显示A1
                                remoteCmdControle(tx);
                                break;
                            case 7:
                                tx = "6820AAAAAAAAAAAAAA3E03902F002E16";//显示A2
                                remoteCmdControle(tx);
                                break;
                            case 8:
                                tx = "6820AAAAAAAAAAAAAA3E03903F003E16";//显示A3
                                remoteCmdControle(tx);
                                break;
                            case 9:
                                tx = "6820AAAAAAAAAAAAAA1A039AE400C916";//显示热量
                                remoteCmdControle(tx);
                                break;
                            case 10:
                                tx = "6820AAAAAAAAAAAAAA1A039A3F002416";//显示分界点
                                remoteCmdControle(tx);
                                break;
                            case 11:
                                tx = "6820AAAAAAAAAAAAAA1A039A6F005416";//显示版本
                                remoteCmdControle(tx);
                                break;
                            default:
                                break;
                        }


                        break;
                    case 1:
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivityForResult(intent, AppConfig.LOGIN_CALLBACK);
                        break;
                    default:
                        break;
                }

            }
        });

    }

    private void remoteCmdControle(String tx) {
        JsMetterApi.exeCmd(appcontext.remote.getServiceIp(), appcontext.remote.getServicePort(),
                appcontext.remote.getServiceName(), collector_no, meter_no, "3", tx,
                new AsyncHttpResponseHandler() {

                    @Override
                    public void onFailure(int arg0, Header[] arg1,
                                          byte[] arg2, Throwable arg3) {
                        AppContext.showToast(R.string.tip_no_internet);

                    }

                    @Override
                    public void onSuccess(int arg0, Header[] arg1,
                                          byte[] arg2) {
                        String response = new String(arg2);
                        if (response.equals("\"-2\"")) {
                            AppContext.showToast("服务未启动，或数据开关(关闭)");
                        } else if (response.equals("\"-1\"")) {
                            AppContext.showToast("TCP服务未启动，请稍后再试");
                        } else if (response.equals("\"0\"")) {
                            AppContext.showToast("服务器繁忙，请稍后再试");
                        } else if (response.equals("\"1\"")) {
                            AppContext.showToast("服务器正在处理，请等待10秒");
                        }
                    }
                });
    }

    private int checkPostInfo() {
        if (AppContext.login) {
            collector_no = this.et_colection_no.getText().toString().replace(" ", "");
            meter_no = this.et_meter_no.getText().toString().replace(" ", "");
            if (collector_no.length() != 11) {
                AppContext.showToast("请输入11位采集器编码");
                return 2;
            } else {
                if (meter_no.length() != 8) {
                    AppContext.showToast("请输入8位出厂编码");
                    return 3;
                } else {
                    return 0;
                }
            }
        } else {
            return 1;
        }
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

    }

    public String openValve(String meterid, String producttypetx, String factorycode) {
        String r = "";
        // Public pub=new Public();
        String cs = StringUtils.getsum("68" + producttypetx + meterid + factorycode + "0404A0170055", 0);
        r = "68" + producttypetx + meterid + factorycode + "0404A0170055" + cs + "16";
        return r;
    }

    public String closeValve(String meterid, String producttypetx, String factorycode) {
        String r = "";
        //  Public pub=new Public();
        String cs = StringUtils.getsum("68" + producttypetx + meterid + factorycode + "0404A0170099", 0);
        r = "68" + producttypetx + meterid + factorycode + "0404A0170099" + cs + "16";
        return r;
    }

    public Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:

                    String msgtype = (String) msg.obj;
                    //AppContext.showToast(msgtype);
                    if ("updatemeterid".equals(msgtype)) {
                        et_colection_no.setText(appcontext.defaultValue.getDefaultCollector());
                        et_meter_no.setText(appcontext.defaultValue.getDefaultMeter());
                    }
                    break;
            }
        }
    };

}
