package com.example.controllerEsp8266Module.DataBase.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.controllerEsp8266Module.DataBase.Repositories.IPAddressRepository;
import com.example.controllerEsp8266Module.DataBase.Tables.IPAddressesDevices;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class IPAddressViewModel extends AndroidViewModel {
    private IPAddressRepository ipAddressRepository;
    private LiveData<List<IPAddressesDevices>> allIPAddresses;


    public IPAddressViewModel(@NonNull Application application) {
        super(application);

        ipAddressRepository = new IPAddressRepository(application);
        allIPAddresses = ipAddressRepository.getAllIpAddresses();

    }

    public void insert(IPAddressesDevices ipAddressesDevices) {
        ipAddressRepository.insert(ipAddressesDevices);
    }

    public void update(IPAddressesDevices ipAddressesDevices) {
        ipAddressRepository.update(ipAddressesDevices);
    }

    public void delete(IPAddressesDevices ipAddressesDevices) {
        ipAddressRepository.delete(ipAddressesDevices);
    }

    public LiveData<List<IPAddressesDevices>> getAllItems() {
        return allIPAddresses;
    }

    public IPAddressesDevices getIpAddresses(int parentId, String parentName) throws ExecutionException, InterruptedException {
        return ipAddressRepository.findIPAddressForDevice1(parentId, parentName);
    }

    public void updateIpAddressPerDevice(String newIpAddress, int parentId, String parentName) {
        ipAddressRepository.updateIpAddressPerDevice(newIpAddress, parentId, parentName);
    }

    public void updateSwitch1State(boolean newS1State, int parentId, String parentName) {
        ipAddressRepository.updateSwitch1State(newS1State, parentId, parentName);
    }

    public void updateSwitch3State(boolean newS3State, int parentId, String parentName) {
        ipAddressRepository.updateSwitch3State(newS3State, parentId, parentName);
    }

    public void updateSwitchRemoteState(boolean newSRemoteState, int parentId, String parentName) {
        ipAddressRepository.updateSwitchRemoteState(newSRemoteState, parentId, parentName);
    }


}

