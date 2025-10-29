package com.example.contextualizacion3;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import db.Producto;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ViewHolder> {
    public interface OnProductClickListener {
        void onClick(Producto product);
    }
    private List<Producto> productos;
    private OnProductClickListener listener;

    public ProductoAdapter(List<Producto> products, OnProductClickListener listener) {
        this.productos = products;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_item_producto, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Producto product = productos.get(position);
        holder.tvName.setText(product.nombre);
        holder.tvDesc.setText(product.descripcion);
        holder.tvPrice.setText("$ " +  (product.precio));
        holder.btnAdd.setOnClickListener(v -> listener.onClick(product));
    }

    @Override
    public int getItemCount() {
        return productos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvDesc, tvPrice;
        Button btnAdd;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvProductonombre);
            tvDesc = itemView.findViewById(R.id.tvProductoDesc);
            tvPrice = itemView.findViewById(R.id.tvProductoPrecio);
            btnAdd = itemView.findViewById(R.id.btnAction);
        }
    }
}
