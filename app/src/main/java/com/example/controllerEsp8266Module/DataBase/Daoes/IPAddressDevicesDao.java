package com.example.controllerEsp8266Module.DataBase.Daoes;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.controllerEsp8266Module.DataBase.Tables.IPAddressesDevices;

import java.util.List;

@Dao
public interface IPAddressDevicesDao {

    @Insert
    void insert(IPAddressesDevices ipAddressesDevices);

    @Update
    void update(IPAddressesDevices ipAddressesDevices);

    @Delete
    void delete(IPAddressesDevices ipAddressesDevices);

    @Query("SELECT * FROM ip_addresses_devices ORDER BY id DESC")
    LiveData<List<IPAddressesDevices>> getAllItems();

    @Query("SELECT * FROM ip_addresses_devices WHERE idParent=:parentId AND parentNameActivity = :parentName")
    IPAddressesDevices findIPAddressForDevice(int parentId, String parentName);

    @Query("UPDATE ip_addresses_devices SET ipAddress= :newIpAddress WHERE idParent= :parentId AND parentNameActivity = :parentName")
    void UpdateIpAddress (String newIpAddress, int parentId, String parentName);

    @Query("UPDATE ip_addresses_devices SET isSwitch1On = :newS1State WHERE idParent= :parentId AND parentNameActivity = :parentName")
    void UpdateSwitch1State (boolean newS1State, int parentId, String parentName);

    @Query("UPDATE ip_addresses_devices SET isSwitch3On = :newS3State WHERE idParent= :parentId AND parentNameActivity = :parentName")
        void UpdateSwitch3State (boolean newS3State, int parentId, String parentName);

    @Query("UPDATE ip_addresses_devices SET isRemoteOn = :newSRemoteState WHERE idParent= :parentId AND parentNameActivity = :parentName")
        void UpdateSwitchRemoteState (boolean newSRemoteState, int parentId, String parentName);


}
