package com.example.controllerEsp8266Module.Models;

import com.example.controllerEsp8266Module.R;

import java.util.ArrayList;

public class DevicesModel {
    int imgDevice;
    int imgDeviceDelete;
    int imgDeviceAdd;
    String edtNameDevice;

    public int getImgDevice() {
        return imgDevice;
    }

    public void setImgDevice(int imgDevice) {
        this.imgDevice = imgDevice;
    }

    public int getImgDeviceDelete() {
        return imgDeviceDelete;
    }

    public void setImgDeviceDelete(int imgDeviceDelete) {
        this.imgDeviceDelete = imgDeviceDelete;
    }

    public int getImgDeviceAdd() {
        return imgDeviceAdd;
    }

    public void setImgDeviceAdd(int imgDeviceAdd) {
        this.imgDeviceAdd = imgDeviceAdd;
    }

    public String getEdtNameDevice() {
        return edtNameDevice;
    }

    public void setEdtNameDevice(String edtNameDevice) {
        this.edtNameDevice = edtNameDevice;
    }

    public static ArrayList<DevicesModel> getData (){

        ArrayList<DevicesModel> devicesModelArrayList = new ArrayList<>();

        int[] imagesRooms = getImages();
        String[] nameRooms = getNameDevices();

        for(int i = 0; i < imagesRooms.length ; i++){
            DevicesModel devicesModel = new DevicesModel();
            devicesModel.setImgDevice(imagesRooms[i]);
            devicesModel.setEdtNameDevice(nameRooms[i]);

            devicesModelArrayList.add(devicesModel);

        }

        return devicesModelArrayList;
    }

    private static String[] getNameDevices() {
        String[] nameDevices = {"کلید","پریز","پرده"};

        return nameDevices;
    }

    public static int[] getImages(){
        int[] images = {R.drawable.switch1, R.drawable.socket, R.drawable.curtens};

        return images;
    }
}
