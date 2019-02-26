package com.allisonapps;

import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    //witget
    private TextView txtGuiatu;
    private TextView txtMascien;


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

        // seteamos el tipo de fuente de los texview
        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/johan.ttf");
        txtGuiatu.setTypeface(face);
        Typeface face1 = Typeface.createFromAsset(getAssets(), "fonts/johan.ttf");
        txtMascien.setTypeface(face1);


    }


}
