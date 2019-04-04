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
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
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
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.Toast;

import com.allisonapps.Adaptadores.AdaptadorLocal;
import com.allisonapps.Adaptadores.VerDetalleAdaptador;
import com.allisonapps.CenterZoomLayoutManager;
import com.allisonapps.Entidades.Locales;
import com.allisonapps.Entidades.Productos;
import com.allisonapps.R;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Random;


import io.grpc.okhttp.internal.Util;

public class VerLocalDetalle extends AppCompatActivity {

    private static final String PRODUCTOS = "productos";
    private static final String DisplayName = "Aaaaazzafasd";
    private static final String MobileNumber = "123132132";


    private RecyclerView rcvVerProducto;
    private VerDetalleAdaptador adaptador;
    private FirebaseFirestore db;
    private Context context;

    private CardView crvVerLocal;
    private ImageView imgGradient;

    private String nombre;
    private String descripcion;
    private String imglogo;
    private String imglocal;
    private String telefono;
    private String color;
    private String ubicacion;
    private String direccion;
    private boolean actualizado;
    private Activity activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_local_detalle);

        crvVerLocal = findViewById(R.id.crv_ver_local_detalle);
        imgGradient = findViewById(R.id.img_degrade_ver_local);

        db = FirebaseFirestore.getInstance();
        context = getApplicationContext();

        rcvVerProducto = findViewById(R.id.rcv_ver_locales_detalle);
        activity = this;


        rescatarVariables();
        llenarRecyclerProducto();


    }

    private void rescatarVariables() {

        nombre = getIntent().getStringExtra("nombre");
        imglocal = getIntent().getStringExtra("imglocal");
        imglogo = getIntent().getStringExtra("imglogo");
        telefono = getIntent().getStringExtra("telefono");
        color = getIntent().getStringExtra("color");
        ubicacion = getIntent().getStringExtra("ubicasion");
        direccion = getIntent().getStringExtra("direccion");
        actualizado = getIntent().getBooleanExtra("actualizado", true);
    }

    private void llenarRecyclerProducto() {

        rcvVerProducto.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);

        CenterZoomLayoutManager czl = new CenterZoomLayoutManager(this);
        czl.setOrientation(LinearLayoutManager.HORIZONTAL);
        rcvVerProducto.setLayoutManager(czl);

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
                // Productos productos = documentSnapshot.toObject(Productos.class);
                telefono = "573164787515";
                andirContacto();
                //Toast.makeText(context, telefono + productos.getNombre(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void andirContacto() {

        ArrayList < ContentProviderOperation > ops = new ArrayList < ContentProviderOperation > ();

        ops.add(ContentProviderOperation.newInsert(
                ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                .build());

        if (DisplayName != null) {
            ops.add(ContentProviderOperation.newInsert(
                    ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                    .withValue(
                            ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
                            DisplayName).build());
        }

        //------------------------------------------------------ Mobile Number
        if (MobileNumber != null) {
            ops.add(ContentProviderOperation.
                    newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, MobileNumber)
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



    private void openWhatsApp() {

        boolean isWhatsappInstalled = whatsappInstalledOrNot("com.whatsapp");
        if (isWhatsappInstalled) {
            try {
                Intent sendIntent = new Intent("android.intent.action.MAIN");
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.setType("text/plain");
                sendIntent.putExtra(Intent.EXTRA_TEXT, "ollllla");
                sendIntent.putExtra("jid", "573164787555" + "@s.whatsapp.net");
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

    // no anexa el mensaje
    public void enviaMensajeWhatsApp() {
        PackageManager pm = getPackageManager();
        try {
            Intent waIntent = new Intent("android.intent.action.MAIN");
            waIntent.setAction(Intent.ACTION_SEND);
            waIntent.setType("text/plain");
            waIntent.putExtra(Intent.EXTRA_TEXT, "sfasdfasdf");
            waIntent.putExtra("jid", "573164787515" + "@s.whatsapp.net");
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
