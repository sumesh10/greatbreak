package com.example.greatbreak;

public class Item {
    private String iname, iprice, imageName, imageUrl;

    public Item() {
    }
    public Item(String s){

    }


    public Item(String iname, String iprice, String imageName, String imageUrl) {
        this.iname = iname;
        this.iprice = iprice;
        this.imageName = imageName;
        this.imageUrl = imageUrl;
    }

    public String getIname() {
        return iname;
    }

    public void setIname(String iname) {
        this.iname = iname;
    }

    public String getIprice() {
        return iprice;
    }

    public void setIprice(String iprice) {
        this.iprice = iprice;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
