package com.example.bar_code_app;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

class CustomAdapter2 implements ListAdapter {
    ArrayList<item_category_details> arrayList;
    ArrayAdapter<String> arrayAdapter;
    Context context;
    String driver_email="";
    AlertDialog mydialog;

    public CustomAdapter2(Context context, ArrayList<item_category_details> arrayList) {
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
        final item_category_details item_data = arrayList.get(position);
        if(convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.list_row2, null);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context,item_data.data,Toast.LENGTH_SHORT).show();
                    String data=item_data.data;
                    if(!data.equals("No Products")) {
                        List_Items.item_id = new ArrayList();
                        List_Items.item_name = new ArrayList();
                        List_Items.item_desc = new ArrayList();
                        List_Items.item_manu = new ArrayList();
                        List_Items.item_price = new ArrayList();
                        List_Items.item_type = new ArrayList();
                        List_Items.bar_code = new ArrayList();
                        List_Items.qty = new ArrayList();
                        List_Items.added_date = new ArrayList();
                        List_Items.added_time = new ArrayList();
                        List_Items.exp_date = new ArrayList();

                        List_Items.arrayList = new ArrayList();

                        String arr[] = data.split("@@");
                        for (int i = 0; i < arr.length; i++) {
                            Log.d("spliting",arr[i]);
                            String det[] = arr[i].split("#");
                            List_Items.item_id.add(det[0]);
                            List_Items.item_name.add(det[1]);
                            List_Items.item_desc.add(det[2]);
                            List_Items.item_manu.add(det[3]);
                            List_Items.item_price.add(det[4]);
                            List_Items.item_type.add(det[5]);
                            List_Items.bar_code.add(det[6]);
                            List_Items.qty.add(det[7]);
                            List_Items.added_date.add(det[8]);
                            List_Items.added_time.add(det[9]);
                            List_Items.exp_date.add(det[10]);
                            List_Items.status.add(det[11]);




                        }
                        Intent i3=new Intent(context,List_Items.class);
                        context.startActivity(i3);
                    }else{
                        Toast.makeText(context,"No Data available",Toast.LENGTH_LONG).show();
                    }





                }
            });
            TextView tittle = convertView.findViewById(R.id.title);
            ImageView imag = convertView.findViewById(R.id.iv);



            tittle.setText(item_data.category+"\n\t"+item_data.quantity);
            if(item_data.category.equals("Good Product"))
            {
                imag.setImageResource(R.drawable.smile);
            }
            if(item_data.category.equals("Great Product"))
            {
                imag.setImageResource(R.drawable.great);
            }
            if(item_data.category.equals("Expiring Soon"))
            {
                imag.setImageResource(R.drawable.expiring);
            }
            if(item_data.category.equals("Expiring Today"))
            {
                imag.setImageResource(R.drawable.expiring_today);
            }
            if(item_data.category.equals("Expired"))
            {
                imag.setImageResource(R.drawable.expired);
            }






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





}