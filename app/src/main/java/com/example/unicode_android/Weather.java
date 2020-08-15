package com.example.unicode_android;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.ConnectException;


public class Weather extends AppCompatActivity {
    EditText cityView;
    String city;
    Button citySubmit;
    RequestQueue queue;
    TextView responseName,responseTemp,responseCondition,setError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        citySubmit = findViewById(R.id.citySubmit);
        citySubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setError = findViewById(R.id.error);
                responseName = findViewById(R.id.responseName);
                responseTemp = findViewById(R.id.responseTemp);
                responseCondition = findViewById(R.id.responseCondition);
                cityView = findViewById(R.id.city);
                city = cityView.getText().toString();
                queue = Volley.newRequestQueue(Weather.this);
                String url = "https://api.openweathermap.org/data/2.5/weather?q="+city+"&units=metric&appid=d5ceaf3255d95c6f0b1b221a19f2ea27";
                JsonObjectRequest object = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            responseName.setText(response.getString("name"));
                            responseTemp.setText(String.format("%s °C", response.getJSONObject("main").getString("temp")));
                            responseCondition.setText(response.getJSONArray("weather").getJSONObject(0).getString("description"));
                            setError.setText("");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        responseName.setText("");
                        responseTemp.setText("");
                        responseCondition.setText("");
                        setError.setText(R.string.notFound);
                    }
                });
                queue.add(object);
            }
        });




    }
}