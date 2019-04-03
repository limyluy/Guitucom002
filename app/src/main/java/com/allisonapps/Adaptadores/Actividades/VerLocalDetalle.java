package com.allisonapps.Adaptadores.Actividades;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.shapes.Shape;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.transition.Visibility;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Display;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.allisonapps.R;

import io.grpc.internal.SharedResourceHolder;

public class VerLocalDetalle extends AppCompatActivity {

    private Drawable gradient;
    private CardView crvVerLocal;
    private ImageView imgGradient;

    @SuppressLint("ResourceType")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_local_detalle);

        crvVerLocal = findViewById(R.id.crv_ver_local_detalle);
        imgGradient = findViewById(R.id.img_degrade_ver_local);




    }
}
