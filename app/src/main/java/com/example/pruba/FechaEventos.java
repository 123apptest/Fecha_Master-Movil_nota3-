package com.example.pruba;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class FechaEventos extends AppCompatActivity {

    private EditText et_titulo,et_evento,et_lugar;
    private Spinner spnImport,spnObser,spnTiempo;
    private Button btnGrabar, btnEliminar, btnRetroceder, btnAvanzar;
    private TextView tvPag;

    private String[]  Importancia;
    private ArrayAdapter adapterImportancia;

    private String[]  observacion;
    private ArrayAdapter adapterObservacion;

    private String[]  tiempo;
    private ArrayAdapter adapterTiempo;

    private ArrayList<Fecha> losFecha;

    private int indiceActual = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fecha_eventos);

        poblar();
        referencias();
        eventos();
        obtenerDatos();
    }

    private void eventos() {
        btnGrabar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grabar();
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               eliminar();
            }
        });

        btnAvanzar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                indiceActual = indiceActual + 1;

                if(indiceActual == losFecha.size()) {
                    indiceActual = 0;
                }

                obtenerDatos();
            }
        });

        btnRetroceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                indiceActual = indiceActual - 1;

                if(indiceActual == -1)
                    indiceActual = losFecha.size() - 1;

                obtenerDatos();
            }
        });
    }

    private void eliminar() {




        AlertDialog.Builder alert = new AlertDialog.Builder(FechaEventos.this);
        alert.setMessage("Desa eliminar datos?").setCancelable(false)
                .setPositiveButton("si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(indiceActual >= 0 && indiceActual < losFecha.size()) {
                            losFecha.remove(indiceActual);
                            limpiarPantalla();
                        }
                    }
                })
                .setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alert.create();
        alertDialog.setTitle("Eliminar datos");
        alertDialog.show();


    }


    private void grabar() {
        String titulo, evento, lugar,importancia = "",observacion = "",tiempo = "";


        titulo = et_titulo.getText().toString();
        evento = et_evento.getText().toString();
        lugar = et_lugar.getText().toString();
        importancia = spnImport.getSelectedItem().toString();
        observacion = spnObser.getSelectedItem().toString();
        tiempo = spnTiempo.getSelectedItem().toString();


            if(titulo.isEmpty() || evento.isEmpty() ||lugar.isEmpty() || spnImport.getSelectedItemPosition() == 0 ||spnObser.getSelectedItemPosition() == 0 ||spnTiempo.getSelectedItemPosition() == 0){
                et_titulo.setError("Ingrese complecto datos");
            }else {

                    Fecha fe = new Fecha(titulo, evento, lugar,importancia,observacion,tiempo);
                    losFecha.add(fe);

                    grabarBaseDatos(fe);

                    tvPag.setText((indiceActual + 1) + " de " + losFecha.size());
                    Toast.makeText(FechaEventos.this, "Grabado exitosamente", Toast.LENGTH_SHORT).show();
                    limpiarPantalla();

            }

        }



    private void grabarBaseDatos(Fecha fe) {
        try{
            adminFechaBD adbd = new adminFechaBD(this, "BDAplicacion", null, 1);
            SQLiteDatabase miBD = adbd.getWritableDatabase();

            ContentValues reg = new ContentValues();
            reg.put("titulo", fe.getTitulo());
            reg.put("evento", fe.getEvento());
            reg.put("lugar", fe.getLugar());


            miBD.insert("fecha", null, reg);

            miBD.close();
        }catch (Exception ex){
            Log.e("TAG_", ex.toString());
        }

        consultaSQL();
    }

    private void consultaSQL() {
        adminFechaBD adbd = new adminFechaBD(this, "BDAplicacion", null, 1);
        SQLiteDatabase miBD = adbd.getWritableDatabase();
        try {
            Cursor c = miBD.rawQuery("Select * from fecha order by titulo desc", null);
            if(c.moveToFirst()){
                Log.d("TAG_","Registros recuperados " + c.getCount());
                do{
                    Log.d("TAG_", "titulo " + c.getString(0) +
                            ", evento " + c.getString(1) +
                            ", lugar " + c.getString(2));
                }while(c.moveToNext());
            }
        }catch (Exception ex){
            Log.e("TAG_", ex.toString());
        }finally {
            miBD.close();
        }
    }

    private void limpiarPantalla() {
        et_titulo.setText(""); et_evento.setText(""); et_lugar.setText("");
        spnImport.setSelection(0);spnObser.setSelection(0);spnTiempo.setSelection(0);
        et_titulo.setError(null);
        tvPag.setText("" + losFecha.size());
        indiceActual = -1;

    }

    private void obtenerDatos() {
        if(indiceActual >= 0 && indiceActual < losFecha.size()) {
            Fecha f = losFecha.get(indiceActual);
            et_titulo.setText(f.getTitulo());
            et_evento.setText(f.getEvento());
            et_lugar.setText(f.getLugar());


            if(f.getImportancia().equals("Muy Importante")) spnImport.setSelection(1);
            if(f.getImportancia().equals("Importante")) spnImport.setSelection(2);
            if(f.getImportancia().equals("No Importante")) spnImport.setSelection(3);

            if(f.getObservacion().equals("cumpleaños")) spnObser.setSelection(1);
            if(f.getObservacion().equals("aniversarios")) spnObser.setSelection(2);
            if(f.getObservacion().equals("conmemoraciones")) spnObser.setSelection(3);
            if(f.getObservacion().equals("compromisos")) spnObser.setSelection(4);

            if(f.getTiempo().equals("1 hora")) spnTiempo.setSelection(1);
            if(f.getTiempo().equals("2 horas")) spnTiempo.setSelection(2);
            if(f.getTiempo().equals("5 horas")) spnTiempo.setSelection(3);

            tvPag.setText((indiceActual + 1) + " de " + losFecha.size());
        }
    }

    private void referencias() {
        et_titulo = findViewById(R.id.et_titulo);
        et_evento = findViewById(R.id.et_evento);
        et_lugar = findViewById(R.id.et_lugar);
        spnImport = findViewById(R.id.spnImport);
        spnObser = findViewById(R.id.spnObser);
        spnTiempo = findViewById(R.id.spnTiempo);
        btnGrabar = findViewById(R.id.btnGrabar);
        btnEliminar = findViewById(R.id.btnEliminar);
        btnRetroceder = findViewById(R.id.btnRetroceder);
        btnAvanzar = findViewById(R.id.btnAvanzar);
        tvPag = findViewById(R.id.tvPag);

        adapterImportancia = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Importancia);
        spnImport.setAdapter(adapterImportancia);

        adapterObservacion = new ArrayAdapter(this, android.R.layout.simple_spinner_item, observacion);
        spnObser.setAdapter(adapterObservacion);

        adapterTiempo = new ArrayAdapter(this, android.R.layout.simple_spinner_item, tiempo);
        spnTiempo.setAdapter(adapterTiempo);
    }

    private void poblar() {
        Importancia = new String[] {"Selecione nievel de importancia","Muy Importante", "Importante", "No Importante"};
        observacion = new String[] {"Selecione tipo de fecha", "cumpleaños", "aniversarios","conmemoraciones","compromisos"};
        tiempo = new String[] {"Selecione tiempo de aviso antes", "1 hora", "2 horas","5 horas"};

        losFecha = new ArrayList<Fecha>();
        losFecha.add(new Fecha("Ejemplo 1:Navidad", "hace fiesta con familia", "las condes","Muy Importante","conmemoraciones","1 hora"));
        losFecha.add(new Fecha("Ejemplo 2:Año nuevo", "junta con amigos", "santiago","Importante","aniversarios","5 horas"));

    }
}