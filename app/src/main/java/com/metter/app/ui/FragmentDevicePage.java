package com.metter.app.ui;

import com.metter.app.AppContext;
import com.metter.app.R;
import com.metter.app.adapter.ViewPageFragmentAdapter;
import com.metter.app.base.BaseViewPagerFragment;
import com.metter.app.fragment.CmdMeterFragment;
import com.metter.app.fragment.DeviceContolFragment;
import com.metter.app.interf.OnTabReselectListener;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

public class FragmentDevicePage extends  BaseViewPagerFragment implements OnTabReselectListener{

	public static  ViewPager  curView;

	@Override
	public void onTabReselect() {
		 try {
	            int currentIndex = mViewPager.getCurrentItem();
	            Fragment currentFragment = getChildFragmentManager().getFragments().get(currentIndex);
	            if (currentFragment != null && currentFragment instanceof OnTabReselectListener) {
	                OnTabReselectListener listener = (OnTabReselectListener) currentFragment;
	                listener.onTabReselect();
	            }
	        } catch (NullPointerException e) {
	        }
	}

	@Override
	protected void onSetupTabAdapter(ViewPageFragmentAdapter adapter) {
		String[] title = getResources().getStringArray(R.array.control_viewpage_arrays);
        adapter.addTab(title[0], "news", DeviceContolFragment.class, getBundle(AppContext.DEVICE_CONTROL));
        adapter.addTab(title[1], "news_week", CmdMeterFragment.class, getBundle(AppContext.DEVICE_CMD));
        this.curView = mViewPager;
	}	
	
	private Bundle getBundle(int newType) {
        Bundle bundle = new Bundle();
        bundle.putInt("BUNDLE_KEY_CATALOG", newType);
        return bundle;
    }
	@Override
    protected void setScreenPageLimit() {
        mViewPager.setOffscreenPageLimit(3);
    }
	
	@Override
    public void onClick(View v) {

    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void initData() {

    }
}
