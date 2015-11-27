package com.example.photori.photoribook;


public class CardItem {
    int image;
    boolean isSelect;
    String date;
    String text;
    String detail;

    public int getImage() {
        return image;
    }

    public String getDate() {
        return date;
    }

    public String getText() {
        return text;
    }

    public String getDetail() {
        return detail;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean isSelect) { this.isSelect=isSelect;}

    public CardItem(int image,boolean isSelect,String date, String text,String detail){
        this.image=image;
        this.isSelect=isSelect;
        this.date=date;
        this.text=text;
        this.detail=detail;
    }
}
