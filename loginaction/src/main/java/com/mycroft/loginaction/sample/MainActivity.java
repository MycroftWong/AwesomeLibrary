package com.mycroft.loginaction.sample;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.mycroft.loginaction.R;
import com.mycroft.loginaction.action.Action;
import com.mycroft.loginaction.action.SingleCall;
import com.mycroft.loginaction.sample.valid.DiscountValid;
import com.mycroft.loginaction.sample.valid.LoginValid;

public class MainActivity extends AppCompatActivity implements Action {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingleCall.getInstance()
                        .addAction(MainActivity.this)
                        .addValid(new LoginValid(MainActivity.this))
                        .doCall();
            }
        });

        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingleCall.getInstance()
                        .addAction(MainActivity.this)
                        .addValid(new LoginValid(MainActivity.this))
                        .addValid(new DiscountValid(MainActivity.this))
                        .doCall();

            }
        });

        findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserConfigCache.setLogin(MainActivity.this, false);
            }
        });
        findViewById(R.id.logoutDis).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserConfigCache.setDiscount(MainActivity.this, false);
            }
        });
    }

    @Override
    public void call() {
        OrderDetailActivity.startActivity(MainActivity.this, "1234");
    }
}
