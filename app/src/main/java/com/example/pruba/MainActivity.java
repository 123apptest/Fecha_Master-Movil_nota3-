package com.example.pruba;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {

    private TextInputLayout tl_usuario,tl_pass;
    private Button btn_login,btn_register,btn_recupera,btn_salir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        referencia();
        eventos();
    }

    private void eventos() {
        btn_salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,RegistrarActividad.class);
                startActivity(i);

            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuario = tl_usuario.getEditText().getText().toString();
                String clave = tl_pass.getEditText().getText().toString();

                if (usuario.isEmpty() || clave.isEmpty()){
                    Toast.makeText(MainActivity.this, "Ingrese rut y clave", Toast.LENGTH_SHORT).show();
                }else {
                    Intent i = new Intent(MainActivity.this,FechaEventos.class);
                    startActivity(i);
                }

            }
        });
    }

    private void referencia() {
        tl_usuario = findViewById(R.id.tl_usuario);
        tl_pass = findViewById(R.id.tl_pass);
        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);
        btn_recupera = findViewById(R.id.btn_recupera);
        btn_salir = findViewById(R.id.btn_salir);

    }
}