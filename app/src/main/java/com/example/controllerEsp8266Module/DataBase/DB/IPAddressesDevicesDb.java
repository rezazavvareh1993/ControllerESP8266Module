package com.example.controllerEsp8266Module.DataBase.DB;

import android.content.Context;
import android.os.AsyncTask;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.controllerEsp8266Module.DataBase.Daoes.IPAddressDevicesDao;
import com.example.controllerEsp8266Module.DataBase.Tables.IPAddressesDevices;

@Database(entities = {IPAddressesDevices.class}, version = 3, exportSchema = false)
public abstract class IPAddressesDevicesDb extends RoomDatabase {

    private static IPAddressesDevicesDb instance;

    public abstract IPAddressDevicesDao ipAddressDevicesDao();

    public static synchronized IPAddressesDevicesDb getInstance(Context context) {
        if (instance == null) {
            instance = androidx.room.Room.databaseBuilder(context.getApplicationContext(),
                    IPAddressesDevicesDb.class, "ip_addresses_devices")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(SupportSQLiteDatabase db) {
            super.onCreate(db);
            new IPAddressesDevicesDb.PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private IPAddressDevicesDao ipAddressDevicesDao;

        private PopulateDbAsyncTask(IPAddressesDevicesDb db) {
            ipAddressDevicesDao = db.ipAddressDevicesDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
//            roomDao.insert(new Room(R.drawable.bedroom, R.drawable.ic_edit1_black_24dp,"اتاق 1"));
//            roomDao.insert(new Room(R.drawable.bedroom, R.drawable.ic_edit1_black_24dp,"اتاق 2"));
            return null;
        }
    }

}



