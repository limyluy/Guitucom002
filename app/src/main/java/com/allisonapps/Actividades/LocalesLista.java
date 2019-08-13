package com.allisonapps.Actividades;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Parcelable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.allisonapps.Adaptadores.AdaptadorLocal;
import com.allisonapps.Adaptadores.FavoritosLocalesAdaptador;
import com.allisonapps.Entidades.Locales;
import com.allisonapps.MainActivity;
import com.allisonapps.MapsActivity;
import com.allisonapps.R;
import com.allisonapps.SujerenciasBusqueda;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class LocalesLista extends AppCompatActivity {


    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1 ;



    // widgets
    private FloatingActionButton fabMapa;
    private String nombre;


    // variables para recucler
    private RecyclerView rcvLocales;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference reference = db.collection("locales");
    private AdaptadorLocal adaptador;
    private Context contex;
    private boolean favoritos = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_barlista);
        setContentView(R.layout.activity_locales_lista);


        // encontramos variables
        fabMapa = findViewById(R.id.fab_mapa_list_locales);

        // recuperamos lo que se va a buscar
        nombre = getIntent().getStringExtra("nombre");
        Log.e("nombre", nombre);
        getSupportActionBar().setTitle("Aqui Encontraras " + nombre);
        getSupportActionBar().setHomeButtonEnabled(true);

        contex = this;


        // boton para mapa
        fabMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<Locales> arrayList = adaptador.obtenerLocales();
                Intent intent = new Intent(LocalesLista.this, MapsActivity.class);
                intent.putExtra("nombre", nombre);
                MapsActivity.localesMapas = arrayList;
                startActivity(intent);

            }
        });

        // verifica si viene de favoritos para llenar un diferente adaptador
        if (nombre.equals("favoritos")) {
            favoritos = true;
            llenarrecyclerFavoritos();
        } else {
            llenarrecycler(conseguirQuery(nombre));
        }
    }

    private void llenarrecyclerFavoritos() {

        ArrayList<Locales> localesFavoritos = new ArrayList<>();
        SharedPreferences preferences = getSharedPreferences("Localesfavoritos", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString("favoritos", null);
        Type type = new TypeToken<ArrayList<Locales>>() {
        }.getType();

        localesFavoritos = gson.fromJson(json, type);


        if (localesFavoritos == null) {
            localesFavoritos = new ArrayList<>();
            Log.e("favoritos ", "bacio");

            Toast.makeText(contex, "No tienes aun locales favoritos", Toast.LENGTH_SHORT).show();
            return;

        }


        FavoritosLocalesAdaptador adaptador2 = new FavoritosLocalesAdaptador(contex, localesFavoritos);
        rcvLocales = findViewById(R.id.rcv_locales);
        rcvLocales.setHasFixedSize(true);
        rcvLocales.setLayoutManager(new LinearLayoutManager(this));
        rcvLocales.setAdapter(adaptador2);


    }

    private Query conseguirQuery(String nombre) {


        String categotia = preferenciaTraer();
        Query query;
        if (nombre.equals("todo")) {
            getSupportActionBar().setTitle("Todos los locales");
            query = reference.orderBy(categotia, Query.Direction.DESCENDING);
        } else {

            query = reference.whereArrayContains("productos", nombre).orderBy(categotia, Query.Direction.DESCENDING);
        }

        return query;
    }


    // metodo para llenar recicler desde firebase
    private void llenarrecycler(Query query) {


        FirestoreRecyclerOptions<Locales> options = new FirestoreRecyclerOptions.Builder<Locales>()
                .setQuery(query, Locales.class)
                .build();

        adaptador = new AdaptadorLocal(options, LocalesLista.this);

        rcvLocales = findViewById(R.id.rcv_locales);
        rcvLocales.setHasFixedSize(true);
        rcvLocales.setLayoutManager(new LinearLayoutManager(this));
        rcvLocales.setAdapter(adaptador);

        adaptador.setOnItemClickListener(new AdaptadorLocal.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                Locales local = documentSnapshot.toObject(Locales.class);
                Intent intent = new Intent(LocalesLista.this, VerLocalDetalle.class);


                Gson gson = new Gson();
                String json = gson.toJson(local);

                intent.putExtra("local", json);
                startActivity(intent);


            }
        });


    }

    // metodo para rescatar el orden en que prefiere mirar los resultados
    public String preferenciaTraer() {

        SharedPreferences prefs = getSharedPreferences("Preferencias", Context.MODE_PRIVATE);
        String categoria = prefs.getString("categoria", "atencion");

        return categoria;
    }

    public void preferenciasGuardar(String categoria) {

        SharedPreferences prefs = getSharedPreferences("Preferencias", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("categoria", categoria);
        editor.commit();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if (!favoritos) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_locales_lista, menu);

            return super.onCreateOptionsMenu(menu);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {

            case R.id.menu_main_locales:
                Toast.makeText(this, "menu locales", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menu_atencion:
                Query query = conseguirQueryCategorizar(nombre, "atencion");
                llenarrecycler(query);
                preferenciasGuardar("atencion");
                adaptador.startListening();
                break;

            case R.id.menu_calidad:
                Query query2 = conseguirQueryCategorizar(nombre, "calidad");
                llenarrecycler(query2);
                preferenciasGuardar("calidad");
                adaptador.startListening();
                break;

            case R.id.menu_precio:
                Query query3 = conseguirQueryCategorizar(nombre, "precio");
                llenarrecycler(query3);
                preferenciasGuardar("precio");
                adaptador.startListening();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    // metodo para ordenar los resultados a gusto del cliente
    private Query conseguirQueryCategorizar(String nombre, String categoria) {

        Query query;
        if (nombre.equals("todo")) {
            getSupportActionBar().setTitle("Todos los locales");
            query = reference.orderBy(categoria, Query.Direction.DESCENDING);
        } else {

            query = reference.whereArrayContains("productos", nombre).orderBy(categoria, Query.Direction.DESCENDING);
        }

        return query;

    }

    @Override
    public void onBackPressed() {

        // se juzga si viene de MainActivity para hacer un regreso optimo en la navegacion
        if (favoritos || nombre.equals("oferta") || nombre.equals("evento") || nombre.equals("lugar")) {
            startActivity(new Intent(LocalesLista.this, MainActivity.class));
        } else {
            Intent intent = new Intent(LocalesLista.this, SujerenciasBusqueda.class);
            intent.putExtra("clave", nombre);
            startActivity(intent);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!favoritos) adaptador.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (!favoritos) adaptador.stopListening();
    }
}
