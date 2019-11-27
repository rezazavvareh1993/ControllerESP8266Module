package com.example.controllerEsp8266Module.DataBase.DB;

import android.content.Context;
import android.os.AsyncTask;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.controllerEsp8266Module.DataBase.Daoes.DevicesDao;
import com.example.controllerEsp8266Module.DataBase.Tables.Devices;

@Database(entities = Devices.class, version = 4, exportSchema = false)
public abstract class DevicesDb extends RoomDatabase {

    private static DevicesDb instance;

    public abstract DevicesDao roomsDevicesDao();

    public static synchronized DevicesDb getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    DevicesDb.class, "devices_table")
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
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private DevicesDao devicesDao;

        private PopulateDbAsyncTask(DevicesDb db) {
            devicesDao = db.roomsDevicesDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
