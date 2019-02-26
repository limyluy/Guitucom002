package com.allisonapps;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toolbar;

public class SujerenciasBusqueda extends AppCompatActivity {

    private Toolbar tooSuperior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sujerencias_busqueda);
        getSupportActionBar();

        tooSuperior = findViewById(R.id.barra_busqueda);



    }
}
