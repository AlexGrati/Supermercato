package com.example.alex.supermercato;

import java.io.Serializable;

/**
 * Created by utente4.academy on 01/12/2017.
 */

public class Carne extends Prodotto implements Serializable {
    public Carne() {
        super();
    }

    public Carne(String marca, double prezzo) {
        super(marca, prezzo);
    }
}
