package com.pratap.sectionrecyclerview.models;

import java.util.ArrayList;

/**
 * Created by pratap.kesaboyina on 30-11-2015.
 */
public class DataModel {



    private String headerTitle;
    private ArrayList<String> allItemsInSection;


    public DataModel() {

    }
    public DataModel(String headerTitle, ArrayList<String> allItemsInSection) {
        this.headerTitle = headerTitle;
        this.allItemsInSection = allItemsInSection;
    }



    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public ArrayList<String> getAllItemsInSection() {
        return allItemsInSection;
    }

    public void setAllItemsInSection(ArrayList<String> allItemsInSection) {
        this.allItemsInSection = allItemsInSection;
    }


}
