package com.oraola.usedcar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.oraola.usedcar.bean.Car;
import com.oraola.usedcar.manager.CarAvaiableManager;

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

public class CarSoidViewActivity extends AppCompatActivity {

    private ImageView returnImg,gradeImg;
    private TextView price,create_time,brand,soldprice,soltime;
    private Button sellBtn;
    List<Car> cars = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_soid_view);

        returnImg = findViewById(R.id.returnImg);
        gradeImg = findViewById(R.id.gradeImg);
        price = findViewById(R.id.price);
        create_time = findViewById(R.id.create_time);
        brand = findViewById(R.id.brand);
        soldprice = findViewById(R.id.soldprice);
        soltime = findViewById(R.id.soltime);

        int carId = getIntent().getIntExtra("carId", -1);
        loadCarDetails(carId);
        returnImg.setOnClickListener(v -> finish());
    }

    private void loadCarDetails(int carId) {
        new Thread(() -> {
            RequestBody formBody = new FormBody.Builder()
                    .addEncoded("carId", Integer.toString(carId))
                    .build();

            Request request = new Request.Builder()
                    .url("http://10.21.48.224:8080//car/bcar")
                    .post(formBody)
                    .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                    .build();
            try {
                OkHttpClient client = new OkHttpClient();
                Response response = client.newCall(request).execute();

                if (response.isSuccessful()) {
                    // 获取响应数据
                    String responseData = response.body().string();
                    Log.d("xzxxzxzx",responseData);
                    JSONArray jsonArray = new JSONArray(responseData);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Car car = new Car();
                        car.setCarId(jsonObject.getInt("carId"));
                        car.setEngineNumber(jsonObject.getString("engineNumber"));
                        car.setBrand(jsonObject.getString("brand"));
                        car.setModel(jsonObject.getString("model"));
                        car.setGrade(jsonObject.getString("grade"));
                        car.setAcquisitionPrice(jsonObject.getDouble("acquisitionPrice"));
                        Date acquisitionDate = new SimpleDateFormat("yyyy-MM-dd").parse(jsonObject.getString("acquisitionDate"));
                        car.setAcquisitionDate(acquisitionDate);
                        car.setSalePrice(jsonObject.getDouble("salePrice"));
                        Date saleDate = new SimpleDateFormat("yyyy-MM-dd").parse(jsonObject.getString("saleDate"));
                        car.setSaleDate(saleDate);
                        car.setStatus(jsonObject.getString("status"));
                        car.setDealerId(jsonObject.getInt("dealerId"));
                        cars.add(car);
                    }

                }
            } catch (IOException | ParseException | JSONException e) {
                throw new RuntimeException(e);
            }


            runOnUiThread(() -> {
                for (Car car : cars){
                    Log.d("vvvvvvvv",car.toString());
                    if (car != null) {
                        String gradle = car.getGrade();
                        int imageResId;
                        switch (gradle) {
                            case "S":
                                imageResId = R.drawable.sgrade;
                                break;
                            case "A":
                                imageResId = R.drawable.agrade;
                                break;
                            case "B":
                                imageResId = R.drawable.bgrade;
                            case "C":
                                imageResId = R.drawable.cgrade;
                                break;
                            default:
                                imageResId = R.drawable.cgrade; // 默认图片
                                break;
                        }
                        gradeImg.setImageResource(imageResId);
                        price.setText(Double.toString(car.getAcquisitionPrice()));

                        create_time.setText(new SimpleDateFormat("yyyy-MM-dd").format(car.getAcquisitionDate()));

                        brand.setText(car.getBrand()+ car.getModel());
                        soldprice.setText(Double.toString(car.getSalePrice()));
                        soltime.setText(new SimpleDateFormat("yyyy-MM-dd").format(car.getSaleDate()));


                    }
                }
            });
        }).start();
    }
}