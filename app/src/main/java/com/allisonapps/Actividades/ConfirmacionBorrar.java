package com.allisonapps.Actividades;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.QuickContactBadge;
import android.widget.TextView;
import android.widget.Toast;

import com.allisonapps.Entidades.Locales;
import com.allisonapps.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ConfirmacionBorrar extends AppCompatActivity {

    private ArrayList<Locales> localesLista;
    private TextView txtPregunta;
    private TextView txtBorrar;
    private TextView txtCancelar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmacion_borrar);

        recuperarWidget();
        localesLista = recuperarListFavoritos();
        final int position = recuperarPosition();
        llenarPregunta(position);

        txtBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                borrarLocal(position);
            }
        });

        txtCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConfirmacionBorrar.this, LocalesLista.class);
                intent.putExtra("nombre", "favorito");
                startActivity(intent);

            }
        });

    }

    // metodo para llenar la pregunra con el nombre del local
    private void llenarPregunta(int position) {
        String nombre = localesLista.get(position).getNombre();
        txtPregunta.setText("¿Desea borrar " + nombre + " de su lista de locales favoritos?");

    }

    // metodo para setear lso widgets
    private void recuperarWidget() {

        txtPregunta = findViewById(R.id.txt_pregunta_alert);
        txtBorrar = findViewById(R.id.txt_borrar_alert);
        txtCancelar = findViewById(R.id.txt_cancelar_alert);
    }

    // metodo para borrar el local y actualizar el arrayList en share preference
    private void borrarLocal(int position) {
        String nombre = localesLista.get(position).getNombre();
        localesLista.remove(position);


        SharedPreferences preferences = getSharedPreferences("Localesfavoritos", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(localesLista);
        editor.putString("favoritos", json);
        editor.apply();
        Toast.makeText(this, "Local " + nombre + " borrado", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(ConfirmacionBorrar.this, LocalesLista.class);
        intent.putExtra("nombre", "favorito");
        startActivity(intent);


    }

    // metodo para recuperar la posicion del local marcado en el arraylist
    private int recuperarPosition() {
        int position = getIntent().getIntExtra("position", 0);
        return position;
    }


    // metodo para recuperar el arrayList de locales favoritos
    private ArrayList<Locales> recuperarListFavoritos() {

        String stringFavoritos = getIntent().getStringExtra("favoritos");
        ArrayList<Locales> localesFavoritos = new ArrayList<>();
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Locales>>() {
        }.getType();
        localesFavoritos = gson.fromJson(stringFavoritos, type);

        return localesFavoritos;
    }
}
