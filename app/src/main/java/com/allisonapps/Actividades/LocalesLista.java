package com.allisonapps.Actividades;

import android.content.Intent;
import android.os.Parcelable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.allisonapps.Adaptadores.AdaptadorLocal;
import com.allisonapps.Entidades.Locales;
import com.allisonapps.MapsActivity;
import com.allisonapps.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;


public class LocalesLista extends AppCompatActivity {


    // widgets
    private FloatingActionButton fabMapa;



    RecyclerView rcvLocales;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference reference = db.collection("locales");



    AdaptadorLocal adaptador;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locales_lista);

        final String nombre = getIntent().getStringExtra("nombre");
        getSupportActionBar().setTitle("Aqui Encontraras " + nombre);

        fabMapa = findViewById(R.id.fab_mapa_list_locales);

        fabMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<Locales> arrayList = adaptador.obtenerLocales();
                Intent intent = new Intent(LocalesLista.this,MapsActivity.class);
                intent.putExtra("nombre",nombre);
                MapsActivity.localesMapas = arrayList;
                startActivity(intent);

            }
        });





        llenarrecycler();
    }



    private void llenarrecycler() {
        Query query = reference.orderBy("nombre", Query.Direction.DESCENDING);

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
                intent.putExtra("nombre", local.getNombre());
                intent.putExtra("imglocal", local.getImgLocal());
                intent.putExtra("imglogo", local.getImgLogo());
                intent.putExtra("telefono", local.getTelefono());
                intent.putExtra("color", local.getColor());
                intent.putExtra("ubicasion", local.getUbicacion().toString());
                intent.putExtra("direccion", local.getDireccion());
                intent.putExtra("actualizado", local.isActualizado());

                startActivity(intent);

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
          inflater.inflate(R.menu.menu_locales_lista,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id){

            case R.id.menu_main_locales:
                Toast.makeText(this, "menu locales", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adaptador.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adaptador.stopListening();
    }
}
