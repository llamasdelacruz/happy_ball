package com.example.happy_ball;

import static kotlinx.coroutines.flow.FlowKt.drop;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Canvas;
import android.graphics.Matrix;

import java.util.ArrayList;

public class Bird extends BaseObject{
    //animar el ave
    private ArrayList<Bitmap> arrBms = new ArrayList<>();
    private int count, vFlap, idCurrentBitmap;
    private float drop;


    public Bird(float x, float y, int width, int height) {
        super(x, y, width, height);
        this.count = 0;
        this.vFlap = 5;
        this.idCurrentBitmap = 0;
        this.drop = 0;
    }

    public  void  draw(Canvas canvas){
        drop();
        canvas.drawBitmap(this.getBm(), this.x, this.y, null);
    }

    private void drop() {
        this.drop += 0.6;
        this.y += this.drop;
    }

    public ArrayList<Bitmap> getArrBms() {
        return arrBms;
    }

    public void setArrBms(ArrayList<Bitmap> arrBms) {
        //ajustamos la escala de las imagenes de el pajaro
        this.arrBms = arrBms;
        for (int i=0; i<arrBms.size(); i++){
            this.arrBms.set(i, Bitmap.createScaledBitmap(this.arrBms.get(i), this.width, this.height,true));

        }
    }

    @Override
    public Bitmap getBm() {
        count++;
        if(this.count == this.vFlap){
            for(int i = 0;i <arrBms.size(); i++){
                if(i == arrBms.size()-1){
                    this.idCurrentBitmap = 0;
                    break;

                }else if(this.idCurrentBitmap == i){
                    idCurrentBitmap = i+1;
                    break;
                }
            }
            count = 0;
        }
        if(this.drop < 0){
            Matrix matrix = new Matrix();
            matrix.postRotate(-25);
            return Bitmap.createBitmap(arrBms.get(idCurrentBitmap),0,0,arrBms.get(idCurrentBitmap).getWidth(),arrBms.get(idCurrentBitmap).getHeight(),matrix,true);
        }else if(drop >= 0){
            Matrix matrix = new Matrix();
            if(drop<70){
                matrix.postRotate(-25+(drop*2));
            }else {
                matrix.postRotate(45);
            }

            return Bitmap.createBitmap(arrBms.get(idCurrentBitmap),0,0,arrBms.get(idCurrentBitmap).getWidth(),arrBms.get(idCurrentBitmap).getHeight(),matrix,true);

        }
        //obtenemos el primer bitmap dle arreglo
        return this.arrBms.get(idCurrentBitmap);

    }

    public float getDrop() {
        return drop;
    }

    public void setDrop(float drop) {
        this.drop = drop;
    }
}
