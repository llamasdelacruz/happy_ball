package com.example.happy_ball;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
    public static Button btn_start, btn_salir;
    private GameView gv;
    private MediaPlayer mediaplayer ;

    private RelativeLayout relativeLayout;
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
        btn_salir = findViewById(R.id.btn_salir);
        relativeLayout = findViewById(R.id.ff);
        gv = findViewById(R.id.gv);

        intFondo();

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gv.setStart(true);
                text_score.setVisibility(View.VISIBLE);
                btn_start.setVisibility(View.INVISIBLE);
                btn_salir.setVisibility(View.INVISIBLE);
            }
        });

        rl_game_over.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_start.setVisibility(View.VISIBLE);
                btn_salir.setVisibility(View.VISIBLE);
                rl_game_over.setVisibility(View.INVISIBLE);
                gv.setStart(false);
                gv.reset();
            }
        });
        mediaplayer = MediaPlayer.create(this, R.raw.sillychipsong);
        mediaplayer.setLooping(true);
        mediaplayer.start();


    }



    public void intFondo(){


        try {
            AdminSQL admin = new AdminSQL(this, "usuario", null, 1);
            SQLiteDatabase bd = admin.getWritableDatabase();
            Cursor fila =bd.rawQuery("select fondo from usuario where id = 'U1'", null);

            if (fila.moveToFirst()) {

                int fondo = fila.getInt(0);

                relativeLayout.setBackgroundResource(fondo);
            }
            if (fila != null) {
                fila.close();
            }


            bd.close();
        }catch (Exception e){
            System.out.println(e);
        }

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

    public void  regresar(View view){
        finish();
    }



}
