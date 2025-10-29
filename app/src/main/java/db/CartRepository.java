package db;

import java.util.ArrayList;
import java.util.List;

public class CartRepository {
    private static final List<Producto> cart = new ArrayList<>();
    public static void addToCart(Producto p) { cart.add(p); }

    public static void removeFromCart(Producto p) { cart.remove(p); }

    public static List<Producto> getCart() { return new ArrayList<>(cart); }
}
