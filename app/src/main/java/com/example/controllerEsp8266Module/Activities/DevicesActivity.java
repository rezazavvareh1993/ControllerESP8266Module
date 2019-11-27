package com.example.controllerEsp8266Module.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.controllerEsp8266Module.Adapters.DevicesAdapter;
import com.example.controllerEsp8266Module.Constants;
import com.example.controllerEsp8266Module.DataBase.Tables.Devices;
import com.example.controllerEsp8266Module.DataBase.ViewModels.DevicesViewModel;
import com.example.controllerEsp8266Module.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class DevicesActivity extends AppCompatActivity {
    private DevicesViewModel devicesViewModel;
    private RecyclerView recyclerView;
    private Spinner spType;
    public final String ROOM_DEVICES_ACTIVITY_NAME = "RoomsDevicesActivity";

    private Devices mLastItemDeleted;

    private static String placeName;
    private static int placeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devices);


        spType = findViewById(R.id.sp_type_rooms_devices);
        updateTypes();

        recyclerView = findViewById(R.id.recycler_rooms_device);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final DevicesAdapter adapter = new DevicesAdapter();
        recyclerView.setAdapter(adapter);

        Intent intent = getIntent();
        placeName = intent.getStringExtra(Constants.PLACE_NAME);
        placeId = intent.getIntExtra(Constants.PLACE_ID, -1);


        devicesViewModel = ViewModelProviders.of(this).get(DevicesViewModel.class);

        devicesViewModel.getDevicesPerParent(placeId, placeName).observe(this, new Observer<List<Devices>>() {
            @Override
            public void onChanged(List<Devices> devices) {
                adapter.setItems(devices);
            }
        });
        spType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                if (parent.getSelectedItemPosition() != 0){
                    addItem(selectedItem);
                    spType.setSelection(0);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        adapter.setOnItemClickListener(new DevicesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Devices devices) {
                Intent intent = new Intent(DevicesActivity.this, ControllerModuleActivity.class);
                intent.putExtra(Constants.DEVICE_NAME, devices.getNameItem());
                intent.putExtra(Constants.PARENT_ID, devices.getId());
                intent.putExtra(Constants.ACTIVITY_NAME, ROOM_DEVICES_ACTIVITY_NAME);
                intent.putExtra(Constants.IMAGE_OF_PARENT, devices.getImgItem());
                DevicesActivity.this.startActivity(intent);
            }
        });

        ///////////////////deleteItem
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                mLastItemDeleted = (adapter.getRoomAt(viewHolder.getAdapterPosition()));
                devicesViewModel.delete(mLastItemDeleted);
                showUndoSnackbar();

            }
        }).attachToRecyclerView(recyclerView);
        ///////////Edit
        adapter.setOnItemNameClickListener(new DevicesAdapter.OnItemTitleClickListener() {
            @Override
            public void onItemTitleClick(Devices devices) {
                Intent intent = new Intent(DevicesActivity.this, EditDevicesNameActivity.class);
                intent.putExtra(Constants.EXTRA_TITLE_ROOMS_DEVICES, devices.getNameItem());
                intent.putExtra(Constants.EXTRA_ID_ROOMS_DEVICES, devices.getId());
                intent.putExtra(Constants.IMG_ITEM_ROOMS_DEVICES, devices.getImgItem());
                startActivityForResult(intent, Constants.EDIT_TITLE_REQUEST_ROOMS_Devices);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.EDIT_TITLE_REQUEST_ROOMS_Devices && resultCode == RESULT_OK) {

            int id = data.getIntExtra(Constants.EXTRA_ID_ROOMS_DEVICES, -1);
            if (id == -1) {
                Toast.makeText(this, "Not cant be updated", Toast.LENGTH_SHORT).show();
                return;
            }
            String title = data.getStringExtra(Constants.EXTRA_TITLE_ROOMS_DEVICES);
            int imgItemRoomDevices = data.getIntExtra(Constants.IMG_ITEM_ROOMS_DEVICES, R.drawable.bedroom);
            Devices devices = new Devices(imgItemRoomDevices, R.drawable.ic_edit1_black_24dp, title, placeId, placeName);
            devices.setId(id);
            devicesViewModel.update(devices);
            Toast.makeText(this, "updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Not Saved", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateTypes() {
        ArrayList<String> data = new ArrayList<>();
        data.add("");
        data.add("کلید");
        data.add("پرده");
        data.add("پریز");
        spType.setAdapter(new ArrayAdapter<>(
                DevicesActivity.this, R.layout.simple_spinner_item, data));

    }

    /////////////////////////AddItem
    private void addItem(String nameDevice) {

        if (placeName != null && placeId != -1) {
            Devices devices;
            if (nameDevice.equals("کلید")) {
                Toast.makeText(this, placeId + placeName, Toast.LENGTH_SHORT).show();
                devices = new Devices(R.drawable.switch1, R.drawable.ic_edit1_black_24dp, nameDevice, placeId, placeName);
                devicesViewModel.insert(devices);
            } else if (nameDevice.equals("پرده")) {
                devices = new Devices(R.drawable.curtens, R.drawable.ic_edit1_black_24dp, nameDevice, placeId, placeName);
                devicesViewModel.insert(devices);
            } else if (nameDevice.equals("پریز")) {
                devices = new Devices(R.drawable.socket, R.drawable.ic_edit1_black_24dp, nameDevice, placeId, placeName);
                devicesViewModel.insert(devices);
            }
        }
        if (nameDevice != null)
            Toast.makeText(this, nameDevice + " جدید اضافه شد", Toast.LENGTH_SHORT).show();

    }

    private void showUndoSnackbar() {
        View view = DevicesActivity.this.findViewById(R.id.device_coordinate_layout);
        Snackbar snackbar = Snackbar.make(view, "وسیله مورد نظر پاک شد. ",
                Snackbar.LENGTH_LONG);
        snackbar.setAction("Undo", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DevicesActivity.this.undoDelete();
            }
        })
                .setActionTextColor(getResources().getColor(R.color.colorPrimary));
        snackbar.show();
    }

    private void undoDelete() {
        devicesViewModel.insert(mLastItemDeleted);
    }
}