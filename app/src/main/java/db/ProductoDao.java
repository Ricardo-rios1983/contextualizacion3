package db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface ProductoDao {
    @Query("SELECT * FROM Producto ORDER BY nombre")
    List<Producto> getAll();

    @Insert
    void insertAll(Producto... products);
    @Insert long insert(Producto p);
    @Update
    void update(Producto p);
    @Delete
    void delete(Producto p);

    @Query("SELECT * FROM Producto WHERE id=:id LIMIT 1")
    Producto findById(int id);
}
