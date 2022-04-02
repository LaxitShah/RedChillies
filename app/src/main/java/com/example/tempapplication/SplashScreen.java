package com.example.tempapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {
    private static int timer=6000;


    ImageView imageView1,imageView2;
    TextView textView1;
    Animation upanimations,topanimations,downanimations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        imageView1 = findViewById(R.id.logo1);
        imageView2 = findViewById(R.id.logo2);

        textView1 = findViewById(R.id.welcomes);


        upanimations = AnimationUtils.loadAnimation(this,R.anim.up_animation);
        topanimations = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        downanimations = AnimationUtils.loadAnimation(this,R.anim.down_animation);

        imageView1.setAnimation(upanimations);
        imageView2.setAnimation(topanimations);
        textView1.setAnimation(downanimations);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this,Welcome_Button.class);
                startActivity(intent);
                finish();
            }
        },timer);

    }
    }
