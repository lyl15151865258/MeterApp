package com.metter.app.fragment;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.Header;
import org.json.JSONObject;

import butterknife.InjectView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.metter.app.AppConfig;
import com.metter.app.AppContext;
import com.metter.app.R;
import com.metter.app.R.layout;
import com.metter.app.adapter.TableAdapter;
import com.metter.app.api.remote.JsMetterApi;
import com.metter.app.base.BaseFragment;
import com.metter.app.bean.Gridlist;
import com.metter.app.ui.LoginActivity;
import com.metter.app.ui.MainActivity;
import com.metter.app.util.StringUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class CmdMeterFragment extends BaseFragment {

    Spinner cmd_SpinnerMeterType;
    EditText cmd_colection_no;
    EditText cmd_meter_no;
    String collector_no = "";
    String meter_no = "";
    TableAdapter adapter;
    ListView cmdMeterListView;
    ArrayList<Gridlist> arrlist;

    AppContext appcontext;
    String[] mItems;
    String producttype = "热量表";

    private Timer timer;
    private TimerTask task;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cmd_meter, null);

        initData();
        initView(rootView);
        return rootView;
    }

    @Override
    public void initView(View view) {
        cmd_colection_no = (EditText) view.findViewById(R.id.cmd_colection_no);
        cmd_meter_no = (EditText) view.findViewById(R.id.cmd_meter_no);
        cmd_colection_no.setText(appcontext.defaultValue.getDefaultCollector());
        cmd_meter_no.setText(appcontext.defaultValue.getDefaultMeter());

        cmdMeterListView = (ListView) view.findViewById(R.id.cmdMeterListView);
        cmdMeterListView.setAdapter(adapter);
        mItems = getResources().getStringArray(R.array.device_type_arrays);
        cmd_SpinnerMeterType = (Spinner) view.findViewById(R.id.cmd_SpinnerMeterType);
        ArrayAdapter<String> arrlist = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, mItems);
        cmd_SpinnerMeterType.setAdapter(arrlist);
        cmd_SpinnerMeterType.setSelection(0, false);
        cmd_SpinnerMeterType.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                producttype = mItems[arg2];
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        super.initView(view);
    }

    @Override
    public void initData() {
        appcontext = AppContext.getInstance();
        arrlist = new ArrayList<Gridlist>();
        adapter = new TableAdapter(mActivity, arrlist);

        task = new TimerTask() {
            @Override
            public void run() {


                Message message = new Message();
                message.what = 2;
                mHandler.sendMessage(message);
            }
        };
        super.initData();
    }

    public void exeCmdMeterReport() {
        switch (checkPostInfo()) {
            case 0:
                String strvalve = "";
                if (producttype.equals("水表")) {
                    strvalve = readen1434(this.meter_no);
                } else {
                    strvalve = readmeter(this.meter_no, "20", "001111");
                }
                remoteCmdControle(strvalve);
                break;
            case 1:
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivityForResult(intent, AppConfig.LOGIN_CALLBACK);
                break;
            default:
                break;
        }
    }

    MainActivity mActivity;

    @Override
    public void onAttach(Activity activity) {
        mActivity = (MainActivity) activity;
        mActivity.setfCmdMeterHandler(mHandler);

        super.onAttach(activity);
    }

    private int checkPostInfo() {
        if (AppContext.login) {
            collector_no = this.cmd_colection_no.getText().toString().replace(" ", "");
            meter_no = this.cmd_meter_no.getText().toString().replace(" ", "");
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
                            //AppContext.showToast("服务器正在处理，请等待10秒");
                            showWaitDialog(R.string.progress_Loading);
                            // timer = new Timer();
                            //timer.schedule(task, 10000, 10000);
                            // mHandler.postDelayed(runnable, 10000);//每两秒执行一次runnable.
                            new Thread(new MyThread()).start();
                        }
                    }
                });
    }

    public String readmeter(String meterid, String producttypetx, String factorycode) {
        String r = "";
        String cs = StringUtils.getsum("68" + producttypetx + meterid + factorycode + "0103901F00", 0);
        r = "68" + producttypetx + meterid + factorycode + "0103901F00" + cs + "16";
        return r;
    }

    public String readen1434(String meterid) {
        String r = "";
        String cs = StringUtils.getsum("680B0B6873FD52" + meterid.substring(6, 6 + 2) + meterid.substring(4, 4 + 2) + meterid.substring(2, 2 + 2) + meterid.substring(0, 0 + 2) + "FFFFFFFF", 4);
        r = "680B0B6873FD52" + meterid.substring(6, 6 + 2) + meterid.substring(4, 4 + 2) + meterid.substring(2, 2 + 2) + meterid.substring(0, 0 + 2) + "FFFFFFFF" + cs + "16";
        return r;
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            mHandler.postDelayed(this, 10000);
        }
    };


    public class MyThread implements Runnable {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            //   while (true) {
            try {
                Thread.sleep(10000);// 线程暂停10秒，单位毫秒
                Message message = new Message();
                message.what = 2;
                mHandler.sendMessage(message);// 发送消息
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            //  }
        }
    }


    public Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    String msgtype = (String) msg.obj;
                    if ("execmd".equals(msgtype)) {
                        arrlist.clear();
                        adapter.notifyDataSetChanged();
                        exeCmdMeterReport();
                    } else if ("updatemeterid".equals(msgtype)) {
                        cmd_colection_no.setText(appcontext.defaultValue.getDefaultCollector());
                        cmd_meter_no.setText(appcontext.defaultValue.getDefaultMeter());
                    }
                    break;
                case 2:
                    JsMetterApi.exeForResoult(appcontext.remote.getServiceIp(), appcontext.remote.getServicePort(), appcontext.remote.getServiceName(),
                            collector_no, meter_no, new AsyncHttpResponseHandler() {

                                @Override
                                public void onFailure(int arg0,
                                                      Header[] arg1, byte[] arg2,
                                                      Throwable arg3) {
                                    hideWaitDialog();

                                }

                                @Override
                                public void onSuccess(int arg0,
                                                      Header[] arg1, byte[] arg2) {
                                    String res = new String(arg2);
                                    appcontext.defaultValue.setDefaultCollector(collector_no);
                                    appcontext.defaultValue.setDefaultMeter(meter_no);
                                    appcontext.saveDefOptValue();
                                    //AppContext.showToast(res);
                                    try {
                                        JSONObject arr = new JSONObject(res);
                                        if (arr.getString("meterID").equals(meter_no)) {
                                            if ((!arr.getString("meterID").equals("")) && (!arr.getString("meterID").equals("-"))) {
                                                arrlist.add(new Gridlist("出厂编码", arr.getString("meterID"), ""));
                                            }
                                            ;
                                            if ((!arr.getString("mbusAddress").equals("")) && (!arr.getString("mbusAddress").equals("-"))) {
                                                arrlist.add(new Gridlist("MBUS", arr.getString("mbusAddress"), ""));
                                            }
                                            ;
                                            if ((!arr.getString("sumCool").equals("")) && (!arr.getString("sumCool").equals("-")) && (!arr.getString("sumCool").equals("0.0"))) {
                                                arrlist.add(new Gridlist("累计冷量", arr.getString("sumCool"), "kW*h"));
                                            }
                                            ;
                                            if ((!arr.getString("creditCool").equals("")) && (!arr.getString("creditCool").equals("-")) && (!arr.getString("creditCool").equals("0.0"))) {
                                                arrlist.add(new Gridlist("剩余冷量", arr.getString("creditCool"), "kW*h"));
                                            }
                                            ;
                                            if ((!arr.getString("sumHeat").equals("")) && (!arr.getString("sumHeat").equals("-")) && (!arr.getString("sumHeat").equals("0.0"))) {
                                                arrlist.add(new Gridlist("累计热量", arr.getString("sumHeat"), "kW*h"));
                                            }
                                            ;
                                            if ((!arr.getString("creditHeat").equals("")) && (!arr.getString("creditHeat").equals("-")) && (!arr.getString("creditHeat").equals("0.0"))) {
                                                arrlist.add(new Gridlist("剩余热量", arr.getString("creditHeat"), "kW*h"));
                                            }
                                            ;
                                            if ((!arr.getString("total").equals("")) && (!arr.getString("total").equals("-")) && (!arr.getString("total").equals("0.0"))) {
                                                arrlist.add(new Gridlist("累计流量", arr.getString("total"), "m³"));
                                            }
                                            ;
                                            if ((!arr.getString("creditTotal").equals("")) && (!arr.getString("creditTotal").equals("-")) && (!arr.getString("creditTotal").equals("0.0"))) {
                                                arrlist.add(new Gridlist("剩余流量", arr.getString("creditTotal"), "m³"));
                                            }
                                            ;
                                            if ((!arr.getString("power").equals("")) && (!arr.getString("power").equals("-"))) {
                                                arrlist.add(new Gridlist("功率", arr.getString("power"), "kW"));
                                            }
                                            ;
                                            if ((!arr.getString("flowRate").equals("")) && (!arr.getString("flowRate").equals("-"))) {
                                                arrlist.add(new Gridlist("瞬时", arr.getString("flowRate"), "m³/h"));
                                            }
                                            ;
                                            if ((!arr.getString("insideT").equals("")) && (!arr.getString("insideT").equals("-")) && (!arr.getString("insideT").equals("0.0"))) {
                                                arrlist.add(new Gridlist("室温", arr.getString("insideT"), "℃"));
                                            }
                                            ;
                                            if ((!arr.getString("insideTSet").equals("")) && (!arr.getString("insideTSet").equals("-"))) {
                                                arrlist.add(new Gridlist("设定室温", arr.getString("insideTSet"), "℃"));
                                            }
                                            ;
                                            if ((!arr.getString("valveStatus").equals("")) && (!arr.getString("valveStatus").equals("-"))) {
                                                arrlist.add(new Gridlist("阀门", arr.getString("valveStatus"), ""));
                                            }
                                            ;
                                            if ((!arr.getString("t1InP").equals("")) && (!arr.getString("t1InP").equals("-"))) {
                                                arrlist.add(new Gridlist("T1", arr.getString("t1InP"), "℃"));
                                            }
                                            ;
                                            if ((!arr.getString("t2InP").equals("")) && (!arr.getString("t2InP").equals("-"))) {
                                                arrlist.add(new Gridlist("T2", arr.getString("t2InP"), "℃"));
                                            }
                                            ;
                                            if ((!arr.getString("workTimeInP").equals("")) && (!arr.getString("workTimeInP").equals("-")) && (!arr.getString("workTimeInP").equals("0.0"))) {
                                                arrlist.add(new Gridlist("工作时间", arr.getString("workTimeInP"), "h"));
                                            }
                                            ;
                                            if ((!arr.getString("timeInP").equals("")) && (!arr.getString("timeInP").equals("-"))) {
                                                arrlist.add(new Gridlist("时间", arr.getString("timeInP"), ""));
                                            }
                                            ;
                                            if ((!arr.getString("vol").equals("")) && (!arr.getString("vol").equals("-")) && (!arr.getString("vol").equals("0.0"))) {
                                                arrlist.add(new Gridlist("电压", arr.getString("vol"), "V"));
                                            }
                                            ;
                                            if ((!arr.getString("status").equals("")) && (!arr.getString("status").equals("-"))) {
                                                arrlist.add(new Gridlist("状态", arr.getString("status"), ""));
                                            }
                                            ;
                                            adapter.notifyDataSetChanged();

                                        }
                                    } catch (Exception e) {

                                    } finally {
                                        hideWaitDialog();
                                    }
                                }
                            }
                    );
                    break;
            }
        }
    };


}