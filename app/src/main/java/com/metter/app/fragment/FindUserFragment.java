package com.metter.app.fragment;

import java.io.ByteArrayInputStream;
import java.util.List;

import com.metter.app.R;
import com.metter.app.adapter.FindUserAdapter;
import com.metter.app.base.BaseFragment;
import com.metter.app.bean.FindUserList;
import com.metter.app.bean.ListEntity;
import com.metter.app.bean.User;
import com.metter.app.ui.empty.EmptyLayout;
import com.metter.app.util.StringUtils;
import com.metter.app.util.XmlUtils;

import org.apache.http.Header;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
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

public class FindUserFragment extends BaseFragment implements
	OnItemClickListener {

    private SearchView mSearchView;

    @InjectView(R.id.lv_list)
    ListView mListView;

    @InjectView(R.id.error_layout)
    EmptyLayout mErrorLayout;

    private FindUserAdapter mAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	    Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_find_user, null);
		//ButterKnife.inject(this, view);
		setHasOptionsMenu(true);
		initView(view);
		return view;
    }

   

    private void search(String nickName) {
		if (nickName == null || StringUtils.isEmpty(nickName)) {
		    return;
		}
		//mErrorLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
		//mListView.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void initView(View view) {
//		mAdapter = new FindUserAdapter();
//		mListView.setAdapter(mAdapter);
//		mListView.setOnItemClickListener(this);

    }

    @Override
    public void initData() {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
	    long id) {
    	//User user = (User) mAdapter.getItem(position);
    	//if (user != null)
	    //UIHelper.showUserCenter(getActivity(), user.getId(),user.getName());
    }

//    private void executeOnLoadDataSuccess(List<User> data) {
//	mAdapter.clear();
//	mAdapter.addData(data);
//	mListView.setVisibility(View.VISIBLE);
//    }
}
