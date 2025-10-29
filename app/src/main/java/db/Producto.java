package db;

public class Producto {
    public int id;
    public String nombre;
    public String descripcion;
    public double precio;


    public Producto(int id, String name, String descripcion, double precio) {
        this.id = id;
        this.nombre = name;
        this.descripcion = descripcion;
        this.precio = precio;
    }
}
