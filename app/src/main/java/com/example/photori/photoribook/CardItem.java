package com.example.photori.photoribook;


public class CardItem {
    String objectId;
    byte[] image;
    boolean isSelect;
    String date;
    String text;
    String detail;

    public String getObjectId() {
        return objectId;
    }

    public byte[] getImage() {
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

    public CardItem(String objectId, byte[] image, boolean isSelect, String date, String text, String detail){
        this.objectId=objectId;
        this.image=image;
        this.isSelect=isSelect;
        this.date=date;
        this.text=text;
        this.detail=detail;
    }
}
