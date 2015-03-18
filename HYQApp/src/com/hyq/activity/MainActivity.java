package com.hyq.activity;


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
public class MainActivity extends FragmentActivity{
	private ViewPager pager;
	private TabHost tabHost;
	private ViewPagerAdapter pagerAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main_layout);
		initView();
	}
	private void initView(){
		 pager = (ViewPager) this.findViewById(R.id.pager);
		 FragmentManager fm=getSupportFragmentManager();
		 FragmentTransaction transaction=fm.beginTransaction();
		 System.out.println("init");
		 addFragment(transaction);
		 transaction.commit();
		 System.out.println("commit");
//		 pagerAdapter = new ViewPagerAdapter(fm);
//	        pager.setAdapter(pagerAdapter);
//	        pager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {//·­Ò³
//	            @Override
//	            public void onPageSelected(int position) {
//	                // when user do a swipe the selected tab change
//	                tabHost.setSelectedTab(position);
//	            }
//	        });
	}
	//Ìí¼Ófragment
	private void addFragment(FragmentTransaction transaction){
		transaction.replace(R.id.linear,new TestFragment());
	}
}
class ViewPagerAdapter extends FragmentStatePagerAdapter {
	private FragmentManager fm;

	public ViewPagerAdapter(FragmentManager fm) {
		super(fm);
		this.fm=fm;
	}

	@Override
	public Fragment getItem(int index) {
		return fm.findFragmentById(index);
	}

	@Override
	public int getCount() {
		return fm.getFragments().size();
	}
	@Override
	public CharSequence getPageTitle(int position) {
		return fm.findFragmentById(position).getTag();
	}
	
}
