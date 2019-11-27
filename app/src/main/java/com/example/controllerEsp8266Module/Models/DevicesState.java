package com.example.controllerEsp8266Module.Models;

public class DevicesState {
    private String Device1;
    private String RemoteLocal;
    private String Device3;
    private String Device4;

    public DevicesState(String device1, String remoteLocal, String device3, String device4) {
        Device1 = device1;
        RemoteLocal = remoteLocal;
        Device3 = device3;
        Device4 = device4;
    }

    public DevicesState() {
    }

    public String getDevice1() {
        return Device1;
    }

    public void setDevice1(String device1) {
        Device1 = device1;
    }

    public String getRemoteLocal() {
        return RemoteLocal;
    }

    public void setRemoteLocal(String remoteLocal) {
        RemoteLocal = remoteLocal;
    }

    public String getDevice3() {
        return Device3;
    }

    public void setDevice3(String device3) {
        Device3 = device3;
    }

    public String getDevice4() {
        return Device4;
    }

    public void setDevice4(String device4) {
        Device4 = device4;
    }
}
