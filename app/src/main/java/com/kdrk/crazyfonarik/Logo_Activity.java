package com.kdrk.crazyfonarik;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.Nullable;

public class Logo_Activity extends Activity {
    private Animation logoAnim;
    private ImageView logoImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
        logoAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.logo_anim);
        logoImage = findViewById(R.id.imageView3);
        logoImage.startAnimation(logoAnim);
        startLogo();
//nf



    }
    public void startLogo(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1200);
                    Intent i = new Intent(Logo_Activity.this, MainActivity.class);
                    startActivity(i);
                    onDestroy();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        }).start();

    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
