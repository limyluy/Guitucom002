package com.allisonapps;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.algolia.instantsearch.core.helpers.Searcher;
import com.algolia.instantsearch.ui.helpers.InstantSearch;
import com.algolia.instantsearch.ui.utils.ItemClickSupport;
import com.algolia.instantsearch.ui.views.Hits;
import com.allisonapps.Actividades.LocalesLista;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.security.Key;

public class SujerenciasBusqueda extends AppCompatActivity {

    //witget
    private Toolbar tooSuperior;
    private EditText edtClave;
    private TextView txtSujerencia;
    private TextView txtVerTodo;
    private ImageView imgIconAtras;
    private ImageView imgIconoIr;


    //   private EditText edtNuevaBusqueda;
    private ProgressBar proSujerencias;

    //vasriables para Algolia
    private Searcher searcher;
    private InstantSearch helper;
    private Hits hits;

    //variables estatica para Algolia
    final static String ALGOLIA_APP_ID = "LXY84IU2VY";
    final static String ALGOLIA_SEARCH_API_KEY = "7edbbd1e0f88f3b1176f3440ad018d3f";
    final static String ALGOLIA_INDEX_NAME = "productos";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sujerencias_busqueda);
        getSupportActionBar().hide();

        // encontramos los witgets del activity SujerenciasBusqueda
        tooSuperior = findViewById(R.id.crv_barra_busqueda);
        edtClave = findViewById(R.id.txt_clave);
        proSujerencias = findViewById(R.id.probra_suje);
        hits = findViewById(R.id.hits);
        imgIconAtras = findViewById(R.id.img_ic_back);
        imgIconoIr = findViewById(R.id.img_buscar_ir_sujerencias);
        txtVerTodo = findViewById(R.id.txt_ver_todo);
        txtSujerencia = findViewById(R.id.txt_sujerencias);


        String palabra = getIntent().getStringExtra("clave");
        txtSujerencia.setText(palabra);


        buscarpalabraAlgolia();


        edtClave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edtClave.getText().toString().isEmpty()) {
                    return;
                }
                realizarBusqueda(edtClave.getText().toString());
            }
        });

        imgIconAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SujerenciasBusqueda.super.onBackPressed();
            }
        });

        imgIconoIr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edtClave.getText().toString().isEmpty()) {
                    Toast.makeText(SujerenciasBusqueda.this, "No hay palabra", Toast.LENGTH_SHORT).show();
                    return;
                }
                realizarBusqueda(edtClave.getText().toString());

            }
        });

        txtVerTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SujerenciasBusqueda.this, LocalesLista.class);
                intent.putExtra("nombre","todo");
                startActivity(intent);
            }
        });


        //para dar oncliklistener a los pruductos encontrados
        hits.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView recyclerView, int position, View v) {
                JSONObject hit = hits.get(position);
                String nombre = (String) hit.opt("nombre");
                Log.e("hit", nombre);


                if (nombre == null) {
                    Toast.makeText(SujerenciasBusqueda.this, "Producto no encontrado", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(SujerenciasBusqueda.this, LocalesLista.class);
                intent.putExtra("nombre", nombre);
                startActivity(intent);
            }
        });


    }

    private void buscarpalabraAlgolia() {
        final String palabraClave = getIntent().getStringExtra("clave");
        if (palabraClave != null) {
            realizarBusqueda(palabraClave);
            // edtClave.setText(palabraClave);
            progresbarr();
        } else {
            Toast.makeText(this, "No es reconoce palabra", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(SujerenciasBusqueda.this, MainActivity.class));
        }
    }


    private void realizarBusqueda(String palabraClave) {

        searcher = Searcher.create(ALGOLIA_APP_ID, ALGOLIA_SEARCH_API_KEY, ALGOLIA_INDEX_NAME);
        helper = new InstantSearch(this, searcher);
        helper.search(palabraClave);


    }

    void progresbarr() {
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            public void run() {
                int progressStatus = 0;
                while (progressStatus < 50) {
                    progressStatus++;
                    // Update the progress bar and display the
                    //current value in the text view
                    handler.post(new Runnable() {
                        public void run() {
                            proSujerencias.setVisibility(View.VISIBLE);

                        }
                    });
                    try {
                        // Sleep for 200 milliseconds.
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                proSujerencias.setVisibility(View.INVISIBLE);
            }
        }).start();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SujerenciasBusqueda.this,MainActivity.class);
        startActivity(intent);
    }
}
