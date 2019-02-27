package com.allisonapps;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    //witget
    private TextView txtGuiatu;
    private TextView txtMascien;
    private EditText edtBusqueda;
    private ImageView imgBuscar;
    private ImageView imgBorrar;


    protected void onCreate(Bundle savedInstanceState) {
        // para realizar el splashScrem seteamos el Style Appthem.SpalshScremm en el Manifes
        // con la liena de codigo abajo volvemos al tema original de la aplicacion
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // linea para dar soporte a la creacion de graficos con vectores
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        // encontrar los witget de la activity main
        txtGuiatu = findViewById(R.id.txt_guiaturistica);
        txtMascien = findViewById(R.id.txt_mascien);
        edtBusqueda = findViewById(R.id.edt_busar);
        imgBuscar = findViewById(R.id.img_bucar);
        imgBorrar = findViewById(R.id.img_borrar);

        // seteamos el tipo de fuente de los texview
        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/johan.ttf");
        txtGuiatu.setTypeface(face);
        Typeface face1 = Typeface.createFromAsset(getAssets(), "fonts/johan.ttf");
        txtMascien.setTypeface(face1);

        imgBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarPalabraClave();
            }
        });

        // para que el seacher trabae por medio del edidtext
        edtBusqueda.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            buscarPalabraClave();
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });


    }

    private void buscarPalabraClave() {

        if (edtBusqueda.getText().toString().isEmpty()){
            Toast.makeText(this, "ingresa una articulo o local", Toast.LENGTH_SHORT).show(); return;}

            String palabraClave = edtBusqueda.getText().toString();

        Intent intent = new Intent(MainActivity.this,SujerenciasBusqueda.class);
        intent.putExtra("clave",palabraClave);
        startActivity(intent);

    }


}
