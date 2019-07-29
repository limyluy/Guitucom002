package com.allisonapps.Adaptadores;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.allisonapps.Actividades.LocalesLista;
import com.allisonapps.Actividades.VerLocalDetalle;
import com.allisonapps.Entidades.Locales;
import com.allisonapps.R;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

public class FavoritosLocalesAdaptador extends RecyclerView.Adapter<FavoritosLocalesAdaptador.LocalesViewHolder> {
    Context context;
    ArrayList<Locales> list;
    AdaptadorLocal.OnItemClickListener listener;


    public FavoritosLocalesAdaptador(Context context, ArrayList<Locales> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public LocalesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.targetas_locales, viewGroup, false);
        return new LocalesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull LocalesViewHolder holder, final int i) {

//        Log.e("pasado",locales.get(position).getNombre());
        final Locales localeCurrent = list.get(i);

        String color = localeCurrent.getColor();

        holder.atencion.setMax(5);
        holder.calidad.setMax(5);
        holder.precio.setMax(5);

        if (localeCurrent.isActualizado()){
            holder.txtActializado.setBackgroundResource(R.color.colorActualizado);
        }else{
            holder.txtActializado.setBackgroundResource(R.color.colorNoActualizado);
        }


        holder.titulo.setText(localeCurrent.getNombre());
        holder.descripcion.setText(localeCurrent.getDescripcion());
        holder.garaje.setVisibility(localeCurrent.isGaraje()? View.VISIBLE : View.INVISIBLE);
        holder.tagerta.setVisibility(localeCurrent.isTarjeta()? View.VISIBLE : View.INVISIBLE);
        holder.garantia.setVisibility(localeCurrent.isGarantia()? View.VISIBLE : View.INVISIBLE);
        holder.fondo.setBackgroundColor(Integer.parseInt(localeCurrent.getColor()));
        holder.atencion.setProgress(localeCurrent.getAtencion());
        holder.calidad.setProgress(localeCurrent.getCalidad());
        holder.precio.setProgress(localeCurrent.getPrecio());
        holder.imgOferta.setVisibility(localeCurrent.isOfertas() ? View.VISIBLE : View.INVISIBLE);
        holder.tagerta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "En " + localeCurrent.getNombre() + " se acepta tarjeta", Toast.LENGTH_LONG).show();
            }
        });
        holder.garaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "En " + localeCurrent.getNombre() + " presta servicio de parqueadero", Toast.LENGTH_LONG).show();
            }
        });
        holder.garantia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "En " + localeCurrent.getNombre() + " da garantia en sus productos", Toast.LENGTH_LONG).show();
            }
        });

        Picasso.with(context).load(localeCurrent.getImgLogo()).placeholder(R.drawable.ic_cloud_of).into(holder.logo);
        holder.numRecomendado.setText(String.valueOf(localeCurrent.getNumRecomendado()));

        holder.dirigeme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                navigateExternalTo(context,localeCurrent.getUbicacion().getLatitude(),localeCurrent.getUbicacion().getLongitude());
            }
        });

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, VerLocalDetalle.class);



                Gson gson = new Gson();
                String json = gson.toJson(localeCurrent);

                intent.putExtra("local",json);
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    class LocalesViewHolder extends RecyclerView.ViewHolder {

        CardView card;
        TextView titulo;
        TextView descripcion;
        ImageButton mgusta;
        ImageView logo;
        ImageView tagerta;
        ImageView garaje;
        ImageView garantia;
        Button dirigeme;
        ImageView fondo;
        ImageView imgOferta;
        TextView txtActializado;
        ProgressBar atencion;
        ProgressBar calidad;
        ProgressBar precio;
        TextView numRecomendado;


        public LocalesViewHolder(@NonNull View itemView) {
            super(itemView);
            card = itemView.findViewById(R.id.card_locales);
            titulo = itemView.findViewById(R.id.txt_nombre_local);
            descripcion = itemView.findViewById(R.id.txt_local_descripcion);
            mgusta = itemView.findViewById(R.id.btn_local_favorite);
            fondo = itemView.findViewById(R.id.img_card_locales);
            tagerta = itemView.findViewById(R.id.img_icon_targeta);
            garaje = itemView.findViewById(R.id.img_icon_garage);
            garantia = itemView.findViewById(R.id.img_icon_garantia);
            dirigeme = itemView.findViewById(R.id.btn_local_dirigir);
            logo = itemView.findViewById(R.id.img_card_logo);
            atencion = itemView.findViewById(R.id.prb_atencion);
            calidad = itemView.findViewById(R.id.prb_calidad);
            precio = itemView.findViewById(R.id.prb_precio);
            numRecomendado = itemView.findViewById(R.id.txt_num_recomendado);
            imgOferta = itemView.findViewById(R.id.img_ofertas_local_card);
            txtActializado = itemView.findViewById(R.id.txt_actualizado_locales_card);






        }
    }

    public interface OnItemClickListener {
        void onItemClick(Locales local, int position);
    }

    public void setOnItemClickListener(AdaptadorLocal.OnItemClickListener listener) {
        this.listener = listener;
    }

    public static void navigateExternalTo(Context context, double latitude, double longitude) {
        String uri = String.format(Locale.ENGLISH, "google.navigation:q=%1$f,%2$f", latitude, longitude);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        intent.setPackage("com.google.android.apps.maps");
        context.startActivity(intent);

    }
}



