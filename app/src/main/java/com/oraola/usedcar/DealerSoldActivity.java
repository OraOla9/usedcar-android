package com.oraola.usedcar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import com.oraola.usedcar.adapter.CarAdapter;
import com.oraola.usedcar.adapter.CarSoldAdapter;
import com.oraola.usedcar.bean.Car;
import com.oraola.usedcar.manager.CarAvaiableManager;
import com.oraola.usedcar.manager.CarSoldManager;
import com.oraola.usedcar.util.GetCarList;

import java.util.ArrayList;
import java.util.List;

public class DealerSoldActivity extends AppCompatActivity {

    private LinearLayout received,sold,addCar,statistics,profile;

    private RecyclerView recyclerView;
    private CarSoldAdapter carSoldAdapter;
    private SharedPreferences sharedPreferences;
    private int id;
    private List<Car> cars;
    private LinearLayoutCompat linearLayoutCompat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealer_sold);

        received = findViewById(R.id.nav_received);
        sold = findViewById(R.id.nav_sold);
        addCar = findViewById(R.id.nav_add_car);
        statistics = findViewById(R.id.nav_statistics);
        profile = findViewById(R.id.nav_profile);


        sharedPreferences = getSharedPreferences("dealer_data", MODE_PRIVATE);
        id = sharedPreferences.getInt("dealerid", 0);

        received.setOnClickListener(view -> {
            finish();
        });

        sold.setOnClickListener(view -> {
//            GetCarList.getSoldList(id,this);
            showList(id);
        });

        profile.setOnClickListener(view -> {
            Intent intent = new Intent(DealerSoldActivity.this, DealerUserActivity.class);
            startActivity(intent);
            finish();
        });
        addCar.setOnClickListener(view -> {
            Intent intent = new Intent(DealerSoldActivity.this,AddCarActivity.class);
            startActivity(intent);
        });

        recyclerView = findViewById(R.id.vehicle_list);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));


        showList(id);

    }
    private void showList(int id) {
        cars = new ArrayList<>();
        cars = GetCarList.getSoldList(id,this);
        runOnUiThread(() -> {
            carSoldAdapter = new CarSoldAdapter(cars,DealerSoldActivity.this);
            recyclerView.setAdapter(carSoldAdapter);
        });
    }
}