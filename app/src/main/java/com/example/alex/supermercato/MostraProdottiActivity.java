package com.example.alex.supermercato;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MostraProdottiActivity extends AppCompatActivity implements ResponseController{
    private List<Prodotto> listaProdotti;
    private ResponseController controller;
    private ProgressDialog progressDialog;
    private CardAdapter cardAdapter;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView;
    private Magazzino magazzino;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostra_prodotti);

        recyclerView = findViewById(R.id.recyclerId);
        recyclerView.setHasFixedSize(true);

        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        controller = this;
        listaProdotti = null;
        magazzino = (Magazzino) FileOperations.readObject(getApplicationContext(),"MAGAZZINO");
        if(magazzino != null )
            listaProdotti = magazzino.getListProdotti();
        else
            magazzino = new Magazzino();

        if(listaProdotti == null ){
            progressDialog = new ProgressDialog(MostraProdottiActivity.this);
            progressDialog.setMessage("Carricamento In Corso...");
            progressDialog.show();
            getData("");
        }else{
            cardAdapter = new CardAdapter(listaProdotti,getApplicationContext());
            recyclerView.setAdapter(cardAdapter);
        }
    }


    public void getData(String string){
        FirebaseRestRequests.get("Prodotti", null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    String str = new String(responseBody);
                    listaProdotti = JSONParser.getListaProdotti(str);
                    magazzino.setListProdotti(listaProdotti);
                    FileOperations.writeObject(getApplicationContext(),"MAGAZZINO",magazzino);
                    cardAdapter = new CardAdapter(listaProdotti,getApplicationContext());
                    recyclerView.setAdapter(cardAdapter);
                    controller.respond();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if (statusCode >= 400 && statusCode < 500) {
                    Toast.makeText(getApplicationContext(),
                            "Riprova",
                            Toast.LENGTH_SHORT).show();
                    controller.respond();
                }
            }
        });
    }

    @Override
    public void respond() {
        progressDialog.dismiss();
        progressDialog.cancel();
    }

    public void onInserisciButtonPressed(View v){
        Intent i = new Intent(getApplicationContext(),InserisciActivity.class);
        startActivityForResult(i,2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 2){
            if(resultCode == Activity.RESULT_OK){
                magazzino = (Magazzino) FileOperations.readObject(getApplicationContext(),"MAGAZZINO");
                listaProdotti = magazzino.getListProdotti();
                cardAdapter = new CardAdapter(listaProdotti,getApplicationContext());
                recyclerView.setAdapter(cardAdapter);
            }
        }
    }
}
