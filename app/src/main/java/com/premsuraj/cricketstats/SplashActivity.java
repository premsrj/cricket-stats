package com.premsuraj.cricketstats;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.premsuraj.cricketstats.base.MainActivity;
import com.premsuraj.cricketstats.startup.StartupItem;
import com.premsuraj.cricketstats.startup.StartupManager;
import com.premsuraj.cricketstats.startup.StartupManagerCallback;

import java.util.ArrayList;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new StartupManager().start(new StartupManagerCallback() {
            @Override
            public void onFinishedProcessing(ArrayList<StartupItem> passedItems, ArrayList<StartupItem> failedItems) {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
