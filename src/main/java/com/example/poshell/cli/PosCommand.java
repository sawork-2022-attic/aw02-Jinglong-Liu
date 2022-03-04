package com.example.poshell.cli;

import com.example.poshell.biz.PosService;
import com.example.poshell.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class PosCommand {

    private PosService posService;

    @Autowired
    public void setPosService(PosService posService) {
        this.posService = posService;
    }

    @ShellMethod(value = "List Products", key = "p")
    public String products() {
        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;
        for (Product product : posService.products()) {
            stringBuilder.append("\t").append(++i).append("\t").append(product).append("\n");
        }
        return stringBuilder.toString();
    }

    @ShellMethod(value = "New Cart", key = "n")
    public String newCart() {
        return posService.newCart() + " OK";
    }

    @ShellMethod(value = "Add a Product to Cart", key = "a")
    public String addToCart(String productId, int amount) {
        if (posService.add(productId, amount)) {
            return posService.getCart().toString();
        }
        return "ERROR";
    }
    @ShellMethod(value = "Empty Cart", key = "e")
    public String emptyCart() {
        posService.clearCart();
        return posService.getCart().toString();
    }
    @ShellMethod(value = "List Cart", key = "l")
    public String listCart() {
        if(posService.getCart() == null){
            return "Null. Using 'n' to new a cart.";
        }
        return posService.getCart().toString();
    }
    @ShellMethod(value = "Remove Product", key = "d")
    public String removeProduct(String id) {
        if(posService.getCart() == null){
            return "NO Cart";
        }
        if(posService.removeProductFromCartById(id)){
            return "Removed";
        }
        return "ERROR";
    }
    @ShellMethod(value = "Modify Product Count", key = "m")
    public String modifyProductCount(String id,Integer amount) {
        if(posService.getCart() == null){
            return "NO Cart";
        }
        if(posService.modifyProductCount(id,amount)){
            return "Modify." + posService.getCart().toString();
        }
        else{
            return "ERROR";
        }
    }
}
