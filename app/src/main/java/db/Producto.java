package db;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Producto")
public class Producto {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @NonNull
    public String nombre;
    public String descripcion;
    public double precio;
}
