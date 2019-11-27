package com.example.controllerEsp8266Module.DataBase.Repositories;


import android.app.Application;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.controllerEsp8266Module.DataBase.DB.RoomsDb;
import com.example.controllerEsp8266Module.DataBase.Daoes.RoomDao;
import com.example.controllerEsp8266Module.DataBase.Tables.Room;

import java.util.List;

import androidx.lifecycle.LiveData;

public class RoomsRepository {
    private RoomDao roomDao;
    private LiveData<List<Room>> allRooms;

    public RoomsRepository(Application application) {
        RoomsDb roomsDb = RoomsDb.getInstance(application);
        roomDao = roomsDb.roomDao();
        allRooms = roomDao.getAllRooms();
    }

    public void insert(Room room) {
        new InsertRoomAsyncTask(roomDao).execute(room);

    }

    public void update(Room room) {
        new UpdateRoomAsyncTask(roomDao).execute(room);

    }

    public void delete(Room room) {
        new DeleteRoomAsyncTask(roomDao).execute(room);

    }

    public LiveData<List<Room>> getAllItems() {
        return allRooms;
    }

    private static class InsertRoomAsyncTask extends AsyncTask<Room, Void, Void> {
        private RoomDao roomDao;

        private InsertRoomAsyncTask(RoomDao roomDao) {
            this.roomDao = roomDao;
        }

        @Override
        protected Void doInBackground(Room... rooms) {
            roomDao.insert(rooms[0]);
            return null;
        }
    }

    private static class UpdateRoomAsyncTask extends AsyncTask<Room, Void, Void> {
        private RoomDao roomDao;

        private UpdateRoomAsyncTask(RoomDao roomDao) {
            this.roomDao = roomDao;
        }

        @Override
        protected Void doInBackground(Room... rooms) {
            roomDao.update(rooms[0]);
            return null;
        }
    }

    private static class DeleteRoomAsyncTask extends AsyncTask<Room, Void, Void> {
        private RoomDao roomDao;

        private DeleteRoomAsyncTask(RoomDao roomDao) {
            this.roomDao = roomDao;
        }

        @Override
        protected Void doInBackground(Room... rooms) {
            roomDao.delete(rooms[0]);
            return null;
        }
    }

}