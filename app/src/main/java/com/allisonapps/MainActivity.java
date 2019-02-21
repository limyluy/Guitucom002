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

    TextView txtIpiales;



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // para realizar el splashScrem seteamos el Style Appthem.SpalshScremm en el Manifes
        // con la liena de codigo abajo volvemos al tema original de la aplicacion
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        txtIpiales = findViewById(R.id.txt_ipiales);

        Typeface face = Typeface.createFromAsset(getAssets(),"fonts/fantastic.ttf");
        txtIpiales.setTypeface(face);





    }

}
