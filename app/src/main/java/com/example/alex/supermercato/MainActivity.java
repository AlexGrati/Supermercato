package com.example.alex.supermercato;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView textViewUser;
    private SharedPreferences preferences;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        textViewUser = findViewById(R.id.textViewUser);
        textViewUser.setText(preferences.getString("USER","Ospite"));
    }

    public void onRegistratiButtonPressed(View v){
        intent = new Intent(this,RegistratiActivity.class);
        startActivityForResult(intent,1);
    }

    public void onMostraProdottiButtonPressed(View v){
        intent = new Intent(this,MostraProdottiActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode == Activity.RESULT_OK){
                textViewUser.setText(preferences.getString("USER","Ospite"));
            }
        }
    }


}
