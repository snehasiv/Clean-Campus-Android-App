package com.example.SEPM;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
 
// insert Login type .. radio buttons not included in code currently.
public class Login extends Activity 
{
 
    private EditText editTextUserName;
    private EditText editTextPassword;
    Button login;
    Button register;
    private static String url_all_products = "http://172.22.11.14/sepm/get_user.php";
    public static final String TAG_SUCCESS = "success";
    public static final String TAG_UID = "uid";
    
    JSONParser jParser = new JSONParser();
    String username;
    String password;
    int uid,success;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
 
        editTextUserName = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.btnlogin);
        register = (Button) findViewById(R.id.btnregister);
  
        login.setOnClickListener(new View.OnClickListener() {
            
        	   @Override
        	   public void onClick(View v) {
        		   username = editTextUserName.getText().toString();
        	        password = editTextPassword.getText().toString();
        	 
        	        rlogin(username,password);
        		/*   
        	    Intent intent  = new Intent(getApplicationContext(),MainMenu.class);
        	     // get user id of that user from JSON
        	    int uid=1;
        	    intent.putExtra("uid", uid);
        	     
        	    startActivity(intent);
        	      */
        	   }
        	  });

        register.setOnClickListener(new View.OnClickListener() {
        
 	   @Override
 	   public void onClick(View v) {
 	     
 	     
 	    Intent intent  = new Intent(getApplicationContext(),RegisterationActivity.class);
 	    startActivity(intent);
 	      
 	   }
 	  });
 	    }
    private void rlogin(final String username, final String password) 
    {
        class LoginAsync extends AsyncTask<String, Void, String>{
 
            private Dialog loadingDialog;
 
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(Login.this, "Please wait", "Loading...");
            }
 
            @Override
            protected String doInBackground(String... params) 
            {
            	List<NameValuePair> params1 = new ArrayList<NameValuePair>();
            	params1.add(new BasicNameValuePair("email_id", username));
            	params1.add(new BasicNameValuePair("password", password));
            	DefaultHttpClient httpClient = new DefaultHttpClient();
                String paramString = URLEncodedUtils.format(params1, "utf-8");
                url_all_products += "?" + paramString;
                // getting JSON string from URL
	            JSONObject json = jParser.makeHttpRequest(url_all_products, "GET", params1);
	 
	            // Check your log cat for JSON reponse
	            Log.d("login: ", json.toString());
	 
	            try {
	                // Checking for SUCCESS TAG
	                success = json.getInt(TAG_SUCCESS);
	    
	                if (success == 1) {
	                    // products found
	                    // Getting Array of Products
	                	uid = json.getInt(TAG_UID);
	                    }
	            } catch (JSONException e) {
	                e.printStackTrace();
	            }
	 
	            return null;
	        }
	 
	            @Override
            protected void onPostExecute(String result){
        // modify read from json output
            	loadingDialog.dismiss();
            	if(success==1)
            	{
                    Intent intent = new Intent(Login.this, TabActivity1.class);
                    intent.putExtra(TAG_UID, uid);
                    finish();
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(), "Invalid User Name or Password", Toast.LENGTH_LONG).show();
                }
            }
        }
 
        LoginAsync la = new LoginAsync();
        la.execute(username, password);
 
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