package com.allisonapps;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdaptadorLocal extends FirestoreRecyclerAdapter<Locales,AdaptadorLocal.LocalHolder> {
    Context context;

    public AdaptadorLocal(@NonNull FirestoreRecyclerOptions<Locales> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull LocalHolder holder, int position, @NonNull Locales model) {
        String color = model.getFondoColor();

        ColorDrawable colorDrawable = new ColorDrawable();
        colorDrawable.setColor(Color.parseColor(color));


        holder.titulo.setText(model.getNombre());
        holder.descripcion.setText(model.getDescripcion());
        holder.garaje.setImageResource(model.isGarage()? R.drawable.ic_clear_ : R.drawable.ic_local_parking);
        holder.tagerta.setImageResource(model.isGarage()? R.drawable.ic_clear_ : R.drawable.ic_credit_card);
        holder.garantia.setImageResource(model.isGarage()? R.drawable.ic_clear_ : R.drawable.ic_verified_user);
        holder.fondo.setBackgroundColor(colorDrawable.getColor());
        Picasso.with(context)
                .load(model.getLogo())
                .fit()
                .centerCrop()
                .into(holder.logo);
      // holder.dirigeme.setText(model.getGeoPoint().toString());
        holder.numAtencion.setText(String.valueOf(model.getAtencion()));
        holder.numCalidad.setText(String.valueOf(model.getCalidad()));
        holder.numPrecio.setText(String.valueOf(model.getPrecio()));
    }

    @NonNull
    @Override
    public LocalHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.targetas_locales,viewGroup,false);
        return new LocalHolder(v);
    }

    class LocalHolder extends RecyclerView.ViewHolder {

        TextView titulo;
        TextView descripcion;
        ImageButton mgusta;
        ImageView logo;
        ImageView tagerta;
        ImageView garaje;
        ImageView garantia;
        Button dirigeme;
        ImageView fondo;
        TextView numAtencion;
        TextView numCalidad;
        TextView numPrecio;


        public LocalHolder(@NonNull View itemView) {
            super(itemView);

            titulo = itemView.findViewById(R.id.txt_nombre_local);
            descripcion = itemView.findViewById(R.id.txt_local_descripcion);
            mgusta = itemView.findViewById(R.id.btn_local_favorite);
            fondo = itemView.findViewById(R.id.img_card_locales);
            tagerta = itemView.findViewById(R.id.img_icon_targeta);
            garaje = itemView.findViewById(R.id.img_icon_garage);
            garantia = itemView.findViewById(R.id.img_icon_garantia);
            dirigeme = itemView.findViewById(R.id.btn_local_dirigir);
            logo = itemView.findViewById(R.id.img_card_logo);
            numAtencion = itemView.findViewById(R.id.txt_num_atencion);
            numCalidad = itemView.findViewById(R.id.txt_num_calidad);
            numPrecio = itemView.findViewById(R.id.txt_num_precio);


        }

    }


}
