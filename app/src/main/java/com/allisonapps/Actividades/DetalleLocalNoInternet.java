package com.allisonapps.Actividades;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.allisonapps.Entidades.Locales;
import com.allisonapps.R;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class DetalleLocalNoInternet extends AppCompatActivity {


    // Widget
    private TextView txtNomLocal;
    private TextView txtDesLocal;
    private TextView txtDirLocal;
    private TextView txtTelLocal;
    private ImageView imgLogoLocal;
    private ImageView imgTarLocal;
    private ImageView imgParLocal;
    private ImageView imgGarLocal;
    private RecyclerView rcvProductosLocal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_bar);
        setContentView(R.layout.activity_detalle_local_no_internet);

        obtenerWidget();
        Locales local = recuperarLocal();
        llenarLocal(local);

    }

    private void llenarLocal(Locales local) {


        Uri myUri = (Uri.parse(local.getImgLogo()));
        imgLogoLocal.setImageURI(myUri);


        txtNomLocal.setText(local.getNombre());
        txtDesLocal.setText(local.getDescripcion());
        txtDirLocal.setText(local.getDireccion());
        txtTelLocal.setText(local.getTelefono());



        imgTarLocal.setVisibility(local.isTarjeta() ? View.VISIBLE : View.INVISIBLE);
        imgParLocal.setVisibility(local.isGaraje() ? View.VISIBLE : View.INVISIBLE);
        imgGarLocal.setVisibility(local.isGarantia() ? View.VISIBLE : View.INVISIBLE);


    }

    private void obtenerWidget() {

        txtNomLocal = findViewById(R.id.txt_nom_no_inter);
        txtDesLocal = findViewById(R.id.txt_des_no_inter);
        txtDirLocal = findViewById(R.id.txt_dir_no_inter);
        txtTelLocal = findViewById(R.id.txt_tel_no_inter);

        imgLogoLocal = findViewById(R.id.img_logo_no_inter);

        imgTarLocal = findViewById(R.id.img_tar_no_inter);
        imgGarLocal = findViewById(R.id.img_gar_no_inter);
        imgParLocal = findViewById(R.id.img_par_no_inter);

        rcvProductosLocal = findViewById(R.id.rcv_pro_no_inter);

    }

    public Locales recuperarLocal() {

        Locales local;

        String localString = getIntent().getStringExtra("LocalNoInternet");
        Gson gson = new Gson();
        Type type = new TypeToken<Locales>() {
        }.getType();
        local = gson.fromJson(localString, type);

        return local;
    }
}
