package com.example.SEPM;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ListFragment;
import android.app.ProgressDialog;
import android.content.Intent;
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

public class AllComplaintsFragment extends Fragment {

	JSONParser jParser;
	JSONArray products;
	ArrayList<Complaints> complaintlist;
	Activity act;
	ProgressDialog pDialog;
	ListView lv;
	Customlist customlist;
	View view;
	int comp_no=1;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		view = inflater.inflate(R.layout.fragment_all_complaints, container, false);
		lv=(ListView)view.findViewById(R.id.list1);
		jParser=new JSONParser();
		
		complaintlist=new ArrayList<Complaints>();
		act=getActivity();
		new LoadAllComplaints().execute();
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(act,ComplaintActivity.class);
				//complaintlist.get
				intent.putExtra("uid", 1);
				intent.putExtra("comp_no", 1);
				startActivity(intent);
			}
		});
        
        return view;
		
		
	}
	
	
	class LoadAllComplaints extends AsyncTask<String, String, String>{

		
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			 pDialog = new ProgressDialog(getActivity());
	            pDialog.setMessage("Loading Notifications");
	            pDialog.setIndeterminate(false);
	            pDialog.setCancelable(false);
	            pDialog.show();
		}

		@Override
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub
			 List<NameValuePair> params = new ArrayList<NameValuePair>();


				JSONObject json = jParser.makeHttpRequest("http://172.22.11.14/sepm/get_all_complaints.php", "GET", params);
Log.d("JSONParser",json.toString());				
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

	                        complaintlist.add(new Complaints(date,area,no));
	                        Log.d("hello1", String.valueOf(no)+ " " + String.valueOf(date )+ " " + String.valueOf(area) );

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


                	
                    customlist = new Customlist(getActivity(), R.layout.row, complaintlist );
                    
                    lv.setAdapter(customlist);
		}
		
		

});
}
	}
}

