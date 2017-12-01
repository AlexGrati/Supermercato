package com.example.alex.supermercato;

import java.io.Serializable;

/**
 * Created by utente4.academy on 01/12/2017.
 */

public class Pesce extends Prodotto implements Serializable {
    public Pesce() {
        super();
    }

    public Pesce(String marca, double prezzo) {
        super(marca, prezzo);
    }
}
