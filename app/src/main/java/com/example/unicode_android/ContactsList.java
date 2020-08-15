package com.example.unicode_android;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ContactsList extends AppCompatActivity {
    ListView list;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_list);

        list = findViewById(R.id.list);


        final Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);
        String[] from = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME};
        int[] to = {R.id.rowText};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,R.layout.contact_row,cursor,from,to,0);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    if (cursor != null) {
                        cursor.moveToPosition(position);
                        String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                        String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        Intent intent = new Intent(ContactsList.this,ContactDetails.class);
                        intent.putExtra("name",name);
                        intent.putExtra("number",number);
                        startActivity(intent);
                    }

                }
        });



    }

}