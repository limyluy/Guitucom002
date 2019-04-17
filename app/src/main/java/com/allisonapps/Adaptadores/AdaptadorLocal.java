package com.allisonapps.Adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.allisonapps.Entidades.Locales;
import com.allisonapps.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.squareup.picasso.Picasso;

public class AdaptadorLocal extends FirestoreRecyclerAdapter<Locales,AdaptadorLocal.LocalHolder> {
    Context context;
    AdaptadorLocal.OnItemClickListener listener;

    public AdaptadorLocal(@NonNull FirestoreRecyclerOptions<Locales> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull LocalHolder holder, int position, @NonNull Locales model) {
        String color = model.getColor();




        holder.titulo.setText(model.getNombre());
        holder.descripcion.setText(model.getDescripcion());
        holder.garaje.setImageResource(model.isGaraje()?R.drawable.ic_local_parking : R.drawable.ic_clear_ );
        holder.tagerta.setImageResource(model.isTarjeta()?R.drawable.ic_credit_card  :  R.drawable.ic_clear_);
        holder.garantia.setImageResource(model.isGarantia()? R.drawable.ic_verified_user : R.drawable.ic_clear_);
        holder.fondo.setBackgroundColor(Integer.parseInt(model.getColor()));

       // holder.dirigeme.setText(model.getGeoPoint().toString());
        holder.numAtencion.setText(String.valueOf(model.getAtencion()));
        holder.numCalidad.setText(String.valueOf(model.getCalidad()));
        holder.numPrecio.setText(String.valueOf(model.getPrecio()));
        Picasso.with(context).load(model.getImgLogo()).placeholder(R.drawable.ic_cloud_of).into(holder.logo);
        holder.numRecomendado.setText(String.valueOf(model.getNumRecomendado()));
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
            numAtencion = itemView.findViewById(R.id.txt_num_atencion);
            numCalidad = itemView.findViewById(R.id.txt_num_calidad);
            numPrecio = itemView.findViewById(R.id.txt_num_precio);
            numRecomendado = itemView.findViewById(R.id.txt_num_recomendado);


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
