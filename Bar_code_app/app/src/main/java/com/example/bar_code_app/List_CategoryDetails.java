package com.example.bar_code_app;

import android.os.Bundle;
import android.widget.ListView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class List_CategoryDetails extends AppCompatActivity {
    public static ArrayList<item_category_details> arrayList = new ArrayList<item_category_details>();


    public static ArrayList category=new ArrayList();
    public static ArrayList data=new ArrayList();
    public static ArrayList quantity=new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list_category_details);
        final ListView list = findViewById(R.id.list);

        for(int i=0;i<category.size();i++)
        {
            arrayList.add(new item_category_details(category.get(i).toString(),data.get(i).toString(),quantity.get(i).toString()));

        }


        CustomAdapter2 customAdapter = new CustomAdapter2(this, arrayList);
        list.setAdapter(customAdapter);

    }
}