package com.example.happy_ball;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.app.Activity;

import androidx.annotation.NonNull;

import java.util.Random;


public class GameView extends View{

    Handler handler; //handler es requerido se corre despues de la espera
    Runnable runnable;
    Bitmap background;
    Bitmap toptube, bottomtube;
    final int UPDATE_MILLIS=20;
    Display display;
    Point point;
    int dWith, dHeight; // dispositivo con largo y ancho
    Rect rect;
    //un bitmap array para la bolita
    Bitmap[] bolitas;
    // necesitamos el interno para ver la imagen de la bolita/frame
    int bolitaFrame = 0;
    int velocity=0,gravity=3;
    //para ver la posicion de la tabla
    int bolitaX, bolitaY;
    boolean gameState = false;
    int gap = 400;// espacio entre el tubo de arriba y el tubo de abajo
    int minTubeOffset, maxTubeOffset;
    int numberOfTubes = 4;
    int distanceBetweenTubes;
    int[] tubeX = new int[numberOfTubes];
    int[] topTubeY = new int[numberOfTubes];
    Random random;
    int tubeVelocity = 8;
    public GameView(Context context){
        super(context);
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                invalidate();// va a llamar a dibujar
            }
        };
        background = BitmapFactory.decodeResource(getResources(),R.drawable.bg);
        toptube = BitmapFactory.decodeResource(getResources(), R.drawable.toptube);
        bottomtube = BitmapFactory.decodeResource(getResources(), R.drawable.bottomtube);

        display = ((Activity)getContext()).getWindowManager().getDefaultDisplay();
        point = new Point();
        display.getSize(point);
        dWith = point.x;
        dHeight = point.y;
        rect = new Rect(0,0,dWith,dHeight);

        bolitas = new Bitmap[2];
        bolitas[0] = BitmapFactory.decodeResource(getResources(),R.drawable.bird);
        bolitas[1] = BitmapFactory.decodeResource(getResources(),R.drawable.bird2);

        bolitaX = dWith/2 - bolitas[0].getWidth()/2;//inicialmente va a estar en el centro
        bolitaY = dHeight/2 - bolitas[0].getHeight()/2;

        distanceBetweenTubes = dWith*3/4; // nuestra asuncion
        minTubeOffset = gap/2;
        maxTubeOffset = dHeight - minTubeOffset - gap;


        random = new Random();
        for(int i=0; i<numberOfTubes; i++){
            tubeX[i] = dWith + i*distanceBetweenTubes;
            topTubeY[i] = minTubeOffset + random.nextInt(maxTubeOffset- minTubeOffset + 1);
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // dibuja la vista
        //canvas.drawBitmap(background,0,0, null);
        canvas.drawBitmap(background,null,rect, null);
        if(bolitaFrame == 0){
            bolitaFrame = 1;
        }else{
            bolitaFrame = 0;
        }
        if(gameState){
            // la bolita debe estar en la pantalla
            if(bolitaY < dHeight - bolitas[0].getHeight() ||  velocity < 0){//asi no se baja hasta mas abajo de la pantalla
                velocity += gravity;// como la bolita cae,  se hace mas rapido y mas rapido con la velocidad el valor incrementa por gravedad
                bolitaY += velocity;
            }

            for (int i =0; i<numberOfTubes;i++){
                tubeX[i] -= tubeVelocity;
                if(tubeX[i] < -toptube.getWidth()){
                    tubeX[i] += numberOfTubes * distanceBetweenTubes;
                    topTubeY[i] = minTubeOffset + random.nextInt(maxTubeOffset- minTubeOffset + 1);

                }
                canvas.drawBitmap(toptube,tubeX[i], topTubeY[i] - toptube.getHeight(),null);
                canvas.drawBitmap(bottomtube,tubeX[i], topTubeY[i]+gap,null);
            }
        }

               // para que la bolita este en el centro de la pantalla
        // dos bolitas[] deben tener las mismas dimensiones
        canvas.drawBitmap(bolitas[bolitaFrame],bolitaX, bolitaY , null);
        handler.postDelayed(runnable,UPDATE_MILLIS);

    }
    //obtner el evento touch


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if(action == MotionEvent.ACTION_DOWN){ // El tap se detecta en la pantalla
            //aqui queremos que la bolita suba por unidad
            velocity = -30;//vamos a decir las 30 unidades en dirección contraria
            gameState = true;

        }
        return super.onTouchEvent(event);//cuando tocamos las cosas y no mas acciones
    }
}
