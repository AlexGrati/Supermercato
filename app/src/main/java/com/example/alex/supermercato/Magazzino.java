package com.example.alex.supermercato;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by utente4.academy on 01/12/2017.
 */

public class Magazzino implements Serializable {
    List<Prodotto> listProdotti;

    public Magazzino(){
        this.listProdotti = new ArrayList<>();
    }
    public Magazzino(List<Prodotto> listProdotti) {
        this.listProdotti = listProdotti;
    }

    public List<Prodotto> getListProdotti() {
        return listProdotti;
    }

    public void setListProdotti(List<Prodotto> listProdotti) {
        this.listProdotti = listProdotti;
    }
}
