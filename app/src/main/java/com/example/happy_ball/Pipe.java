package com.example.happy_ball;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.Random;

public class Pipe extends BaseObject{

    public static int speed;

    public Pipe(float x, float y, int width, int height) {
        super(x, y, width, height);
        speed = 10*Constants.SCREEN_WIDTH/1080;

    }
    //dibuja los tubos que se mueven
    public void draw(Canvas canvas){
        //las mueve de derecha a izquierda
        this.x-=speed;
        //posicion de el tubo desendiendo en la direccion x
        canvas.drawBitmap(this.bm, this.x,this.y, null);

    }
    public  void randomY(){
        Random r = new Random();
        this.y = r.nextInt((0+this.height/3)+1)- (float) this.height /3 ;
    }
    //coloca el bitmap altura y largo igual a la altura y el largo del tubo

    @Override
    public void setBm(Bitmap bm) {
        this.bm = Bitmap.createScaledBitmap(bm,width,height,true);

    }
}
