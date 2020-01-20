package com.example.salma.doctolib;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    EditText ss,dd;
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button searchButton =(Button) findViewById(R.id.activity_main_search_btn);
        ss = (EditText) findViewById(R.id.specialiteSearchViewEdit);
        dd = (EditText) findViewById(R.id.activity_main_location_searchView);
        img = (ImageView) findViewById(R.id.personImage);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchSpecial = ss.getText().toString();
                String add = dd.getText().toString();
                Intent detailIntent = new Intent(getApplicationContext(), listMedecinActivity.class);
                detailIntent.putExtra("sp",searchSpecial);
                detailIntent.putExtra("ad",add);
                startActivity(detailIntent);
            }
        });
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent deIntent = new Intent(getApplicationContext() , AuthentificationActivity.class);
                startActivity(deIntent);
                finish();
            }
        });
    }
}
