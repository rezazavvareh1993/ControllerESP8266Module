package com.example.controllerEsp8266Module.DataBase.Daoes;


import com.example.controllerEsp8266Module.DataBase.Tables.Room;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface RoomDao {

    @Insert
    void insert(Room room);

    @Delete
    void delete(Room room);

    @Update
    void update(Room room);

    @Query("SELECT * FROM room_table ORDER BY id DESC ")
    LiveData<List<Room>> getAllRooms();


}
