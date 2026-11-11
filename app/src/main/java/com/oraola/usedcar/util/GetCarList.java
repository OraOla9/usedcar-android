package com.oraola.usedcar.util;

import android.content.Context;
import android.util.Log;

import com.oraola.usedcar.bean.Car;
import com.oraola.usedcar.manager.CarAvaiableManager;
import com.oraola.usedcar.manager.CarSoldManager;

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

public class GetCarList {

    public static void getAvaiableList(int id, Context context) {
        List<Car> cars = new ArrayList<>();
        // 创建 POST 请求
        RequestBody formBody = new FormBody.Builder()
                .addEncoded("dealerId", Integer.toString(id))
                .build();

        Request request = new Request.Builder()
                .url("http://10.21.48.224:8080//car/alist")
                .post(formBody)
                .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                .build();
        // 发送请求

            try {
                OkHttpClient client = new OkHttpClient();
                Response response = client.newCall(request).execute();
                Log.d("eeeeeeeeee", response.toString());

                if (response.isSuccessful()) {
                    // 获取响应数据
                    String responseData = response.body().string();
                    Log.d("", responseData);
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
                        car.setStatus(jsonObject.getString("status"));
                        car.setDealerId(jsonObject.getInt("dealerId"));
                        cars.add(car);
                    }
                    CarAvaiableManager db = new CarAvaiableManager(context);
                    db.addCar(cars);
                    if (db != null) {
                        db.closeDB();
                    }
                } else {
                    /*runOnUiThread(() -> {
                        Intent intent = new Intent(MainActivity.this, DealerMainActivity.class);
                        startActivity(intent);
                    });*/
                }

            } catch (IOException | ParseException | JSONException e) {
                throw new RuntimeException(e);
            }



    }

    public static List<Car> getSoldList(int id,Context context) {
        List<Car> cars = new ArrayList<>();
        // 创建 POST 请求
        RequestBody formBody = new FormBody.Builder()
                .addEncoded("dealerId", Integer.toString(id))
                .build();

        Request request = new Request.Builder()
                .url("http://10.21.48.224:8080//car/blist")
                .post(formBody)
                .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                .build();
        // 发送请求

            try {
                OkHttpClient client = new OkHttpClient();
                Response response = client.newCall(request).execute();

                if (response.isSuccessful()) {
                    // 获取响应数据
                    String responseData = response.body().string();
                    Log.d("", responseData);
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
                    Log.d("====",cars.toString());
                    return cars;

                } else {
                    /*runOnUiThread(() -> {
                        Intent intent = new Intent(MainActivity.this, DealerMainActivity.class);
                        startActivity(intent);
                    });*/
                }

            } catch (IOException | ParseException | JSONException e) {
                throw new RuntimeException(e);
            }

        return cars;
    }

}
