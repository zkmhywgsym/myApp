package com.hyq.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Window;
import android.view.WindowManager;

import com.hyq.fragment.BaseFragment;
import com.hyq.fragment.TestFragment;
import com.hyq.fragment.TestFragment1;
import com.hyq.fragment.TestFragment2;
import com.hyq.widget.Tab;
import com.hyq.widget.Tab.TabListener;
import com.hyq.widget.TabHost;
import com.hyqapp.R;

//Ê×Ò³
public class MainActivity extends FragmentActivity implements TabListener{
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
		tabHost=(TabHost) findViewById(R.id.tabHost);
		FragmentManager fm = getSupportFragmentManager();
//		FragmentTransaction transaction = fm.beginTransaction();
		addFragment();
//		transaction.commit();
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
	private void addFragment() {
		fragments.add(new TestFragment());
		tabHost.addTab(tabHost.newTab().setIcon(getResources().getDrawable(R.drawable.ic_person_black_24dp)).setTabListener(this));
		fragments.add(new TestFragment1());
		tabHost.addTab(tabHost.newTab().setIcon(getResources().getDrawable(R.drawable.ic_person_black_24dp)).setTabListener(this));
		fragments.add(new TestFragment2());
		tabHost.addTab(tabHost.newTab().setIcon(getResources().getDrawable(R.drawable.ic_person_black_24dp)).setTabListener(this));
		fragments.add(new TestFragment());
		tabHost.addTab(tabHost.newTab().setIcon(getResources().getDrawable(R.drawable.ic_person_black_24dp)).setTabListener(this));
		fragments.add(new TestFragment1());
		tabHost.addTab(tabHost.newTab().setIcon(getResources().getDrawable(R.drawable.ic_person_black_24dp)).setTabListener(this));
		fragments.add(new TestFragment2());
		tabHost.addTab(tabHost.newTab().setIcon(getResources().getDrawable(R.drawable.ic_person_black_24dp)).setTabListener(this));
	}

	@Override
	public void onTabSelected(Tab tab) {
		pager.setCurrentItem(tab.getPosition(), true);
	}

	@Override
	public void onTabReselected(Tab tab) {
		
	}

	@Override
	public void onTabUnselected(Tab tab) {
		
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
