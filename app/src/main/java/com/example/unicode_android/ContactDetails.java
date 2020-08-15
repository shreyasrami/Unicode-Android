package com.example.unicode_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ContactDetails extends AppCompatActivity {

    TextView contactName,contactNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);

        Intent contact = getIntent();
        String name = contact.getStringExtra("name");
        String number = contact.getStringExtra("number");

        contactName = findViewById(R.id.contactName);
        contactNumber = findViewById(R.id.contactNumber);

        contactName.setText(name);
        contactNumber.setText(number);

    }


}