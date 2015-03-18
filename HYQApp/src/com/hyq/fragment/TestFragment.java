package com.hyq.fragment;

import com.hyqapp.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TestFragment extends Fragment{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		System.out.println("oncreate");
		return inflater.inflate(R.layout.test_fragment_layout, container, false);
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		System.out.println("oncreateview");
		super.onCreate(savedInstanceState);
	}
	@Override
	public void onPause() {
		System.out.println("onpouse");
		super.onPause();
	}
}
