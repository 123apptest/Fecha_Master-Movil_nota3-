package com.example.pruba;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;

public class RegistrarActividad extends AppCompatActivity {

    private Button btn_volver,btn_registrar;
    private TextInputLayout tl_usuario,tl_pass,tl_pregunta,tl_Contesta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_actividad);

        referencia();
        eventos();
    }

    private void referencia() {
        btn_volver = findViewById(R.id.btn_volver);
        btn_registrar = findViewById(R.id.btn_registrar);
        tl_usuario = findViewById(R.id.tl_usuario);
        tl_pass = findViewById(R.id.tl_pass);
        tl_pregunta = findViewById(R.id.tl_pregunta);
        tl_Contesta = findViewById(R.id.tl_Contesta);
    }

    private void eventos() {

        btn_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}