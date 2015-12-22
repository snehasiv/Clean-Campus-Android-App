package com.example.SEPM;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class RegisterationActivity extends FragmentActivity {

	 JSONParser jsonParser = new JSONParser();
	 private ProgressDialog pDialog;
	 EditText Name,Email_id,Type,Password,Confirmpass;
	 RadioGroup rg;
	 Integer value;
	 RadioButton student,fac,othr;
	 private static String url_create_resident = "http://172.22.11.14/sepm/create_resident.php";
	 
	    // JSON Node names
	    private static final String TAG_SUCCESS = "success";
	 
	Button btn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registration);
		
		Name=(EditText)findViewById(R.id.editText1);
	//	Roll_no=(EditText)findViewById(R.id.editText2);
		Email_id=(EditText)findViewById(R.id.editText3);
		rg=(RadioGroup)findViewById(R.id.radioGroup1);
		student=(RadioButton)findViewById(R.id.radio0);
		fac=(RadioButton)findViewById(R.id.radio1);
		othr=(RadioButton)findViewById(R.id.radio2);
		Password=(EditText)findViewById(R.id.editText5);
		Confirmpass=(EditText)findViewById(R.id.editText6);
		btn=(Button)findViewById(R.id.button1);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		student.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
				if(student.isChecked())
				{
					value=1;
				}
			}
		});
		
		fac.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(fac.isChecked())
				{
					value=2;
				}
			}
		});
		
		othr.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(othr.isChecked())
				{
					value=3;
				}
			}
		});
	}
	 class CreateNewProduct extends AsyncTask<String, String, String> {
		 
	        /**
	         * Before starting background thread Show Progress Dialog
	         * */
	      //  @Override
		 		protected void onPreExecute() {
	            super.onPreExecute();
	            pDialog = new ProgressDialog(RegisterationActivity.this);
	            pDialog.setMessage("Creating Product..");
	            pDialog.setIndeterminate(false);
	            pDialog.setCancelable(false);
	            pDialog.show();
	            
	        }
	       
	        /**
	         * Creating product
	         * */
	      protected String doInBackground(String... args) {
	            String name = Name.getText().toString();
	          //  String roll_no = Roll_no.getText().toString();
	            String email_id = Email_id.getText().toString();
	           // String type =Type.getText().toString();
	            String password = Password.getText().toString();
	          
	 
	            // Building Parameters
	            List<NameValuePair> params = new ArrayList<NameValuePair>();
	            params.add( new BasicNameValuePair("name", name));
	        //    params.add(new BasicNameValuePair("roll_no", roll_no));
	            params.add(new BasicNameValuePair("email_id", email_id));
	            params.add(new BasicNameValuePair("type", value.toString()));
	            params.add(new BasicNameValuePair("password", password));
	 
	            Log.d("hello", "hey");
	            // getting JSON Object
	            // Note that create product url accepts POST method
	            JSONObject json = jsonParser.makeHttpRequest(url_create_resident,
	                    "POST", params);
	 
	            // check log cat fro response
	            Log.d("Create Response", json.toString());
	 
	            // check for success tag
	            try {
	                int success = json.getInt(TAG_SUCCESS);
	 
	                if (success == 1) {
	                    // successfully created product
	                    Intent i = new Intent(getApplicationContext(), TabActivity1.class);
	                    startActivity(i);
	 
	                    // closing this screen
	                    finish();
	                } else {
	                    // failed to create product
	                }
	            } catch (JSONException e) {
	                e.printStackTrace();
	            }
	 
	            return null;
	        }
	 
	        /**
	         * After completing background task Dismiss the progress dialog
	         * **/
	  /*      protected void onPostExecute(String file_url) {
	            // dismiss the dialog once done
	            pDialog.dismiss();
	        }*/
	 
	    }

}

