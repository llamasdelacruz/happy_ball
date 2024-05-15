package com.example.happy_ball;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Skins extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.skins);

    }


    public void seleccionar_flappy(View view){
        AdminSQL admin = new AdminSQL(this, "usuario", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        ContentValues registro = new ContentValues();
        registro.put("bird1", R.drawable.bird);
        registro.put("bird2", R.drawable.bird2);

        int cant = bd.update("usuario", registro, "id ='U1'", null);
        bd.close();

        if (cant == 1) {
            Toast.makeText(this, "Se ha seleccionado la skin de flappy bird", Toast.LENGTH_SHORT)
                    .show();

        }

    }

    public void seleccionar_amarillo(View view){
        AdminSQL admin = new AdminSQL(this, "usuario", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        ContentValues registro = new ContentValues();
        registro.put("bird1", R.drawable.bird_amarilla1);
        registro.put("bird2", R.drawable.bird_amarilla2);

        int cant = bd.update("usuario", registro, "id ='U1'", null);
        bd.close();

        if (cant == 1) {
            Toast.makeText(this, "Se ha seleccionado la skin del pajaro amarillo", Toast.LENGTH_SHORT)
                    .show();

        }

    }


    public void seleccionar_borracho(View view){
        AdminSQL admin = new AdminSQL(this, "usuario", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        ContentValues registro = new ContentValues();
        registro.put("bird1", R.drawable.bird_cafe1);
        registro.put("bird2", R.drawable.bird_cafe2);

        int cant = bd.update("usuario", registro, "id ='U1'", null);
        bd.close();

        if (cant == 1) {
            Toast.makeText(this, "Se ha seleccionado la skin del borrachito", Toast.LENGTH_SHORT)
                    .show();

        }

    }


    public void seleccionar_jeff(View view){
        AdminSQL admin = new AdminSQL(this, "usuario", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        ContentValues registro = new ContentValues();
        registro.put("bird1", R.drawable.jeff1);
        registro.put("bird2", R.drawable.jeff2);

        int cant = bd.update("usuario", registro, "id ='U1'", null);
        bd.close();

        if (cant == 1) {
            Toast.makeText(this, "Se ha seleccionado la skin de jeff", Toast.LENGTH_SHORT)
                    .show();

        }

    }

    public void  regresar(View view){
        finish();
    }





}