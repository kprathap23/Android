package com.pratap.endlessrecyclerview;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.pratap.endlessrecyclerview.models.WallPaper;
import com.pratap.endlessrecyclerview.utils.Const;
import com.pratap.endlessrecyclerview.utils.ServiceHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pratap.kesaboyina on 29-06-2015.
 */
public class WallpaperActivity extends AppCompatActivity {


    private static final String TAG = "WallpaperActivity";


    private Toolbar toolbar;


    private RecyclerView mRecyclerView;
    private WallPaperDataAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;


    public static int pageNumber;

    private List<WallPaper> wallpaperImagesList;


    protected Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallpaper_activity);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        pageNumber = 1;
        wallpaperImagesList = new ArrayList<WallPaper>();
        handler = new Handler();
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("WallPapers");

        }


        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);


        mLayoutManager = new LinearLayoutManager(this);


        // use a linear layout manager
        mRecyclerView.setLayoutManager(mLayoutManager);


        // create an Object for Adapter
        mAdapter = new WallPaperDataAdapter(wallpaperImagesList, mRecyclerView);

        // set the adapter object to the Recyclerview
        mRecyclerView.setAdapter(mAdapter);


        getWebServiceData();


        mAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                //add null , so the adapter will check view_type and show progress bar at bottom
                wallpaperImagesList.add(null);
                mAdapter.notifyItemInserted(wallpaperImagesList.size() - 1);
                ++pageNumber;


                getWebServiceData();


            }
        });


    }


    public void getWebServiceData() {

        BackGroundAsyncTask backGroundTask = new BackGroundAsyncTask(this, pageNumber);
        backGroundTask.execute();

    }


    public class BackGroundAsyncTask extends AsyncTask<Object, Void, String> {


        public OnTaskCompleted listener = null;//Call back interface


        Context context;
        int pageNumber;

        public BackGroundAsyncTask(Context context1, int pageNumber) {
            context = context1;

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
            parsejosnData(result);

        }

    }


    public void parsejosnData(String response) {

        try {

            JSONObject jsonObject = new JSONObject(response);

            //    String json = jsonObject.toString();

            JSONArray jsonArray = jsonObject.getJSONArray("wallpapers");


            if (jsonArray != null) {
                // looping through All albums


                if (pageNumber > 1) {
                    wallpaperImagesList.remove(wallpaperImagesList.size() - 1);
                    mAdapter.notifyItemRemoved(wallpaperImagesList.size());
                }

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject c = jsonArray.getJSONObject(i);

                    // Storing each json item values in variable
                    String id = c.getString("id");
                    String orig_url = c.getString("orig_url");
                    String thumb_url = c.getString("thumb_url");
                    String downloads = c.getString("downloads");
                    String fav = c.getString("fav");

                    // Creating object for each product
                    WallPaper singleWall = new WallPaper(id, orig_url, thumb_url, downloads, fav);

                    // adding HashList to ArrayList
                    wallpaperImagesList.add(singleWall);


                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.notifyItemInserted(wallpaperImagesList.size());


                        }
                    });


                }


                mAdapter.setLoaded();


            } else {
                Log.d("Wallpapers: ", "null");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}
