package com.example.SEPM;

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
import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;	 

public class ComplaintActivity extends Activity 
{
 
    // Progress Dialog
    private ProgressDialog pDialog;
 
    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();
 
    ArrayList<HashMap<String, String>> productsList;
 
    // url to get all products list
    private static String url_all_products = "http://localhost/sepm/get_full_complaints.php";
    private static String url = "http://localhost/sepm/set_volunteer.php"; 
    ImageView imageview;
    TextView time,count,location,priority,name,desc; 
    int vvid,uid,success,vcount,comp_no;
    Boolean chkvid;
    Button btnv,btnret;
    String vpriority,vdesc,vtime,vlocation,vname;
    // products JSONArray
    JSONArray products = null;
   
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);
        
        imageview = (ImageView) findViewById(R.id.imageView1);
        time = (TextView) findViewById(R.id.rTime);
        count = (TextView) findViewById(R.id.rDate);
        location = (TextView) findViewById(R.id.textView5);
        priority = (TextView) findViewById(R.id.textView7);
        name = (TextView) findViewById(R.id.textView9);
        desc = (TextView) findViewById(R.id.textView3);
        btnv = (Button) findViewById(R.id.volunteer);
        btnret = (Button) findViewById(R.id.volunteer);
        
        //volunteer = (Button)findViewById(R.id.btn1);
        // Hashmap for ListView
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                comp_no=1; uid=1;
	            } else {
                comp_no= extras.getInt("comp_no");
                uid= extras.getInt("uid");
            }
        } else {
            comp_no= (Integer) savedInstanceState.getSerializable("comp_no");
            uid= (Integer) savedInstanceState.getSerializable("uid");
        }
        
        // Loading products in Background Thread
        new LoadProduct().execute(); 
        
        //imageView.set("Step One: blast egg");
        try{
        Thread.sleep(1000);//Simulate intensive code
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        time.setText(vtime);
        try{
            Thread.sleep(1000);//Simulate intensive code
    		} catch (InterruptedException e) {
    			e.printStackTrace();
    		}
        count.setText(String.valueOf(vcount));
        try{
            Thread.sleep(1000);//Simulate intensive code
    		} catch (InterruptedException e) {
    			e.printStackTrace();
    		}
        location.setText(vlocation);
        try{
            Thread.sleep(1000);//Simulate intensive code
    		} catch (InterruptedException e) {
    			e.printStackTrace();
    		}
        priority.setText(vpriority);
        try{
            Thread.sleep(1000);//Simulate intensive code
    		} catch (InterruptedException e) {
    			e.printStackTrace();
    		}
        name.setText(vname);
        try{
            Thread.sleep(1000);//Simulate intensive code
    		} catch (InterruptedException e) {
    			e.printStackTrace();
    		}
        desc.setText(vdesc);
        if(!(vvid>=1))
        {
        	btnv.setEnabled(false);
        }else
        {
        	btnv.setEnabled(true);
        }
        btnv.setOnClickListener(new View.OnClickListener() {
            
     	   @Override
     	  public void onClick(View v) {
     	  new CheckVolunteer().execute(); 
     	    
     	   }
     	  });
        btnret.setOnClickListener(new View.OnClickListener() {
            
      	   @Override
      	  public void onClick(View v) {
      		 Intent intent = new Intent(ComplaintActivity.this, TabActivity1.class);
             intent.putExtra("uid", uid);
             finish();
             startActivity(intent);  
      	   }
      	  });


    }
    class CheckVolunteer extends AsyncTask<String, Void, String>{
    	 
        private Dialog loadingDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadingDialog = ProgressDialog.show(ComplaintActivity.this, "Please wait", "Loading...");
        }

        @Override
        protected String doInBackground(String... params) 
        {
        	List<NameValuePair> params1 = new ArrayList<NameValuePair>();
        	params1.add(new BasicNameValuePair("comp_no", String.valueOf(comp_no)));
        	params1.add(new BasicNameValuePair("vid", String.valueOf(uid)));
        	// getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url, "POST", params1);
 
            // Check your log cat for JSON reponse+
            Log.d("login: ", json.toString());
 
            try {
            	success = json.getInt("success");

                if (success == 1) 
                {
                	 Toast.makeText(getApplicationContext(), "Successfully Registered as a Volunteer for Complaint ID="+ String.valueOf(comp_no), Toast.LENGTH_LONG).show();      	                       
                   }
                else
                {	
                  	 Toast.makeText(getApplicationContext(), "Unsuccessfully Registration... try again"+ String.valueOf(comp_no), Toast.LENGTH_LONG).show();      	                       
    	                	
                }
            }catch (JSONException e) {
                e.printStackTrace();
            }
        return null;
        }
 
            @Override
        protected void onPostExecute(String result){
    // modify read from json output
        	loadingDialog.dismiss();
        	
        }
    }

        class LoadProduct extends AsyncTask<String, Void, String>{
 
            private Dialog loadingDialog;
 
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(ComplaintActivity.this, "Please wait", "Loading...");
            }
 
            @Override
            protected String doInBackground(String... params) 
            {
            	List<NameValuePair> params1 = new ArrayList<NameValuePair>();
            	params1.add(new BasicNameValuePair("comp_no", String.valueOf(comp_no)));
            	DefaultHttpClient httpClient = new DefaultHttpClient();
                String paramString = URLEncodedUtils.format(params1, "utf-8");
                url_all_products += "?" + paramString;
                // getting JSON string from URL
	            JSONObject json = jParser.makeHttpRequest(url_all_products, "GET", params1);
	 
	            // Check your log cat for JSON reponse+
	            Log.d("login: ", json.toString());
	 
	            try {
	            	success = json.getInt("success");

	                if (success == 1) {

	                        vcount = json.getInt("count");
	                        vtime = json.getString("reg_time");
	                        vlocation = json.getString("location");
	                        vname = json.getString("name");
	                        vvid = json.getInt("vid");
	                        vdesc = json.getString("description");
	                        vpriority = json.getString("priority");
	                       
	                    }
	                else
	                {
	                	Toast.makeText(getApplicationContext(), "THIS COMPLAINT IS NO LONGER ACTIVE", Toast.LENGTH_LONG).show();
	                }
	
	            }catch (JSONException e) {
	                e.printStackTrace();
	            }
            return null;
	        }
	 
	            @Override
            protected void onPostExecute(String result){
        // modify read from json output
            	loadingDialog.dismiss();
            	
            }
        }
 
        
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
 
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
 
        return super.onOptionsItemSelected(item);
    }
}