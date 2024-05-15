package com.example.happy_ball;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.app.Activity;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class GameView extends View{
    private Bird bird;
    private Handler handler;
    private Runnable r;
    private ArrayList<Pipe> arrPipes;
    //espacio entre los tubos, el de abajo y arriba
    private int sumpipe, distance;
    private  int score,bestscore;
    private boolean start;
    private Context context;
    private int soundJump;
    private float volume;
    private boolean loadedsound;
    private SoundPool soundpool;
    private int bird1,bird2,fondo;

   public GameView(Context context, @Nullable AttributeSet attrs){
       super(context, attrs);
       score = 0;
       this.context = context;
       buscar();

       start = false;
       initBird();
       initPipe();
       handler = new Handler();
       r = new Runnable() {
           @Override
           public void run() {
               //pa que actualize el metodo
               invalidate();
           }
       };
       //sonidos
       if(Build.VERSION.SDK_INT >=21){
           AudioAttributes audioAttributes = new AudioAttributes.Builder()
                   .setUsage(AudioAttributes.USAGE_GAME)
                   .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                   .build();
           SoundPool.Builder builder = new SoundPool.Builder();
           builder.setAudioAttributes(audioAttributes).setMaxStreams(5);
           this.soundpool = builder.build();
       }else{
           soundpool = new SoundPool(5, AudioManager.STREAM_MUSIC,0);

       }
       this.soundpool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
           @Override
           public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
               loadedsound = true;
           }
       });

       soundJump = this.soundpool.load(context,R.raw.jump_02,1);
   }
   public void initPipe(){
        sumpipe = 6;
        distance = 300*Constants.SCREEN_HEIGHT/1920;
        arrPipes = new ArrayList<>();
       //posiciones iniciales de las pipas
       for (int i = 0; i <sumpipe;i++){
            if(i<sumpipe/2){
                //inicializa las posiciones de los tubos
                this.arrPipes.add(new Pipe(Constants.SCREEN_WIDTH+i*((float) (Constants.SCREEN_WIDTH + 200 * Constants.SCREEN_WIDTH / 1080) /((float) sumpipe /2)),
                        0,200*Constants.SCREEN_WIDTH/1080,Constants.SCREEN_HEIGHT/2));
                this.arrPipes.get(this.arrPipes.size()-1).setBm(BitmapFactory.decodeResource(this.getResources(),R.drawable.toptube));
                this.arrPipes.get(this.arrPipes.size()-1).randomY();
            }else{
                this.arrPipes.add(new Pipe(this.arrPipes.get(i-sumpipe/2).getX(),this.arrPipes.get(i-sumpipe/2).getY()
                + this.arrPipes.get(i-sumpipe/2).getHeight() + this.distance, 200*Constants.SCREEN_WIDTH/1080,Constants.SCREEN_HEIGHT/2));
                this.arrPipes.get(this.arrPipes.size()-1).setBm(BitmapFactory.decodeResource(this.getResources(), R.drawable.bottomtube));

            }
       }

   }
   public void initBird(){
       float x,y;
       int width,height;

       width = 90*Constants.SCREEN_WIDTH/1080;
       height = 90*Constants.SCREEN_HEIGHT/1920;
       x = (float) (100 * Constants.SCREEN_WIDTH) /1080;
       y = (float) Constants.SCREEN_HEIGHT /2- (float) height /2;


       bird = new Bird(x,y,width,height);

       //para el pajaro
       ArrayList<Bitmap> arrBms = new ArrayList<>();
       arrBms.add(BitmapFactory.decodeResource(this.getResources(),bird1));
       arrBms.add(BitmapFactory.decodeResource(this.getResources(),bird2));

       bird.setArrBms(arrBms);
   }

   public  void draw(Canvas canvas){
        super.draw(canvas);
        if(start){

            //render el pajaro en la vista de juego
            bird.draw(canvas);

            // si los tubos se salen de la pantalla reseteas la posicion
            for(int i = 0; i <sumpipe;i++){
                //colisiones
                if(bird.getRect().intersect(arrPipes.get(i).getRect()) || bird.getY()-bird.getHeight() < 0 || bird.getY()>Constants.SCREEN_HEIGHT){
                    Pipe.speed = 0;

                    if(score>bestscore){
                        bestscore = score;
                        editar(bestscore);
                    }
                    StartGame.txt_score_over.setText(StartGame.text_score.getText());
                    StartGame.txt_best_score.setText("Best: "+ bestscore);

                    StartGame.text_score.setVisibility(INVISIBLE);
                    StartGame.rl_game_over.setVisibility(VISIBLE);
                }

                //incrementar score
                if(this.bird.getX()+this.bird.getWidth()> arrPipes.get(i).getX()+arrPipes.get(i).getWidth()/2
                        && this.bird.getX()+this.bird.getWidth()<=arrPipes.get(i).getX()+arrPipes.get(i).getWidth()/2+Pipe.speed
                        && i <sumpipe/2){
                    score++;

                    StartGame.text_score.setText(""+score);

                }
                //-------------------
                if(this.arrPipes.get(i).getX() < -arrPipes.get(i).getWidth()){
                    this.arrPipes.get(i).setX(Constants.SCREEN_WIDTH);
                    if(i < sumpipe/2){
                        arrPipes.get(i).randomY();

                    }else{
                        arrPipes.get(i).setY(this.arrPipes.get(i-sumpipe/2).getY()
                                + this.arrPipes.get(i-sumpipe/2).getHeight() + this.distance);
                    }

                }
                this.arrPipes.get(i).draw(canvas);




            }

        }else{
            if(bird.getY()>Constants.SCREEN_HEIGHT/2){
                bird.setDrop(-15*Constants.SCREEN_HEIGHT/1920);

            }
            bird.draw(canvas);
        }

        //cuanto se tarda en actualizar
        handler.postDelayed(r,10);

   }

   // que se mueva el pajaro en cuanto toque la pantalla
    @Override
    public boolean onTouchEvent(MotionEvent event) {
       if(event.getAction() == MotionEvent.ACTION_DOWN){
            bird.setDrop(-15);
            if(loadedsound){
                int streamId = this.soundpool.play(this.soundJump,(float)1,(float)1,1,0,1f);
            }
       }
        return super.onTouchEvent(event);
    }

    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public void reset() {
       StartGame.text_score.setText("0");
       score=0;
        initPipe();
       initBird();

    }


    // edita el score para guardar el mas alto
    public void editar(int score){
        AdminSQL admin = new AdminSQL(this.context, "usuario", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        ContentValues registro = new ContentValues();
        registro.put("score", String.valueOf(score));

        int cant = bd.update("usuario", registro, "id ='U1'", null);
        bd.close();

    }

    public void buscar(){

        String nombre, puntuacion;

        try {
            AdminSQL admin = new AdminSQL(this.context, "usuario", null, 1);
            SQLiteDatabase bd = admin.getWritableDatabase();
            Cursor fila =bd.rawQuery("select score,bird1,bird2,fondo from usuario where id = 'U1'", null);

            if (fila.moveToFirst()) {
                String score = fila.getString(0);
                bird1 = fila.getInt(1);
                bird2 = fila.getInt(2);
                fondo = fila.getInt(3);


                bestscore = Integer.parseInt(score);
            }
            if (fila != null) {
                fila.close();
            }


            bd.close();
        }catch (Exception e){
            System.out.println(e);
        }


    }
}
