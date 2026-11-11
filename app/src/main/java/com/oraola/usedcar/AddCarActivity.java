package com.oraola.usedcar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.oraola.usedcar.bean.Car;
import com.oraola.usedcar.manager.CarAvaiableManager;
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

public class AddCarActivity extends AppCompatActivity {
    private String grade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);

        EditText engine_number = findViewById(R.id.engine_number);
        EditText brand = findViewById(R.id.brand);
        EditText model = findViewById(R.id.model);
        EditText price = findViewById(R.id.price);

        SharedPreferences sharedPreferences = getSharedPreferences("dealer_data", MODE_PRIVATE);
        int dealerId = sharedPreferences.getInt("dealerid", 0);

        Spinner level_spinner = findViewById(R.id.level_spinner);
        String[] levels = getResources().getStringArray(R.array.levels_array);

        level_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                grade = levels[position];
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

        });


        Button addCarBtn = findViewById(R.id.addCarBtn);



        addCarBtn.setOnClickListener(view -> {
            RequestBody formBody = new FormBody.Builder()
                    .addEncoded("dealerId", Integer.toString(dealerId))
                    .addEncoded("engineNumber", engine_number.getText().toString())
                    .addEncoded("brand", brand.getText().toString())
                    .addEncoded("model", model.getText().toString())
                    .addEncoded("grade", grade)
                    .addEncoded("acquisitionPrice", price.getText().toString())
                    .build();

            Request request = new Request.Builder()
                    .url("http://10.21.48.224:8080//car/addcar")
                    .post(formBody)
                    .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                    .build();
            Log.d("rrrrrrrrrrr",formBody.toString());
            Log.d("mmmmm",request.toString());

            try {
                OkHttpClient client = new OkHttpClient();
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    JSONObject responseJson = new JSONObject(responseData);
                    String msg = responseJson.getString("message");
                    if (msg.equals("ok")) {
                        GetCarList.getAvaiableList(dealerId, AddCarActivity.this);
                        runOnUiThread(() -> {
                            new Handler().postDelayed(() -> {
                                Intent intent = new Intent(AddCarActivity.this, DealerMainActivity.class);
                                startActivity(intent);
                                finish();
                            }, 500);
                        });
                    } else {
                        runOnUiThread(() -> Toast.makeText(AddCarActivity.this, "请重新确认", Toast.LENGTH_SHORT).show());
                    }
                }else {
                    Log.d("fail",response.toString());
                }
            } catch (IOException | JSONException e) {
                throw new RuntimeException(e);
            }
        });

        ImageView returnImg = findViewById(R.id.returnImg);
        returnImg.setOnClickListener(v -> finish());

    }
}