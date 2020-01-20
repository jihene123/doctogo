package com.example.salma.doctolib;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MedecinOrClient extends AppCompatActivity {
    Button clientbtn , medecinbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medecin_or_client);
        clientbtn = (Button) findViewById(R.id.clientBtn);
        medecinbtn = (Button) findViewById(R.id.medecinBtn);
        clientbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detIntent = new Intent(getApplicationContext(), RegisterClient.class);
                startActivity(detIntent);
                finish();
            }
        });
        medecinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detIntent = new Intent(getApplicationContext(), RegisterMedecin.class);
                startActivity(detIntent);
                finish();
            }
        });

    }
}
