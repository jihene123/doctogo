package com.example.salma.doctolib;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
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
import java.util.List;
import java.util.Map;

import static com.example.salma.doctolib.Utils.Constants.URL_ROOT;

public class listTimeActivity extends AppCompatActivity {
    String[] TIMES = new String[100] ;
    Integer[] ID = new Integer[100] ;
    int count = 0 ;
    TextView mydate;
    Button confirmBtn;
    String date,idms;
    TextView msg ;
    ListView listView ;
    Integer idmm;
    TimeListAdapter timeAdapter = new TimeListAdapter();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_time);
        Intent ooIntent = getIntent();
        idms = ooIntent.getStringExtra("idm");

        msg = (TextView) findViewById(R.id.message);
        listView = (ListView) findViewById(R.id.listviewtime);
        confirmBtn = (Button) findViewById(R.id.confirmBtn);
        mydate = (TextView) findViewById(R.id.yourDateText) ;
        date = getIntent().getExtras().getString("date");
        mydate.setText(date);
        attempt() ;

        listView.setAdapter(timeAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                msg.setText("votre rendez-vous est le " + date + " vers " + TIMES[position]);
                    Toast.makeText(listTimeActivity.this, " votre rendez-vous est le " + date + " vers " + TIMES[position], Toast.LENGTH_SHORT).show();

            }
        });

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailIntent = new Intent(getApplicationContext(), AuthentificationActivity.class);
                startActivity(detailIntent);
                finish();
            }
        });
    }
    public void attempt() {
        System.out.println(idms);

        String getTimebyid= URL_ROOT + "getTimeByID.php?id="+ idms+ "&date=" + date ;

        final JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET,
                getTimebyid , null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Toast.makeText(getApplicationContext(),response.getString("message"),Toast.LENGTH_LONG).show();
                            JSONArray timebyID = response.getJSONArray("timebyid");
                            count = timebyID.length() ;
                            for (int i=0; i<timebyID.length(); i++) {
                                JSONObject timeO = timebyID.getJSONObject(i);
                                Integer id = timeO.getInt("id");
                                ID[i]= id;
                                String timee = timeO.getString("heure");
                                TIMES[i] = timee;
                                timeAdapter.notifyDataSetChanged();

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
    public class TimeListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return count;
        }

        @Override
        public Object getItem(int position) {
            return TIMES[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            @SuppressLint({"ViewHolder", "InflateParams"}) View v = getLayoutInflater().inflate(R.layout.item_time, null);
            TextView time = (TextView) v.findViewById(R.id.timeText);
            time.setText(TIMES[position]);
            return v;
        }
    }
}
