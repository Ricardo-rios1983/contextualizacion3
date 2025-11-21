package db;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class CartRepository {
    private static final List<Producto> cart = new ArrayList<>();
    public static List<Producto> getCart() { return new ArrayList<>(cart); }
    private static CarritoDao dao;

    public static void init(AppDatabase db) {
        dao = db.carritoDao();
    }

    public static void addToCart(Context context, Producto product, long compraId) {
        new Thread(() -> {
            try {
                AppDatabase db = AppDatabase.get(context.getApplicationContext());
                CarritoDao localDao = db.carritoDao();
                CartItem item = localDao.findByProduct((int)compraId,product.id);

                if (item != null) {
                    item.cantidad++;
                    localDao.update(item);
                } else {
                    CartItem nuevo = new CartItem();
                    nuevo.idCompra = (int) compraId;
                    nuevo.productoId = product.id;
                    nuevo.cantidad = 1;
                    localDao.insert(nuevo);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void getCart(Consumer<List<CartItem>> callback) {
        new Thread(() -> {
            List<CartItem> items = dao.getAll();
            callback.accept(items);
        }).start();
    }


    public static void EliminarProductoCarrito(Context context, CartItem cartItem, Consumer<List<CartItem>> callback) {
            new Thread(() -> {
                try {
                    AppDatabase db = AppDatabase.get(context.getApplicationContext());
                    CarritoDao dao = db.carritoDao();
                    dao.delete(cartItem);
                    List<CartItem> items = dao.getAll();
                    callback.accept(items);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
    }
}
