package com.oraola.usedcar;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.oraola.usedcar.bean.Car;
import com.oraola.usedcar.helper.CarAvaiableHelper;
import com.oraola.usedcar.manager.CarAvaiableManager;
import com.oraola.usedcar.manager.CarSoldManager;
import com.oraola.usedcar.util.GetCarList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CarViewActivity extends AppCompatActivity {

    private ImageView returnImg, gradeImg;
    private TextView price, create_time, brand;
    private Button sellBtn;
    private Car car;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_view);

        returnImg = findViewById(R.id.returnImg);
        gradeImg = findViewById(R.id.gradeImg);
        price = findViewById(R.id.price);
        create_time = findViewById(R.id.create_time);
        brand = findViewById(R.id.brand);
        sellBtn = findViewById(R.id.sellBtn);


        int carId = getIntent().getIntExtra("carId", -1);

        loadCarDetails(carId);

        sellBtn.setOnClickListener(view -> {

            Dialog dialog = new Dialog(CarViewActivity.this);
            dialog.setContentView(R.layout.dialog_car_sold);

            EditText price = dialog.findViewById(R.id.price);

            DatePicker datePicker = dialog.findViewById(R.id.stime);
            datePicker.setMinDate(car.getAcquisitionDate().getTime());
            String date = datePicker.getYear() + "-" + (datePicker.getMonth() + 1) + "-" + datePicker.getDayOfMonth();
            Button btn = dialog.findViewById(R.id.btn_submit);

            btn.setOnClickListener(view1 -> {
                RequestBody formBody = new FormBody.Builder()
                        .addEncoded("carId", Integer.toString(carId))
                        .addEncoded("salePrice", price.getText().toString())
                        .addEncoded("saleDate", date)
                        .build();

                Request request = new Request.Builder()
                        .url("http://10.21.48.224:8080//car/update")
                        .post(formBody)
                        .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                        .build();

                try {
                    OkHttpClient client = new OkHttpClient();
                    Response response = client.newCall(request).execute();

                    if (response.isSuccessful()) {
                        String responseData = response.body().string();
                        JSONObject responseJson = new JSONObject(responseData);
                        String msg = responseJson.getString("message");
                        if (msg.equals("ok")){
                            SharedPreferences sharedPreferences = getSharedPreferences("dealer_data", MODE_PRIVATE);
                            int dealerId = sharedPreferences.getInt("dealerid", 0);
                            GetCarList.getAvaiableList(dealerId,CarViewActivity.this);
                            runOnUiThread(() -> {
                                new Handler().postDelayed(() -> {
                                    Intent intent = new Intent(CarViewActivity.this, DealerMainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }, 500);
                            });

                        }else {
                            runOnUiThread(() -> Toast.makeText(CarViewActivity.this, "请重新确认", Toast.LENGTH_SHORT).show());
                        }
                    }

                } catch (IOException | JSONException e) {
                    throw new RuntimeException(e);
                }


            });

            Button cancelBtn = dialog.findViewById(R.id.btn_cncle);

            cancelBtn.setOnClickListener(view2 -> {
                dialog.dismiss();
            });

            dialog.show();

        });


        returnImg.setOnClickListener(v -> finish());
    }

    private void loadCarDetails(int carId) {
        new Thread(() -> {
            CarAvaiableManager db = new CarAvaiableManager(CarViewActivity.this);
            car = db.findAvailableCarByCarId(carId);
            if (db != null) {
                db.closeDB();
            }

            runOnUiThread(() -> {
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

                    brand.setText(car.getBrand() + car.getModel());

                }
            });
        }).start();
    }
}