package com.example.unicode_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class Profile extends AppCompatActivity {
    private int PERMISSION_CODE = 1;
    EditText name,birthDate,phone,email;
    Button save,clear,getContacts,getWeather;
    ListView contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getContacts = findViewById(R.id.getContacts);
        getWeather = findViewById(R.id.getWeather);
        name = findViewById(R.id.name);
        birthDate = findViewById(R.id.birthDate);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);


        SharedPreferences get = getSharedPreferences("Data",MODE_PRIVATE);
        name.setText(get.getString("name",""));
        birthDate.setText(get.getString("birth",""));
        phone.setText(get.getString("phone",""));
        email.setText(get.getString("email",""));

        save = findViewById(R.id.save);
        clear = findViewById(R.id.clear);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = findViewById(R.id.name);
                birthDate = findViewById(R.id.birthDate);
                phone = findViewById(R.id.phone);
                email = findViewById(R.id.email);

                SharedPreferences sp = getSharedPreferences("Data",MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("name",name.getText().toString());
                editor.putString("birth",birthDate.getText().toString());
                editor.putString("phone",phone.getText().toString());
                editor.putString("email",email.getText().toString());
                editor.apply();
                Toast.makeText(Profile.this,"Saved",Toast.LENGTH_SHORT).show();

            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sp = getSharedPreferences("Data",MODE_PRIVATE);
                sp.edit().clear().apply();
                name.setText("");
                birthDate.setText("");
                phone.setText("");
                email.setText("");
                Toast.makeText(Profile.this,"Cleared",Toast.LENGTH_SHORT).show();
            }
        });

        getContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(Profile.this,Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(Profile.this,ContactsList.class);
                    startActivity(intent);
                } else {
                    requestContactsPermission();
                }

            }
        });

        getWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this,Weather.class);
                startActivity(intent);
            }
        });

    }
    private void requestContactsPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_CONTACTS)) {
            new AlertDialog.Builder(this)
                    .setTitle("Permission Needed")
                    .setMessage("This permission is needed to list your contacts")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(Profile.this,
                                    new String[] {Manifest.permission.READ_CONTACTS}, PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_CONTACTS}, PERMISSION_CODE);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_CODE)  {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Profile.this,ContactsList.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }



}