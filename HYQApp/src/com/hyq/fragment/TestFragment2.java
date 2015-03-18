package com.hyq.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hyqapp.R;

public class TestFragment2 extends BaseFragment{
	private View rootView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		System.out.println("oncreateView");
		rootView=inflater.inflate(R.layout.test_fragment_layout, container, false);
		((TextView)rootView.findViewById(R.id.content)).setText(getClass().getSimpleName());
		return rootView;
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		System.out.println("oncreate");
		super.onCreate(savedInstanceState);
	}
	@Override
	public void onPause() {
		super.onPause();
	}
}
