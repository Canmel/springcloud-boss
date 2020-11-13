package com.camel.interviewer.entity.wx;

public class ImageText {
    //图文标题
    private String Title;
    //图文描述
    private String Description;
    //图片链接，支持JPG、PNG格式，较好的效果为大图360*200，小图200*200
    private String PicUrl;
    //点击图文消息跳转链接
    private String Url;

    public ImageText(String title, String description, String picUrl, String url) {
        this.Title = title;
        this.Description = description;
        this.PicUrl = picUrl;
        this.Url = url;
    }
    public ImageText(){super();}

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getPicUrl() {
        return PicUrl;
    }

    public void setPicUrl(String picUrl) {
        PicUrl = picUrl;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }
}
