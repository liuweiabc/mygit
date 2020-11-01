package com.example.xuh.shopping;

public class goodsModel {
    private int imageId;
    private String content;

    public goodsModel(int imageId,String content){
        this.imageId = imageId;
        this.content = content;
    }
    public void setImageId(int imageId){
        this.imageId = imageId;
    }
    public void setContent(String content){
        this.content = content;
    }
    public int getImageId(){
        return this.imageId;
    }
    public String getContent(){
        return this.content;
    }

}
