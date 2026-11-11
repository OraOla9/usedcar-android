package com.oraola.usedcar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DealerRegisterActivity extends AppCompatActivity {

    private EditText dealernameEditText, passwordEditText, confirmPasswordEditText;
    private Button registerButton;
    private TextView dealerLoginLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealer_register);

        dealernameEditText = findViewById(R.id.dealername);
        passwordEditText = findViewById(R.id.password);
        confirmPasswordEditText = findViewById(R.id.confirmPassword);
        registerButton = findViewById(R.id.registerButton);
        dealerLoginLink = findViewById(R.id.dealerLoginLink);

        registerButton.setOnClickListener(view -> {
            String dealername = dealernameEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            String confirmPassword = confirmPasswordEditText.getText().toString().trim();

            // 验证用户输入
            if (TextUtils.isEmpty(dealername) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)) {
                Toast.makeText(DealerRegisterActivity.this, "所有字段都不能为空", Toast.LENGTH_SHORT).show();
            } else if (!password.equals(confirmPassword)) {
                Toast.makeText(DealerRegisterActivity.this, "密码和确认密码不匹配", Toast.LENGTH_SHORT).show();
            } else {
                regist(dealername,password);
            }
        });

        dealerLoginLink.setOnClickListener(view -> {
            Intent intent = new Intent(DealerRegisterActivity.this, DealerLoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void regist(String dealername,String password){
        RequestBody formBody = new FormBody.Builder()
                .addEncoded("dealername",dealername)
                .addEncoded("password",password)
                .build();

        Request request = new Request.Builder()
                .url("http://10.21.48.224:8080//dealer/registe")
                .post(formBody)
                .build();

        new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient();
                Response response = client.newCall(request).execute();
                if(response.isSuccessful()){
                    String responseData = response.body().string();
                    Log.d("aaaaaaaaaaa",responseData);

                    JSONObject responseJson = new JSONObject(responseData);
                    String dealernameResponse = null;
                    String passwordResponse = null;
                    int id = 0; 
                    String msg;
                    if(responseJson.has("dealername")) dealernameResponse = responseJson.getString("dealername");
                    if(responseJson.has("dealerpassword")) passwordResponse = responseJson.getString("dealerpassword");
                    if(responseJson.has("id")) id = responseJson.getInt("id");
                    if(responseJson.has("message")) msg = responseJson.getString("message");
                    else {
                        msg = "ok";
                    }
                    if (msg.equals("ok")) {

                        SharedPreferences sharedPreferences = getSharedPreferences("dealer_data", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("dealername", dealernameResponse);
                        editor.putString("dealerpassword", passwordResponse);
                        editor.putInt("dealerid", id);
                        editor.apply();

                        runOnUiThread(() -> {
                            Intent intent = new Intent(DealerRegisterActivity.this, DealerMainActivity.class);
                            startActivity(intent);
                            finish();
                        });
                    } else {
                        runOnUiThread(() -> Toast.makeText(DealerRegisterActivity.this, msg, Toast.LENGTH_SHORT).show());
                    }
                }else {
                    runOnUiThread(() -> Toast.makeText(DealerRegisterActivity.this, "请求失败请重试", Toast.LENGTH_SHORT).show());
                }

            } catch (IOException | JSONException e) {
                throw new RuntimeException(e);
            }
        }).start();


    }
}
