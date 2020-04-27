package com.example.schedule.sleep;


/**
 * @author twn29004
 * 每个卡片信息的类
 */


public class CardBean {

    private int pic;
    private String mediaUrl;
    private String mediaName;
    private String mediaDercribe;

    public int getPic() {
        return pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public String getMediaName() {
        return mediaName;
    }

    public void setMediaName(String mediaName) {
        this.mediaName = mediaName;
    }


    public String getMediaDercribe() {
        return mediaDercribe;
    }

    public void setMediaDercribe(String mediaDercribe) {
        this.mediaDercribe = mediaDercribe;
    }

    @Override
    public String toString() {
        return "CardBean{" +
                "pic=" + pic +
                ", title='" + mediaUrl + '\'' +
                ", ballYear='" + mediaName + '\'' +
                ", team='" + mediaDercribe + '\'' +
                '}';
    }

}