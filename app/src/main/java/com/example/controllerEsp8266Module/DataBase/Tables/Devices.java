package com.example.controllerEsp8266Module.DataBase.Tables;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "devices_table")
public class Devices {
    public Devices(int imgItem, int imgEditItem, String nameItem, int idParent, String parentName) {
        this.imgItem = imgItem;
        this.imgEditItem = imgEditItem;
        this.nameItem = nameItem;
        this.idParent = idParent;
        this.parentName = parentName;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int imgItem;

    private int imgEditItem;

    private String nameItem;

    private int idParent;

    private String parentName;

    public int getIdParent() {
        return idParent;
    }

    public String getParentName() {
        return parentName;
    }

    public void setImgItem(int imgItem) {
        this.imgItem = imgItem;
    }

    public void setIdParent(int idParent) {
        this.idParent = idParent;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImgItem() {
        return imgItem;
    }

    public int getImgEditItem() {
        return imgEditItem;
    }

    public String getNameItem() {
        return nameItem;
    }
}
