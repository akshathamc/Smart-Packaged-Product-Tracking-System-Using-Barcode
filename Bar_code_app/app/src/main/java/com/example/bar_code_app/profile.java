package com.example.bar_code_app;

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

public class profile extends AppCompatActivity {
    EditText etn,etm,ete,etp,etcp;
    Button btn,b2;
    String name="",mob="",email="",pass="",cpass="";
    Intent n,b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        etn=(EditText)findViewById(R.id.etName);
        etm=(EditText)findViewById(R.id.etMob);
        ete=(EditText)findViewById(R.id.etEmail);
        etp=(EditText)findViewById(R.id.etPass);
        etcp=(EditText)findViewById(R.id.etCPass);
        btn=(Button)findViewById(R.id.btnSign);
        b2=(Button)findViewById(R.id.b2);
        n=new Intent(this,Login.class);
        b=new Intent(this,Splashscreen.class);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name=etn.getText().toString();
                email=ete.getText().toString();
                mob=etm.getText().toString();
                pass=etp.getText().toString();
                cpass=etcp.getText().toString();


                if(!pass.equals(cpass))
                {

                    Toast.makeText(getApplicationContext(),"Passwords are not matching", Toast.LENGTH_LONG).show();
                }
                else if(mob.length()!=10)
                {
                    Toast.makeText(getApplicationContext(),"Enter Proper Mobile Number", Toast.LENGTH_LONG).show();

                }
                else{

                    new sign_up().execute();

                }







            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(b);
            }
        });

    }



    private class sign_up extends AsyncTask<Void, String, String>
    {
        @Override
        public String doInBackground(Void... Void)
        {
            JSONObject jsn = new JSONObject();
            String response = "";
            try {
                URL url = new URL(Global.url +"add_sub_user");   // servlet
                jsn.put("hid", user_home.hid);
                jsn.put("name", name);
                jsn.put("email", email);

                jsn.put("mobile",mob);
                jsn.put("password",pass);

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
            if(!s.equals("")) {
                Toast.makeText(getApplicationContext(),"Sign Up Successful. SID "+s, Toast.LENGTH_LONG).show();

                startActivity(n);
            }
            else{

                Toast.makeText(getApplicationContext(),"Sign Up Not Successful", Toast.LENGTH_LONG).show();
            }


        }
    }




}
