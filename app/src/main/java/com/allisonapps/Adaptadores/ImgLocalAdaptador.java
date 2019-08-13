package com.allisonapps.Adaptadores;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.allisonapps.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ImgLocalAdaptador extends RecyclerView.Adapter<ImgLocalAdaptador.TangsViewHolder> {
    Context context;
    ArrayList<String> list;


    public ImgLocalAdaptador(Context context, ArrayList list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public TangsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v =  LayoutInflater.from(context).inflate(R.layout.item_locales_detalle,viewGroup,false);
        return new TangsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TangsViewHolder holder, final int i) {



        Log.e("iamgen",list.get(i));
        Uri uri = Uri.parse(list.get(i));
        Picasso.with(context).load(uri).into(holder.imgLocal);


    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    class TangsViewHolder extends RecyclerView.ViewHolder {

        ImageView imgLocal;

        public TangsViewHolder(@NonNull View itemView) {
            super(itemView);


            imgLocal = itemView.findViewById(R.id.img_card_locles_detalle);

        }
    }


}
