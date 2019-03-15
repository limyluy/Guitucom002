package com.allisonapps;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
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
