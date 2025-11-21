package com.example.contextualizacion3;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import db.AppDatabase;
import db.Cliente;
import db.ClienteDao;
import db.Compra;
import db.CompraDao;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "CicloVida";
    private EditText etEmail, etPassword;
    private Button btnLogin;
    private TextView tvRegistrar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegistrar = findViewById(R.id.tvRegistrar);

        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String pass = etPassword.getText().toString().trim();

            if (email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Ingrese correo y contraseña", Toast.LENGTH_SHORT).show();
                return;
            }

            new Thread(() -> {
                AppDatabase db = AppDatabase.get(getApplicationContext());
                ClienteDao dao = db.clienteDao();
                Cliente cliente = dao.findByEmail(email);

                long idNuevaCompra;
                boolean loginExitoso = false;
                String mensaje = "";

                if (cliente == null) {
                    idNuevaCompra = 0;
                    mensaje = "Usuario no encontrado";
                } else if (!cliente.password.equals(pass)) {
                    idNuevaCompra = 0;
                    mensaje = "Contraseña incorrecta";
                } else {
                    loginExitoso = true;
                    mensaje = "Inicio de sesión exitoso";

                    Compra compra = new Compra();
                    compra.IdCliente = cliente.id;
                    CompraDao compraDao = db.compraDao();
                    idNuevaCompra = compraDao.insert(compra);
                }

                final boolean finalLoginExitoso = loginExitoso;
                final String finalMensaje = mensaje;

                runOnUiThread(() -> {
                    Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, ProductosListActivity.class);

                    // Enviamos el ID a la siguiente actividad
                    intent.putExtra("ID_COMPRA_ACTUAL", idNuevaCompra);

                    startActivity(intent);
                    finish();
                });

                /*
                runOnUiThread(() -> {
                    Toast.makeText(this, finalMensaje, Toast.LENGTH_SHORT).show();
                    if (finalLoginExitoso) {
                        startActivity(new Intent(this, ProductosListActivity.class));
                        finish();
                    }
                });*/
            }).start();
        });

        tvRegistrar.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, activity_registrar.class);
            startActivity(intent);
            finish();
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
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