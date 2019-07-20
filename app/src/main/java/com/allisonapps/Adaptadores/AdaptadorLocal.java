package com.allisonapps.Adaptadores;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.allisonapps.Entidades.Locales;
import com.allisonapps.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdaptadorLocal extends FirestoreRecyclerAdapter<Locales,AdaptadorLocal.LocalHolder> {
    Context context;
    AdaptadorLocal.OnItemClickListener listener;
    private ArrayList<Locales> locales = new ArrayList<>();

    public AdaptadorLocal(@NonNull FirestoreRecyclerOptions<Locales> options, Context context) {
        super(options);
        this.context = context;
    }


    @Override
    protected void onBindViewHolder(@NonNull LocalHolder holder, int position, @NonNull final Locales model) {

        locales.add(model);
        Log.e("pasado",locales.get(position).getNombre());

        String color = model.getColor();

        holder.atencion.setMax(5);
        holder.calidad.setMax(5);
        holder.precio.setMax(5);

        if (model.isActualizado()){
            holder.txtActializado.setBackgroundResource(R.color.colorActualizado);
        }else{
            holder.txtActializado.setBackgroundResource(R.color.colorNoActualizado);
        }


        holder.titulo.setText(model.getNombre());
        holder.descripcion.setText(model.getDescripcion());
        holder.garaje.setVisibility(model.isGaraje()? View.VISIBLE : View.INVISIBLE);
        holder.tagerta.setVisibility(model.isTarjeta()? View.VISIBLE : View.INVISIBLE);
        holder.garantia.setVisibility(model.isGarantia()? View.VISIBLE : View.INVISIBLE);
        holder.fondo.setBackgroundColor(Integer.parseInt(model.getColor()));
        holder.atencion.setProgress(model.getAtencion());
        holder.calidad.setProgress(model.getCalidad());
        holder.precio.setProgress(model.getPrecio());
        holder.imgOferta.setVisibility(model.isOfertas() ? View.VISIBLE : View.INVISIBLE);
        holder.tagerta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "En " + model.getNombre() + " se acepta tarjeta", Toast.LENGTH_LONG).show();
            }
        });
        holder.garaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "En " + model.getNombre() + " presta servicio de parqueadero", Toast.LENGTH_LONG).show();
            }
        });
        holder.garantia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "En " + model.getNombre() + " da garantia en sus productos", Toast.LENGTH_LONG).show();
            }
        });

        Picasso.with(context).load(model.getImgLogo()).placeholder(R.drawable.ic_cloud_of).into(holder.logo);
        holder.numRecomendado.setText(String.valueOf(model.getNumRecomendado()));

        holder.dirigeme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @NonNull
    @Override
    public LocalHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.targetas_locales,viewGroup,false);
        return new LocalHolder(v);
    }

    public ArrayList<Locales> obtenerLocales(){
        return locales;
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
        ImageView imgOferta;
        TextView txtActializado;
        ProgressBar atencion;
        ProgressBar calidad;
        ProgressBar precio;
        TextView numRecomendado;


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
            atencion = itemView.findViewById(R.id.prb_atencion);
            calidad = itemView.findViewById(R.id.prb_calidad);
            precio = itemView.findViewById(R.id.prb_precio);
            numRecomendado = itemView.findViewById(R.id.txt_num_recomendado);
            imgOferta = itemView.findViewById(R.id.img_ofertas_local_card);
            txtActializado = itemView.findViewById(R.id.txt_actualizado_locales_card);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(getSnapshots().getSnapshot(position),position);
                    }
                }
            });
        }

    }
    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickListener(AdaptadorLocal.OnItemClickListener listener) {
        this.listener = listener;
    }




}
