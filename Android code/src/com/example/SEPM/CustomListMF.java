package com.example.SEPM;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;


 public class CustomListMF extends ArrayAdapter<MyComplaints> {
    List<MyComplaints> items;
    public CustomListMF(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public CustomListMF(Context context, int resource, List<MyComplaints> items) {
        super(context, resource, items);
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.row_for_my_comp, null);
        }

        if(items!=null)
        {
            TextView tt1 = (TextView) v.findViewById(R.id.area_MF);
            TextView tt2 = (TextView) v.findViewById(R.id.date_MF);
         

         
                String a=items.get(position).area;
                String b=items.get(position).date;

             tt1.setText(items.get(position).area);
            tt2.setText(items.get(position).date);
            
          
            
            Log.d("heeeeelloooMF",String.valueOf(a) + " "  );
           
        }
        return v;
    }

}