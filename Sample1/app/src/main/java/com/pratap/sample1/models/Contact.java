package com.pratap.sample1.models;

import java.io.Serializable;

/**
 * Created by pratap.kesaboyina on 23-09-2015.
 */
public class Contact implements Serializable {


    public String getGidId() {
        return gidId;
    }

    public void setGidId(String gidId) {
        this.gidId = gidId;
    }

    private String gidId;
    private String fullName;

    public Contact() {
    }

    private String mobileNumber;

    private String personalEmailId;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getPersonalEmailId() {
        return personalEmailId;
    }

    public void setPersonalEmailId(String personalEmailId) {
        this.personalEmailId = personalEmailId;
    }


    public int getTextDrawbleColor() {
        return textDrawbleColor;
    }

    public void setTextDrawbleColor(int textDrawbleColor) {
        this.textDrawbleColor = textDrawbleColor;
    }

    private int textDrawbleColor;


}
