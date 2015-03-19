package com.hyq.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.hyq.entity.Test;
import com.hyq.utils.WebHelper;
import com.hyqapp.R;

@TargetApi(Build.VERSION_CODES.KITKAT)
public class TestFragment extends BaseFragment{
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
	public void onResume() {
		AsyncTask<Void, Integer, String> task=new AsyncTask<Void, Integer, String>(){

			@Override
			protected String doInBackground(Void... paramArrayOfParams) {
				StringBuilder resultStr=new StringBuilder();
				List<NameValuePair> params=new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("name", "valueº«"));
				params.add(new BasicNameValuePair("ITypeName", "ÆÕÍ¨·¢Æ±"));
//				Map<String, String> result=WebHelper.getInstance().resultUrlPost("http://192.168.1.16:45377/test/test.aspx", params);
				Map<String, String> result=WebHelper.getInstance().resultUrlGet("http://192.168.1.16:8090/test/test.aspx", params);
				for (String str : result.keySet()) {
//					System.out.println(str+":"+result.get(str));
					resultStr.append(str+":"+result.get(str));
				}
//				List<Test> tests=new ArrayList<Test>();
//				Test test=null;
				Gson gson=new Gson();
				String json=result.get("resultStr");
				JSONArray array;
				try {
					array = new JSONArray(json);
					for (int i = 0; i < array.length(); i++) {
						Object jsonObj=array.get(i);
						System.out.println(jsonObj.toString());
						Test t=gson.fromJson(jsonObj.toString(), Test.class);
						System.out.println("test:"+t.getiTypeName());
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//				System.out.println(gson.toJson(tests));
				return resultStr.toString();
			}
			
		};
		task.execute();
		super.onResume();
	}
	@Override
	public void onPause() {
		super.onPause();
	}
}
