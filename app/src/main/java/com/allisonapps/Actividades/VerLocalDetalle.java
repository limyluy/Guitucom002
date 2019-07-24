package com.allisonapps.Actividades;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentProviderOperation;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.allisonapps.Adaptadores.AdaptadorLocal;
import com.allisonapps.Adaptadores.ImgLocalAdaptador;
import com.allisonapps.Adaptadores.TangsAdaptador;
import com.allisonapps.Adaptadores.VerDetalleAdaptador;
import com.allisonapps.CenterZoomLayoutManager;
import com.allisonapps.Entidades.Locales;
import com.allisonapps.Entidades.Productos;
import com.allisonapps.R;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.Query;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;


import io.grpc.okhttp.internal.Util;

public class VerLocalDetalle extends AppCompatActivity {

    // nombre de la collencion de firebase
    private static final String PRODUCTOS = "productos";

    // en easta actividad tratamos de enviar un mensaje a wasapt agregando nosotro el texto y
    // tambien el contacto si el usuario lo permite

    //variables para llenar el recycler falta especificar query
    private RecyclerView rcvVerProducto;
    private VerDetalleAdaptador adaptador;
    private FirebaseFirestore db;
    private Context context;


    // widget
    private CardView crvVerLocalDetalle;
    private ImageView imgLogoLocal;
    private ImageView imgVerLocal;
    private ImageView imgBack;
    private TextView txtDireccion;
    private TextView txtTelefono;
    private TextView txtDescripcion;
    private TextView txtNumeroLike;
    private RecyclerView lista;
    private Button btnGuiame;
    private CardView cardBtnLike;
    private RecyclerView rcvImgLocal;

    //variables que se rescataran de la anterior actividad
    private String nombre;
    private String descripcion;
    private String imglogo;
    private ArrayList<String> imglocal = new ArrayList<>();
    private String telefono;
    private String color;
    private GeoPoint ubicacion;
    private String direccion;
    private ArrayList tangs;
    private ArrayList<String> localesFavoritos;
    private String imglocalUno;
    private String imglocalDos;
    private String imglocalTres;
    private boolean isFavorito = false;
    private boolean actualizado;
    private Activity activity;
    private String PalabraBuscada;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_barDetalle);
        setContentView(R.layout.activity_ver_local_detalle);


        // encontramos los widget
        crvVerLocalDetalle = findViewById(R.id.crv_ver_local_detalle);
       // imgVerLocal = findViewById(R.id.img_local_ver_local);
        imgLogoLocal = findViewById(R.id.img_logo_local_detalle);
        txtDireccion = findViewById(R.id.txt_dir_local_detalle);
        txtTelefono = findViewById(R.id.txt_tel_local_detalle);
        txtDescripcion = findViewById(R.id.txt_descripcion_detalle);
        txtNumeroLike = findViewById(R.id.txt_numero_like);
        lista = findViewById(R.id.liv_tangs_detalle);
        btnGuiame = findViewById(R.id.btn_guiame_local_detalle);
        rcvVerProducto = findViewById(R.id.rcv_ver_locales_detalle);
        imgBack = findViewById(R.id.img_ic_back);
        rcvImgLocal = findViewById(R.id.rcv_local_ver_local);
        cardBtnLike = findViewById(R.id.crv_boton_like);


        // inicalizamos variables
        db = FirebaseFirestore.getInstance();
        context = getApplicationContext();
        activity = this;

        rescatarVariables();
        recuperarFavoritos();

        llenarLocal();
        llenarRecyclerProducto();

        btnGuiame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                abrirMapa(context, ubicacion.getLatitude(), ubicacion.getLongitude());
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        cardBtnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarLocalesFavoritos();
            }
        });


    }

    private void llenarRecyclerImgLocal(ArrayList<String> imglocal) {


        ImgLocalAdaptador adapter = new ImgLocalAdaptador(this, imglocal);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        rcvImgLocal.setHasFixedSize(true);
        rcvImgLocal.setLayoutManager(llm);
        rcvImgLocal.setAdapter(adapter);
    }

    private void agregarLocalesFavoritos() {

        if (isFavorito == true){
            Toast.makeText(context, nombre + " Ya se encuentra en tu lista de favoritos", Toast.LENGTH_LONG).show();
            return;
        }

        localesFavoritos.add(nombre);

        SharedPreferences preferences = getSharedPreferences("Localesfavoritos", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(localesFavoritos);
        editor.putString("favoritos", json);
        editor.apply();

        int numero = Integer.parseInt(txtNumeroLike.getText().toString());
        numero++;
        txtNumeroLike.setText(String.valueOf(numero));


    }

    private void recuperarFavoritos() {

        SharedPreferences preferences = getSharedPreferences("Localesfavoritos", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString("favoritos", null);
        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        localesFavoritos = gson.fromJson(json, type);


        if (localesFavoritos == null) {
            localesFavoritos = new ArrayList<>();
            Log.e("favoritos ", "bacio");
            return;
        }


        for (int i = 0; i < localesFavoritos.size(); i++) {

            Log.e("recuperado", localesFavoritos.get(i));

            if (localesFavoritos.get(i).equals(nombre)) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    cardBtnLike.setCardBackgroundColor(getColor(R.color.colorTextoGris));
                    isFavorito = true;
                }



                int numero = Integer.parseInt(txtNumeroLike.getText().toString());
                numero++;
                txtNumeroLike.setText(String.valueOf(numero));
            }
        }

    }

    private void llenarLocal() {

        // metodo para ajustar .centerCrop()
//        Log.e("weith", String.valueOf(imgVerLocal.getMaxHeight()));
       /* Picasso.with(context).load(imglocal)
                .fit()
                .into(imgVerLocal);*/
        Picasso.with(context).load(imglogo).into(imgLogoLocal);
        crvVerLocalDetalle.setCardBackgroundColor(Integer.parseInt(color));

        txtDireccion.setText(direccion);
        txtTelefono.setText(telefono);
        txtDescripcion.setText(descripcion);

        TangsAdaptador adapter = new TangsAdaptador(context, tangs);
        lista.setLayoutManager(new LinearLayoutManager(this));
        lista.setAdapter(adapter);


    }

    private void rescatarVariables() {

        nombre = getIntent().getStringExtra("nombre");
        imglocalUno = getIntent().getStringExtra("uno");
        imglocalDos = getIntent().getStringExtra("dos");
        imglocalTres = getIntent().getStringExtra("tres");
        imglogo = getIntent().getStringExtra("imglogo");
        telefono = getIntent().getStringExtra("telefono");
        color = getIntent().getStringExtra("color");
        Double latitud = getIntent().getDoubleExtra("latitud", 0.0);
        Double longitud = getIntent().getDoubleExtra("longitud", 0.0);
        direccion = getIntent().getStringExtra("direccion");
        actualizado = getIntent().getBooleanExtra("actualizado", true);
        descripcion = getIntent().getStringExtra("descripcion");
        tangs = getIntent().getStringArrayListExtra("tangs");


        imglocal.add(imglocalUno);
        imglocal.add(imglocalDos);
        imglocal.add(imglocalTres);

        llenarRecyclerImgLocal(imglocal);


        ubicacion = new GeoPoint(latitud, longitud);


    }

    //metodo para llenar recyler y dar accion al boton mas detalle
    private void llenarRecyclerProducto() {

        rcvVerProducto.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);

        //   CenterZoomLayoutManager czl = new CenterZoomLayoutManager(this);
        rcvVerProducto.setLayoutManager(llm);

        CollectionReference ref = db.collection(PRODUCTOS);
        Query query = ref.orderBy("nombre", Query.Direction.ASCENDING);


        FirestoreRecyclerOptions<Productos> options = new FirestoreRecyclerOptions.Builder<Productos>()
                .setQuery(query, Productos.class)
                .build();

        adaptador = new VerDetalleAdaptador(options, context);
        rcvVerProducto.setAdapter(adaptador);


        adaptador.setOnItemClickListener(new AdaptadorLocal.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {

                //los metodos funcionan correctamente
                //falta integrar pedir permiso y acomodarlo para que se haga mas fluidamente
                //primero hay que crear el contacto en un intent y despues en otro enviar el mensaje

                if (verduplicado(telefono)) {
                    enviaMsjContacto(telefono);
                } else {
                    //falta pedir permiso para agregar contacto
                    crearContacto(telefono, nombre);
                }

            }
        });


    }

    private boolean verduplicado(String telefono) {
        // Get query phone contacts cursor object.
        Uri readContactsUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        Cursor c = getContentResolver().query(readContactsUri, null, null, null, null);

        ArrayList<String> lista = new ArrayList<>();


        if (c != null) {

            int i = c.getColumnIndexOrThrow((ContactsContract.CommonDataKinds.Phone.NUMBER));

            while (c.moveToNext()) {

                String usuario = c.getString(i);
                if (usuario.equals(telefono)) {
                    Log.e("numero allado", usuario);
                    return true;
                }


            }
        } else {
            Toast.makeText(getApplicationContext(),
                    "No hay nada :(", Toast.LENGTH_LONG).show();
        }


        return false;

    }


    private void crearContacto(String telefono, String nombre) {


        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

        ops.add(ContentProviderOperation.newInsert(
                ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                .build());

        if (nombre != null) {
            ops.add(ContentProviderOperation.newInsert(
                    ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                    .withValue(
                            ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
                            nombre).build());
        }


        if (telefono != null) {
            ops.add(ContentProviderOperation.
                    newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, telefono)
                    .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
                            ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                    .build());
        }

        try {
            getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }

    // lo envia si tenemos el contacto icnluido el mensaje
    private void enviaMsjContacto(String telefono) {

        boolean isWhatsappInstalled = whatsappInstalledOrNot("com.whatsapp");
        if (isWhatsappInstalled) {
            try {
                Intent sendIntent = new Intent("android.intent.action.MAIN");
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.setType("text/plain");
                sendIntent.putExtra(Intent.EXTRA_TEXT, "ollllla");
                sendIntent.putExtra("jid", "573183088222" + "@s.whatsapp.net");
                // phone number without "+" prefix
                sendIntent.setPackage("com.whatsapp");
                startActivity(sendIntent);
            } catch (Exception e) {
                Toast.makeText(context, "Error/n" + e.toString(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Uri uri = Uri.parse("market://details?id=com.whatsapp");
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            Toast.makeText(context, "WhatsApp not Installed", Toast.LENGTH_SHORT).show();
            context.startActivity(goToMarket);
        }
    }

    // metodo para saber si wassap esta isntalado
    private boolean whatsappInstalledOrNot(String uri) {
        PackageManager pm = context.getPackageManager();
        boolean app_installed = false;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }

    // metodo para enviar wassap sin agregar contacto de esta forma no anexa mensaje
    public void enviaMsjNoContacto(String telefono) {
        PackageManager pm = getPackageManager();
        try {
            Intent waIntent = new Intent("android.intent.action.MAIN");
            waIntent.setAction(Intent.ACTION_SEND);
            waIntent.setType("text/plain");
            waIntent.putExtra(Intent.EXTRA_TEXT, "sfasdfasdf");
            waIntent.putExtra("jid", telefono + "@s.whatsapp.net");
            waIntent.setComponent(new ComponentName("com.whatsapp", "com.whatsapp.Conversation"));
            PackageInfo info = pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
            waIntent.setPackage("com.whatsapp");


            startActivity(Intent.createChooser(waIntent, "Compartir con:"));


        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(this, "WhatsApp no esta instalado!", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_detalle, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {

            case R.id.menu_main:
                Toast.makeText(context, "Menu main", Toast.LENGTH_SHORT).show();
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

    public static void abrirMapa(Context context, double latitude, double longitude) {
        String uri = String.format(Locale.ENGLISH, "google.navigation:q=%1$f,%2$f", latitude, longitude);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        intent.setPackage("com.google.android.apps.maps");
        context.startActivity(intent);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
