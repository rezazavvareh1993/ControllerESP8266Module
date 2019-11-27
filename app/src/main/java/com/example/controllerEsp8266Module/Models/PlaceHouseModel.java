package com.example.controllerEsp8266Module.Models;

import com.example.controllerEsp8266Module.R;

import java.util.ArrayList;

public class PlaceHouseModel {
    int imgPlace;
    String txtPlace;

    public int getImgPlace() {
        return imgPlace;
    }

    public void setImgPlace(int imgPlace) {
        this.imgPlace = imgPlace;
    }

    public String getTxtPlace() {
        return txtPlace;
    }

    public void setTxtPlace(String txtPlace) {
        this.txtPlace = txtPlace;
    }

//    public SmartHouseModel(int imgPlace, String txtPlace) {
//        this.imgPlace = imgPlace;
//        this.txtPlace = txtPlace;
//    }
    public static ArrayList<PlaceHouseModel> getData (){

        ArrayList<PlaceHouseModel> placeHouseModelArrayList = new ArrayList<>();

        int[] imagesPlaces = getImages();
        String[] namePlaces = getNamePlaces();

        for(int i = 0; i < imagesPlaces.length ; i++){
            PlaceHouseModel placeHouseModel = new PlaceHouseModel();
            placeHouseModel.setImgPlace(imagesPlaces[i]);
            placeHouseModel.setTxtPlace(namePlaces[i]);

            placeHouseModelArrayList.add(placeHouseModel);

        }

        return placeHouseModelArrayList;
    }

    private static String[] getNamePlaces() {
        String[] namePlaces = {"پذیرایی", "اتاق نشیمن","اتاق خواب", "اشپزخانه"};

        return namePlaces;
    }

    public static int[] getImages(){
        int[] images = {R.drawable.mainroom1, R.drawable.livingroom, R.drawable.bedroom,
        R.drawable.kitchen};

        return images;
    }
}
