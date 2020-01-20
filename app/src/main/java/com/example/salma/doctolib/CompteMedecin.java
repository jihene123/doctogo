package com.example.salma.doctolib;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.salma.doctolib.Utils.Constants;
import com.example.salma.doctolib.Utils.RequestHandler;
import com.example.salma.doctolib.Utils.SharedPrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import static com.example.salma.doctolib.Utils.Constants.URL_ROOT;


public class CompteMedecin extends AppCompatActivity {
    int count = 0 ;
    String[] M =  new String[100];
    String[] H =  new String[100];
    String[] T =  new String[100];
    String cDate ;
    ImageView changeBtn,appBtn;
    AppListAdapter appAdapter = new AppListAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compte_medecin);
        changeBtn = (ImageView) findViewById(R.id.changeBtn);
        appBtn = (ImageView) findViewById(R.id.appBtn);
        Toast.makeText(getApplicationContext(),"WELCOME "+ SharedPrefManager.getInstance(getApplicationContext()).getUserNom(),Toast.LENGTH_LONG).show();

        changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ooIntent = new Intent(getApplicationContext(), UpdateActivity.class);
                startActivity(ooIntent);
            }
        });
        appBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cDate = new SimpleDateFormat("dd/MM/YYYY", Locale.getDefault()).format(new Date());
                Toast.makeText(getApplicationContext(),cDate,Toast.LENGTH_LONG).show();
                attempt();
                ListView listView = (ListView) findViewById(R.id.listviewpatient);
                listView.setAdapter(appAdapter);

            }
        });
    }
    public void attempt() {

        final Integer id = SharedPrefManager.getInstance(this).getUserId();
        String getRdvByDATE = URL_ROOT + "getRdvByDate.php?id="+ id + "&date=" + cDate ;

        final JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET,
                getRdvByDATE , null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Toast.makeText(getApplicationContext(),response.getString("message"),Toast.LENGTH_LONG).show();
                            JSONArray rdvbydate = response.getJSONArray("rdvbydate");
                            count = rdvbydate.length();
                            for (int i=0; i<rdvbydate.length(); i++) {
                                JSONObject rdv = rdvbydate.getJSONObject(i);
                                String nom = rdv.getString("nom");
                                M[i]= nom;
                                String heure = rdv.getString("heure");
                                H[i] = heure;
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
            @SuppressLint({"ViewHolder", "InflateParams"}) View v = getLayoutInflater().inflate(R.layout.item_app_med, null);
            TextView nom= (TextView) v.findViewById(R.id.patientText);

            TextView  tel= (TextView) v.findViewById(R.id.telText);

            TextView  heure= (TextView) v.findViewById(R.id.heureText);

            nom.setText(M[position]);
            tel.setText(T[position]);
            heure.setText(H[position]);

            return v;
        }
    }
}
