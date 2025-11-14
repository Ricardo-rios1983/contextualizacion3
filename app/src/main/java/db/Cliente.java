package db;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "Cliente",
        indices = {@Index(value = {"email"}, unique = true)})
public class Cliente {
    @PrimaryKey(autoGenerate = true) public int id;
    @NonNull
    public String name;
    @NonNull public String email;
    @NonNull public String password;
}
