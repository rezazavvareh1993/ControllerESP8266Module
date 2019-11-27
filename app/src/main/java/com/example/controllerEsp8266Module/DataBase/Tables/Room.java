package com.example.controllerEsp8266Module.DataBase.Tables;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "room_table")

public class Room {
    public Room(int imgItem, int imgEdit, String nameItem) {
        this.imgItem = imgItem;
        this.imgEdit = imgEdit;
        this.nameItem = nameItem;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int imgItem;

    private int imgEdit;


    private String nameItem;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getImgItem() {
        return imgItem;
    }

    public int getImgEdit() {
        return imgEdit;
    }

    public String getNameItem() {
        return nameItem;
    }


}
