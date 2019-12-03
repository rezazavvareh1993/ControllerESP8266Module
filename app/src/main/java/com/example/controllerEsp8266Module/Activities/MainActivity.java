package com.example.controllerEsp8266Module.Activities;

import android.content.Intent;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


import androidx.appcompat.app.AppCompatActivity;

import com.example.controllerEsp8266Module.Constants;
import com.example.controllerEsp8266Module.R;


public class MainActivity extends AppCompatActivity {
    private Button btnOneDevice;
    private Button btnSmartHouse;
    private Button btnConnectToModem;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        moveToActivity();
    }

    private void findViews() {
        btnOneDevice = findViewById(R.id.btn_a_device);
        btnSmartHouse = findViewById(R.id.btn_smart_house);
        btnConnectToModem = findViewById(R.id.btn_connect_modem_in_main_activity);
    }

    private void moveToActivity(){
        btnOneDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, ControllerModuleActivity.class);
                intent.putExtra(Constants.ADD_DEVICE, "oneDevice");
                intent.putExtra(Constants.PARENT_ID, Constants.ID_OF_ONE_DEVICE);
                intent.putExtra(Constants.ACTIVITY_NAME, "oneDevice");
                MainActivity.this.startActivity(intent);
            }
        });

        btnSmartHouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getBaseContext(), SmartHouseActivity.class);
                startActivity(intent);

            }
        });

        btnConnectToModem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getBaseContext(), ConnectToModemActivity.class);
                startActivity(intent);
            }
        });
    }

    /////////////////////menu///////////////////////////////////////////////////////
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        menu.add(0, 1, 0, getResources().getString(R.string.about_us));
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about_us:
                Intent intent = new Intent(this, AboutAsActivity.class);
                this.startActivity(intent);
                break;
        }
        return true;
    }
}
