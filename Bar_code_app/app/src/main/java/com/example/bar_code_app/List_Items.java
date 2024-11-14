package com.example.bar_code_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class List_Items extends AppCompatActivity {
    public static ArrayList<list_items_catg> arrayList = new ArrayList<list_items_catg>();


    public static ArrayList item_id=new ArrayList();
    public static ArrayList item_name=new ArrayList();
    public static ArrayList item_desc=new ArrayList();
    public static ArrayList item_manu=new ArrayList();
    public static ArrayList item_price=new ArrayList();
    public static ArrayList item_type=new ArrayList();
    public static ArrayList bar_code=new ArrayList();
    public static ArrayList qty=new ArrayList();
    public static ArrayList added_date=new ArrayList();
    public static ArrayList added_time=new ArrayList();
    public static ArrayList exp_date=new ArrayList();
    public static ArrayList status=new ArrayList();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);
        final ListView list = findViewById(R.id.list);

        for(int i=0;i<item_id.size();i++)
        {
            arrayList.add(new list_items_catg(item_id.get(i).toString(),item_name.get(i).toString(),item_desc.get(i).toString(),item_manu.get(i).toString(),item_price.get(i).toString(),item_type.get(i).toString(),bar_code.get(i).toString()
                    ,qty.get(i).toString(),added_date.get(i).toString(),added_time.get(i).toString(),exp_date.get(i).toString(),status.get(i).toString()));

        }


        CustomAdapter1 customAdapter = new CustomAdapter1(this, arrayList);
        list.setAdapter(customAdapter);
    }
}