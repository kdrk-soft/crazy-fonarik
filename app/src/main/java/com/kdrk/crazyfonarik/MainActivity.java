package com.kdrk.crazyfonarik;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;



public class MainActivity extends AppCompatActivity {
    private TextView textContent;
    private Typeface typeface;
    private Typeface typeface1;
    private Button button;
    private Button strobo;
    private FlashClass flashClass;
    private Boolean status = false;
    private byte count =0;
    private SeekBar seek;
    private final int MY_PERMISSIONS_REQUEST_CAMERA = 101;


   @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                // если пользователь закрыл запрос на разрешение, не дав ответа, массив grantResults будет пустым
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    flashClass = new FlashClass(this);
                    // разрешение было предоставлено
                    // выполните здесь необходимые операции для включения функциональности приложения, связанной с запрашиваемым разрешением
                } else  {


//                    Intent i = new Intent(MainActivity.this, MainActivity.class);
//                    startActivity(i);

                    //this.finish();
                   // finish();
                    // разрешение не было предоставлено
                    // выполните здесь необходимые операции для выключения функциональности приложения, связанной с запрашиваемым разрешением
                }

                return;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
            // разрешение не предоставлено
        }
        else {
            flashClass = new FlashClass(this);
            // разрешение предоставлено
        }

        init();
    }


    @SuppressLint("CutPasteId")
    private void init() {
        button = findViewById(R.id.button);
        strobo = findViewById(R.id.strobo);
        textContent = findViewById(R.id.textContent);
        seek = findViewById(R.id.seekBar);

        typeface = Typeface.createFromAsset(this.getAssets(), "fonts/Pacifico.ttf");
        typeface1 = Typeface.createFromAsset(this.getAssets(), "fonts/1.ttf");
        textContent.setTypeface(typeface);
        button.setTypeface(typeface1);


    }

    public void onClickFlash(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
            // разрешение не предоставлено
        } else {


            if (flashClass.isFlash_status()) {
                flashClass.flashOff();
                status = false;
               // button.setText(R.string.on);
                button.setBackgroundResource(R.drawable.off1);
                strobo.setText(R.string.stOn);
                strobo.setBackgroundResource(R.drawable.strobo_green);
            } else {
                status = false;
                flashClass.flashOn();
               // button.setText(R.string.off);
                button.setBackgroundResource(R.drawable.on1);
                strobo.setText(R.string.stOn);
                strobo.setBackgroundResource(R.drawable.strobo_green);

            }
        }
    }

    public void onClickStON(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
            // разрешение не предоставлено
        } else {

            if (!status) {
                flashClass.flashOff();
                status = true;
                strobo.setText(R.string.stOff);
                strobo.setBackgroundResource(R.drawable.strobo_red);
                //button.setText(R.string.on);
                button.setBackgroundResource(R.drawable.off1);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (status) {
                            count++;
                            switch (count) {
                                case 1:
                                    flashClass.flashOn();
                                    break;
                                case 2:
                                    flashClass.flashOff();
                                    count = 0;
                                    break;
                            }
                            try {
                                Thread.sleep(seek.getProgress());
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                }).start();
            } else {
                flashClass.flashOff();
                status = false;
               // button.setText(R.string.on);
                button.setBackgroundResource(R.drawable.off1);
                strobo.setText(R.string.stOn);
                strobo.setBackgroundResource(R.drawable.strobo_green);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        status = false;
        if(flashClass!=null){
        flashClass.flashOff();}
    }

}
