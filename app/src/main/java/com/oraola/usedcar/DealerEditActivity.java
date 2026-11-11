package com.oraola.usedcar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.oraola.usedcar.util.GetCarList;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DealerEditActivity extends AppCompatActivity {

    private EditText dealerNameEditText;
    private EditText dealerPasswordEditText;
    private Button saveButton;
    private SharedPreferences sharedPreferences;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealer_edit);

        dealerNameEditText = findViewById(R.id.dealerName);
        dealerPasswordEditText = findViewById(R.id.dealerpassword);
        saveButton = findViewById(R.id.saveBtn);
        ImageView returnImage = findViewById(R.id.returnImg);

        sharedPreferences = getSharedPreferences("dealer_data", MODE_PRIVATE);
        id = sharedPreferences.getInt("dealerid", 0);
        Log.d("aaaaaa",Integer.toString(id));
        String dealername = sharedPreferences.getString("dealername", "");
        String password = sharedPreferences.getString("dealerpassword", "");
        dealerNameEditText.setText(dealername);
        dealerPasswordEditText.setText(password);

        saveButton.setOnClickListener(v -> {
            String newDealerName = dealerNameEditText.getText().toString();
            String newDealerPassword = dealerPasswordEditText.getText().toString();
            if(!newDealerPassword.equals(password) || !newDealerName.equals(dealername)){
                dealerEdit(newDealerName, newDealerPassword);
            }
        });

        returnImage.setOnClickListener(v -> finish());
    }

    private void dealerEdit(String dealername, String password) {
        // 创建 POST 请求
        RequestBody formBody = new FormBody.Builder()
                .addEncoded("dealername", dealername)
                .addEncoded("password", password)
                .addEncoded("id", Integer.toString(id))
                .build();

        Request request = new Request.Builder()
                .url("http://10.21.48.224:8080//dealer/edit") // 替换为实际的 Servlet URL
                .post(formBody)
                .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                .build();
        // 发送请求
        new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient();
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    // 获取响应数据
                    String responseData = response.body().string();
                    Log.d("aaa", responseData);
                    JSONObject responseJson = new JSONObject(responseData);
                    String msg = responseJson.getString("message");

                    if (msg.equals("ok")) {
                        sharedPreferences = getSharedPreferences("dealer_data", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("dealername", dealername);
                        editor.putString("dealerpassword", password);
                        editor.apply();
                        runOnUiThread(() -> {
                            Intent intent = new Intent(DealerEditActivity.this, DealerMainActivity.class);
                            startActivity(intent);

                            finish();
                        });
                    } else {
                        runOnUiThread(() -> Toast.makeText(DealerEditActivity.this, msg, Toast.LENGTH_SHORT).show());
                    }


                } else {
                    runOnUiThread(() -> Toast.makeText(DealerEditActivity.this, "请求失败请重试", Toast.LENGTH_SHORT).show());
                }

            } catch (IOException | JSONException e) {
                throw new RuntimeException(e);
                /*e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(DealerLoginActivity.this, "请求失败，请重试", Toast.LENGTH_SHORT).show());*/
            }
        }).start();
    }
}