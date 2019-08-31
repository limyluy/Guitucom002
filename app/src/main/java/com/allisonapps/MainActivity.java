package com.allisonapps;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.allisonapps.Actividades.LocalesLista;


public class MainActivity extends AppCompatActivity {

    public static boolean isInternet = false;

    //witget
    private TextView txtGuiatu;
    private TextView txtMascien;
    private EditText edtBusqueda;
    private ImageView imgBuscar;
    private ImageView imgBuscarIr;
    private ImageView imgNube1;
    private ImageView imgNube2;
    private ImageView imgNube3;
    private Button btnFavoritos;
    private Button btnOfertas;
    private Button btnEventos;
    private Button btnDescuentos;
    private Button btnLugaresInteres;
    private CardView crdBarra;


    protected void onCreate(Bundle savedInstanceState) {
        // para realizar el splashScrem seteamos el Style Appthem.SpalshScremm en el Manifes
        // con la liena de codigo abajo volvemos al tema original de la aplicacion
        setTheme(R.style.AppTheme);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getSupportActionBar().setTitle("");
        isInternet = isNetDisponible() && isOnlineNet();
        Log.e("is internet",String.valueOf(isInternet));



        // linea para dar soporte a la creacion de graficos con vectores
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);





        // encontrar los witget de la activity main
        txtGuiatu = findViewById(R.id.txt_guiaturistica);
        txtMascien = findViewById(R.id.txt_mascien);
        edtBusqueda = findViewById(R.id.edt_busar);
        imgBuscar = findViewById(R.id.img_bucar);
        imgBuscarIr = findViewById(R.id.img_bucar_ir);
        imgNube1 = findViewById(R.id.img_nube1);
        imgNube2 = findViewById(R.id.img_nube2);
        imgNube3 = findViewById(R.id.img_nube3);
        btnFavoritos = findViewById(R.id.btn_favoritos);
        btnOfertas = findViewById(R.id.btn_ofertas);
        btnEventos = findViewById(R.id.btn_eventos);
        btnDescuentos = findViewById(R.id.btn_descuentos);
        btnLugaresInteres = findViewById(R.id.btn_lugares);
        crdBarra = findViewById(R.id.crv_barra_busqueda);


        // animaciones para los botones
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            animacionesEntrada();
        }




        // seteamos el tipo de fuente de los texview
        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/johan.ttf");
        txtGuiatu.setTypeface(face);
        Typeface face1 = Typeface.createFromAsset(getAssets(), "fonts/johan.ttf");
        txtMascien.setTypeface(face1);

        // seteamos accion de buscar
        imgBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarPalabraClave();
            }
        });

        // seteamos accion de buscar
        imgBuscarIr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarPalabraClave();
            }
        });


        // para que el seacher trabaje por medio del edidtext
        edtBusqueda.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            buscarPalabraClave();
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });

        // lleva a activity LocalesLista lleva un putExtra para dar a conocer de que puerta llega
        btnFavoritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LocalesLista.class);
                intent.putExtra("nombre", "favoritos");
                startActivity(intent);
            }
        });
        // lleva a activity LocalesLista lleva un putExtra para dar a conocer de que puerta llega
        btnOfertas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LocalesLista.class);
                intent.putExtra("nombre", "oferta");
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(MainActivity.this,
                                btnOfertas, "btnoferta");
                startActivity(intent, options.toBundle());
            }
        });
        // lleva a activity LocalesLista lleva un putExtra para dar a conocer de que puerta llega
        btnEventos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LocalesLista.class);
                intent.putExtra("nombre", "evento");
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(MainActivity.this,
                                btnEventos, "btnoferta");
                startActivity(intent, options.toBundle());

            }
        });
        // lleva a activity LocalesLista lleva un putExtra para dar a conocer de que puerta llega
        btnDescuentos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Lo sentimos aun no esta habilitado", Toast.LENGTH_LONG).show();
            }
        });
        // lleva a activity LocalesLista lleva un putExtra para dar a conocer de que puerta llega
        btnLugaresInteres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LocalesLista.class);
                intent.putExtra("nombre", "lugar");
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(MainActivity.this,
                                btnLugaresInteres, "btnoferta");
                startActivity(intent, options.toBundle());


            }
        });

        if (!isInternet){

            funcionarLimitadamente();
        }


    }

    private void funcionarLimitadamente() {

        btnOfertas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "No disponible sin internet", Toast.LENGTH_SHORT).show();
            }
        });
        btnEventos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "No disponible sin internet", Toast.LENGTH_SHORT).show();
            }
        });
        btnDescuentos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "No disponible sin internet", Toast.LENGTH_SHORT).show();
            }
        });
        btnLugaresInteres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "No disponible sin internet", Toast.LENGTH_SHORT).show();
            }
        });  btnOfertas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "No disponible sin internet", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // metodo para realizar animaciones
    private void animacionesEntrada() {

        final Animation move = AnimationUtils.loadAnimation(this, R.anim.anim_move);
        final Animation move2 = AnimationUtils.loadAnimation(this, R.anim.anim_move2);
        final Animation move3 = AnimationUtils.loadAnimation(this, R.anim.anim_move3);
        final Animation move4 = AnimationUtils.loadAnimation(this, R.anim.anim_move4);
        final Animation moveNube1 = AnimationUtils.loadAnimation(this, R.anim.anim_move_centro);
        final Animation moveNube2 = AnimationUtils.loadAnimation(this, R.anim.anim_move_centro2);


        btnOfertas.setAnimation(move4);
        btnEventos.setAnimation(move3);
        btnDescuentos.setAnimation(move2);
        btnLugaresInteres.setAnimation(move);
        imgNube1.setAnimation(moveNube1);
        imgNube2.setAnimation(moveNube1);
        imgNube3.setAnimation(moveNube2);
    }

    private void buscarPalabraClave() {

        if (edtBusqueda.getText().toString().isEmpty()) {
            Toast.makeText(this, "ingresa una articulo o local", Toast.LENGTH_SHORT).show();
            return;
        }

        String palabraClave = edtBusqueda.getText().toString();

        Intent intent = new Intent(MainActivity.this, SujerenciasBusqueda.class);
        intent.putExtra("clave", palabraClave);


        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(MainActivity.this,
                        crdBarra, "barraBusqueda");


        startActivity(intent, options.toBundle());


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {

            case R.id.menu_main:
                Toast.makeText(this, "Menu Main", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean isNetDisponible() {

        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo actNetInfo = connectivityManager.getActiveNetworkInfo();

        return (actNetInfo != null && actNetInfo.isConnected());
    }

    public Boolean isOnlineNet() {

        try {
            Process p = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.es");

            int val           = p.waitFor();
            boolean reachable = (val == 0);
            return reachable;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }
}
