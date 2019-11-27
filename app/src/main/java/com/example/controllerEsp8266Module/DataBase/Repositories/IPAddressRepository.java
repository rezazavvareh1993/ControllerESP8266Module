package com.example.controllerEsp8266Module.DataBase.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.controllerEsp8266Module.DataBase.DB.IPAddressesDevicesDb;
import com.example.controllerEsp8266Module.DataBase.Daoes.IPAddressDevicesDao;
import com.example.controllerEsp8266Module.DataBase.Tables.IPAddressesDevices;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class IPAddressRepository {


    private IPAddressDevicesDao ipAddressDevicesDao;
    private LiveData<List<IPAddressesDevices>> allIpAddresses;


    public IPAddressRepository(Application application) {
        IPAddressesDevicesDb ipAddressesDevicesDb = IPAddressesDevicesDb.getInstance(application);
        ipAddressDevicesDao = ipAddressesDevicesDb.ipAddressDevicesDao();
        allIpAddresses = ipAddressDevicesDao.getAllItems();

    }


    public void insert(IPAddressesDevices ipAddressesDevices) {
        new InsertIPAddressAsyncTask(ipAddressDevicesDao).execute(ipAddressesDevices);

    }

    public void update(IPAddressesDevices ipAddressesDevices) {
        new UpdateIPAddressAsyncTask(ipAddressDevicesDao).execute(ipAddressesDevices);

    }

    public void delete(IPAddressesDevices ipAddressesDevices) {
        new DeleteIPAddressAsyncTask(ipAddressDevicesDao).execute(ipAddressesDevices);

    }

    public LiveData<List<IPAddressesDevices>> getAllIpAddresses() {
        return allIpAddresses;
    }


    public IPAddressesDevices findIPAddressForDevice1(final int parentId, final String parentName) throws ExecutionException, InterruptedException {
        IPAddressesDevices ipAddress = new IPAddressesDevices(null, parentId, parentName, false, false, true);
        return new getIpAddressPerIdParent(ipAddressDevicesDao).execute(ipAddress).get();
    }


    /////////updateIpAddress
    public void updateIpAddressPerDevice(final String newIpAddress, final int parentId, final String parentName) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                ipAddressDevicesDao.UpdateIpAddress(newIpAddress, parentId, parentName);
            }
        });
    }

    ///////updateStateSwitch1
    public void updateSwitch1State(final boolean newS1State, final int parentId, final String parentName) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                ipAddressDevicesDao.UpdateSwitch1State(newS1State, parentId, parentName);
            }
        });
    }

    ///////updateStateSwitch3
    public void updateSwitch3State(final boolean newS3State, final int parentId, final String parentName) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                ipAddressDevicesDao.UpdateSwitch3State(newS3State, parentId, parentName);
            }
        });
    }

    ///////updateStateSwitchRemote
    public void updateSwitchRemoteState(final boolean newSRemoteState, final int parentId, final String parentName) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                ipAddressDevicesDao.UpdateSwitchRemoteState(newSRemoteState, parentId, parentName);
            }
        });
    }

    ///////////////getIp
    public class getIpAddressPerIdParent extends AsyncTask<IPAddressesDevices, Void, IPAddressesDevices> {
        private IPAddressDevicesDao ipAddressDevicesDao;

        public getIpAddressPerIdParent(IPAddressDevicesDao ipAddressDevicesDao) {
            this.ipAddressDevicesDao = ipAddressDevicesDao;
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub

        }

        @Override
        protected IPAddressesDevices doInBackground(IPAddressesDevices... ipAddressesDevices) {

            return ipAddressDevicesDao.findIPAddressForDevice(ipAddressesDevices[0].getIdParent(), ipAddressesDevices[0].getParentNameActivity());
        }
    }


    ////////INSERT
    private static class InsertIPAddressAsyncTask extends AsyncTask<IPAddressesDevices, Void, Void> {
        private IPAddressDevicesDao ipAddressDevicesDao;

        private InsertIPAddressAsyncTask(IPAddressDevicesDao ipAddressDevicesDao) {
            this.ipAddressDevicesDao = ipAddressDevicesDao;
        }

        @Override
        protected Void doInBackground(IPAddressesDevices... ipAddressesDevices) {
            ipAddressDevicesDao.insert(ipAddressesDevices[0]);
            return null;
        }
    }

    /////////////UPDATE
    private static class UpdateIPAddressAsyncTask extends AsyncTask<IPAddressesDevices, Void, Void> {
        private IPAddressDevicesDao ipAddressDevicesDao;

        private UpdateIPAddressAsyncTask(IPAddressDevicesDao ipAddressDevicesDao) {
            this.ipAddressDevicesDao = ipAddressDevicesDao;
        }

        @Override
        protected Void doInBackground(IPAddressesDevices... ipAddressesDevices) {
            ipAddressDevicesDao.update(ipAddressesDevices[0]);
            return null;
        }
    }

    /////////////////////DELETE
    private static class DeleteIPAddressAsyncTask extends AsyncTask<IPAddressesDevices, Void, Void> {
        private IPAddressDevicesDao ipAddressDevicesDao;

        private DeleteIPAddressAsyncTask(IPAddressDevicesDao ipAddressDevicesDao) {
            this.ipAddressDevicesDao = ipAddressDevicesDao;
        }

        @Override
        protected Void doInBackground(IPAddressesDevices... ipAddressesDevices) {
            ipAddressDevicesDao.delete(ipAddressesDevices[0]);
            return null;
        }
    }

}
