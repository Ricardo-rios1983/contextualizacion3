package db;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Compra")
public class Compra {
    @PrimaryKey(autoGenerate = true)
    public int idCompra;
    public int IdCliente;
}
