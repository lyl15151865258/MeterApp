package com.metter.app.ui;

import com.metter.app.AppContext;
import com.metter.app.R;
import com.metter.app.api.remote.JsMetterApi;
import com.metter.app.base.BaseActivity;
import com.metter.app.bean.Constants;
import com.metter.app.bean.LoginUser;
import com.metter.app.util.SimpleTextWatcher;
import com.metter.app.util.StringUtils;
import com.metter.app.util.TDevice;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.loopj.android.http.AsyncHttpResponseHandler;

/**
 * 用户登录界面
 *
 * @author FireAnt（http://my.oschina.net/LittleDY）
 * @version 创建时间：2014年9月26日 下午3:24:31
 */

public class LoginActivity extends BaseActivity {

    public static final int REQUEST_CODE_INIT = 0;
    private static final String BUNDLE_KEY_REQUEST_CODE = "BUNDLE_KEY_REQUEST_CODE";
    protected static final String TAG = LoginActivity.class.getSimpleName();

    AppContext appcontext;
    EditText mEtWebserviceIp;
    EditText mEtWebservicePort;
    EditText mEtWebserviceName;

    //@InjectView(R.id.et_username)
    EditText mEtUserName;

    //@InjectView(R.id.et_password)
    EditText mEtPassword;

    //@InjectView(R.id.iv_clear_username)
    View mIvClearUserName;

    // @InjectView(R.id.iv_clear_password)
    View mIvClearPassword;

    View mIvWebserviceIp;
    View mIvWebservicePort;
    View mIvWebserviceName;

    // @InjectView(R.id.btn_login)
    Button mBtnLogin;


    private final int requestCode = REQUEST_CODE_INIT;

    private String mWebserviceIp;
    private String mWebservicePort;
    private String mWebserviceName;
    private String mUserName;
    private String mPassword;


    @Override
    public void initData() {
        // mEtUserName.setText(AppContext.getInstance().getProperty("user.account"));
        // mEtPassword.setText(CyptoUtils.decode("oschinaApp", AppContext.getInstance().getProperty("user.pwd")));
    }

    @Override
    public void initView() {
        mEtWebserviceIp = (EditText) this.findViewById(R.id.et_webserviceip);
        mEtWebservicePort = (EditText) this.findViewById(R.id.et_webserviceport);
        mEtWebserviceName = (EditText) this.findViewById(R.id.et_webservicename);
        mEtUserName = (EditText) this.findViewById(R.id.et_username);
        mEtPassword = (EditText) this.findViewById(R.id.et_password);

        mIvWebserviceIp = (View) this.findViewById(R.id.iv_clear_webserviceip);
        mIvWebservicePort = (View) this.findViewById(R.id.iv_clear_webserviceport);
        mIvWebserviceName = (View) this.findViewById(R.id.iv_clear_webservicename);
        mIvClearUserName = (View) this.findViewById(R.id.iv_clear_username);
        mIvClearPassword = (View) this.findViewById(R.id.iv_clear_password);

        mBtnLogin = (Button) this.findViewById(R.id.btn_login);

        //mIvWebserviceIp.setOnClickListener(this);
        //mIvWebservicePort.setOnClickListener(this);
        // mIvWebserviceName.setOnClickListener(this);
        // mIvClearUserName.setOnClickListener(this);
        // mIvClearPassword.setOnClickListener(this);
        mBtnLogin.setOnClickListener(this);
        appcontext = AppContext.getInstance();


        mEtWebserviceIp.setText(appcontext.remote.getServiceIp());
        mEtWebservicePort.setText(appcontext.remote.getServicePort());
        mEtWebserviceName.setText(appcontext.remote.getServiceName());
        // mEtUserName.setText(R.string.login_username);
        //mEtPassword.setText(R.string.login_password);
        mEtUserName.setText(appcontext.user.getLoginName());
        mEtPassword.setText(appcontext.user.getPassword());
        //mEtUserName.addTextChangedListener(mUserNameWatcher);
        // mEtPassword.addTextChangedListener(mPassswordWatcher);
        //mEtWebserviceIp .addTextChangedListener(mUserNameWatcher);
        //mEtWebservicePort.addTextChangedListener(mUserNameWatcher);
        //mEtWebserviceName.addTextChangedListener(mUserNameWatcher);

        mBtnLogin.requestFocus();

    }

    private final TextWatcher mUserNameWatcher = new SimpleTextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            mIvClearUserName.setVisibility(TextUtils.isEmpty(s) ? View.INVISIBLE : View.VISIBLE);
        }
    };
    private final TextWatcher mPassswordWatcher = new SimpleTextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
            mIvClearPassword.setVisibility(TextUtils.isEmpty(s) ? View.INVISIBLE : View.VISIBLE);
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected boolean hasBackButton() {
        return true;
    }

    @Override
    protected int getActionBarTitle() {
        return R.string.login;
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        switch (id) {
            case R.id.iv_clear_username:
                mEtUserName.getText().clear();
                mEtUserName.requestFocus();
                break;
            case R.id.iv_clear_password:
                mEtPassword.getText().clear();
                mEtPassword.requestFocus();
                break;
            case R.id.iv_clear_webserviceip:
                mEtWebserviceIp.getText().clear();
                mEtWebserviceIp.requestFocus();
                break;
            case R.id.iv_clear_webserviceport:
                mEtWebservicePort.getText().clear();
                mEtWebservicePort.requestFocus();
                break;
            case R.id.iv_clear_webservicename:
                mEtWebserviceName.getText().clear();
                mEtWebserviceName.requestFocus();
                break;
            case R.id.btn_login:
                handleLogin();
                break;
            default:
                break;
        }
    }

    public int getuser(String str) {
        int r = -1;
        try {
            JSONObject arr = new JSONObject(str);
            if (arr != null && !"".equals(arr)) {
                //MainActivity.supplierId=arr.getInt("supplierId");
                //MainActivity.exchangStationId=arr.getInt("exchangStationId");
                r = 1;
            } else
                r = 0;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return r;
    }

    private void handleLogin() {

        if (!prepareForLogin()) {
            return;
        }
        // if the data has ready
        mWebserviceIp = mEtWebserviceIp.getText().toString();
        mWebservicePort = mEtWebservicePort.getText().toString();
        mWebserviceName = mEtWebserviceName.getText().toString();
        mUserName = mEtUserName.getText().toString();
        mPassword = mEtPassword.getText().toString();

        showWaitDialog(R.string.progress_login);
        JsMetterApi.login(mWebserviceIp, mWebservicePort, mWebserviceName, mUserName, mPassword, mHandler);
    }

    private final AsyncHttpResponseHandler mHandler = new AsyncHttpResponseHandler() {

        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            try {
                String recString = new String(arg2);
                AppContext.login = true;
                appcontext.remote.setServiceIp(mWebserviceIp);
                appcontext.remote.setServicePort(mWebservicePort);
                appcontext.remote.setServiceName(mWebserviceName);
                appcontext.saveRemoteInfo();

                JSONObject jsonObject = new JSONObject(recString);//.getJSONObject("userbean");

                LoginUser user = new LoginUser();
                user.setLoginName(jsonObject.getString("loginName"));
                user.setBuildingId(jsonObject.getInt("buildingId"));
                user.setDeptName(jsonObject.getString("deptName"));
                user.setDeptNo(jsonObject.getString("deptNo"));
                user.setEntranceId(jsonObject.getInt("entranceId"));
                user.setExchangStationId(jsonObject.getInt("exchangStationId"));
                user.setId(jsonObject.getInt("id"));
                user.setPassword(jsonObject.getString("password"));
                user.setSupplierId(jsonObject.getInt("supplierId"));
                user.setUserName(jsonObject.getString("userName"));
                user.setVillageId(jsonObject.getInt("villageId"));
                appcontext.saveUserInfo(user);
                hideWaitDialog();
                handleLoginSuccess();
            } catch (Exception e) {
                e.printStackTrace();
                onFailure(arg0, arg1, arg2, e);
            }
        }

        @Override
        public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                              Throwable arg3) {
            hideWaitDialog();
            AppContext.showToast(R.string.tip_login_error_for_network);
        }
    };

    private void handleLoginSuccess() {
        AppContext.showToastShort("登陆成功！");
        Intent data = new Intent();
        data.putExtra(BUNDLE_KEY_REQUEST_CODE, requestCode);
        setResult(RESULT_OK, data);
        this.sendBroadcast(new Intent(Constants.INTENT_ACTION_USER_CHANGE));
        finish();
    }

    private boolean prepareForLogin() {
        if (!TDevice.hasInternet()) {
            AppContext.showToastShort(R.string.tip_no_internet);
            return false;
        }
        String uSeviviceIp = mEtWebserviceIp.getText().toString();
        if (StringUtils.isEmpty(uSeviviceIp)) {
            AppContext.showToastShort(R.string.tip_please_input_serviceip);
            mEtWebserviceIp.requestFocus();
            return false;
        }

        String uSeivicePort = mEtWebservicePort.getText().toString();
        if (StringUtils.isEmpty(uSeivicePort)) {
            AppContext.showToastShort(R.string.tip_please_input_serviceport);
            mEtWebservicePort.requestFocus();
            return false;
        }
        String uSeiviceName = mEtWebserviceName.getText().toString();
        if (StringUtils.isEmpty(uSeiviceName)) {
            AppContext.showToastShort(R.string.tip_please_input_servicename);
            mEtWebserviceName.requestFocus();
            return false;
        }
        String uName = mEtUserName.getText().toString();
        if (StringUtils.isEmpty(uName)) {
            AppContext.showToastShort(R.string.tip_please_input_username);
            mEtUserName.requestFocus();
            return false;
        }
        // 去除邮箱正确性检测
        // if (!StringUtils.isEmail(uName)) {
        // AppContext.showToastShort(R.string.tip_illegal_email);
        // mEtUserName.requestFocus();
        // return false;
        // }
        String pwd = mEtPassword.getText().toString();
        if (StringUtils.isEmpty(pwd)) {
            AppContext.showToastShort(R.string.tip_please_input_password);
            mEtPassword.requestFocus();

            return false;
        }
        return true;
    }


}
