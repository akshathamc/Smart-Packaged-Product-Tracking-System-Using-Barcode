package com.example.bar_code_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class user_home extends AppCompatActivity {
public static String hid="",name="",type="";
public static TextView tv;
EditText et;
boolean flag=false;
Button b1,b2,b3,b4,b5,b6;
int qty;
Intent i3,i4,i5,i6;
public static String item_id="",item_catg="";
Spinner sp;
    public static int ncount=0;
    MediaPlayer mediaPlayer;
    Vibrator vibrator;
public  static String msg="";
    String Expired="",Expiring_Today="",Expiring_Soon="",Good_Product="",Great_Product="";
    int Expiredi=0,Expiring_Todayi=0,Expiring_Sooni=0,Good_Producti=0,Great_Producti=0;

    ArrayAdapter<String> adapter;
    String catg[] = { "Select", "Baked Grains", "Bakery", "Beverages",
            "Canned Food","Dairy","Laundry","Spices","Body Hygiene" };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        tv=(TextView) findViewById(R.id.tv);
        tv.setText("Welcome "+name);
        et=(EditText) findViewById(R.id.et);
        et.setVisibility(View.GONE);
        b1=(Button) findViewById(R.id.b1);
        b2=(Button) findViewById(R.id.b2);
        b3=(Button) findViewById(R.id.b3);
        b4=(Button) findViewById(R.id.b4);
        b5=(Button) findViewById(R.id.b5);
        b6=(Button) findViewById(R.id.b6);
        b1.setVisibility(View.GONE);
        sp = (Spinner) findViewById(R.id.sp);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, catg);
        sp.setAdapter(adapter);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
i3=new Intent(this,List_Items.class);
i4=new Intent(this,List_CategoryDetails.class);
i5=new Intent(this,profile.class);
i6=new Intent(this,ScanQR.class);
if(!msg.equals("")) {
    tv.setText(msg);
    b1.setVisibility(View.VISIBLE);
    et.setVisibility(View.VISIBLE);
}



if(type.equals("sub"))
{

    b4.setVisibility(View.GONE);
}
        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask()
        {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try
                        {
                            if(flag==false) {
                                new check_scanned_item().execute();
                            }
                            else {
                                Log.d("ncount",ncount+"");
                                if(ncount<3) {

                                    new check_expired_item().execute();
                                }
                                else{
                                    if (flag == false) {
                                        flag = true;
                                    } else if (flag == true) {
                                        flag = false;
                                    }
                                }
                            }

                        }
                        catch (Exception e)
                        {

                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 5000); //execute in every 60s*

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qty=Integer.parseInt(et.getText().toString());
                new add_scanned_product().execute();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item_catg=sp.getSelectedItem().toString();
                try
                {
                    URL url = new URL(Global.url + "get_my_items");
                    JSONObject jsn = new JSONObject();
                    jsn.put("hid",hid);
                    jsn.put("catg",item_catg);


                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().build();
                    StrictMode.setThreadPolicy(policy);
                    String response = HttpClientConnection.getData(url,jsn);

                    if(response.endsWith("null"))
                    {
                        response=response.substring(0,response.length()-4);

                    }
                    Log.d("response",response);
                    if(!response.equals("")) {
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

                        String arr[] = response.split("@@");
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
//                        list_users.task = "send";
                        startActivity(i3);
                    }else{
                        Toast.makeText(getApplicationContext(),"No Transaction available",Toast.LENGTH_LONG).show();
                    }



                }
                catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item_catg=sp.getSelectedItem().toString();
                new check_expiry().execute();
            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            startActivity(i5);
            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(i6);
            }
        });
b6.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
//        String packageName = "com.quintus.labs.grocerystore";
//Log.d("name",packageName);
//        Intent launchIntent = getPackageManager().getLaunchIntentForPackage(packageName);
//        if (launchIntent != null) {
//            Log.d("name...",packageName);
//            startActivity(launchIntent);
//        }


        Intent i = new Intent();
        i.setAction(Intent.ACTION_VIEW);

        i.setClassName("com.quintus.labs.grocerystore",
                "com.quintus.labs.grocerystore.WelcomeActivity1");

        startActivity(i);



    }
});

    }
    private class check_expiry extends AsyncTask<Void,String,String>
    {
        @Override
        public String doInBackground(Void... Void)
        {
            JSONObject jsn = new JSONObject();
            String response = "";
            try {
                URL url = new URL(Global.url +"check_expiry");
                jsn.put("hid",hid);



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

            if(s.endsWith("null"))

            {

                s=s.substring(0,s.length()-4);
            }
Log.d("s=",s);
                if(!s.equals("")) {

                    String arr[] = s.split("@@##");
                    Log.d("length",arr.length+"");
                    for (int i = 0; i < arr.length; i++) {
                        Log.d(">>>>>" + i, arr[i]);


                        if (arr[i].equals("No Products")) {
                            if (i == 0) {
                                Expired = "No Products";
                                Expiredi = 0;
                            }
                            if (i == 1) {
                                Expiring_Today = "No Products";
                                Expiring_Todayi = 0;
                            }
                            if (i == 2) {
                                Expiring_Soon = "No Products";
                                Expiring_Sooni = 0;
                            }
                            if (i == 3) {
                                Good_Product = "No Products";
                                Good_Producti = 0;
                            }
                            if (i == 4) {
                                Great_Product = "No Products";
                                Great_Producti = 0;
                            }
                        } else {


                            String a[] = arr[i].split("@@");
                            if (i == 0) {
                                Expired = arr[i];
                                Log.d("setting expired "+a.length,arr[i]);
                                Expiredi = a.length;
                            }
                            if (i == 1) {
                                Expiring_Today = arr[i];
                                Expiring_Todayi = a.length;
                                Log.d("setting Expiring_Today "+a.length,arr[i]);
                            }
                            if (i == 2) {
                                Expiring_Soon = arr[i];
                                Expiring_Sooni = a.length;
                                Log.d("setting Expiring_Soon "+a.length,arr[i]);
                            }
                            if (i == 3) {
                                Good_Product = arr[i];
                                Good_Producti = a.length;
                                Log.d("setting Good_Product "+a.length,arr[i]);
                            }
                            if (i == 4) {
                                Great_Product = arr[i];
                                Great_Producti = a.length;
                                Log.d("setting Great_Product "+a.length,arr[i]);
                            }
                            for (int j = 0; j < a.length; j++) {

                                String b[] = a[j].split("#");
                                for (int k = 0; k < b.length; k++) {
                                    Log.d(k + " **", b[k]);
                                }

                            }
                        }
                    }

                    Log.d("Expired procudts ",Expired);

                    Log.d("Expiring Today ",Expiring_Today);

                    Log.d("Expiring Soon ",Expiring_Soon);

                    Log.d("Good Product ",Good_Product);

                    Log.d("Great Product ",Great_Product);
                    Log.d("Expired procudts counts ",Expiredi+"");

                    Log.d("Expiring Today counts ",Expiring_Todayi+"");

                    Log.d("Expiring Soon counts ",Expiring_Sooni+"");

                    Log.d("Good Product counts ",Good_Producti+"");

                    Log.d("Great Product counts ",Great_Producti+"");
                    List_CategoryDetails.category=new ArrayList();
                    List_CategoryDetails.data=new ArrayList();
                    List_CategoryDetails.quantity=new ArrayList();
                    List_CategoryDetails.category.add("Expired");
                    List_CategoryDetails.category.add("Expiring Today");
                    List_CategoryDetails.category.add("Expiring Soon");
                    List_CategoryDetails.category.add("Good Product");
                    List_CategoryDetails.category.add("Great Product");
                    List_CategoryDetails.data.add(Expired);
                    List_CategoryDetails.data.add(Expiring_Today);
                    List_CategoryDetails.data.add(Expiring_Soon);
                    List_CategoryDetails.data.add(Good_Product);
                    List_CategoryDetails.data.add(Great_Product);
                    List_CategoryDetails.quantity.add(Expiredi);
                    List_CategoryDetails.quantity.add(Expiring_Todayi);
                    List_CategoryDetails.quantity.add(Expiring_Sooni);
                    List_CategoryDetails.quantity.add(Good_Producti);
                    List_CategoryDetails.quantity.add(Great_Producti);

                    List_CategoryDetails.arrayList=new ArrayList();




                     startActivity(i4);
                }






        }
    }
    private class check_scanned_item extends AsyncTask<Void,String,String>
    {
        @Override
        public String doInBackground(Void... Void)
        {
            JSONObject jsn = new JSONObject();
            String response = "";
            try {
                URL url = new URL(Global.url +"check_scanned_item");
                jsn.put("hid",hid);


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

            if(s.endsWith("null"))

            {

                s=s.substring(0,s.length()-4);
            }
            if( !s.equals(""))
            {
                b1.setVisibility(View.VISIBLE);
                et.setVisibility(View.VISIBLE);
Log.d("info",s);
String a[]=s.split("##");
                item_id=a[0];
tv.setText("Item Name "+a[1]+"\n"+a[2]+"\nBrand "+a[3]+"\nUnit Price "+a[4]+"\nCategory "+a[5]);



            }
            if(flag==false){flag=true;}
            else if(flag==true){flag =false;}



        }
    }
    private class check_expired_item extends AsyncTask<Void,String,String>
    {
        @Override
        public String doInBackground(Void... Void)
        {
            JSONObject jsn = new JSONObject();
            String response = "";
            try {
                URL url = new URL(Global.url +"check_expired_item");
                jsn.put("hid",hid);


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

            if(s.endsWith("null"))

            {

                s=s.substring(0,s.length()-4);
            }
            if( !s.equals("0"))//No Products
            {

               // b1.setVisibility(View.VISIBLE);
              //  et.setVisibility(View.VISIBLE);
                Log.d("info",s);
             //   String a[]=s.split("#");
              //  item_id=a[0];
               // tv.setText("Item Name "+a[1]+"\n"+a[2]+"\nBrand "+a[3]+"\nUnit Price "+a[4]+"\nCategory "+a[5]);
                    if(ncount<3) {
                        Toast.makeText(getApplicationContext(), "Alert " + s + " items going to expire soon", Toast.LENGTH_SHORT).show();
                        if (Build.VERSION.SDK_INT >= 26) {
                            vibrator.vibrate(VibrationEffect.createOneShot(4000, VibrationEffect.DEFAULT_AMPLITUDE));
                        } else {
                            vibrator.vibrate(4000);
                        }
                        mediaPlayer = MediaPlayer.create(user_home.this, R.raw.beep);
                        mediaPlayer.start();
                        NotificationHelper.createNotificationChannel(getApplicationContext());

                        // Show notification
                        NotificationHelper.showNotification(getApplicationContext(), "Expiring Soon Notification", "Alert " + s + " items going to expire soon");

                    }

            }
            ncount++;
            if(flag==false){flag=true;}
            else if(flag==true){flag =false;}


        }
    }

    private class add_scanned_product extends AsyncTask<Void,String,String>
    {
        @Override
        public String doInBackground(Void... Void)
        {
            JSONObject jsn = new JSONObject();
            String response = "";
            try {
                URL url = new URL(Global.url +"add_scanned_product");
                jsn.put("hid",hid);
                jsn.put("item_id",item_id);
                jsn.put("qty",qty);


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

            if(s.endsWith("null"))

            {

                s=s.substring(0,s.length()-4);
            }
            if( !s.equals(""))
            {
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();

                tv.setText("");
                b1.setVisibility(View.GONE);
                et.setVisibility(View.GONE);
            }



        }
    }

}