package com.example.photori.photoribook;


public class CardItem {
    int image;
    boolean isSelect;
    String date;
    String text;

    public int getImage() {
        return image;
    }

    public String getDate() {
        return date;
    }

    public String getText() {
        return text;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public CardItem(int image,boolean isSelect,String date, String text){
        this.image=image;
        this.isSelect=isSelect;
        this.date=date;
        this.text=text;
    }
}
