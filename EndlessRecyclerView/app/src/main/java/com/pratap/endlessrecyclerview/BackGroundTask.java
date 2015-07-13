package com.pratap.endlessrecyclerview;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.pratap.endlessrecyclerview.utils.Const;
import com.pratap.endlessrecyclerview.utils.ServiceHandler;

/**
 * Created by pratap.kesaboyina on 27-05-2015.
 */
public class BackGroundTask extends AsyncTask<Object, Void, String> {


    public OnTaskCompleted listener = null;//Call back interface


    Context context;
    int pageNumber;

    public BackGroundTask(Context context1, OnTaskCompleted listener1, int pageNumber) {
        context = context1;
        listener = listener1;   //Assigning call back interface  through constructor
        this.pageNumber = pageNumber;
    }


    @Override
    protected String doInBackground(Object... params) {

        //My Background tasks are written here

        synchronized (this) {

            String url = Const.URL_WALLPAPERS_HD + pageNumber;
            String jsonStr = ServiceHandler.makeServiceCall(url, ServiceHandler.GET);
            Log.i("Url: ", "> " + url);

            Log.i("Response: ", "> " + jsonStr);
            return jsonStr;
        }

    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        listener.onTaskCompleted(result);

    }

}