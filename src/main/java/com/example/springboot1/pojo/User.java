package com.example.springboot1.pojo;

import java.util.Date;

/**
 * @author Binquan.Cao
 */
public class User {
    private int uId;
    private String uName;
    private String uPassword;
    private String uPhoto;
    private String uPhone;
    private int uErrorCount;
    private Date uErrorDate;

    public int getuId() {
        return uId;
    }

    public void setuId(int uId) {
        this.uId = uId;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getuPassword() {
        return uPassword;
    }

    public void setuPassword(String uPassword) {
        this.uPassword = uPassword;
    }

    public String getuPhoto() {
        return uPhoto;
    }

    public void setuPhoto(String uPhoto) {
        this.uPhoto = uPhoto;
    }

    public String getuPhone() {
        return uPhone;
    }

    public void setuPhone(String uPhone) {
        this.uPhone = uPhone;
    }

    public int getuErrorCount() {
        return uErrorCount;
    }

    public void setuErrorCount(int uErrorCount) {
        this.uErrorCount = uErrorCount;
    }

    public Date getuErrorDate() {
        return uErrorDate;
    }

    public void setuErrorDate(Date uErrorDate) {
        this.uErrorDate = uErrorDate;
    }

    @Override
    public String toString() {
        return "User{" +
                "uId=" + uId +
                ", uName='" + uName + '\'' +
                ", uPassword='" + uPassword + '\'' +
                ", uPhoto='" + uPhoto + '\'' +
                ", uPhone='" + uPhone + '\'' +
                ", uErrorCount='" + uErrorCount + '\'' +
                ", uErrorDate='" + uErrorDate + '\'' +
                '}';
    }

    public User() {
    }

    public User(int uId, String uName, String uPassword, String uPhoto, String uPhone) {
        this.uId = uId;
        this.uName = uName;
        this.uPassword = uPassword;
        this.uPhoto = uPhoto;
        this.uPhone = uPhone;
    }

    public User(int uId, String uName, String uPassword, String uPhoto, String uPhone, int uErrorCount, Date uErrorDate) {
        this.uId = uId;
        this.uName = uName;
        this.uPassword = uPassword;
        this.uPhoto = uPhoto;
        this.uPhone = uPhone;
        this.uErrorCount = uErrorCount;
        this.uErrorDate = uErrorDate;
    }
}
