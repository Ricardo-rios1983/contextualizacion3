package com.example.contextualizacion3;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
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

import db.AppDatabase;
import db.CartRepository;
import db.Producto;

public class ProductosListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductoAdapter adapter;
    private List<Producto> productos = new ArrayList<>();
    private Button btnUbicacion;
    private static final String TAG = "Ciclo Vida";
    private long compraIdActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            EdgeToEdge.enable(this);
            setContentView(R.layout.activity_productos_list);
            btnUbicacion = findViewById(R.id.btnUbicacion);

            compraIdActual = getIntent().getLongExtra("ID_COMPRA_ACTUAL", -1);


            if (compraIdActual == -1) {
                Toast.makeText(this, "Error al cargar la compra", Toast.LENGTH_SHORT).show();
            }

            recyclerView = findViewById(R.id.recyclerProductos);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            adapter = new ProductoAdapter(new ArrayList<>(), product -> {
                new Thread(() -> {
                    CartRepository.addToCart(ProductosListActivity.this, product,compraIdActual);
                }).start();
                Toast.makeText(this, product.nombre + " añadido al carrito", Toast.LENGTH_SHORT).show();
            });

            recyclerView.setAdapter(adapter);

            new Thread(() -> {
                AppDatabase db = AppDatabase.get(this);
                if (db.productoDao().getAll().isEmpty()) {
                    db.productoDao().insertAll(
                            make("Camiseta", "Algodón", 100000),
                            make("Pantalón", "Mezclilla", 150000),
                            make("Zapatillas", "Deportivas", 220000)
                    );
                }

                List<Producto> products = db.productoDao().getAll();
                runOnUiThread(() -> {
                    adapter.setProducts(products);
                    adapter.notifyDataSetChanged();
                });
            }).start();

            btnUbicacion.setOnClickListener(v -> {
                Intent i = new Intent(this, MapsActivity.class);
                startActivity(i);
            });
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
        }
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

    private Producto make(String n, String d, double p){
        Producto pr = new Producto(); pr.nombre=n; pr.descripcion=d; pr.precio=p; return pr;
    }
}