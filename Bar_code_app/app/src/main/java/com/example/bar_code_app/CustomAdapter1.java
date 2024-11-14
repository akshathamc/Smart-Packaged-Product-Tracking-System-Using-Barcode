package com.example.bar_code_app;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

class CustomAdapter1 implements ListAdapter {
    ArrayList<list_items_catg> arrayList;
    ArrayAdapter<String> arrayAdapter;
    Context context;
    String driver_email="";
    AlertDialog mydialog;
    int q=0,q1=0,new_qty=0,days=0,qq=0;
    String pid="",old_date="";

    public CustomAdapter1(Context context, ArrayList<list_items_catg> arrayList) {
        this.arrayList=arrayList;
        this.context=context;





    }
    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }
    @Override
    public boolean isEnabled(int position) {
        return true;
    }
    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
    }
    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
    }
    @Override
    public int getCount() {
        return arrayList.size();
    }
    @Override
    public Object getItem(int position) {
        return position;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public boolean hasStableIds() {
        return false;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final list_items_catg item_data = arrayList.get(position);
        if(convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.list_row3, null);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });

            TextView tittle = convertView.findViewById(R.id.title);
            TextView tv1 = convertView.findViewById(R.id.tv1);
            TextView tvd = convertView.findViewById(R.id.title1);
            ImageView iv = convertView.findViewById(R.id.iv);
            ImageView ivp = convertView.findViewById(R.id.ivp);
            ImageView ivm = convertView.findViewById(R.id.ivm);
            ImageView ivd = convertView.findViewById(R.id.imageView2);

            Button b=convertView.findViewById(R.id.b);
            Button b1=convertView.findViewById(R.id.b1);

            tittle.setText("Item ID:"+item_data.item_id+"\nItem Name:"+item_data.item_name
                    +"\nDescription:"+item_data.item_desc
                    //+"\nBrand:"+item_data.item_manu
                    +"\nPrice: Rs."+item_data.item_price
                    +"\nCategory:"+item_data.item_type
                    +"\nBar Code:"+item_data.bar_code
                    +"\nQuantity:"+item_data.qty
                    +"\nAdded Date:"+item_data.added_date
                   // +"\nAdded Time:"+item_data.added_time
                    +"\nExpiry Date:"+item_data.exp_date


            );
            Picasso.with(context)
                    .load(Global.url+"I"+item_data.item_id+".png")
                    .into(iv);
            //b.setVisibility(View.GONE);
            //new_qty=Integer.parseInt(item_data.qty);

            ivp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   //q++;
                    int c_qty=Integer.parseInt(item_data.qty);
                    c_qty++;
                    qq++;
                    item_data.qty=c_qty+"";
                   Toast.makeText(context,c_qty+"",Toast.LENGTH_SHORT).show();
                   // q1--;
                 //  tv1.setText(c_qty+"");
                   tv1.setText(qq+"");
                   q=c_qty;

                }
            });
            ivm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int c_qty=Integer.parseInt(item_data.qty);
                    c_qty--;
                    qq--;
                    item_data.qty=c_qty+"";
                    Toast.makeText(context,c_qty+"",Toast.LENGTH_SHORT).show();
                   // tv1.setText(c_qty+"");
                    tv1.setText(qq+"");
                    q=c_qty;

//                    if(q1<Integer.parseInt(item_data.qty)) {
//
//
//                        q--;
//                        q1++;
//                        new_qty--;
//                        Toast.makeText(context,new_qty+"",Toast.LENGTH_SHORT).show();
//                        tv1.setText(q + "");
//                    }
//                    else{
//                        Toast.makeText(context,"You dont have more than "+item_data.qty,Toast.LENGTH_SHORT).show();
//                    }

                }
            });

            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pid=item_data.item_id;

                new update_qty().execute();

                }
            });
            ivd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    days++;
                    tvd.setText("Extends days by "+days+" days");



                }
            });
            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pid=item_data.item_id;
                    old_date=item_data.exp_date;

                    new update_dates().execute();

                }
            });
        }
        return convertView;
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }
    @Override
    public int getViewTypeCount() {
        return arrayList.size();
    }
    @Override
    public boolean isEmpty() {
        return false;
    }

    private class update_qty extends AsyncTask<Void,String,String>
    {
        @Override
        public String doInBackground(Void... Void)
        {
            JSONObject jsn = new JSONObject();
            String response = "";
            try {
                URL url = new URL(Global.url +"update_qty");
                //jsn.put("qty", new_qty);
                jsn.put("qty", q+"");

                jsn.put("hid",user_home.hid);
                jsn.put("pid",pid);

                response = HttpClientConnection.getData(url,jsn);
                Log.d("Response",""+response);
            }
            catch (MalformedURLException e)
            {
                e.printStackTrace();
            }
            catch(JSONException e)
            {
                e.printStackTrace();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
            return response;
        }
        @Override
        protected void onPostExecute(String s) {


            if(s.contains("notok")) {
                Toast.makeText(context,"Not Updated", Toast.LENGTH_LONG).show();

            }
            else{
                if(s.endsWith("null"))
                {
                    s=s.substring(0,s.length()-4);
                }
                Toast.makeText(context,"Quantity Updated", Toast.LENGTH_LONG).show();




            }


        }
    }

    private class update_dates extends AsyncTask<Void,String,String>
    {
        @Override
        public String doInBackground(Void... Void)
        {
            JSONObject jsn = new JSONObject();
            String response = "";
            try {
                URL url = new URL(Global.url +"update_dates");
                jsn.put("days", days+"");

                jsn.put("hid",user_home.hid);
                jsn.put("pid",pid);
                jsn.put("old_date",old_date);

                response = HttpClientConnection.getData(url,jsn);
                Log.d("Response",""+response);
            }
            catch (MalformedURLException e)
            {
                e.printStackTrace();
            }
            catch(JSONException e)
            {
                e.printStackTrace();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
            return response;
        }
        @Override
        protected void onPostExecute(String s) {


            if(s.contains("notok")) {
                Toast.makeText(context,"Not Updated", Toast.LENGTH_LONG).show();

            }
            else{
                if(s.endsWith("null"))
                {
                    s=s.substring(0,s.length()-4);
                }
                Toast.makeText(context,"Date Extended by "+days, Toast.LENGTH_LONG).show();




            }


        }
    }


}