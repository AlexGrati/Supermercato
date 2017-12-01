package com.example.alex.supermercato;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.Iterator;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class RegistratiActivity extends AppCompatActivity implements ResponseController {
    private TextView tUsername;
    private SharedPreferences preferences;
    private ProgressDialog progressDialog;
    private ResponseController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrati);

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        tUsername = findViewById(R.id.editTextInsertUsername);
        controller = this;
        progressDialog = new ProgressDialog(RegistratiActivity.this);
    }

    public void onSalvaUtenteButtonPressed(View view){
        String data = tUsername.getText().toString();
        if(!data.equals("")) {
            progressDialog.setMessage("Salvataggio In Corso...");
            progressDialog.show();
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("USER", data);
            editor.apply();
            writeUserToFirebase(data, controller);
            setResult(Activity.RESULT_OK);
        }else {
            Toast.makeText(getApplicationContext(),
                    "Devi inseriri un Username per registrarti!!!",
                    Toast.LENGTH_SHORT).show();
            setResult(Activity.RESULT_CANCELED);
        }
    }

    private void writeUserToFirebase(final String username,final ResponseController controller){
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReferenceFromUrl("https://supermercato-b4c0d.firebaseio.com/Users");
        FirebaseRestRequests.get("Users", null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    String str = new String(responseBody);
                    int i = JSONParser.lastUserKey(str);
                    Log.d("DEBUG",""+i);
                    String key = "";
                    if(i< 10){
                        key = "0"+i;
                    }else {
                        key = ""+i;
                    }
                    controller.respond();
                    myRef.child(key).setValue(username);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                controller.respond();
                Toast.makeText(getApplicationContext(),
                        "Riprova",
                        Toast.LENGTH_SHORT).show();
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
