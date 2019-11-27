package com.example.controllerEsp8266Module.DataBase.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.controllerEsp8266Module.DataBase.Repositories.DevicesRepository;
import com.example.controllerEsp8266Module.DataBase.Tables.Devices;

import java.util.List;

public class DevicesViewModel extends AndroidViewModel {
    DevicesRepository devicesRepository;
    LiveData<List<Devices>> allRoomsDevices;

    public DevicesViewModel(@NonNull Application application) {
        super(application);

        devicesRepository = new DevicesRepository(application);
        allRoomsDevices = devicesRepository.getAllRoomsDevices();

    }

    public void insert(Devices devices) {
        devicesRepository.insert(devices);
    }

    public void update(Devices devices) {
        devicesRepository.update(devices);
    }

    public void delete(Devices devices) {
        devicesRepository.delete(devices);
    }

    public LiveData<List<Devices>> getAllItems() {
        return allRoomsDevices;
    }

    public LiveData<List<Devices>> getDevicesPerParent(int id, String parentName) {
        return devicesRepository.getDevicesPerParent(id, parentName);
    }

}
