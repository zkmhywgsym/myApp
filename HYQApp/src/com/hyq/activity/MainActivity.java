package com.hyq.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.hyq.fragment.BaseFragment;
import com.hyq.fragment.TestFragment;
import com.hyq.fragment.TestFragment1;
import com.hyq.fragment.TestFragment2;
import com.hyq.widget.Tab;
import com.hyq.widget.Tab.TabListener;
import com.hyq.widget.TabHost;
import com.hyqapp.R;
import com.ikimuhendis.ldrawer.ActionBarDrawerToggle;
import com.ikimuhendis.ldrawer.DrawerArrowDrawable;

//Ê×Ò³
public class MainActivity extends FragmentActivity implements TabListener {
	private ViewPager pager;
	private TabHost tabHost;
	private List<BaseFragment> fragments = new ArrayList<BaseFragment>();
	private ViewPagerAdapter pagerAdapter;
	private DrawerArrowDrawable drawerArrow;
	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private ListView navdrawer;

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
		tabHost = (TabHost) findViewById(R.id.tabHost);
		navdrawer=(ListView) findViewById(R.id.navdrawer);
		initNavdrawer();
		FragmentManager fm = getSupportFragmentManager();
		// FragmentTransaction transaction = fm.beginTransaction();
		addFragment();
		// transaction.commit();
		pagerAdapter = new ViewPagerAdapter(fm, fragments);
		pager.setAdapter(pagerAdapter);
		pager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {// ·­Ò³
			@Override
			public void onPageSelected(int position) {
				// when user do a swipe the selected tab change
				tabHost.setSelectedTab(position);
			}
		});
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawerArrow = new DrawerArrowDrawable(this) {
			@Override
			public boolean isLayoutRtl() {
				return false;
			}
		};
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				drawerArrow, R.string.drawer_open, R.string.drawer_close) {

			public void onDrawerClosed(View view) {
				super.onDrawerClosed(view);
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		mDrawerToggle.syncState();
	}
	//²Ëµ¥
	private void initNavdrawer(){
		String[] items=getResources().getStringArray(R.array.main_left_menu);
		List<Map<String, String>> data=new ArrayList<Map<String,String>>();
		Map<String, String> map=new HashMap<String, String>();
		for (String item : items) {
			map.put("item", item);
		}
		data.add(map);
		navdrawer.setAdapter(new SimpleAdapter(this, data, android.R.layout.simple_list_item_1, new String[]{"item"},new int[]{android.R.id.text1}));
		navdrawer.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> root, View view, int index,
					long position) {
				
			}
		});
	}
	// Ìí¼Ófragment
	@SuppressWarnings("deprecation")
	private void addFragment() {
		fragments.add(new TestFragment());
		tabHost.addTab(tabHost
				.newTab()
				.setIcon(
						getResources().getDrawable(
								R.drawable.ic_person_black_24dp))
				.setTabListener(this));
		fragments.add(new TestFragment1());
		tabHost.addTab(tabHost
				.newTab()
				.setIcon(
						getResources().getDrawable(
								R.drawable.ic_person_black_24dp))
				.setTabListener(this));
		fragments.add(new TestFragment2());
		tabHost.addTab(tabHost
				.newTab()
				.setIcon(
						getResources().getDrawable(
								R.drawable.ic_person_black_24dp))
				.setTabListener(this));
		fragments.add(new TestFragment());
		tabHost.addTab(tabHost
				.newTab()
				.setIcon(
						getResources().getDrawable(
								R.drawable.ic_person_black_24dp))
				.setTabListener(this));
		fragments.add(new TestFragment1());
		tabHost.addTab(tabHost
				.newTab()
				.setIcon(
						getResources().getDrawable(
								R.drawable.ic_person_black_24dp))
				.setTabListener(this));
		fragments.add(new TestFragment2());
		tabHost.addTab(tabHost
				.newTab()
				.setIcon(
						getResources().getDrawable(
								R.drawable.ic_person_black_24dp))
				.setTabListener(this));
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
