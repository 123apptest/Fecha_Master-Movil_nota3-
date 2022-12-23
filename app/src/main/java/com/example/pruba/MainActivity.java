package com.example.pruba;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextInputLayout tl_usuario,tl_pass,tl_pregunta,tl_contesta;
    private Button btn_login,btn_register,btn_recupera,btn_salir;

    private ArrayList<Usuario> losUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pobla();
        referencia();
        eventos();
    }

    private void pobla() {
        losUsuario = new ArrayList<Usuario>();
        losUsuario.add(new Usuario("123", "123", "1+1","2"));
        losUsuario.add(new Usuario("321", "321", "1-1","0"));

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
                String rut, clave,pregunta, contesta ;
                boolean rutOK = true;

                rut = tl_usuario.getEditText().getText().toString();
                clave = tl_pass.getEditText().getText().toString();
                pregunta = tl_pregunta.getEditText().getText().toString();
                contesta = tl_contesta.getEditText().getText().toString();

                for(Usuario c : losUsuario){
                    if(c.getRut().equals(rut)) {
                        rutOK = false;
                        break;
                    }
                }
                if(rut.isEmpty() || clave.isEmpty() || pregunta.isEmpty() || contesta.isEmpty()){
                    tl_usuario.setError("Ingrese todos datos");
                }else {
                    if(rutOK) {
                        Usuario u = new Usuario(rut, clave, pregunta, contesta);
                        losUsuario.add(u);

                        grabarBaseDatos(u);


                        Toast.makeText(MainActivity.this, "Registrar exitosamente", Toast.LENGTH_SHORT).show();


                        Intent i = new Intent(MainActivity.this,FechaEventos.class);
                        startActivity(i);
                    }else{
                        tl_usuario.setError("Rut ya está ingresado");

                    }

                }


            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rut, clave;
                boolean claveOK = false;

                rut = tl_usuario.getEditText().getText().toString();
                clave = tl_pass.getEditText().getText().toString();

                for(Usuario c : losUsuario){
                    if(c.getRut().equals(rut) && c.getClave().equals(clave)) {
                        claveOK = true ;
                        break;
                    }
                }




                if (rut.isEmpty() || clave.isEmpty()){
                    Toast.makeText(MainActivity.this, "Ingrese rut y clave", Toast.LENGTH_SHORT).show();
                }else {
                    if (claveOK){
                        Toast.makeText(MainActivity.this, "Bienvenido ", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(MainActivity.this,FechaEventos.class);
                        startActivity(i);
                    }else {
                        Toast.makeText(MainActivity.this, "Clave no correcta", Toast.LENGTH_SHORT).show();
                    }



                }

            }
        });


        btn_recupera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rut, clave, pregunta,contesta;
                boolean recupera = false;

                rut = tl_usuario.getEditText().getText().toString();
                clave = tl_pass.getEditText().getText().toString();
                pregunta = tl_pregunta.getEditText().getText().toString();
                contesta = tl_contesta.getEditText().getText().toString();

                for(Usuario c : losUsuario){
                    if(c.getRut().equals(rut) && c.getPregunta().equals(pregunta) && c.getContesta().equals(contesta)) {
                        recupera = true ;
                        c.setClave(clave);
                        break;
                    }
                }
                if (rut.isEmpty() || pregunta.isEmpty() || contesta.isEmpty()){
                    Toast.makeText(MainActivity.this, "Ingrese rut y clave", Toast.LENGTH_SHORT).show();
                }else {
                    if (recupera){

                        Toast.makeText(MainActivity.this, "se cambia su calve ", Toast.LENGTH_SHORT).show();

                    }else {
                        Toast.makeText(MainActivity.this, "Clave no cambia", Toast.LENGTH_SHORT).show();
                    }



                }


            }
        });
    }

    private void grabarBaseDatos(Usuario u) {
        try{
            adminBaseDatos adbd = new adminBaseDatos(this, "BDAplicacion", null, 1);
            SQLiteDatabase miBD = adbd.getWritableDatabase();

            //Forma android
            ContentValues reg = new ContentValues();
            reg.put("rut", u.getRut());
            reg.put("clave", u.getClave());
            reg.put("pregunta", u.getPregunta());
            reg.put("contesta", u.getContesta());


            miBD.insert("usuario", null, reg);


            miBD.close();
        }catch (Exception ex){
            Log.e("TAG_", ex.toString());
        }

    }


    private void referencia() {
        tl_usuario = findViewById(R.id.tl_usuario);
        tl_pass = findViewById(R.id.tl_pass);
        tl_pregunta = findViewById(R.id.tl_pregunta);
        tl_contesta = findViewById(R.id.tl_contesta);
        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);
        btn_recupera = findViewById(R.id.btn_recupera);
        btn_salir = findViewById(R.id.btn_salir);

    }
}