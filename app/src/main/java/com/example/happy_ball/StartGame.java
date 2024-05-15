package com.example.happy_ball;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class StartGame extends AppCompatActivity {
    GameView gameView;
    public static TextView text_score,txt_best_score,txt_score_over;
    public static RelativeLayout rl_game_over;
    public static Button btn_start;
    private GameView gv;
    private MediaPlayer mediaplayer ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        Constants.SCREEN_WIDTH = dm.widthPixels;
        Constants.SCREEN_HEIGHT = dm.heightPixels;
        setContentView(R.layout.juego_activity);
        text_score = findViewById(R.id.txt_score);
        txt_best_score = findViewById(R.id.txt_best_score);
        txt_score_over = findViewById(R.id.txt_score_over);
        rl_game_over = findViewById(R.id.rl_game_over);
        btn_start = findViewById(R.id.btn_start);
        gv = findViewById(R.id.gv);


        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gv.setStart(true);
                text_score.setVisibility(View.VISIBLE);
                btn_start.setVisibility(View.INVISIBLE);
            }
        });

        rl_game_over.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_start.setVisibility(View.VISIBLE);
                rl_game_over.setVisibility(View.INVISIBLE);
                gv.setStart(false);
                gv.reset();
            }
        });
        mediaplayer = MediaPlayer.create(this, R.raw.sillychipsong);
        mediaplayer.setLooping(true);
        mediaplayer.start();


    }
    @Override
    public void onResume(){
        super.onResume();
        mediaplayer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaplayer.pause();
    }
}
