package models;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

public class Cart {
    private int id;
    private ArrayList<Product> products;
    private int total;
    private int discountedTotal;
    private int userId;
    private int totalProducts;
    private int totalQuantity;

    public Cart(int id, ArrayList<Product> products, int total, int discountedTotal, int userId, int totalProducts, int totalQuantity) {
        this.id = id;
        this.products = products;
        this.total = total;
        this.discountedTotal = discountedTotal;
        this.userId = userId;
        this.totalProducts = totalProducts;
        this.totalQuantity = totalQuantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getDiscountedTotal() {
        return discountedTotal;
    }

    public void setDiscountedTotal(int discountedTotal) {
        this.discountedTotal = discountedTotal;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTotalProducts() {
        return totalProducts;
    }

    public void setTotalProducts(int totalProducts) {
        this.totalProducts = totalProducts;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    @NonNull
    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", products=" + products +
                ", total=" + total +
                ", discountedTotal=" + discountedTotal +
                ", userId=" + userId +
                ", totalProducts=" + totalProducts +
                ", totalQuantity=" + totalQuantity +
                '}';
    }
}
