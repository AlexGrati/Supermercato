package com.example.alex.supermercato;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class InserisciActivity extends AppCompatActivity implements ResponseController{
    private Spinner mSpinner;
    private TextView tMarca;
    private TextView tPrezzo;
    private ResponseController controller;
    private ProgressDialog progressDialog;
    private Magazzino magazzino;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inserisci);
        mSpinner = findViewById(R.id.spinnerId);
        tMarca = findViewById(R.id.editTextMarca);
        tPrezzo = findViewById(R.id.editTextPrezzo);
        controller = this;
        progressDialog = new ProgressDialog(InserisciActivity.this);
    }

    public void onInserisciItemButtonPressed(View v){
        String marca = tMarca.getText().toString();
        String prezzo = tPrezzo.getText().toString();
        if(marca.equals("")||prezzo.equals("")){
            Toast.makeText(getApplicationContext(),
                    "Informazioni Incomplete",
                    Toast.LENGTH_SHORT).show();
            setResult(Activity.RESULT_CANCELED);
        }else{
            progressDialog.setMessage("Salvataggio In Corso...");
            progressDialog.show();

            Prodotto p = null;
            String selectedItem = mSpinner.getSelectedItem().toString();
            if(selectedItem.equals("Latte")){
                p = new Latte(marca, Double.parseDouble(prezzo));
            }
            if(selectedItem.equals("Carne")){
                p = new Carne(marca, Double.parseDouble(prezzo));
            }
            if(selectedItem.equals("Pesce")){
                p = new Pesce(marca, Double.parseDouble(prezzo));
            }

            magazzino = (Magazzino) FileOperations.readObject(getApplicationContext(),"MAGAZZINO");
            List<Prodotto> listaProdotto = null;
            if(magazzino != null)
                listaProdotto = magazzino.getListProdotti();
            else
                magazzino = new Magazzino();

            if(listaProdotto == null)
                listaProdotto = new ArrayList<>();

            setResult(Activity.RESULT_OK);
            listaProdotto.add(0,p);
            magazzino.setListProdotti(listaProdotto);
            FileOperations.writeObject(getApplicationContext(),"MAGAZZINO",magazzino);
            writeObjectToFirebase(p,selectedItem);
        }
    }

    private void writeObjectToFirebase(final Prodotto p,String type){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReferenceFromUrl("https://supermercato-b4c0d.firebaseio.com/Prodotti/"+type);

        FirebaseRestRequests.get("Prodotti/"+type, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    String str = new String(responseBody);
                    String key  = JSONParser.lastUserKey(str);
                    Log.d("DEBUG",""+key);
                    myRef.child(key).child("Marca").setValue(p.getMarca());
                    myRef.child(key).child("Prezzo").setValue(p.getPrezzo());
                    controller.respond();
                    Toast.makeText(getApplicationContext(),
                            "Prodotto Inserito",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if (statusCode >= 400 && statusCode < 500) {
                    controller.respond();
                    Toast.makeText(getApplicationContext(),
                            "Riprova",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void respond() {
        progressDialog.dismiss();
        progressDialog.cancel();
        finish();
    }
}