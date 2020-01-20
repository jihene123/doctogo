package com.example.salma.doctolib;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.salma.doctolib.Utils.RequestHandler;
import com.example.salma.doctolib.Utils.SharedPrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.salma.doctolib.Utils.Constants.URL_ROOT;

public class ComptePatient extends AppCompatActivity {
    int count = 0 ;
    ImageView changeBtn,appBtn,accBtn , outBtn ;
    String[] M =  new String[100];
    String[] A =  new String[100];
    String[] T =  new String[100];
    String[] D =  new String[100];
    String[] H =  new String[100];
    AppListAdapter appAdapter = new AppListAdapter();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compte_patient);
        changeBtn = (ImageView) findViewById(R.id.changeBtn);
        appBtn = (ImageView) findViewById(R.id.appBtn);
        accBtn = (ImageView) findViewById(R.id.accueil);
        accBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent aaIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(aaIntent);
            }
        });
        changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ooIntent = new Intent(getApplicationContext(), UpdatePatient.class);
                startActivity(ooIntent);
            }
        });
        appBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attempt();
                ListView listView = (ListView) findViewById(R.id.listviewpatient);
                listView.setAdapter(appAdapter);

            }
        });

    }

    public void attempt() {

        final Integer id = SharedPrefManager.getInstance(this).getUserId();
        String getRdvbyid= URL_ROOT + "getRdvByID.php?id="+ id;

        final JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET,
                getRdvbyid , null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Toast.makeText(getApplicationContext(),response.getString("message"),Toast.LENGTH_LONG).show();
                            JSONArray rdvbyid = response.getJSONArray("rdvbyid");
                            count = rdvbyid.length();
                            for (int i=0; i<rdvbyid.length(); i++) {
                                JSONObject rdv = rdvbyid.getJSONObject(i);
                                String med = rdv.getString("nom");
                                M[i]= med;
                                String heure = rdv.getString("heure");
                                H[i] = heure;
                                String date = rdv.getString("date");
                                D[i] = date;
                                String addr= rdv.getString("adresse");
                                A[i] = addr;
                                String tel= rdv.getString("telephone");
                                T[i] = tel;


                            }
                            appAdapter.notifyDataSetChanged();

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
                params.put("id",id.toString());
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest); // bech tetb3ath lel serveur
    }
    public class AppListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return count;
        }

        @Override
        public Object getItem(int position) {
            return M[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            @SuppressLint({"ViewHolder", "InflateParams"}) View v = getLayoutInflater().inflate(R.layout.item_app, null);
            TextView  nomRdv= (TextView) v.findViewById(R.id.nomMedecinRdvText);

            TextView  addRdv= (TextView) v.findViewById(R.id.addRdvText);
            TextView  telRdv= (TextView) v.findViewById(R.id.telRdvText);
            TextView  dateRdv= (TextView) v.findViewById(R.id.dateRdvText);
            TextView  heureRdv= (TextView) v.findViewById(R.id.heureText);

            nomRdv.setText(M[position]);
            addRdv.setText(A[position]);
            telRdv.setText(T[position]);
            dateRdv.setText(D[position]);
            heureRdv.setText(H[position]);

            return v;
        }
    }
}
