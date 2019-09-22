package com.example.hospitals;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class InfoPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String nameOfPlace1 = bundle.getString("nameOfPlace");
        String vicinity1 = bundle.getString("vicinity");
        String dist = bundle.getString("dist");

        TextView nameOfPlace = (TextView)findViewById(R.id.nameOfPlace);
        TextView vicinity = (TextView)findViewById(R.id.vicinity);
        TextView latlng = (TextView)findViewById(R.id.latlng);
        nameOfPlace.setText(nameOfPlace1);
        vicinity.setText(vicinity1);
        latlng.setText(dist + " KM");


    }
}
