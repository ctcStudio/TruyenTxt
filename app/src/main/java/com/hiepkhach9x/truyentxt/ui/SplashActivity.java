package com.hiepkhach9x.truyentxt.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;

import com.hiepkhach9x.truyentxt.R;

/**
 * Created by HungHN on 2/20/2016
 */
public class SplashActivity extends AppCompatActivity {

    private ProgressBar mLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash_activity);
        mLoading = (ProgressBar) findViewById(R.id.loading);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mLoading.setVisibility(View.GONE);
                        Intent intent = new Intent(SplashActivity.this,
                                MainActivity.class);
                        SplashActivity.this.startActivity(intent);
                        SplashActivity.this.finish();
                    }
                });
            }
        }, 1000);
    }
}
