package com.allisonapps;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.Toolbar;

import com.algolia.instantsearch.core.helpers.Searcher;
import com.algolia.instantsearch.ui.helpers.InstantSearch;
import com.algolia.instantsearch.ui.utils.ItemClickSupport;
import com.algolia.instantsearch.ui.views.Hits;
import com.allisonapps.Adaptadores.Actividades.LocalesLista;

import org.json.JSONObject;

public class SujerenciasBusqueda extends AppCompatActivity {

    //witget
    private Toolbar tooSuperior;
    private EditText edtClave;
    private EditText edtNuevaBusqueda;
    private ImageView imgBuscarnuevo;
    private ImageView imgBorrarnuevo;
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
        getSupportActionBar();

        // encontramos los witgets del activity SujerenciasBusqueda
        tooSuperior = findViewById(R.id.barra_busqueda);
        edtClave = findViewById(R.id.txt_clave);
        imgBuscarnuevo = findViewById(R.id.img_bucarnuevo);
        imgBorrarnuevo = findViewById(R.id.img_borrarnuevo);
        proSujerencias = findViewById(R.id.probra_suje);
        hits = findViewById(R.id.hits);

        buscarpalabraAlgolia();


        // para que el seacher trabaje por medio del edidtext
        edtClave.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (edtClave.getText().toString().isEmpty()) {
                    Toast.makeText(SujerenciasBusqueda.this, "No hay palabra para Buscar", Toast.LENGTH_SHORT).show();
                    return true;
                }

                String palabraNueva = edtClave.getText().toString();
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            realizarBusqueda(palabraNueva);
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });

        //para dar oncliklistener a los pruductos encontrados
        hits.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView recyclerView, int position, View v) {
                JSONObject hit = hits.get(position);
                String ola = (String) hit.opt("objectID");

                if (ola == null) {
                    Toast.makeText(SujerenciasBusqueda.this, "Producto no encontrado", Toast.LENGTH_SHORT).show();
                    return;
                }
                startActivity(new Intent(SujerenciasBusqueda.this, LocalesLista.class));
            }
        });

    }

    private void buscarpalabraAlgolia() {
        final String palabraClave = getIntent().getStringExtra("clave");
        if (palabraClave != null) {
            realizarBusqueda(palabraClave);
            edtClave.setText(palabraClave);
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
}
