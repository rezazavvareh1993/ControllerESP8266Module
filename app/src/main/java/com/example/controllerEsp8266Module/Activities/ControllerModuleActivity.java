package com.example.controllerEsp8266Module.Activities;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.controllerEsp8266Module.Constants;
import com.example.controllerEsp8266Module.DataBase.Tables.IPAddressesDevices;
import com.example.controllerEsp8266Module.DataBase.ViewModels.IPAddressViewModel;
import com.example.controllerEsp8266Module.Models.DevicesState;
import com.example.controllerEsp8266Module.R;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ControllerModuleActivity extends AppCompatActivity {
    private static boolean checkResponseOfServer;
    private Context mContext;

    private Button btnSwitch1On;
    private Button btnSwitch1Off;
    private Button btnSwitch3On;
    private Button btnSwitch3Off;
    private Button btnRemoteSwitch;
    private Button btnLocalSwitch;
    private Button btnSave;
    private Button btnUpdate;
    private TextView txtShowIpAddress;
    private TextView txtNameDevices;
    private EditText edtIpAddress;
    private ImageView imgLogo;
    private final String status = "status";
    private String ipAddress = "";
    private Vibrator vibe;
    private String url;
    private String stateDevice1;
    private String stateDevice2;
    private String stateDevice3;
    private String stateDevice4;

    private IPAddressViewModel ipAddressViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller_module);

        mContext = this;

        btnSwitch1On = findViewById(R.id.btnSwitch1On);
        btnSwitch1Off = findViewById(R.id.btnSwitch1Off);
        btnSwitch3On = findViewById(R.id.btnSwitch3On);
        btnSwitch3Off = findViewById(R.id.btnSwitch3Off);
        btnSave = findViewById(R.id.btnSave);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnRemoteSwitch = findViewById(R.id.remote1_switch);
        btnLocalSwitch = findViewById(R.id.local1_switch);
        edtIpAddress = findViewById(R.id.edtIpAddress);
        txtNameDevices = findViewById(R.id.txtNameDevices);
        txtShowIpAddress = findViewById(R.id.txt_show_IpAddress);
        imgLogo = findViewById(R.id.imgLogo);

        Intent intent = getIntent();
        String deviceType = intent.getStringExtra(Constants.DEVICE_NAME);
        int imgDevice = intent.getIntExtra(Constants.IMAGE_OF_PARENT, -1);
        if (imgDevice != -1 && android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Drawable drawable = getDrawable(imgDevice);
            if (drawable.getConstantState().equals(ControllerModuleActivity.this.getResources().getDrawable(R.drawable.curtens).getConstantState())) {
                btnSwitch1On.setText("Open");
                btnSwitch3On.setText("Open");
                btnSwitch1Off.setText("Close");
                btnSwitch3Off.setText("Close");
            }
        }

        final int parentId = intent.getIntExtra(Constants.PARENT_ID, -1);
        final String parentActivityName = intent.getStringExtra(Constants.ACTIVITY_NAME);

        ipAddressViewModel = ViewModelProviders.of(ControllerModuleActivity.this).get(IPAddressViewModel.class);


        if (parentActivityName.equals("oneDevice")) {
            edtIpAddress.setVisibility(View.GONE);
            ipAddress = Constants.IP_ADDRESS_FOR_ONE_DEVICE;
            btnSave.setVisibility(View.GONE);
            txtNameDevices.setVisibility(View.GONE);
            btnUpdate.setVisibility(View.GONE);
            imgLogo.setVisibility(View.VISIBLE);

        } else {
            txtNameDevices.setText(deviceType);
            imgLogo.setVisibility(View.GONE);
        }

        IPAddressesDevices ipAddressesDevices = null;
        try {
            ipAddressesDevices = ipAddressViewModel.getIpAddresses(parentId, parentActivityName);

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (ipAddressesDevices != null) {
            ipAddress = ipAddressesDevices.getIpAddress();
            txtShowIpAddress.setText(ipAddress);
            checkStateDeviceFromServer(ipAddress);

        } else {
            if (parentActivityName.equals("oneDevice")) {
                requestByOkHttp("192.168.4.1", "", "");
                IPAddressesDevices ipAddressForOneDevice = new IPAddressesDevices("192.168.4.1", parentId, parentActivityName, false, false, true);
                ipAddressViewModel.insert(ipAddressForOneDevice);
            } else {
                disableClickListener();
            }
        }




        ////////////Insert
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setVibrationOnButton();
                ipAddress = edtIpAddress.getText().toString();
                if (!ipAddress.equals("")) {
                    IPAddressesDevices ipAddressesDevices = new IPAddressesDevices(ipAddress, parentId, parentActivityName, false, false, true);
                    ipAddressViewModel.insert(ipAddressesDevices);
                    txtShowIpAddress.setText(ipAddress);
                    Toast.makeText(mContext, mContext.getResources().getString(R.string.saved_your_ip_address), Toast.LENGTH_SHORT).show();
                    requestByOkHttp(ipAddress, "", "");
                    edtIpAddress.setText("");
                    disableClickListener();
//                    checkResponseOfServer = false;//
                } else {
                    Toast.makeText(mContext, mContext.getResources().getString(R.string.enter_your_ip_address_error), Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setVibrationOnButton();
                ipAddress = edtIpAddress.getText().toString();
                if (!ipAddress.equals("")) {
                    IPAddressViewModel update = ViewModelProviders.of(ControllerModuleActivity.this).get(IPAddressViewModel.class);
                    update.updateIpAddressPerDevice(ipAddress, parentId, parentActivityName);
                    Toast.makeText(mContext, mContext.getResources().getString(R.string.updated_your_ip_address), Toast.LENGTH_SHORT).show();
                    requestByOkHttp(ipAddress, "", "");
                    txtShowIpAddress.setText(ipAddress);
                    edtIpAddress.setText("");
//                    checkResponseOfServer = false;//
                } else {
                    Toast.makeText(mContext, mContext.getResources().getString(R.string.enter_your_new_ip_address), Toast.LENGTH_SHORT).show();

                }
            }
        });

        btnSwitch1On.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setVibrationOnButton();
                String parameterValue = "1=1";
                requestByOkHttp(ipAddress, status, parameterValue);
                if (checkResponseOfServer == true) {
                    ipAddressViewModel.updateSwitch1State(true, parentId, parentActivityName);
                    btnSwitch1On.setBackgroundResource(R.drawable.btn_on);
                    btnSwitch1Off.setBackgroundResource(R.drawable.btn_shape_off_disable);
                }
            }
        });

        btnSwitch1Off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setVibrationOnButton();
                String parameterValue = "1=0";
                requestByOkHttp(ipAddress, status, parameterValue);
                if (checkResponseOfServer == true) {
                    ipAddressViewModel.updateSwitch1State(false, parentId, parentActivityName);
                    btnSwitch1Off.setBackgroundResource(R.drawable.btn_off);
                    btnSwitch1On.setBackgroundResource(R.drawable.btn_shape_on_disable);
                }
            }
        });

        btnSwitch3On.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setVibrationOnButton();
                String parameterValue = "3=1";
                requestByOkHttp(ipAddress, status, parameterValue);
                if (checkResponseOfServer == true) {
                    ipAddressViewModel.updateSwitch3State(true, parentId, parentActivityName);
                    btnSwitch3On.setBackgroundResource(R.drawable.btn_on);
                    btnSwitch3Off.setBackgroundResource(R.drawable.btn_shape_off_disable);
                }
            }
        });

        btnSwitch3Off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setVibrationOnButton();
                String parameterValue = "3=0";
                requestByOkHttp(ipAddress, status, parameterValue);
                if (checkResponseOfServer == true) {
                    ipAddressViewModel.updateSwitch3State(false, parentId, parentActivityName);
                    btnSwitch3Off.setBackgroundResource(R.drawable.btn_off);
                    btnSwitch3On.setBackgroundResource(R.drawable.btn_shape_on_disable);

                }
            }
        });

        btnRemoteSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setVibrationOnButton();
                String parameterValue = "2=1";
                requestByOkHttp(ipAddress, status, parameterValue);
                if (checkResponseOfServer == true) {
                    ipAddressViewModel.updateSwitchRemoteState(true, parentId, parentActivityName);
                    btnRemoteSwitch.setBackgroundResource(R.drawable.btn_shape);
                    btnRemoteSwitch.setTextColor(getResources().getColor(R.color.colorWhite));
                    btnLocalSwitch.setBackgroundResource(R.drawable.btn_shape_local_and_remote_disable);
                    btnLocalSwitch.setTextColor(getResources().getColor(R.color.colorAccent));
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            requestByOkHttp(ipAddress, "", "");
                        }
                    }, 1000);

                }

            }
        });

        btnLocalSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setVibrationOnButton();
                String parameterValue = "2=0";
                requestByOkHttp(ipAddress, status, parameterValue);
                if (checkResponseOfServer == true) {
                    ipAddressViewModel.updateSwitchRemoteState(false, parentId, parentActivityName);
                    btnLocalSwitch.setBackgroundResource(R.drawable.btn_shape);
                    btnLocalSwitch.setTextColor(getResources().getColor(R.color.colorWhite));
                    btnRemoteSwitch.setBackgroundResource(R.drawable.btn_shape_local_and_remote_disable);
                    btnRemoteSwitch.setTextColor(getResources().getColor(R.color.colorAccent));
                    DisableSwitchesInLocalMode();
                }
            }
        });


    }

    private void checkStateDeviceFromServer(String ipAddress) {
        requestByOkHttp(ipAddress, status, "");


    }

    private void disableClickListener() {
        String checkIpAddress = txtShowIpAddress.getText().toString();
        if(checkIpAddress.equals("IP Address")){
            btnSwitch1On.setEnabled(false);
            btnSwitch1Off.setEnabled(false);
            btnSwitch3Off.setEnabled(false);
            btnSwitch3On.setEnabled(false);
            btnLocalSwitch.setEnabled(false);
            btnRemoteSwitch.setEnabled(false);
            btnUpdate.setEnabled(false);
            btnSave.setEnabled(true);
        }else {
            btnSwitch1On.setEnabled(true);
            btnSwitch1Off.setEnabled(true);
            btnSwitch3Off.setEnabled(true);
            btnSwitch3On.setEnabled(true);
            btnLocalSwitch.setEnabled(true);
            btnRemoteSwitch.setEnabled(true);
            btnUpdate.setEnabled(true);
            btnUpdate.setBackgroundResource(R.drawable.btn_shape);
            btnUpdate.setTextColor(getResources().getColor(R.color.colorWhite));
            btnSave.setEnabled(false);
            btnSave.setBackgroundResource(R.drawable.btn_shape_local_and_remote_disable);
            btnSave.setTextColor(getResources().getColor(R.color.colorAccent));
        }
    }

    private void setVibrationOnButton(){
        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibe.vibrate(100);

    }

    private void checkButtonsState(IPAddressesDevices ipAddressesDevices) {
        btnUpdate.setBackgroundResource(R.drawable.btn_shape);
        btnUpdate.setTextColor(getResources().getColor(R.color.colorWhite));

        btnSave.setBackgroundResource(R.drawable.btn_shape_local_and_remote_disable);
        btnSave.setTextColor(getResources().getColor(R.color.colorAccent));
        btnSave.setEnabled(false);

        if (ipAddressesDevices.isSwitch1On() == true) {
            btnSwitch1On.setBackgroundResource(R.drawable.btn_on);
            btnSwitch1Off.setBackgroundResource(R.drawable.btn_shape_off_disable);
        } else {
            btnSwitch1On.setBackgroundResource(R.drawable.btn_shape_on_disable);
            btnSwitch1Off.setBackgroundResource(R.drawable.btn_off);
        }
        if (ipAddressesDevices.isSwitch3On() == true) {
            btnSwitch3On.setBackgroundResource(R.drawable.btn_on);
            btnSwitch3Off.setBackgroundResource(R.drawable.btn_shape_off_disable);
        } else {
            btnSwitch3On.setBackgroundResource(R.drawable.btn_shape_on_disable);
            btnSwitch3Off.setBackgroundResource(R.drawable.btn_off);
        }
        if (ipAddressesDevices.isRemoteOn() == true) {
            btnRemoteSwitch.setBackgroundResource(R.drawable.btn_shape);
            btnRemoteSwitch.setTextColor(getResources().getColor(R.color.colorWhite));
            btnLocalSwitch.setBackgroundResource(R.drawable.btn_shape_local_and_remote_disable);
        } else {
            btnLocalSwitch.setBackgroundResource(R.drawable.btn_shape);
            btnLocalSwitch.setTextColor(getResources().getColor(R.color.colorWhite));
            btnRemoteSwitch.setBackgroundResource(R.drawable.btn_shape_local_and_remote_disable);
            btnRemoteSwitch.setTextColor(getResources().getColor(R.color.colorAccent));
        }
    }

    /////////////request and response//////////

    private void requestByOkHttp(final String ipAddress, String status, String parameterValue) {

        OkHttpClient client = new OkHttpClient();

        if(status.equals(""))
            url = "http://" + ipAddress;
        else
            url = "http://" + ipAddress + "/" + status + parameterValue;

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {

                ControllerModuleActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ControllerModuleActivity.this, "در هنگام اتصال خطایی رخ داده است.", Toast.LENGTH_SHORT).show();
                        checkResponseOfServer = false;

                    }
                });
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String myResponse = response.body().string();
                    if(myResponse.length()>20) {
                        String[] splitResponse = myResponse.split("<br/>", 6);

                        String [] splitDevice1Response = splitResponse[1].split(": ", 2);
                        stateDevice1 = splitDevice1Response[1];

                        String [] splitDevice2Response = splitResponse[2].split(": ", 2);
                        stateDevice2 =  splitDevice2Response[1];

                        String [] splitDevice3Response = splitResponse[3].split(": ", 2);
                        stateDevice3 = splitDevice3Response[1];

                        String [] splitDevice4Response = splitResponse[4].split(": ", 2);
                        stateDevice4 = splitDevice4Response[1];

                        ControllerModuleActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                DevicesState devicesStateModel = new DevicesState(stateDevice1, stateDevice2, stateDevice3, stateDevice4);
                                setStateDevicesFromServer(devicesStateModel);
                            }
                        });
                    }


                    ControllerModuleActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            checkResponseOfServer = true;
                            Toast.makeText(ControllerModuleActivity.this, R.string.connected , Toast.LENGTH_SHORT).show();

                        }
                    });
                }

            }
        });

    }

    private void setStateDevicesFromServer(DevicesState devicesStateModel) {

        if(devicesStateModel.getRemoteLocal().equals("ON")){
            btnRemoteSwitch.setBackgroundResource(R.drawable.btn_shape);
            btnRemoteSwitch.setTextColor(getResources().getColor(R.color.colorWhite));
            btnLocalSwitch.setBackgroundResource(R.drawable.btn_shape_local_and_remote_disable);
            EnableSwitchesInLocalMode();
            if(devicesStateModel.getDevice1().equals("ON")){
                btnSwitch1On.setBackgroundResource(R.drawable.btn_on);
                btnSwitch1Off.setBackgroundResource(R.drawable.btn_shape_off_disable);
            } else {
                btnSwitch1On.setBackgroundResource(R.drawable.btn_shape_on_disable);
                btnSwitch1Off.setBackgroundResource(R.drawable.btn_off);
            }

            if(devicesStateModel.getDevice3().equals("ON")){
                btnSwitch3On.setBackgroundResource(R.drawable.btn_on);
                btnSwitch3Off.setBackgroundResource(R.drawable.btn_shape_off_disable);
            } else {
                btnSwitch3On.setBackgroundResource(R.drawable.btn_shape_on_disable);
                btnSwitch3Off.setBackgroundResource(R.drawable.btn_off);
            }
        } else {
            btnLocalSwitch.setBackgroundResource(R.drawable.btn_shape);
            btnLocalSwitch.setTextColor(getResources().getColor(R.color.colorWhite));
            btnRemoteSwitch.setBackgroundResource(R.drawable.btn_shape_local_and_remote_disable);
            DisableSwitchesInLocalMode();
        }


        }


    private void DisableSwitchesInLocalMode() {
        btnSwitch1On.setEnabled(false);
        btnSwitch1Off.setEnabled(false);
        btnSwitch3Off.setEnabled(false);
        btnSwitch3On.setEnabled(false);
    }
    private void EnableSwitchesInLocalMode(){
        btnSwitch1On.setEnabled(true);
        btnSwitch1Off.setEnabled(true);
        btnSwitch3Off.setEnabled(true);
        btnSwitch3On.setEnabled(true);
    }

    /////////////////////////////////////////////menu/////////////////////////////////////////
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about_us:
                Intent intent = new Intent(mContext, AboutAsActivity.class);
                this.startActivity(intent);
                break;
        }
        return true;
    }
}
