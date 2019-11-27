package com.example.controllerEsp8266Module.Models;

import com.example.controllerEsp8266Module.R;

import java.util.ArrayList;

public class RoomModel {
    int imgRoom;
    int imgRoomDelete;
    int imgRoomAdd;
    String edtNameRoom;

    public int getImgRoom() {
        return imgRoom;
    }

    public void setImgRoom(int imgRoom) {
        this.imgRoom = imgRoom;
    }

    public int getImgRoomDelete() {
        return imgRoomDelete;
    }

    public void setImgRoomDelete(int imgRoomDelete) {
        this.imgRoomDelete = imgRoomDelete;
    }

    public int getImgRoomAdd() {
        return imgRoomAdd;
    }

    public void setImgRoomAdd(int imgRoomAdd) {
        this.imgRoomAdd = imgRoomAdd;
    }

    public String getEdtNameRoom() {
        return edtNameRoom;
    }

    public void setEdtNameRoom(String edtNameRoom) {
        this.edtNameRoom = edtNameRoom;
    }

    public static ArrayList<RoomModel> getData (){

        ArrayList<RoomModel> roomModelArrayList = new ArrayList<>();

        int[] imagesRooms = getImages();
        String[] nameRooms = getNameRooms();

        for(int i = 0; i < imagesRooms.length ; i++){
            RoomModel roomModel = new RoomModel();
            roomModel.setImgRoom(imagesRooms[i]);
            roomModel.setEdtNameRoom(nameRooms[i]);

            roomModelArrayList.add(roomModel);

        }

        return roomModelArrayList;
    }

    private static String[] getNameRooms() {
        String[] nameRooms = {"اتاق ۱", "اتاق ۲"};

        return nameRooms;
    }

    public static int[] getImages(){
        int[] images = {R.drawable.bedroom,R.drawable.bedroom};

        return images;
    }
}

