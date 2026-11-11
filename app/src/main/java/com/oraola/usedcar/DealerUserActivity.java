package com.oraola.usedcar;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

public class DealerUserActivity extends AppCompatActivity {

    private LinearLayout received,sold,addCar,statistics,profile;
    private TextView userName;
    private Button btnEditProfile, btnLogout;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealer_user);

        received = findViewById(R.id.nav_received);
        sold = findViewById(R.id.nav_sold);
        addCar = findViewById(R.id.nav_add_car);
        statistics = findViewById(R.id.nav_statistics);
        profile = findViewById(R.id.nav_profile);


        received.setOnClickListener(view -> {
            finish();
        });


        // 获取控件实例
        userName = findViewById(R.id.user_name);
        btnEditProfile = findViewById(R.id.btn_edit_profile);
        btnLogout = findViewById(R.id.btn_logout);

        // 设置用户信息（这里可以从数据库或者本地存储加载数据）
        sharedPreferences = getSharedPreferences("dealer_data", MODE_PRIVATE);
        userName.setText(sharedPreferences.getString("dealername", ""));


        // 编辑个人信息按钮点击事件
        btnEditProfile.setOnClickListener(v -> {
            // 跳转到编辑个人信息的页面
            Intent intent = new Intent(DealerUserActivity.this, DealerEditActivity.class);
            startActivity(intent);
            finish();
        });

        // 退出登录按钮点击事件
        btnLogout.setOnClickListener(v -> {
            // 清除登录状态，退出登录
            logout();
        });
    }

    private void logout() {
        // 清除用户登录信息
        // 比如清除SharedPreferences或Session中的数据
        SharedPreferences preferences = getSharedPreferences("dealer_data", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("dealername");
        editor.remove("dealerpassword");
        editor.remove("dealerid");
        editor.apply();
        // 跳转到登录页面
        Intent intent = new Intent(DealerUserActivity.this, DealerLoginActivity.class);
        startActivity(intent);
        finish();  // 关闭当前页面
    }
}