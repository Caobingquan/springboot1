package com.example.springboot1.pojo;

import java.util.Date;

/**
 * @author Binquan.Cao
 */
public class Blog {
    private int bId;
    private String bContent;
    private String bPhoto;
    private Date bDate;
    private int buId;
    private int bcAmount;
    private int blAmount;
    private String buName;

    public int getbId() {
        return bId;
    }

    public void setbId(int bId) {
        this.bId = bId;
    }

    public String getbContent() {
        return bContent;
    }

    public void setbContent(String bContent) {
        this.bContent = bContent;
    }

    public String getbPhoto() {
        return bPhoto;
    }

    public void setbPhoto(String bPhoto) {
        this.bPhoto = bPhoto;
    }

    public Date getbDate() {
        return bDate;
    }

    public void setbDate(Date bDate) {
        this.bDate = bDate;
    }

    public int getBuId() {
        return buId;
    }

    public void setBuId(int buId) {
        this.buId = buId;
    }

    public int getBcAmount() {
        return bcAmount;
    }

    public void setBcAmount(int bcAmount) {
        this.bcAmount = bcAmount;
    }

    public int getBlAmount() {
        return blAmount;
    }

    public void setBlAmount(int blAmount) {
        this.blAmount = blAmount;
    }

    public String getBuName() {
        return buName;
    }

    public void setBuName(String buName) {
        this.buName = buName;
    }
}
