package com.example.androidprogram6;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
public class MainActivity extends AppCompatActivity {
    private final String apiString = "https://agify.io/?name=";
    private String jsonResponse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }
    public class apiObj implements java.io.Serializable {
        String age;
        int count;
        String name;
    }
    public class jsonConverter{
        public apiObj convertToObject(String jsonResponse){
            Gson gson = new Gson();
            apiObj agify = gson.fromJson(jsonResponse, apiObj.class);
            return agify;
        }
    }
    public void onClick(View v){
        EditText nameET = findViewById(R.id.nameET);

        String query = apiString + nameET.getText().toString();
        StringRequest req = new StringRequest(Request.Method.GET, query, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                jsonResponse = response;
                jsonConverter converter = new jsonConverter();
                apiObj agify = converter.convertToObject(jsonResponse);
                TextView ageTV = findViewById(R.id.agePredTV);

                if(agify.age == null){
                    ageTV.setText("Age is ...");
                }else {
                    ageTV.setText("Age is " + agify.age);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TAG", error.toString().trim());
            }
        });
        RequestQueue reqQueue = Volley.newRequestQueue(this);
        reqQueue.add(req);

    }
}
