package org.techtown.mydiarytop;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class IntroActivity extends AppCompatActivity {

    LinearLayout backgroundIntro;
    Button introButton;
    private Handler handler;





    Runnable runnable = new Runnable() {
        @Override
        public void run() {

            Intent intent = new Intent(IntroActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        SoundPool soundPool = new SoundPool(5,AudioManager.STREAM_MUSIC,0);	//작성
        int soundID = soundPool.load(this,R.raw.start,1);

        init();

        backgroundIntro = (LinearLayout) findViewById(R.id.backgroundIntro);
        introButton = (Button) findViewById(R.id.introButton);


        introButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soundPool.play(soundID,1f,1f,0,0,1f);	//작성
                handler.postDelayed(runnable, 100);
            }
        });

    }

    public void init() {
        handler = new Handler();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        handler.removeCallbacks(runnable);
    }

}



