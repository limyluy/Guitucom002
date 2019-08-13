package com.allisonapps.Adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.allisonapps.Entidades.Productos;
import com.allisonapps.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class VerDetalleAdaptador extends FirestoreRecyclerAdapter<Productos, VerDetalleAdaptador.VerDetalleViewHolder> {


    private Context context;
    AdaptadorLocal.OnItemClickListener listener;
    private ArrayList<Productos> productos = new ArrayList<>();
    public VerDetalleAdaptador(@NonNull FirestoreRecyclerOptions<Productos> options,Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull VerDetalleViewHolder holder, int position, @NonNull final Productos model) {

        Productos producto = model;

        productos.add(producto);

        holder.nombre.setText(model.getNombre());
//        holder.descripcion.setText(model.getDescripcion());
        holder.marca.setText(model.getMarca());
        holder.desAdicional.setText(model.getDesAdicional());
//        holder.infAdicional.setText(model.getInfAdicional());
        Picasso.with(context).load(model.getImgProducto())
                .fit()
                .centerInside()
                .into(holder.imgProducto);



    }

    @NonNull
    @Override
    public VerDetalleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_ver_local_detalle,viewGroup,false);
        return new VerDetalleViewHolder(v);
    }

    class VerDetalleViewHolder extends RecyclerView.ViewHolder {


        TextView nombre;
        TextView descripcion;
        TextView marca;
        ImageView imgProducto;
        TextView infAdicional;
        TextView desAdicional;
        // ImageView oferta;
        Button btnMasInfo;


        public VerDetalleViewHolder(@NonNull View itemView) {
            super(itemView);

            nombre = itemView.findViewById(R.id.txt_nom_producto_ver_local);
         //   descripcion = itemView.findViewById(R.id.txt_des_producto_ver_local);
            marca = itemView.findViewById(R.id.txt_mar_producto_ver_local);
            imgProducto = itemView.findViewById(R.id.img_producto_card_item_ver_local);
         //   infAdicional = itemView.findViewById(R.id.txt_desadi_producto_ver_local);
            desAdicional = itemView.findViewById(R.id.txt_esp_producto_ver_local);
            btnMasInfo = itemView.findViewById(R.id.btn_ver_producto_local);

            btnMasInfo.setOnClickListener(new View.OnClickListener() {
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

    public ArrayList<Productos> getProductos(){

        return productos;
    }

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickListener(AdaptadorLocal.OnItemClickListener listener) {
        this.listener = listener;
    }
}
