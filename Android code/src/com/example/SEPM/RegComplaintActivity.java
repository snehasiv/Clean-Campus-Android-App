package com.example.SEPM;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class RegComplaintActivity extends Activity {
	private ProgressDialog pDialog;
	protected static final int RESULT_LOAD_IMAGE = 1;
	ImageView iv;
	Button send,upload;
	EditText spec;
	String place,msg;
	 EditText Description;
	 JSONParser jsonParser = new JSONParser();
	    Button btnadd;
	    private RadioGroup radiogroup1;
	    private RadioGroup radiogroup2;
	    private RadioButton radio0,radio1,radio2,radio3,radio4,radio5,radio6,radio7,radio8,radio9,radio10;
	    int priority;
	    String location;
	    int uid;
	    private static String url_create_product = "http://172.22.11.14/sepm/create_complaint.php";
	    
	    // JSON Node names
	    private static final String TAG_SUCCESS = "success";
	    private static final String TAG_IMAGE = "image";
	    
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.complaintregistration);
		send=(Button)findViewById(R.id.add);
		iv=(ImageView)findViewById(R.id.imageView1);
		spec=(EditText)findViewById(R.id.editText1);
		upload=(Button)findViewById(R.id.upload);
		
		 place=spec.getText().toString();
		 msg = "Sir , complaint registration for: " + place;
	        radiogroup1 = (RadioGroup) findViewById(R.id.radioGroup1);
	        radiogroup2 = (RadioGroup) findViewById(R.id.radioGroup2);
	        Description = (EditText) findViewById(R.id.editText1);
	        btnadd = (Button) findViewById(R.id.add);
	        Bundle extras = getIntent().getExtras();
	        uid = extras.getInt("uid");
	        radiogroup1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
	        	@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					// find which radio button is selected
					if(checkedId == R.id.radio0) 
					{
						priority=3;
					} else if(checkedId == R.id.radio1) {
						priority=2;
					} else { 
							priority=1;
							}
				}
				
			});
	        
	        radiogroup2.setOnCheckedChangeListener(new OnCheckedChangeListener() {
	        	@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					// find which radio button is selected
					if(checkedId == R.id.radio3) 
					{
						location="CP";
					} 
					else if(checkedId == R.id.radio4) {
						location="BH-1";
					}else if(checkedId == R.id.radio5) {
						location="BH-2";
					}else if(checkedId == R.id.radio6) {
						location="BH-3";
					}else if(checkedId == R.id.radio7) {
						location="GH";
					}else if(checkedId == R.id.radio8) {
						location="canteen";
					}else if(checkedId == R.id.radio9) {
						location="SAC";
					} else { 
							location="acads";
							}
				}
				
			});
		
		send.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent(Intent.ACTION_SEND);
				intent.setType("text/plain");
				intent.putExtra("uid",uid);
				intent.putExtra(Intent.EXTRA_EMAIL, "swapnilgarg0@gmail.com");
				intent.putExtra(Intent.EXTRA_SUBJECT, "Complaint regarding Cleanliness issues");
			intent.putExtra(Intent.EXTRA_TEXT,msg  );

				startActivity(Intent.createChooser(intent, "Send Email"));
				new CreateNewProduct().execute();
				
			}
		});
		
		
		upload.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				 Intent i = new Intent(
	                        Intent.ACTION_PICK,
	                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

	                startActivityForResult(i, RESULT_LOAD_IMAGE);		}
		});
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		 if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
	            Uri selectedImage = data.getData();
	            String[] filePathColumn = { MediaStore.Images.Media.DATA };

	            Cursor cursor = getContentResolver().query(selectedImage,
	                    filePathColumn, null, null, null);
	            cursor.moveToFirst();

	            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
	            String picturePath = cursor.getString(columnIndex);
	            cursor.close();

	            
	            iv.setImageBitmap(BitmapFactory.decodeFile(picturePath));
	}
	}
    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
	class CreateNewProduct extends AsyncTask<String, String, String> {
   	 
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(RegComplaintActivity.this);
            pDialog.setMessage("Creating Product..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        protected String doInBackground(String... args) {
            String desc = Description.getText().toString();
            Bitmap viewBitmap = Bitmap.createBitmap(iv.getWidth(),iv.getHeight(),Bitmap.Config.ARGB_8888);
    	    
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("uid", Integer.valueOf(uid).toString()));
            params.add(new BasicNameValuePair("priority", Integer.valueOf(priority).toString()));
            params.add(new BasicNameValuePair("location", location));
            params.add(new BasicNameValuePair("description", desc));
            params.add(new BasicNameValuePair("image", getStringImage(viewBitmap)));
            
               
            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_create_product,
                    "POST", params);
 
            // check log cat fro response
            Log.d("Create Response", json.toString());
 
            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);
 
                if (success == 1) {
                    // successfully created product
 
                	Intent i = new Intent(getApplicationContext(), AllComplaintsFragment.class);
					Toast.makeText( RegComplaintActivity.this, "complaint successfully registered" , Toast.LENGTH_SHORT).show();
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
 
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();
        }
 
    }

}
	
	

	

