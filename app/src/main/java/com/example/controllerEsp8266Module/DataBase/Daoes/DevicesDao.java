package com.example.controllerEsp8266Module.DataBase.Daoes;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.controllerEsp8266Module.DataBase.Tables.Devices;

import java.util.List;

@Dao
public interface DevicesDao {

    @Insert
    void insert(Devices devices);

    @Delete
    void delete(Devices devices);

    @Update
    void update(Devices devices);

    @Query("SELECT * FROM devices_table ORDER by id DESC")
    LiveData<List<Devices>> getAllItems();

    @Query("SELECT * FROM devices_table WHERE idParent = :idParent AND parentName = :parentName")
    LiveData<List<Devices>> getDevicesPerParent(int idParent, String parentName);


}
