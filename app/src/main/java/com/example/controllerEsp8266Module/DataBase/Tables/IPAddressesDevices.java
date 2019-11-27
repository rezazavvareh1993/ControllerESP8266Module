package com.example.controllerEsp8266Module.DataBase.Tables;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ip_addresses_devices")

public class IPAddressesDevices {

    public IPAddressesDevices(String ipAddress, int idParent, String parentNameActivity,
                              boolean isSwitch1On, boolean isSwitch3On, boolean isRemoteOn) {
        this.ipAddress = ipAddress;
        this.idParent = idParent;
        this.parentNameActivity = parentNameActivity;
        this.isSwitch1On = isSwitch1On;
        this.isSwitch3On = isSwitch3On;
        this.isRemoteOn = isRemoteOn;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String ipAddress;

    private int idParent;

    private String parentNameActivity;

    private boolean isSwitch1On;

    private boolean isSwitch3On;

    private boolean isRemoteOn;

    public boolean isSwitch1On() {
        return isSwitch1On;
    }

    public void setSwitch1On(boolean switch1On) {
        isSwitch1On = switch1On;
    }

    public boolean isSwitch3On() {
        return isSwitch3On;
    }

    public void setSwitch3On(boolean switch3On) {
        isSwitch3On = switch3On;
    }

    public boolean isRemoteOn() {
        return isRemoteOn;
    }

    public void setRemoteOn(boolean remoteOn) {
        isRemoteOn = remoteOn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public void setIdParent(int idParent) {
        this.idParent = idParent;
    }

    public void setParentNameActivity(String parentNameActivity) {
        this.parentNameActivity = parentNameActivity;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public int getIdParent() {
        return idParent;
    }

    public String getParentNameActivity() {
        return parentNameActivity;
    }
}






