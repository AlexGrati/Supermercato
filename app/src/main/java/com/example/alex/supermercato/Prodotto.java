package com.example.alex.supermercato;

import java.io.Serializable;

/**
 * Created by utente4.academy on 01/12/2017.
 */

public class Prodotto implements Serializable{
    public String marca;
    public double prezzo;

    public Prodotto(){
        this.marca = null;
        this.prezzo = 0.0;
    }

    public Prodotto(String marca, double prezzo) {
        this.marca = marca;
        this.prezzo = prezzo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }
}
