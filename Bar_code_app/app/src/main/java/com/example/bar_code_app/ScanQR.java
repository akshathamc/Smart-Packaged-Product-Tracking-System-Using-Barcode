package com.example.bar_code_app;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

public class ScanQR extends AppCompatActivity {
    private CodeScanner mCodeScanner;

    public static String qr = "", usr = "";
    public static Intent i1, i2, i3, i4;
    Intent bk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qr);
        bk = new Intent(this, user_home.class);

        if (ContextCompat.checkSelfPermission(ScanQR.this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(ScanQR.this, new String[]{Manifest.permission.CAMERA}, 123);
        } else {
            startScanning();
        }
    }

    private void startScanning() {
        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(this, scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ScanQR.this, result.getText(), Toast.LENGTH_SHORT).show();
                        qr = result.getText();
                        if (!qr.equalsIgnoreCase("")) {
                            try {
                                get_qr_details();
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "You have scanned wrong Product", Toast.LENGTH_SHORT).show();


                            startActivity(bk);

                        }
                        //startActivity(bk);
                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 123) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Camera permission granted", Toast.LENGTH_LONG).show();
                startScanning();
            } else {
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mCodeScanner != null) {
            mCodeScanner.startPreview();
        }
    }

    @Override
    protected void onPause() {
        if (mCodeScanner != null) {
            mCodeScanner.releaseResources();
        }
        super.onPause();
    }

    public void get_qr_details() throws IOException, JSONException {
        URL url = new URL(Global.url + "check_barcode");
        JSONObject jsn = new JSONObject();

        jsn.put("barcode", qr);
        jsn.put("hid", user_home.hid+"");


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().build();
        StrictMode.setThreadPolicy(policy);
        String s = null;
        s = HttpClientConnection.getData(url, jsn);


        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();

        if (s.endsWith("null")) {

            s = s.substring(0, s.length() - 4);
        }
        if (!s.equals("")) {
            // b1.setVisibility(View.VISIBLE);
            // et.setVisibility(View.VISIBLE);
            Log.d("info", s);
            String a[] = s.split("#");

            user_home.tv.setText("Item Name " + a[1] + "\n" + a[2] + "\nBrand " + a[3] + "\nUnit Price " + a[4] + "\nCategory " + a[5]);
            user_home.msg = "Item Name " + a[1] + "\n" + a[2] + "\nBrand " + a[3] + "\nUnit Price " + a[4] + "\nCategory " + a[5];
            user_home.item_id = a[0];
            startActivity(bk);
        }
            else{

                Toast.makeText(getApplicationContext(),"No Item found",Toast.LENGTH_SHORT).show();
            }
        }
    }
