package com.pratap.endlessrecyclerview.utils;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PrefManager {
    private static final String TAG = PrefManager.class.getSimpleName();

    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "PratapHDWallpapers";


    // Gallery directory name
    private static final String KEY_GALLERY_NAME = "gallery_name";


    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        setGalleryName(PREF_NAME);

    }

    /**
     * storing gallery name
     */
    public void setGalleryName(String galleryName) {
        editor = pref.edit();

        editor.putString(KEY_GALLERY_NAME, galleryName);

        // commit changes
        editor.commit();
    }

    public String getGalleryName() {
        return pref.getString(KEY_GALLERY_NAME, AppConst.SDCARD_DIR_NAME);
    }


    /**
     * Fetching albums from shared preferences. Albums will be sorted before
     * returning in alphabetical order
     * */


}