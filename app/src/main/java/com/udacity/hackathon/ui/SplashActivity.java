package com.udacity.hackathon.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

import com.udacity.hackathon.R;
import com.udacity.hackathon.util.PrefUtils;

public class SplashActivity extends Activity {

    private Handler launchHandler = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        launchHandler = new Handler();
        launchHandler.postDelayed(nextlaunch, 3000);

    }

    Runnable nextlaunch = new Runnable() {
        @Override
        public void run() {
            launchActivity();
        }
    };


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        launchHandler.removeCallbacks(nextlaunch);
    }

    // 화면을 터치하면 로딩없이 바로 시작
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        launchHandler.removeCallbacks(nextlaunch);
        launchActivity();
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    private void launchActivity() {

        Intent intent = null;

        String name = PrefUtils.getString(getApplicationContext(), "name");
        if (null == name || "".equals(name)) {
            intent = new Intent(getApplicationContext(), NameInputActivity.class);
        } else {
            intent = new Intent(getApplicationContext(), WiFiDirectActivity.class);
        }
        startActivity(intent);
        finish();
    }
}
