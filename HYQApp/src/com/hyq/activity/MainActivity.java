package com.hyq.activity;

import java.util.ArrayList;
import java.util.List;

import com.hyq.fragment.BaseFragment;
import com.hyq.fragment.TestFragment;
import com.hyq.widget.TabHost;
import com.hyqapp.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.Window;
import android.view.WindowManager;

//Ê×Ò³
public class MainActivity extends FragmentActivity {
	private ViewPager pager;
	private TabHost tabHost;
	private List<BaseFragment> fragments = new ArrayList<BaseFragment>();
	private ViewPagerAdapter pagerAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main_layout);
		initView();
	}

	private void initView() {
		pager = (ViewPager) this.findViewById(R.id.pager);
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction transaction = fm.beginTransaction();
		addFragment(transaction);
		transaction.commit();
		pagerAdapter = new ViewPagerAdapter(fm, fragments);
		pager.setAdapter(pagerAdapter);
		pager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {// ·­Ò³
			@Override
			public void onPageSelected(int position) {
				// when user do a swipe the selected tab change
				tabHost.setSelectedTab(position);
			}
		});
	}

	// Ìí¼Ófragment
	private void addFragment(FragmentTransaction transaction) {
		BaseFragment fragment = new TestFragment();
		transaction.add(R.id.linear, fragment);
		fragments.add(fragment);
	}
}

class ViewPagerAdapter extends FragmentStatePagerAdapter {
	private List<BaseFragment> fragments;

	public ViewPagerAdapter(FragmentManager fm, List<BaseFragment> fragments) {
		super(fm);
		this.fragments = fragments;
	}

	@Override
	public Fragment getItem(int index) {
		return fragments.get(index);
	}

	@Override
	public int getCount() {
		return fragments.size();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return fragments.get(position).name;
	}

}
