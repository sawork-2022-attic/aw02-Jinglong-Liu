package com.example.poshell.db;

import com.example.poshell.model.Cart;
import com.example.poshell.model.Item;
import com.example.poshell.model.Product;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class PosInMemoryDB implements PosDB {
    private List<Product> products = new ArrayList<>();

    private Cart cart;

    @Override
    public List<Product> getProducts() {
        return products;
    }

    @Override
    public Cart saveCart(Cart cart) {
        this.cart = cart;
        return this.cart;
    }

    @Override
    public Cart getCart() {
        return this.cart;
    }

    @Override
    public Product getProduct(String productId) {
        for (Product p : getProducts()) {
            if (p.getId().equals(productId)) {
                return p;
            }
        }
        return null;
    }

    @Override
    public boolean removeProductFromCartById(String productId) {
        Iterator iterator = getCart().getItems().iterator();
        while(iterator.hasNext()){
            Item item  = (Item) iterator.next();
            if(item.getProduct().getId().equals(productId)){
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    private PosInMemoryDB() {
        this.products.add(new Product("PD1", "iPhone 13", 8999));
        this.products.add(new Product("PD2", "MacBook Pro", 29499));
    }

}
