package db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ClienteDao {
    @Insert
    void insert(Cliente c);
    @Query("SELECT * FROM Cliente WHERE email=:email LIMIT 1")
    Cliente findByEmail(String email);

    @Query("SELECT * FROM Cliente")
    List<Cliente> getAll();
}
