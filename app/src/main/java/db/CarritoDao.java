package db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CarritoDao {
    @Query("SELECT * FROM cart_items")
    List<CartItem> getAll();

    @Query("SELECT * FROM cart_items WHERE idcompra = :compra AND productoId = :productId LIMIT 1")
    CartItem findByProduct(int compra, int productId);

    @Insert
    long insert(CartItem c);

    @Update
    void update(CartItem item);

    @Query("DELETE FROM cart_items") void clear();
    @Delete
    void delete(CartItem c);

    @Query("SELECT SUM(p.precio * c.cantidad) FROM cart_items c JOIN Producto p ON p.id = c.productoId")
    Double getTotal();

}
