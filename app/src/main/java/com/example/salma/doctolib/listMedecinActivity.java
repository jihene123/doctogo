package com.example.salma.doctolib;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
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

import java.util.HashMap;
import java.util.Map;

import static com.example.salma.doctolib.Utils.Constants.URL_ROOT;

public class listMedecinActivity extends AppCompatActivity {
    int count = 0;
    String [] NOM = new String[100];
    String [] SP = new String[100];
    String [] ADRESSE = new String[100];
    Integer [] PRIX =new Integer[100];
    String [] PROCHE =new String[100];
    String specialite,adress;
    ListView listView;
    Integer [] ID = new  Integer[100];
    MedecinListAdapter medecinAdapter = new MedecinListAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_medecin);
        listView = (ListView) findViewById(R.id.listviewmedecin);

        Intent detailIntent = getIntent();
        specialite = detailIntent.getStringExtra("sp");
        adress = detailIntent.getStringExtra("ad");
        attempt();
        listView.setAdapter(medecinAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String iddd=ID[position].toString();
                Intent ooIntent = new Intent(getApplicationContext(), ProfileActivity.class);
                ooIntent.putExtra("idm",iddd);
                startActivity(ooIntent);
            }
        });

    }

    public void attempt() {
        String getMedBySp;
        if (! adress.equals(""))
        {getMedBySp= URL_ROOT + "getMedBySpecialite.php?specialite="+specialite + "&adresse=" + adress ;}
        else
        {getMedBySp= URL_ROOT + "getMedBySpecialite.php?specialite="+specialite ;}

        final JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET,
                getMedBySp , null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Toast.makeText(getApplicationContext(),response.getString("message"),Toast.LENGTH_LONG).show();
                            JSONArray medbySp = response.getJSONArray("medbysp");
                            count = medbySp.length();
                            for (int i=0; i<medbySp.length(); i++) {
                                JSONObject med = medbySp.getJSONObject(i);
                                Integer id = med.getInt("id");
                                ID[i]= id;
                                String nom = med.getString("nom");
                                NOM[i] = nom;
                                SP[i] = specialite;
                                ADRESSE[i] = adress;
                                Integer prix = med.getInt("prixDeConsultation");
                                PRIX[i] = prix;
                                PROCHE[i]="j";
                            }
                            medecinAdapter.notifyDataSetChanged();
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
                params.put("specialite",specialite);
                params.put("adresse",adress);
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest); // bech tetb3ath lel serveur
    }
    public class MedecinListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return count;
        }

        @Override
        public Object getItem(int position) {
            return NOM[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            @SuppressLint({"ViewHolder", "InflateParams"}) View v = getLayoutInflater().inflate(R.layout.item_medecin, null);
            TextView nomA = (TextView) v.findViewById(R.id.nomMedecinEdit);
            TextView specialitegA = (TextView) v.findViewById(R.id.specialiteEdit);
            TextView prixA = (TextView) v.findViewById(R.id.phoneEdit);
            TextView adresseA = (TextView) v.findViewById(R.id.addEdit);
            TextView prochdispoA = (TextView) v.findViewById(R.id.dateDispoEdit);
            nomA.setText(NOM[position]);
            specialitegA.setText(SP[position]);
            String spA = specialitegA.getText().toString().trim();
            adresseA.setText(ADRESSE[position]);
            prixA.setText(String.valueOf(PRIX[position]));
            prochdispoA.setText(PROCHE[position]);
            return v;
        }
    }


}
