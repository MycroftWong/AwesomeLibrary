package com.mycroft.loginaction.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.mycroft.loginaction.R;
import com.mycroft.loginaction.action.SingleCall;

/**
 * @author mycroft
 */
public class DiscountActivity extends AppCompatActivity {
    public static void start(Activity activity) {
        Intent intent = new Intent(activity, DiscountActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount_activivty);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserConfigCache.setDiscount(DiscountActivity.this, true);
                //这里继续
                SingleCall.getInstance().doCall();
                finish();
            }
        });
    }
}
