package com.metter.app;

import static com.metter.app.AppConfig.KEY_FRITST_START;
import static com.metter.app.AppConfig.KEY_LOAD_IMAGE;
import static com.metter.app.AppConfig.KEY_TWEET_DRAFT;

import java.util.Properties;
import java.util.UUID;

import org.kymjs.kjframe.KJBitmap;
import org.kymjs.kjframe.bitmap.BitmapConfig;
import org.kymjs.kjframe.utils.KJLoger;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.PersistentCookieStore;
import com.metter.app.api.ApiHttpClient;
import com.metter.app.base.BaseApplication;
import com.metter.app.bean.Constants;
import com.metter.app.bean.DefaultOptValue;
import com.metter.app.bean.RemoteServer;
import com.metter.app.bean.LoginUser;
import com.metter.app.cache.DataCleanManager;
import com.metter.app.util.CyptoUtils;
import com.metter.app.util.MethodsCompat;
import com.metter.app.util.StringUtils;
import com.metter.app.util.TLog;
import com.metter.app.util.UIHelper;


public class AppContext extends BaseApplication {

    public static final int PAGE_SIZE = 20;// 默认分页大小

    
    public final static int COLLECTOR_INFO = 1;
    public final static int COLLECTOR_CONFIG = 2;
    
    public final static int HIE_LIST = 3;
    public final static int HIE_INFO = 4;
    public final static int HIE_DATA = 5;
    
    public final static int DEVICE_CONTROL = 6;
    public final static int DEVICE_CMD = 7;
    
    
    
    
    private static AppContext instance;

    public  LoginUser user;
    public  RemoteServer remote;
    public  DefaultOptValue defaultValue;
    
    public static boolean login;
    
    private String deptno = "0-0";

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        init();
        //initLogin();
        Thread.setDefaultUncaughtExceptionHandler(AppException.getAppExceptionHandler(this));
        UIHelper.sendBroadcastForNotice(this);
    }

    private void init() {
        // 初始化网络请求
        AsyncHttpClient client = new AsyncHttpClient();
       // PersistentCookieStore myCookieStore = new PersistentCookieStore(this);
        //client.setCookieStore(myCookieStore);
        ApiHttpClient.setHttpClient(client);
       // ApiHttpClient.setCookie(ApiHttpClient.getCookie(this));

        remote = getRemoteServer();
        user = getLoginUser();
        defaultValue = getDefaultValue();
        // Log控制器
        KJLoger.openDebutLog(true);
        TLog.DEBUG = BuildConfig.DEBUG;

        // Bitmap缓存地址
        BitmapConfig.CACHEPATH = "JSMetter/imagecache";
    }


	/**
     * 获得当前app运行的AppContext
     * 
     * @return
     */
    public static AppContext getInstance() {
        return instance;
    }

    public boolean containsProperty(String key) {
        Properties props = getProperties();
        return props.containsKey(key);
    }

    public void setProperties(Properties ps) {
        AppConfig.getAppConfig(this).set(ps);
    }

    public Properties getProperties() {
        return AppConfig.getAppConfig(this).get();
    }

    public void setProperty(String key, String value) {
        AppConfig.getAppConfig(this).set(key, value);
    }

    /**
     * 获取cookie时传AppConfig.CONF_COOKIE
     * 
     * @param key
     * @return
     */
    public String getProperty(String key) {
        String res = AppConfig.getAppConfig(this).get(key);
        return res;
    }

    public void removeProperty(String... key) {
        AppConfig.getAppConfig(this).remove(key);
    }

    /**
     * 获取App唯一标识
     * 
     * @return
     */
    public String getAppId() {
        String uniqueID = getProperty(AppConfig.CONF_APP_UNIQUEID);
        if (StringUtils.isEmpty(uniqueID)) {
            uniqueID = UUID.randomUUID().toString();
            setProperty(AppConfig.CONF_APP_UNIQUEID, uniqueID);
        }
        return uniqueID;
    }

    /**
     * 获取App安装包信息
     * 
     * @return
     */
    public PackageInfo getPackageInfo() {
        PackageInfo info = null;
        try {
            info = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (NameNotFoundException e) {
            e.printStackTrace(System.err);
        }
        if (info == null)
            info = new PackageInfo();
        return info;
    }

    /**
     * 保存登录信息
     * 
     * @param username
     * @param pwd
     */
    @SuppressWarnings("serial")
    public void saveUserInfo(final LoginUser user) {
        this.user = user;
        this.login = true;
        setProperties(new Properties() {
            {
                setProperty("user.loginName", user.getLoginName());
                setProperty("user.userName", user.getUserName());//
                setProperty("user.password", user.getPassword());
                setProperty("user.deptNo", user.getDeptNo());
                setProperty("user.deptName", user.getDeptName());
                setProperty("user.supplierId", String.valueOf(user.getSupplierId()));
            }
        });
    }

    @SuppressWarnings("serial")
    public void saveRemoteInfo(final RemoteServer remote) {
        setProperties(new Properties() {
            {
                setProperty("remote.serviceName", remote.getServiceName());
                setProperty("remote.serviceIp", remote.getServiceIp());// 
                setProperty("remote.servicePort", remote.getServicePort());
            }
        });
    }
    @SuppressWarnings("serial")
    public void saveRemoteInfo() {
        setProperties(new Properties() {
            {
                setProperty("remote.serviceName", remote.getServiceName());
                setProperty("remote.serviceIp", remote.getServiceIp());// 
                setProperty("remote.servicePort", remote.getServicePort());
            }
        });
    }
    @SuppressWarnings("serial")
    public void saveDefOptValue() {
        setProperties(new Properties() {
            {
                setProperty("defaultValue.defaultCollector", defaultValue.getDefaultCollector());
                setProperty("defaultValue.defaultMeter", defaultValue.getDefaultMeter());// 
            }
        });
    }
    /**
     * 更新用户信息
     * 
     * @param user
     */
    @SuppressWarnings("serial")
    public void updateUserInfo(final LoginUser user) {
        setProperties(new Properties() {
            {
                setProperty("user.loginName", user.getLoginName());
                setProperty("user.userName", user.getUserName());// 
                setProperty("user.password",user.getPassword());
                setProperty("user.deptNo", user.getDeptNo());
                setProperty("user.deptName", user.getDeptName());
                setProperty("user.supplierId", String.valueOf(user.getSupplierId()));
            }
        });
    }
    @SuppressWarnings("serial")
    public void updateRemoteInfo(final RemoteServer remote) {
        setProperties(new Properties() {
            {
            	 setProperty("remote.serviceName", remote.getServiceName());
                 setProperty("remote.serviceIp", remote.getServiceIp());// 
                 setProperty("remote.servicePort", remote.getServicePort());
            }
        });
    }
    @SuppressWarnings("serial")
    public void updateDefOptValue(final DefaultOptValue  dvalue) {
        setProperties(new Properties() {
            {
            	 setProperty("remote.serviceName", dvalue.getDefaultCollector());
                 setProperty("remote.serviceIp", dvalue.getDefaultMeter());// 
            }
        });
    }
    /**
     * 获得登录用户的信息
     * 
     * @return
     */
    public LoginUser getLoginUser() {
    	LoginUser user = new LoginUser();
        user.setLoginName(getProperty("user.loginName"));
        user.setUserName(getProperty("user.userName"));
        user.setPassword(getProperty("user.password"));
        user.setDeptNo(getProperty("user.deptNo"));
        user.setDeptName(getProperty("user.deptName"));
        user.setSupplierId(StringUtils.toInt(getProperty("user.supplierId"), 0));
        user.setBuildingId(StringUtils.toInt(getProperty("user.buildingId"), 0));
        user.setEntranceId(StringUtils.toInt(getProperty("user.entranceId"), 0));
        user.setExchangStationId(StringUtils.toInt(getProperty("user.exchangStationId"), 0));
        user.setVillageId(StringUtils.toInt(getProperty("user.villageId"), 0));
        return user;
    }
    public RemoteServer getRemoteServer() {
    	RemoteServer remote = new RemoteServer();
    	remote.setServiceName(getProperty("remote.serviceName"));
    	remote.setServiceIp(getProperty("remote.serviceIp"));
    	remote.setServicePort(getProperty("remote.servicePort"));
        return remote;
    }
    
    private DefaultOptValue getDefaultValue() {
    	DefaultOptValue dvalue = new DefaultOptValue();
    	dvalue.setDefaultCollector(getProperty("defaultValue.defaultCollector"));
    	dvalue.setDefaultMeter(getProperty("defaultValue.defaultMeter"));
		return dvalue;
	}
    
    public void cleanDefaultOptValue(){
    	
    	
    }
    /**
     * 清除登录信息
     */
    public void cleanLoginInfo() {
        this.deptno ="0-0";
        this.login = false;
        removeProperty("user.loginName", "user.userName", "user.password", "user.deptNo","user.deptName", "user.supplierId","remote.serviceName", "remote.serviceIp", "remote.servicePort");
    }
    


    
    public void cleanRemoteServer() {
        removeProperty("remote.serviceName", "remote.serviceIp", "remote.servicePort");
    }
    public String getDeptno() {
        return deptno;
    }

    public boolean isLogin() {
        return login;
    }

    /**
     * 用户注销
     */
    public void Logout() {
        cleanLoginInfo();
       // ApiHttpClient.cleanCookie();
        this.cleanCookie();
        this.login = false;
        this.deptno ="0-0";

        Intent intent = new Intent(Constants.INTENT_ACTION_LOGOUT);
        sendBroadcast(intent);
    }

    /**
     * 清除保存的缓存
     */
    public void cleanCookie() {
        removeProperty(AppConfig.CONF_COOKIE);
    }

    /**
     * 清除app缓存
     */
    public void clearAppCache() {
        DataCleanManager.cleanDatabases(this);
        // 清除数据缓存
        DataCleanManager.cleanInternalCache(this);
        // 2.2版本才有将应用缓存转移到sd卡的功能
        if (isMethodsCompat(android.os.Build.VERSION_CODES.FROYO)) {
            DataCleanManager.cleanCustomCache(MethodsCompat.getExternalCacheDir(this));
        }
        // 清除编辑器保存的临时内容
        Properties props = getProperties();
        for (Object key : props.keySet()) {
            String _key = key.toString();
            if (_key.startsWith("temp"))
                removeProperty(_key);
        }
        new KJBitmap().cleanCache();
    }

    public static void setLoadImage(boolean flag) {
        set(KEY_LOAD_IMAGE, flag);
    }

    /**
     * 判断当前版本是否兼容目标版本的方法
     * 
     * @param VersionCode
     * @return
     */
    public static boolean isMethodsCompat(int VersionCode) {
        int currentVersion = android.os.Build.VERSION.SDK_INT;
        return currentVersion >= VersionCode;
    }

//    public static String getTweetDraft() {
//        return getPreferences().getString(
//                KEY_TWEET_DRAFT + getInstance().getLoginUid(), "");
//    }
//
//    public static void setTweetDraft(String draft) {
//        set(KEY_TWEET_DRAFT + getInstance().getLoginUid(), draft);
//    }
//
//    public static String getNoteDraft() {
//        return getPreferences().getString(
//                AppConfig.KEY_NOTE_DRAFT + getInstance().getLoginUid(), "");
//    }
//
//    public static void setNoteDraft(String draft) {
//        set(AppConfig.KEY_NOTE_DRAFT + getInstance().getLoginUid(), draft);
//    }
//
//    public static boolean isFristStart() {
//        return getPreferences().getBoolean(KEY_FRITST_START, true);
//    }
//
//    public static void setFristStart(boolean frist) {
//        set(KEY_FRITST_START, frist);
//    }
}
