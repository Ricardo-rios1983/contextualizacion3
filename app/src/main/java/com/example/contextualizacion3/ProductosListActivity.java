package com.example.contextualizacion3;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import db.CartRepository;
import db.Producto;

public class ProductosListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductoAdapter adapter;
    private List<Producto> productos = new ArrayList<>();

    private static final String TAG = "Ciclo Vida";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_productos_list);

        recyclerView = findViewById(R.id.recyclerProductos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        productos.add(new Producto(1, "Camiseta", "Camiseta 100% algodón", 100000.0));
        productos.add(new Producto(2, "Pantalón", "Pantalón de mezclilla", 150000.0));
        productos.add(new Producto(3, "Zapatillas", "Zapatillas deportivas", 220000.0));
        productos.add(new Producto(4, "Gorra", "Gorra ajustable de algodón", 80000.0));
        productos.add(new Producto(5, "sudadera", "sudadera de algodón", 400000.0));

        adapter = new ProductoAdapter(productos, product -> {
            CartRepository.addToCart(product);
            Toast.makeText(this, product.nombre + " añadido al carrito", Toast.LENGTH_SHORT).show();
        });

        recyclerView.setAdapter(adapter);
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: La Activity está a punto de ser visible.");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: La Activity está en primer plano y lista para el usuario.");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: La Activity está parcialmente oculta.");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: La Activity ya no es visible.");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: La Activity vuelve a mostrarse después de estar detenida.");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: La Activity se está destruyendo.");
    }

}