package com.vitlaovuong.model;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<OrderDetail> items;

    public Cart() {
        items = new ArrayList<>();
    }

    public List<OrderDetail> getItems() {
        return items;
    }

    public void addItem(OrderDetail newItem) {
        for (OrderDetail item : items) {
            if (item.getProductId() == newItem.getProductId()) {
                item.setQuantity(item.getQuantity() + newItem.getQuantity());
                return;
            }
        }
        items.add(newItem);
    }

    public void removeItem(int productId) {
        items.removeIf(item -> item.getProductId() == productId);
    }

    public void updateQuantity(int productId, int quantity) {
        for (OrderDetail item : items) {
            if (item.getProductId() == productId) {
                item.setQuantity(quantity);
                return;
            }
        }
    }

    public double getTotalMoney() {
        double total = 0;
        for (OrderDetail item : items) {
            total += (item.getPrice() * item.getQuantity());
        }
        return total;
    }

    public int getTotalQuantity() {
        int total = 0;
        for (OrderDetail item : items) {
            total += item.getQuantity();
        }
        return total;
    }
}
