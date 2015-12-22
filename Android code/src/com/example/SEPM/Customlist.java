

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


 public class Customlist extends ArrayAdapter<Complaints> {
    List<Complaints> items;
    public Customlist(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public Customlist(Context context, int resource, List<Complaints> items) {
        super(context, resource, items);
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.row, null);
        }

        if(items!=null)
        {
            TextView tt1 = (TextView) v.findViewById(R.id.place);
            TextView tt2 = (TextView) v.findViewById(R.id.date);
            TextView tt3 = (TextView) v.findViewById(R.id.number);
         

         
               String a=items.get(position).area;
                String b=items.get(position).date;
                Integer c=items.get(position).counter;

         tt1.setText(items.get(position).area);
            tt2.setText(items.get(position).date);
            tt3.setText(String.valueOf(c));
            
          
            
            Log.d("heeeeellooo",String.valueOf(a) + " "  );
           
        }
        return v;
    }

}