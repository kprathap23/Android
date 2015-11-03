package com.pratap.sample1.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.pratap.sample1.R;

/**
 * Created by pratap.kesaboyina on 23-09-2015.
 */
public class Utils {


    public static int colors[] = {R.color.red, R.color.pink, R.color.purple, R.color.deep_purple,
            R.color.indigo, R.color.blue, R.color.light_blue, R.color.cyan, R.color.teal, R.color.green,
            R.color.light_green, R.color.lime, R.color.yellow, R.color.amber, R.color.orange, R.color.deep_orange};

    /**
     * call PhoneNumber
     *
     * @param ctx
     * @param phoneNumber
     */
    public static void callPhoneNumber(Context ctx, String phoneNumber) {
        if (phoneNumber.length() != 0) {

            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + phoneNumber));

            Log.e("phone number", phoneNumber);

            ctx.startActivity(callIntent);
        } else {
            Utils.showAlertDialog(ctx, "Alert !!!", "No Phone Number");
        }

    }


    public static void showAlertDialog(Context context, String title,
                                       String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (title != null)
            builder.setTitle(title);
        builder.setMessage(message);
        builder.setNegativeButton("OK", null);
        builder.show();
    }



}
