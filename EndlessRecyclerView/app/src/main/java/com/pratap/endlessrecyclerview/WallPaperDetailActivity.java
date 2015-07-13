package com.pratap.endlessrecyclerview;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.pratap.endlessrecyclerview.models.WallPaper;
import com.pratap.endlessrecyclerview.utils.Utils;

/**
 * Created by pratap.kesaboyina on 03-07-2015.
 */
public class WallPaperDetailActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView imageView;
    private Toolbar toolbar;
    Button btnDownload,btnSetWallpaper;
    Utils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallpaper_detail);
        imageView = (ImageView) findViewById(R.id.imageView);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        btnDownload = (Button) findViewById(R.id.btnDownload);
        btnSetWallpaper = (Button) findViewById(R.id.btnSetWallpaper);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        utils = new Utils(this);

        WallPaper singleWallPaper = (WallPaper) getIntent().getSerializableExtra("WallPaper");


        Glide.with(this)
                .load(singleWallPaper.getOrigUrl())

                .asBitmap()
                .placeholder(R.drawable.bg)

                .into(imageView);

        btnDownload.setOnClickListener(this);

        btnSetWallpaper.setOnClickListener(this);

    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {

        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable())
                .getBitmap();
        switch (v.getId()) {
            // button Download Wallpaper tapped
            case R.id.btnDownload:
                utils.saveImageToSDCard(bitmap);
                break;
            // button Set As Wallpaper tapped
            case R.id.btnSetWallpaper:
                utils.setAsWallpaper(bitmap);
                break;
            default:
                break;
        }
    }
}
