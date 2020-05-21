package com.example.locatorproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.example.locatorproject.R;

public class SplashActivity extends AppCompatActivity {
    private ImageView image;
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_splash);
        this.image = (ImageView) findViewById(R.id.image);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(2000);

        alphaAnimation.setFillAfter(true);
        RotateAnimation animation = new RotateAnimation(0f, 360f);

        ImageView imageView = this.image;
        image.animate().rotation(2160f).setDuration(3000);
        ImageView imageView2 = this.image;
        imageView.setVisibility(View.INVISIBLE);
        this.image.startAnimation(alphaAnimation);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle((CharSequence) "");

        new Thread() {
            public void run() {
                try {
                    sleep(2500);
                    SplashActivity.this.startActivity(new Intent( SplashActivity.this, MainActivity.class));
                    SplashActivity.this.finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}

