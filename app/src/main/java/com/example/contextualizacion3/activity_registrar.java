package com.example.contextualizacion3;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import db.AppDatabase;
import db.Cliente;

public class activity_registrar extends AppCompatActivity {

    private EditText etName, etEmail, etPassword, etConfirmPassword;
    private Button btnRegister;
    private TextView tvGoLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_registrar);

            etName = findViewById(R.id.etName);
            etEmail = findViewById(R.id.etEmail);
            etPassword = findViewById(R.id.etPassword);
            etConfirmPassword = findViewById(R.id.etConfirmPassword);
            btnRegister = findViewById(R.id.btnRegister);
            tvGoLogin = findViewById(R.id.tvGoLogin);

            tvGoLogin.setOnClickListener(v -> {
                startActivity(new Intent(activity_registrar.this, MainActivity.class));
                finish();
            });

            btnRegister.setOnClickListener(v -> {
                String name = etName.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString();
                String confirm = etConfirmPassword.getText().toString();

                if (!validarRegistro(name, email, password, confirm)) {
                    return;
                }

                new Thread(() -> {
                    try {
                        AppDatabase db = AppDatabase.get(getApplicationContext());

                        Cliente existente = db.clienteDao().findByEmail(email);
                        if (existente != null) {
                            runOnUiThread(() -> Toast.makeText(activity_registrar.this, "El correo ya está registrado", Toast.LENGTH_SHORT).show());
                            return;
                        }

                        Cliente nuevo = new Cliente();
                        nuevo.name = name;
                        nuevo.email = email;
                        nuevo.password = password;
                        db.clienteDao().insert(nuevo);

                        runOnUiThread(() -> {
                            Toast.makeText(activity_registrar.this, "Cliente registrado exitosamente", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(activity_registrar.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        });

                    } catch (Exception e) {
                        runOnUiThread(() -> Toast.makeText(activity_registrar.this, "Error al registrar: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                    }
                }).start();
            });
        }
        catch(Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isValidEmail(String s){
        return !TextUtils.isEmpty(s) && Patterns.EMAIL_ADDRESS.matcher(s).matches();
    }

    private boolean validarRegistro(String name, String email, String pass, String confirm){
        if (TextUtils.isEmpty(name)) { etName.setError("Requerido"); return false; }
        if (!isValidEmail(email)) { etEmail.setError("Correo inválido"); return false; }
        if (pass.length() < 6) { etPassword.setError("Mínimo 6 caracteres"); return false; }
        //if (!pass.equals(confirm)) { etConfirm.setError("No coincide"); return false; }
        return true;
    }



}