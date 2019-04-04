package com.allisonapps.Actividades;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.allisonapps.Adaptadores.AdaptadorLocal;
import com.allisonapps.Entidades.Locales;
import com.allisonapps.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class LocalesLista extends AppCompatActivity {

    RecyclerView rcvLocales;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference reference  = db.collection("locales");

    AdaptadorLocal adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locales_lista);

        llenarrecycler();
    }

    private void llenarrecycler() {
        Query query = reference.orderBy("nombre", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<Locales> options = new FirestoreRecyclerOptions.Builder<Locales>()
                .setQuery(query,Locales.class)
                .build();

        adaptador = new AdaptadorLocal(options,LocalesLista.this);

        rcvLocales = findViewById(R.id.rcv_locales);
        rcvLocales.setHasFixedSize(true);
        rcvLocales.setLayoutManager(new LinearLayoutManager(this));
        rcvLocales.setAdapter(adaptador);

        adaptador.setOnItemClickListener(new AdaptadorLocal.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                Locales local = documentSnapshot.toObject(Locales.class);
                Intent intent = new Intent(LocalesLista.this,VerLocalDetalle.class);
                intent.putExtra("nombre",local.getNombre());
                intent.putExtra("imglocal",local.getImgLocal());
                intent.putExtra("imglogo",local.getImgLogo());
                intent.putExtra("telefono",local.getTelefono());
                intent.putExtra("color",local.getColor());
                intent.putExtra("ubicasion",local.getUbicacion().toString());
                intent.putExtra("direccion",local.getDireccion());
                intent.putExtra("actualizado",local.getActualizado());
                startActivity(intent);
            }
        });



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