package com.allisonapps.Adaptadores.Actividades;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.transition.Visibility;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Display;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.allisonapps.R;

public class VerLocalDetalle extends AppCompatActivity {

    private Drawable gradient;
    private CardView crvVerLocal;
    private ImageView imgGradient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_local_detalle);

        crvVerLocal = findViewById(R.id.crv_ver_local_detalle);
        imgGradient = findViewById(R.id.img_degrade_ver_local);

        int color =  Color.parseColor("#ffffff");


        String colorToApply = "#805841";
        Drawable shape = (Drawable) imgGradient.getBackground();
       // shape.setColorFilter(Color.parseColor(colorToApply), android.graphics.PorterDuff.Mode.SRC);
        shape.
    }
}
