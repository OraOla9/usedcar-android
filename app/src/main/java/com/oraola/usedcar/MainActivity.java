package com.oraola.usedcar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.oraola.usedcar.adapter.CarAdapter;
import com.oraola.usedcar.bean.Car;
import com.oraola.usedcar.manager.CarAvaiableManager;
import com.oraola.usedcar.manager.CarSoldManager;
import com.oraola.usedcar.util.GetCarList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView text1 = findViewById(R.id.text01);
        TextView text2 = findViewById(R.id.text02);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "zhiti.ttf");
        text1.setTypeface(typeface);
        text2.setTypeface(typeface);



        sharedPreferences = getSharedPreferences("dealer_data", MODE_PRIVATE);
        if (sharedPreferences.contains("dealername") && sharedPreferences.contains("dealerpassword")) {
            id = sharedPreferences.getInt("dealerid", 0);
            GetCarList.getAvaiableList(id,this);
//            GetCarList.getSoldList(id,this);
//            getAvaiableList(id,this);
//            getSoldList(id,this);
            new Handler().postDelayed(() -> {
                Intent intent = new Intent(MainActivity.this, DealerMainActivity.class);
                startActivity(intent);
                finish();
            }, 3000);
        }else {
            new Handler().postDelayed(() -> {
                Intent intent = new Intent(MainActivity.this, DealerLoginActivity.class);
                startActivity(intent);
                finish();
            }, 2000);
        }

    }

}