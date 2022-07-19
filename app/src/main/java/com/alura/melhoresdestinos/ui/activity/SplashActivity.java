package com.alura.melhoresdestinos.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.alura.melhoresdestinos.R;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity implements Runnable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mudarCorDaStatusBar();

        setContentView(R.layout.activity_splash);

        Handler splashScreen = new Handler();
        splashScreen.postDelayed(SplashActivity.this, 1000);

    }

    private void mudarCorDaStatusBar() {

        getWindow().setStatusBarColor(ResourcesCompat
                .getColor(getResources(),
                        R.color.marrom, getTheme()));
    }

    //o que vai acontecer após os 3 segundos do  splashScreen
    @Override
    public void run() {

        Intent intent = new Intent(SplashActivity.this,
                ListaPacotesActivity.class);
        startActivity(intent);
        finish();

    }
}