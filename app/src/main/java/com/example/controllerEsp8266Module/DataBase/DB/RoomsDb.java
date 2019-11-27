package com.example.controllerEsp8266Module.DataBase.DB;

import android.content.Context;
import android.os.AsyncTask;

import com.example.controllerEsp8266Module.DataBase.Daoes.RoomDao;
import com.example.controllerEsp8266Module.DataBase.Tables.Room;
import com.example.controllerEsp8266Module.R;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Room.class}, version = 2, exportSchema = false)
public abstract class RoomsDb extends RoomDatabase {

    private static RoomsDb instance;

    public abstract RoomDao roomDao();

    public static synchronized RoomsDb getInstance(Context context) {
        if (instance == null) {
            instance = androidx.room.Room.databaseBuilder(context.getApplicationContext(),
                    RoomsDb.class, "room_table")
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
        private RoomDao roomDao;

        private PopulateDbAsyncTask(RoomsDb db) {
            roomDao = db.roomDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            roomDao.insert(new Room(R.drawable.bedroom, R.drawable.ic_edit1_black_24dp, "اتاق 1"));
            roomDao.insert(new Room(R.drawable.bedroom, R.drawable.ic_edit1_black_24dp, "اتاق 2"));
            return null;
        }
    }
}

