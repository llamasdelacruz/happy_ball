package com.example.happy_ball;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Skins extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.skins);

        final Button btn_roja = (Button)findViewById(R.id.btn_roja);
        final Button btn_azul = (Button)findViewById(R.id.btn_azul);
        final Button btn_verde = (Button)findViewById(R.id.btn_verde);
        final Button btn_jeff = (Button)findViewById(R.id.btn_jeff);


        btn_roja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nueva_actividad =new Intent(Skins.this,imagen.class);
                Bundle nombre_bolita  = new Bundle();
                nombre_bolita.putString("NombreBolita","bolita roja");
                nueva_actividad.putExtras(nombre_bolita);

                startActivity(nueva_actividad);
            }
        });
        btn_azul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nueva_actividad =new Intent(Skins.this,imagen.class);
                Bundle nombre_bolita  = new Bundle();
                nombre_bolita.putString("NombreBolita","bolita azul");
                nueva_actividad.putExtras(nombre_bolita);

                startActivity(nueva_actividad);
            }
        });

        btn_verde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nueva_actividad =new Intent(Skins.this,imagen.class);
                Bundle nombre_bolita  = new Bundle();
                nombre_bolita.putString("NombreBolita","bolita verde");
                nueva_actividad.putExtras(nombre_bolita);

                startActivity(nueva_actividad);
            }
        });

        btn_jeff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nueva_actividad =new Intent(Skins.this,imagen.class);
                Bundle nombre_bolita  = new Bundle();
                nombre_bolita.putString("NombreBolita","has elegido a jeff");
                nueva_actividad.putExtras(nombre_bolita);

                startActivity(nueva_actividad);
            }
        });
    }



}