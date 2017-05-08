package com.metter.app.ui;




import com.metter.app.bean.BlogList;
import com.metter.app.bean.NewsList;
import com.metter.app.AppContext;
import com.metter.app.R;
import com.metter.app.R.layout;
import com.metter.app.adapter.ViewPageFragmentAdapter;
import com.metter.app.base.BaseViewPagerFragment;
import com.metter.app.fragment.FindUserFragment;
import com.metter.app.fragment.GprsInfoFragment;
import com.metter.app.fragment.HierarchyDataFragment;
import com.metter.app.fragment.HierarchyInfoFragment;
import com.metter.app.fragment.HierarchyFragment;
import com.metter.app.interf.OnTabReselectListener;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentReportPage extends  BaseViewPagerFragment implements OnTabReselectListener{

	public static   ViewPageFragmentAdapter adapter;
	public static  ViewPager  rootView;
	public ViewPageFragmentAdapter getAdapter() {
		return adapter;
	}

	public void setAdapter(ViewPageFragmentAdapter adapter) {
		this.adapter = adapter;
	}
	
	
	
	
	@Override
	public void onTabReselect() {
		 try {
	            int currentIndex = mViewPager.getCurrentItem();
	            Fragment currentFragment = getChildFragmentManager().getFragments().get(currentIndex);
	            if (currentFragment != null
	                    && currentFragment instanceof OnTabReselectListener) {
	                OnTabReselectListener listener = (OnTabReselectListener) currentFragment;
	                listener.onTabReselect();
	            }
	            
	        } catch (NullPointerException e) {
	        }
		
	}

	



	@Override
	protected void onSetupTabAdapter(ViewPageFragmentAdapter adapter) {
		String[] title = getResources().getStringArray(R.array.basedata_viewpage_arrays);
        adapter.addTab(title[0], "hierarchy_fragment", HierarchyFragment.class,
                getBundle(AppContext.HIE_LIST));
        adapter.addTab(title[1], "meterinfo_fragment", HierarchyInfoFragment.class,
                getBundle(AppContext.HIE_INFO));
        adapter.addTab(title[2], "meterdata_fragment", HierarchyDataFragment.class,
                getBundle(AppContext.HIE_DATA));
//        adapter.addTab(title[3], "gprsinfo_fragment", GprsInfoFragment.class,
//                getBundle(NewsList.CATALOG_INTEGRATION));
        this.rootView = mViewPager;
		this.adapter = adapter;
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
		AppContext.showToast("切换页面点击了");
    }

    @Override
    public void initView(View view) {
    	
    }

    @Override
    public void initData() {

    }


}
