package com.oraola.usedcar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AdministratorLoginActivity extends AppCompatActivity {

    private EditText administratornameEditText, passwordEditText;
    private Button loginButton;
    private TextView loginLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrator_login);

        // 初始化视图组件
        administratornameEditText = findViewById(R.id.administratorname);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        loginLink = findViewById(R.id.loginLink);

        // 设置登录按钮的点击事件
        loginButton.setOnClickListener(view -> {
            String administratorname = administratornameEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            // 验证用户输入
            if (TextUtils.isEmpty(administratorname) || TextUtils.isEmpty(password)) {
                Toast.makeText(AdministratorLoginActivity.this, "用户名和密码不能为空", Toast.LENGTH_SHORT).show();
            } else {
                // 这里可以调用后端接口，验证管理员的登录信息
                // 假设登录成功，进行提示并跳转到管理员界面
                // 在实际项目中，您需要将用户名和密码通过网络请求传到服务器，验证信息

                // 模拟登录成功
                Toast.makeText(AdministratorLoginActivity.this, "管理员登录成功！", Toast.LENGTH_SHORT).show();

                // 跳转到管理员主界面（例如管理车商、车辆等）
                // finish(); 结束当前登录界面
                // Intent intent = new Intent(AdministratorLoginActivity.this, AdminMainActivity.class);
                // startActivity(intent);
            }
        });

        loginLink.setOnClickListener(view -> {
            Intent intent = new Intent(AdministratorLoginActivity.this, DealerLoginActivity.class);
            startActivity(intent);
            finish();
        });

    }
}