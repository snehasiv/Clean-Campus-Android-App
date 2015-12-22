package com.example.SEPM;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ListFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class MyComplaintsFragment extends Fragment {

	JSONParser jParser;
	JSONArray products;
	ArrayList<MyComplaints> complaintlist;
	Activity act;
	ProgressDialog pDialog;
	ListView lv;
	CustomListMF customListMF;
	View view;
	int comp_no;
	int uid=1;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		view = inflater.inflate(R.layout.fragment_my_complaints, container, false);
		lv=(ListView)view.findViewById(R.id.mycomp);
		jParser=new JSONParser();
		

	
		complaintlist=new ArrayList<MyComplaints>();
		act=getActivity();
		new LoadMyComplaints().execute();
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
				Intent intent=new Intent(act,ComplaintActivity.class);
				//complaintlist.get
				intent.putExtra("comp_no", 1);
				intent.putExtra("uid", uid);
				startActivity(intent);
				
				
			}
		});
        
        return view;
		
		
	}
	
	
	class LoadMyComplaints extends AsyncTask<String, String, String>{

		
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			 pDialog = new ProgressDialog(getActivity());
	            pDialog.setMessage("Loading Your Complaints");
	            pDialog.setIndeterminate(false);
	            pDialog.setCancelable(false);
	            pDialog.show();
		}

		@Override
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub
			
			 List<NameValuePair> params = new ArrayList<NameValuePair>();
			 String url = "http://172.22.11.14/sepm/get_my_complaints.php";
			 List<NameValuePair> params1 = new ArrayList<NameValuePair>();
         	params1.add(new BasicNameValuePair("uid", String.valueOf(uid)));
         	DefaultHttpClient httpClient = new DefaultHttpClient();
             String paramString = URLEncodedUtils.format(params1, "utf-8");
             url += "?" + paramString;
				JSONObject json = jParser.makeHttpRequest(url, "GET", params);
				Log.d("myjson", json.toString());
				try {
	                // Checking for SUCCESS TAG
	                int success = json.getInt("success");

	                if (success == 1) {

	                    products = json.getJSONArray("products");

	                    for (int i = 0; i < products.length(); i++)
	                    {
	                        JSONObject c = products.getJSONObject(i);

	                        Integer no = c.getInt("Count");
	                        String date = c.getString("Reg_time");
	                        String area = c.getString("Location");
	                        comp_no = c.getInt("Comp_NO");
	                        
	                        complaintlist.add(new MyComplaints(date,area,comp_no));
	                        Log.d("hello1MF", String.valueOf(no)+ " " + String.valueOf(date )+ " " + String.valueOf(comp_no) );

	                    }
	                }
	            } catch (JSONException e) {
	                e.printStackTrace();
	            }

	            return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pDialog.dismiss();

            getActivity().runOnUiThread(new Runnable(){
                public void run() {

                    customListMF = new CustomListMF(getActivity(), R.layout.row_for_my_comp, complaintlist );
                    
                    lv.setAdapter(customListMF);
		}
		
		

});
}
	}
}

