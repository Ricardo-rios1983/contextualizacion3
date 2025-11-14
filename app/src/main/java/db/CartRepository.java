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

    public static void addToCart(Context context, Producto product) {
        new Thread(() -> {
            AppDatabase db = AppDatabase.get(context.getApplicationContext());
            CarritoDao localDao = db.carritoDao();

            try {
                CartItem item = localDao.findByProduct(product.id);

                if (item != null) {
                    item.cantidad++;
                    localDao.update(item);
                } else {
                    CartItem nuevo = new CartItem();
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
}
