package com.example.controllerEsp8266Module.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.controllerEsp8266Module.R;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ConnectToModemActivity extends AppCompatActivity {
    private EditText edtSSIDModem;
    private EditText edtPasswordModem;
    private Button btnConnectToModem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_to_modem);

        findViews();

        btnConnectToModem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ssid = edtSSIDModem.getText().toString();
                String password = edtPasswordModem.getText().toString();
                requestByOkHttp(ssid, password);
            }
        });
    }

    private void findViews() {
        edtSSIDModem = findViewById(R.id.edt_ssid_modem);
        edtPasswordModem = findViewById(R.id.edt_password_modem);
        btnConnectToModem = findViewById(R.id.btn_connect_in_connect_to_modem_activity);
    }

    private void requestByOkHttp(String ssid, String password) {

        OkHttpClient client = new OkHttpClient();

        String url = "http://192.168.4.1/wifi?Mode=STA&ssid=" + ssid + "&pass=" + password;

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {

                ConnectToModemActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ConnectToModemActivity.this, "در هنگام اتصال خطایی رخ داده است.", Toast.LENGTH_SHORT).show();
                    }
                });
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {
                    ConnectToModemActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ConnectToModemActivity.this, R.string.connected, Toast.LENGTH_SHORT).show();

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(ConnectToModemActivity.this, R.string.suggest_connect_to_modem, Toast.LENGTH_SHORT).show();
                                }
                            }, 1000);
                        }
                    });

                }
            }
        });

    }
}
