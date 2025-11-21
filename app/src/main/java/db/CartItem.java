package db;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "cart_items",
        foreignKeys = @ForeignKey(entity = Producto.class,
                parentColumns = "id", childColumns = "productoId",
                onDelete = ForeignKey.CASCADE),
        indices = {@Index("productoId")})
public class CartItem {
    @PrimaryKey(autoGenerate = true) public int id;

    public int idCompra;
    public int productoId;
    public int cantidad;
}
