package com.example.salma.doctolib;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.salma.doctolib.Utils.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.example.salma.doctolib.Utils.Constants.URL_ROOT;

public class ProfileActivity extends AppCompatActivity  {
    TextView e_nom , e_sp , e_phone, e_addr , e_email , e_prochD;
    Integer idmm ;
     String idms ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Button button = (Button) findViewById(R.id.activity_main_search_btn);
        Intent ooIntent = getIntent();
        idms = ooIntent.getStringExtra("idm");



        e_nom = (TextView) findViewById(R.id.nomMedecinEdit) ;
        e_sp = (TextView) findViewById(R.id.specialiteEdit) ;
        e_phone = (TextView) findViewById(R.id.phoneEdit) ;
        e_addr = (TextView) findViewById(R.id.addEdit) ;
        e_email = (TextView) findViewById(R.id.emailEdit) ;
        e_prochD = (TextView) findViewById(R.id.dateDispoEdit) ;
        attempt();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idms1 = idms;
                Intent detIntent = new Intent(getApplicationContext(), CalendarActivity.class);
                detIntent.putExtra("idm",idms1);

                startActivity(detIntent);
                finish();
            }
        });
    }

    public void attempt() {
        String getMedByID= URL_ROOT + "getMedByID.php?id="+idms;

        final JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET,
                getMedByID, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Toast.makeText(getApplicationContext(),response.getString("message"),Toast.LENGTH_LONG).show();
                            JSONArray medbyID = response.getJSONArray("medbyid");
                            for (int i=0; i<medbyID.length(); i++) {
                                JSONObject medid = medbyID.getJSONObject(i);

                                String nom = medid.getString("nom");
                                String specialite = medid.getString("specialite");
                                String addr = medid.getString("adresse");
                                String  tel = medid.getString("telephone");
                                String email = medid.getString("email");
                                String poche="samedi";

                                e_nom.setText(nom);
                                e_sp.setText(specialite);
                                e_addr.setText(addr);
                                e_phone.setText(tel);
                                e_email.setText(email);
                                e_prochD.setText(poche);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error != null) {
                            Toast.makeText(getApplicationContext(), "Something went wrong.", Toast.LENGTH_LONG).show();
                        }}

                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("id",idms);
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest); // bech tetb3ath lel serveur
    }
}
