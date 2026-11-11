package com.oraola.usedcar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/*
 * 车商登录
 * */
public class DealerLoginActivity extends AppCompatActivity {

    private EditText dealernameEditText, passwordEditText;
    private Button loginButton;
    private TextView registerLink, administratorLoginLink;
    private SharedPreferences sharedPreferences;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealer_login);

        dealernameEditText = findViewById(R.id.dealername);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        registerLink = findViewById(R.id.registerLink);
        administratorLoginLink = findViewById(R.id.administratorLoginLink);

        //登录
        loginButton.setOnClickListener(view -> {
            String dealername = dealernameEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (dealername.isEmpty() || password.isEmpty()) {
                Toast.makeText(DealerLoginActivity.this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
            } else {
                login(dealername, password);
            }
        });

        // 设置注册链接的点击事件
        registerLink.setOnClickListener(view -> {
            // 跳转到注册页面
            Intent intent = new Intent(DealerLoginActivity.this, DealerRegisterActivity.class); // 请替换 RegisterActivity 为注册页面
            startActivity(intent);
            finish();
        });

        // 设置管理员登录链接的点击事件
        administratorLoginLink.setOnClickListener(view -> {
            // 跳转到管理员登录页面
            Intent intent = new Intent(DealerLoginActivity.this, AdministratorLoginActivity.class); // 请替换 AdministratorLoginActivity 为管理员登录页面
            startActivity(intent);
            finish();
        });
    }

    private void login(String dealername, String password) {

        // 创建 POST 请求
        RequestBody formBody = new FormBody.Builder()
                .addEncoded("dealername", dealername)
                .addEncoded("password", password)
                .build();

        Request request = new Request.Builder()
                .url("http://10.21.48.224:8080//dealer/login") // 替换为实际的 Servlet URL
                .post(formBody)
                .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                .build();
        // 发送请求

            try {
                OkHttpClient client = new OkHttpClient();
                Response response = client.newCall(request).execute();
                Log.d("bbbbbbbbb", response.toString());
                if (response.isSuccessful()) {
                    // 获取响应数据
                    String responseData = response.body().string();
                    Log.d("", responseData);
                    /*JSONArray jsonArray = new JSONArray(responseData);

                    for(int i = 0;i <jsonArray.length();i++){
                        // 从响应中提取数据*/
                    JSONObject responseJson = new JSONObject(responseData);
                    String dealernameResponse = null;
                    String passwordResponse = null;
                    id = 0;
                    String msg;
                    if (responseJson.has("dealername"))
                        dealernameResponse = responseJson.getString("dealername");
                    if (responseJson.has("dealerpassword"))
                        passwordResponse = responseJson.getString("dealerpassword");
                    if (responseJson.has("id")) id = responseJson.getInt("id");
                    if (responseJson.has("message")) msg = responseJson.getString("message");
                    else {
                        msg = "ok";
                    }

                    Log.d("a", dealernameResponse + passwordResponse + id + msg);

                    if (msg.equals("ok")) {

                        sharedPreferences = getSharedPreferences("dealer_data", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("dealername", dealernameResponse);
                        editor.putString("dealerpassword", passwordResponse);
                        editor.putInt("dealerid", id);
                        editor.apply();

                        GetCarList.getAvaiableList(id,DealerLoginActivity.this);
//                        GetCarList.getSoldList(id,DealerLoginActivity.this);
//                        getAvaiableList(id,DealerLoginActivity.this);
//                        getSoldList(id,DealerLoginActivity.this);

                        Log.d(";;;","]]]]");
                        runOnUiThread(() -> {
                            new Handler().postDelayed(() -> {
                                Intent intent = new Intent(DealerLoginActivity.this, DealerMainActivity.class);
                                startActivity(intent);
                                finish();
                            }, 500);
                        });
                    } else {
                        runOnUiThread(() -> Toast.makeText(DealerLoginActivity.this, msg, Toast.LENGTH_SHORT).show());
                    }


                } else {
                    runOnUiThread(() -> Toast.makeText(DealerLoginActivity.this, "请求失败请重试", Toast.LENGTH_SHORT).show());
                }

            } catch (IOException | JSONException e) {
                throw new RuntimeException(e);
                /*e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(DealerLoginActivity.this, "请求失败，请重试", Toast.LENGTH_SHORT).show());*/
            }
    }

}
