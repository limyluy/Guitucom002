package com.allisonapps.Adaptadores;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.allisonapps.Actividades.LocalesLista;
import com.allisonapps.R;
import com.allisonapps.SujerenciasBusqueda;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class TangsAdaptador extends RecyclerView.Adapter<TangsAdaptador.TangsViewHolder> {
    Context context;
    ArrayList<String> list;


    public TangsAdaptador(Context context, ArrayList list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public TangsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v =  LayoutInflater.from(context).inflate(R.layout.item_list,viewGroup,false);
        return new TangsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TangsViewHolder holder, final int i) {

        holder.tang.setText(list.get(i));
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, LocalesLista.class);
                intent.putExtra("nombre", list.get(i));
                context.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    class TangsViewHolder extends RecyclerView.ViewHolder {

        TextView tang;
        CardView card;

        public TangsViewHolder(@NonNull View itemView) {
            super(itemView);

            tang = itemView.findViewById(R.id.txt1);
            card = itemView.findViewById(R.id.card_list);

        }
    }


}
