package com.example.alex.supermercato;

import java.io.Serializable;

/**
 * Created by utente4.academy on 01/12/2017.
 */

public class Latte extends Prodotto implements Serializable {
    public Latte() {
        super();
    }

    public Latte(String marca, double prezzo) {
        super(marca, prezzo);
    }
}
