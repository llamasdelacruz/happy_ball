package com.example.happy_ball;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.lang.reflect.Array;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    public EditText lab_nombre, lab_score;
    public Button b_editar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lab_nombre = findViewById(R.id.LblNombre);
        lab_score = findViewById(R.id.TxtScore);

        String nombre, score;

        ArrayList r = (ArrayList) buscar();

        nombre = (String) r.get(0);
        score = (String) r.get(1);


        if(!nombre.equals("Escribe tu nombre")){
            lab_nombre.setText(nombre);
            lab_score.setText(score);

        }


    }

    //esta funcion es la que decide si actualiza o registra
    public void evento(View v){

        String nombre;

        ArrayList r = (ArrayList) buscar();

        nombre = (String) r.get(0);

        if(nombre.equals("Escribe tu nombre")){
            guardar();
        }

        else{
            //Toast.makeText(this,"se supone que aquí entra a editar", Toast.LENGTH_SHORT).show();
            editar();
        }


    }
    //esta funcion es para cuando el usuario se registra por primera vez
    public void guardar(){
    AdminSQL admin = new AdminSQL(this, "usuario", null, 1);
    SQLiteDatabase bd = admin.getWritableDatabase();
    String nombre = lab_nombre.getText().toString();

    ContentValues registro = new ContentValues();

    registro.put("id", "U1");
    registro.put("nombre", nombre);
    registro.put("score", 0);
    registro.put("bird1",R.drawable.bird);
    registro.put("bird2",R.drawable.bird2);
    registro.put("fondo",R.drawable.bg);

    bd.insert("usuario", null, registro);
    bd.close();
    Toast.makeText(this,"Registrado con éxito", Toast.LENGTH_SHORT).show();

    }

    public List <String> buscar(){
        List<String> resultado = new ArrayList<>();
        String nombre, puntuacion;
        AdminSQL admin = new AdminSQL(this, "usuario", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        Cursor fila =bd.rawQuery("select nombre, score from usuario where id = 'U1'", null);

        if(fila.moveToFirst()){
            nombre = fila.getString(0);
            int score = fila.getInt(1);
            puntuacion = String.valueOf(score);
            resultado.add(nombre);
            resultado.add(puntuacion);

        }

        else{
            resultado.add("Escribe tu nombre");
            resultado.add("0");
        }

        return resultado;
    }

    public void borrar(){
        String nombre;
        AdminSQL admin = new AdminSQL(this, "usuario", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        bd.execSQL("delete from usuario");
        bd.close();

    }

    public void editar(){
        AdminSQL admin = new AdminSQL(this, "usuario", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        String nombre = lab_nombre.getText().toString();

        ContentValues registro = new ContentValues();
        registro.put("nombre", nombre);

        int cant = bd.update("usuario", registro, "id ='U1'", null);
        bd.close();

        if (cant == 1) {
            Toast.makeText(this, "Datos actualizados correctamente", Toast.LENGTH_SHORT)
                    .show();

            lab_nombre.setText(nombre);
        }

    }

    public void startGame(View view){

        Intent intent = new Intent(this, StartGame.class);
        startActivityForResult(intent, 1);
    }

    public void verSkins(View view){
        Intent intent = new Intent(this, Skins.class);
        startActivity(intent);
        // no le puse finish para que pudiera regresar a la actividad anterior

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            // Cuando regreses de StartGame, consulta el nuevo score de la base de datos
            AdminSQL admin = new AdminSQL(this, "usuario", null, 1);
            SQLiteDatabase bd = admin.getWritableDatabase();

            Cursor fila =bd.rawQuery("select score from usuario where id = 'U1'", null);

            if(fila.moveToFirst()){

                int score = fila.getInt(0);
                String puntuacion = String.valueOf(score);
                lab_score.setText(puntuacion);

            }
        }
    }



}