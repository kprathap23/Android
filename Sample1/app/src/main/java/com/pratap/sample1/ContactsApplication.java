package com.pratap.sample1;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by pratap.kesaboyina on 24-09-2015.
 */
public class ContactsApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/RobotoCondensed-Regular.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );


    }
}
