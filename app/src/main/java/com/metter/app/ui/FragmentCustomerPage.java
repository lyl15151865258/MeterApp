package com.metter.app.ui;




import com.metter.app.bean.BlogList;
import com.metter.app.bean.NewsList;
import com.metter.app.R;
import com.metter.app.R.layout;
import com.metter.app.adapter.ViewPageFragmentAdapter;
import com.metter.app.base.BaseViewPagerFragment;
import com.metter.app.fragment.FindUserFragment;
import com.metter.app.interf.OnTabReselectListener;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentCustomerPage extends  BaseViewPagerFragment implements OnTabReselectListener{



	@Override
	public void onTabReselect() {
		 try {
	            int currentIndex = mViewPager.getCurrentItem();
	            Fragment currentFragment = getChildFragmentManager().getFragments()
	                    .get(currentIndex);
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
		String[] title = getResources().getStringArray(
                R.array.customer_viewpage_arrays);
        adapter.addTab(title[0], "news", FindUserFragment.class,
                getBundle(NewsList.CATALOG_ALL));
        adapter.addTab(title[1], "news_week", FindUserFragment.class,
                getBundle(NewsList.CATALOG_WEEK));
		
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
