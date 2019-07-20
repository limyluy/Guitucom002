package com.allisonapps;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.PatternMatcher;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.allisonapps.Actividades.LocalesLista;
import com.allisonapps.Actividades.VerLocalDetalle;
import com.allisonapps.Entidades.Locales;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FirebaseFirestore fb;
    private ArrayList<Marker> markers = new ArrayList<>();
    public static ArrayList<Locales> localesMapas;
    private String nombre;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_barcard);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        Log.e("prueba", localesMapas.get(0).getNombre());
        nombre = getIntent().getStringExtra("nombre");

        getSupportActionBar().setTitle("Vista por ubicacion");
        getSupportActionBar().setSubtitle("Aqui encontratras: " + nombre);




    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng ipiales = new LatLng(0.83028, -77.64444);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ipiales, 14));

        llenarMarkers();


        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {


                String id = marker.getId();
                String id2 = id.substring(1);
                int i = Integer.parseInt(id2);

                Locales local = localesMapas.get(i);

                Intent intent = new Intent(MapsActivity.this, VerLocalDetalle.class);
                intent.putExtra("nombre", local.getNombre());
                intent.putExtra("imglocal", local.getImgLocal());
                intent.putExtra("imglogo", local.getImgLogo());
                intent.putExtra("telefono", local.getTelefono());
                intent.putExtra("color", local.getColor());
                intent.putExtra("ubicasion", local.getUbicacion().toString());
                intent.putExtra("direccion", local.getDireccion());
                intent.putExtra("actualizado", local.isActualizado());
                intent.putExtra("descripcion", local.getDescripcion());
                intent.putStringArrayListExtra("tangs", (ArrayList<String>) local.getEtiquetas());

                startActivity(intent);


            }
        });


        // Add a marker in Sydney and move the camera
     /*
        mMap.addMarker(new MarkerOptions()
                .position(ipiales)
                .title("Marker in Sydney")
                .snippet("snped para la decripcion")
                //parqa cambiar de color el marker
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));*/


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            Toast.makeText(this, "Pedir permiso", Toast.LENGTH_SHORT).show();

        } else {

            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
        }


        mMap.getUiSettings().setAllGesturesEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);


    }

    private void llenarMarkers() {

        for (int i = 0; i < localesMapas.size(); i++) {

            Locales locale = localesMapas.get(i);
            LatLng ubicacion = new LatLng(locale.getUbicacion().getLatitude(), locale.getUbicacion().getLongitude());
            MarkerOptions marker = new MarkerOptions().title(locale.getNombre())
                    .position(ubicacion)
                    .snippet(locale.getEtiquetas().get(0))
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
            mMap.addMarker(marker);
        }

    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(MapsActivity.this,LocalesLista.class);
        intent.putExtra("nombre",nombre);
        startActivity(intent);
    }
}
