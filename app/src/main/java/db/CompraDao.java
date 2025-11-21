package db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CompraDao {
    @Query("SELECT * FROM compra")
    List<Compra> getAll();

    @Query("SELECT * FROM compra WHERE IdCompra = :id")
    Compra getById(int id);

    @Insert
    long insert(Compra compra);

    @Update
    void update(Compra compra);

    @Delete
    void delete(Compra compra);
}
