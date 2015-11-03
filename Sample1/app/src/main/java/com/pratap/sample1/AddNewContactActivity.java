package com.pratap.sample1;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.pratap.sample1.database.DatabaseHandler;
import com.pratap.sample1.models.Contact;

import java.util.UUID;

public class AddNewContactActivity extends AppCompatActivity {


    private Toolbar toolbar;
    private EditText etName, etMobile, etEmailId;
    private Button btnSaveContact;

    boolean isNew=true;
    Contact singleContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addnew_contact);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Add New Contact");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        isNew = (boolean) getIntent().getSerializableExtra("ISNEWCONTACT");



        etName = (EditText) findViewById(R.id.etName);
        etMobile = (EditText) findViewById(R.id.etMobile);
        etEmailId = (EditText) findViewById(R.id.etEmailId);
        //Format phone number as user is typing
        etMobile.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        btnSaveContact = (Button) findViewById(R.id.btnSaveContact);


        if(!isNew) {

            singleContact = (Contact) getIntent().getSerializableExtra("CONTACT");



            etMobile.append(singleContact.getMobileNumber());

            etEmailId.append(singleContact.getPersonalEmailId());
            etName.append(singleContact.getFullName());

        }







        btnSaveContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View mainView = (View) findViewById(R.id.main_content);
                String name = etName.getText().toString();
                String mobileNumber = etMobile.getText().toString();
                String emailId = etEmailId.getText().toString();

                if (!name.isEmpty()&& !mobileNumber.isEmpty() && !emailId.isEmpty()) {
                    ColorGenerator generator = ColorGenerator.MATERIAL;
                    Contact newContact = new Contact();

                    newContact.setFullName(name);
                    newContact.setMobileNumber(mobileNumber);
                    newContact.setPersonalEmailId(emailId);
                    newContact.setTextDrawbleColor(generator.getRandomColor());

                    DatabaseHandler db = DatabaseHandler.getDbInstance(v.getContext());
                    long contactInsertID=0;
                    if(isNew) {
                        newContact.setGidId(UUID.randomUUID().toString());
                         contactInsertID = db.addNewContact(newContact);
                    }else {


                        newContact.setGidId(singleContact.getGidId());
                        contactInsertID = (long)db.updateSingleContactRecord(newContact);
                    }

                    if (contactInsertID > 0) {

                        if(isNew) {

                            Snackbar.make(mainView, "Contact Saved Successfully !!!",
                                    Snackbar.LENGTH_SHORT)
                                    .show();
                        }
                        else
                        {
                            Snackbar.make(mainView, "Contact Updated Successfully !!!",
                                    Snackbar.LENGTH_SHORT)
                                    .show();

                            Intent mainScreen= new Intent(v.getContext(), SimpleTabsActivity.class);

                            mainScreen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                            startActivity(mainScreen);
                        }

                        etEmailId.setText("");
                        etMobile.setText("");
                        etName.setText("");

                    } else {
                        Snackbar.make(mainView, "Contact Saving Failed !!!",
                                Snackbar.LENGTH_SHORT)
                                .show();
                    }


                } else {

                    Snackbar.make(mainView, "Please fill the details",
                            Snackbar.LENGTH_SHORT)
                            .show();


                }
            }
        });

    }

}





