package com.example.bar_code_app;

//import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Login extends AppCompatActivity {

    EditText etUser,etPass;
    Button btnLog,btnSignup,btnLog1;

    Intent next,nh,ndl,ndl1;
    String user="",pass="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUser=(EditText)findViewById(R.id.etUser);
        etPass=(EditText)findViewById(R.id.etPass);

        btnLog=(Button)findViewById(R.id.btnLogin);
        btnLog1=(Button)findViewById(R.id.btnLogin1);

        btnSignup=(Button)findViewById(R.id.btnSignup);



        next=new Intent(this,SignUp.class);

        nh=new Intent(this,user_home.class);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(next);
                btnSignup.setVisibility(View.INVISIBLE);
            }
        });
        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user= etUser.getText().toString();
                pass=etPass.getText().toString();
                if(user.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Please Enter your HID",Toast.LENGTH_LONG).show();

                }
                else if(pass.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Please Enter your password",Toast.LENGTH_LONG).show();

                }
                else {
                    new login_check().execute();
                }
            }
        });
        btnLog1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user= etUser.getText().toString();
                pass=etPass.getText().toString();
                if(user.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Please Enter your SID",Toast.LENGTH_LONG).show();

                }
                else if(pass.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Please Enter your password",Toast.LENGTH_LONG).show();

                }
                else {
                    new login_check1().execute();
                }
            }
        });
    }

    private class login_check extends AsyncTask<Void,String,String>
    {
        @Override
        public String doInBackground(Void... Void)
        {
            JSONObject jsn = new JSONObject();
            String response = "";
            try {
                URL url = new URL(Global.url +"login_check");
                jsn.put("user", user);

                jsn.put("pass",pass);

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
                Toast.makeText(getApplicationContext(),"Login Failed", Toast.LENGTH_LONG).show();

            }
            else{
                if(s.endsWith("null"))
                {
                    s=s.substring(0,s.length()-4);
                }
                user_home.type="main";
                user_home.hid=user;
                user_home.name=s;
                startActivity(nh);




            }


        }
    }


    private class login_check1 extends AsyncTask<Void,String,String>
    {
        @Override
        public String doInBackground(Void... Void)
        {
            JSONObject jsn = new JSONObject();
            String response = "";
            try {
                URL url = new URL(Global.url +"login_check1");
                jsn.put("user", user);

                jsn.put("pass",pass);

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
                Toast.makeText(getApplicationContext(),"Login Failed", Toast.LENGTH_LONG).show();

            }
            else{
                if(s.endsWith("null"))
                {
                    s=s.substring(0,s.length()-4);
                }
                String a[]=s.split("#");
                user_home.type="sub";
                user_home.hid=a[0];
                user_home.name=a[1];
                startActivity(nh);




            }


        }
    }

}