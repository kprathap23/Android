/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pratap.sample1;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pratap.sample1.database.DatabaseHandler;
import com.pratap.sample1.models.Contact;
import com.pratap.sample1.utils.Utils;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ContactDetailActivity extends AppCompatActivity {


    private CollapsingToolbarLayout collapsingToolbar;

    TextView contactNameValue, mobileNumberValue, emailIdValue;

    Contact singleContact;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_detail);

        Intent intent = getIntent();

        singleContact = (Contact) intent.getSerializableExtra("CONTACT");


        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(singleContact.getFullName());


        loadBackdrop();

        setupViews();


    }

    private void setupViews() {


        contactNameValue = (TextView) findViewById(R.id.contactNameValue);
        mobileNumberValue = (TextView) findViewById(R.id.mobileNumberValue);
        emailIdValue = (TextView) findViewById(R.id.emailIdValue);
        contactNameValue.setText(singleContact.getFullName());
        mobileNumberValue.setText(singleContact.getMobileNumber());
        emailIdValue.setText(singleContact.getPersonalEmailId());

        CardView numberCardView = (CardView) findViewById(R.id.info_card2);
        numberCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Utils.callPhoneNumber(v.getContext(), singleContact.getMobileNumber());


            }
        });

    }

    private void loadBackdrop() {
        final ImageView imageView = (ImageView) findViewById(R.id.backdrop);


        imageView.setBackgroundColor(singleContact.getTextDrawbleColor());

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.contactdetail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.edit_menu) {

            callEditContact();


            return true;
        } else if (id == R.id.delete_menu) {
            callDeleteContact();

        }

        return super.onOptionsItemSelected(item);
    }



    private void callEditContact() {


        Intent newContactIntent = new Intent(ContactDetailActivity.this, AddNewContactActivity.class);
        newContactIntent.putExtra("ISNEWCONTACT", false);
        newContactIntent.putExtra("CONTACT", singleContact);
        startActivity(newContactIntent);

    }

    public void callDeleteContact() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        // Yes button clicked
                        DatabaseHandler.getDbInstance(ContactDetailActivity.this).deleteSingleContactRecord(singleContact.getGidId());

                        Toast.makeText(ContactDetailActivity.this, "Contact Deleted !!!",
                                Toast.LENGTH_LONG).show();

                        Intent mainScreen = new Intent(ContactDetailActivity.this, SimpleTabsActivity.class);

                        mainScreen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        startActivity(mainScreen);


                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        // No button clicked
                        // do nothing

                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure?")
                .setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }
}






