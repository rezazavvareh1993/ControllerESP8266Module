package com.example.controllerEsp8266Module.DataBase.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.controllerEsp8266Module.DataBase.DB.DevicesDb;
import com.example.controllerEsp8266Module.DataBase.Daoes.DevicesDao;
import com.example.controllerEsp8266Module.DataBase.Tables.Devices;

import java.util.List;

public class DevicesRepository {
    private DevicesDao devicesDao;
    private LiveData<List<Devices>> allRoomsDevices;

    public DevicesRepository(Application application) {
        DevicesDb devicesDb = DevicesDb.getInstance(application);
        devicesDao = devicesDb.roomsDevicesDao();
        allRoomsDevices = devicesDao.getAllItems();
    }

    public void insert(Devices devices) {
        new InsertRoomsDevicesAsyncTask(devicesDao).execute(devices);

    }

    public void update(Devices devices) {
        new UpdateRoomsDevicesAsyncTask(devicesDao).execute(devices);

    }

    public void delete(Devices devices) {
        new DeleteRoomsDevicesAsyncTask(devicesDao).execute(devices);

    }

    public LiveData<List<Devices>> getAllRoomsDevices() {
        return allRoomsDevices;
    }

    public LiveData<List<Devices>> getDevicesPerParent(int id , String parentName){
        return devicesDao.getDevicesPerParent(id, parentName);
    }



    ////AsyncTasks
    ////Insert
    private static class InsertRoomsDevicesAsyncTask extends AsyncTask<Devices, Void, Void> {
        private DevicesDao devicesDao;

        private InsertRoomsDevicesAsyncTask(DevicesDao devicesDao){
            this.devicesDao = devicesDao;
        }

        @Override
        protected Void doInBackground(Devices... devices) {
            devicesDao.insert(devices[0]);
            return null;
        }
    }

    /////Update
    private static class UpdateRoomsDevicesAsyncTask extends AsyncTask<Devices, Void, Void> {
        private DevicesDao devicesDao;

        private UpdateRoomsDevicesAsyncTask(DevicesDao devicesDao){
            this.devicesDao = devicesDao;
        }

        @Override
        protected Void doInBackground(Devices... devices) {
            devicesDao.update(devices[0]);
            return null;
        }
    }

    ///Delete
    private static class DeleteRoomsDevicesAsyncTask extends AsyncTask<Devices, Void, Void> {
        private DevicesDao devicesDao;

        private DeleteRoomsDevicesAsyncTask(DevicesDao devicesDao){
            this.devicesDao = devicesDao;
        }

        @Override
        protected Void doInBackground(Devices... devices) {
            devicesDao.delete(devices[0]);
            return null;
        }
    }



    ///////////////getDevicesPerParent
//    public class getIpAddressPerIdParent extends AsyncTask<IPAddressesDevices, Void,  IPAddressesDevices>{
//        int parentId;
//        String parentActivityName;
//        private IPAddressDevicesDao ipAddressDevicesDao;
//        private IPAddressesDevices ipAddressesDevices;
//
//        public getIpAddressPerIdParent(IPAddressDevicesDao ipAddressDevicesDao){
//            this.ipAddressDevicesDao = ipAddressDevicesDao;
//        }
//        @Override
//        protected void onPreExecute() {
//            // TODO Auto-generated method stub
//
//        }
//        @Override
//        protected IPAddressesDevices doInBackground(IPAddressesDevices... ipAddressesDevices) {
//
//            return ipAddressDevicesDao.findIPAddressForDevice(ipAddressesDevices[0].getIdParent(), ipAddressesDevices[0].getParentNameActivity());
//        }
//    }
}
