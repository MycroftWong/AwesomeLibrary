package com.mycroft.loginaction.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mycroft.loginaction.R;
import com.mycroft.loginaction.action.SingleCall;

public class LoginActivity extends AppCompatActivity {

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activivty);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                UserConfigCache.setLogin(LoginActivity.this, true);
                // 这里继续
                SingleCall.getInstance().doCall();
                finish();
            }
        });
    }
}
