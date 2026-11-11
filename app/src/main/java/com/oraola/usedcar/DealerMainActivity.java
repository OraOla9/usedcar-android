package com.oraola.usedcar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.oraola.usedcar.bean.Car;
import com.oraola.usedcar.adapter.CarAdapter;
import com.oraola.usedcar.manager.CarAvaiableManager;
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

public class DealerMainActivity extends AppCompatActivity {


    private LinearLayout received,sold,addCar,statistics,profile;

    private RecyclerView recyclerView;
    private CarAdapter carAdapter;
    private SharedPreferences sharedPreferences;
    private int id;
    private List<Car> cars;
    private LinearLayoutCompat linearLayoutCompat;
    private ImageView searchBtn;
    private EditText search_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealer_main);

        received = findViewById(R.id.nav_received);
        sold = findViewById(R.id.nav_sold);
        addCar = findViewById(R.id.nav_add_car);
        statistics = findViewById(R.id.nav_statistics);
        profile = findViewById(R.id.nav_profile);

        searchBtn = findViewById(R.id.searchBtn);
        search_bar = findViewById(R.id.search_bar);
        searchBtn.setOnClickListener(view -> {
            String serch = search_bar.getText().toString();
            CarAvaiableManager db = new CarAvaiableManager(DealerMainActivity.this);
            cars = db.findAvailableCarByBrand(serch);
            Log.d("nnnmnnmmnm",cars.toString());
            cars.addAll(db.findAvailableCarByModel(serch));
            Log.d("nnnmnnmmnm",cars.toString());
            runOnUiThread(() -> {
                carAdapter = new CarAdapter(cars,DealerMainActivity.this);
                recyclerView.setAdapter(carAdapter);
            });
            if (db != null) {
                db.closeDB();
            }
        });

        sharedPreferences = getSharedPreferences("dealer_data", MODE_PRIVATE);
        id = sharedPreferences.getInt("dealerid", 0);


        received.setOnClickListener(view -> {
            GetCarList.getAvaiableList(id,DealerMainActivity.this);
            showList(id);
        });

        sold.setOnClickListener(view -> {
            Intent intent = new Intent(DealerMainActivity.this, DealerSoldActivity.class);
            startActivity(intent);
        });

        addCar.setOnClickListener(view -> {
            Intent intent = new Intent(DealerMainActivity.this,AddCarActivity.class);
            startActivity(intent);
        });

        profile.setOnClickListener(view -> {
            Intent intent = new Intent(DealerMainActivity.this, DealerUserActivity.class);
            startActivity(intent);
        });

        recyclerView = findViewById(R.id.vehicle_list);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));


        showList(id);

    }
    private void showList(int id) {
        cars = new ArrayList<>();

        CarAvaiableManager db = new CarAvaiableManager(DealerMainActivity.this);
        cars = db.findAllAvailableCars();
        if (db != null) {
            db.closeDB();
        }
        Log.d("oooo",cars.toString());
        runOnUiThread(() -> {
            carAdapter = new CarAdapter(cars,DealerMainActivity.this);
            recyclerView.setAdapter(carAdapter);
        });
    }
}