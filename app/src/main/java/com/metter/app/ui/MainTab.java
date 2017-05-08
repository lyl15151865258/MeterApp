package com.metter.app.ui;

import com.metter.app.R;
import com.metter.app.fragment.CmdMeterFragment;


public enum MainTab {

    BASE(0, R.string.main_tab_name_report, R.drawable.tab_icon_query, FragmentReportPage.class),

    DEVICE(1, R.string.main_tab_name_device, R.drawable.tab_icon_meter, FragmentDevicePage.class),

    NOTICE(2, R.string.main_tab_name_notice, R.drawable.tab_icon_tweet, null),

    CHARGE(3, R.string.main_tab_name_cmd, R.drawable.tab_icon_config, FragmentCollectorPage.class),

    MORE(4, R.string.main_tab_name_more, R.drawable.tab_icon_tweet, FragmentCustomerPage.class);

    private int idx;
    private int resName;
    private int resIcon;
    private Class<?> clz;

    private MainTab(int idx, int resName, int resIcon, Class<?> clz) {
        this.idx = idx;
        this.resName = resName;
        this.resIcon = resIcon;
        this.clz = clz;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public int getResName() {
        return resName;
    }

    public void setResName(int resName) {
        this.resName = resName;
    }

    public int getResIcon() {
        return resIcon;
    }

    public void setResIcon(int resIcon) {
        this.resIcon = resIcon;
    }

    public Class<?> getClz() {
        return clz;
    }

    public void setClz(Class<?> clz) {
        this.clz = clz;
    }
}
