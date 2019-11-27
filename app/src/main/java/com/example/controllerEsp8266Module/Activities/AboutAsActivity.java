package com.example.controllerEsp8266Module.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.controllerEsp8266Module.R;

public class AboutAsActivity extends AppCompatActivity {
    private TextView txtWhatsApp;
    private TextView txtWebSite;
    private TextView devNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_as);

        txtWhatsApp = findViewById(R.id.txtWhatsApp);
        txtWebSite = findViewById(R.id.txtWebSite);
        devNumber = findViewById(R.id.dev_num);

        txtWhatsApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectToWhatsApp("+98 9907226210");

            }

        });

        devNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectToWhatsApp("+98 9193617922");
            }
        });



        txtWebSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.shian-sana.ir")));
            }
        });
    }
    private void ConnectToWhatsApp(String contact) {

        String url = "https://api.whatsapp.com/send?phone=" + contact;
        try {
            PackageManager pm = AboutAsActivity.this.getPackageManager();
            pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(AboutAsActivity.this, "Whatsapp app not installed in your phone", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


}
