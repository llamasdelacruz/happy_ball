package com.example.happy_ball;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class imagen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imagen_activity);
        Bundle b = this.getIntent().getExtras();
        final TextView texto = (TextView)findViewById(R.id.textView);
        texto.setText(b.getString("NombreBolita"));

    }
}