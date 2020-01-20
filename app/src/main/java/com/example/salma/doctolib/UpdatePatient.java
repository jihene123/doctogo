package com.example.salma.doctolib;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.salma.doctolib.Utils.RequestHandler;
import com.example.salma.doctolib.Utils.SharedPrefManagerClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.salma.doctolib.Utils.Constants.URL_ROOT;

public class UpdatePatient extends AppCompatActivity {

    EditText nom , adr ,  email , tel , prix;
    String nomA, emailA , adrA , telA , prixA ;
    Button confirmBtn , annuleBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_patient);

        nom = (EditText ) findViewById(R.id.nomP_edt);
        email = (EditText ) findViewById(R.id.emailP_edt);
        tel = (EditText ) findViewById(R.id.phoneP_edt);

        nom.setText(SharedPrefManagerClient.getInstance(this).getUserNom());
        email.setText(SharedPrefManagerClient.getInstance(this).getUserEmail());
        tel.setText(SharedPrefManagerClient.getInstance(this).getUserTel());

        confirmBtn = findViewById(R.id.details_action_add_btn);
        annuleBtn = findViewById(R.id.details_action_annuler_btn);



        nomA = nom.getText().toString();
        emailA = email.getText().toString();
        telA = tel.getText().toString();

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatep();
            }
        });
        annuleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent i = new Intent(getApplicationContext(), ComptePatient.class);
                    startActivity(i);
                    finish();
            }
        });
    }
    public void updatep(){
        nomA = nom.getText().toString();
        emailA = email.getText().toString();
        telA = tel.getText().toString();


        String urla= URL_ROOT +"updatePatientInfo.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                urla,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();

                    }
                }
        ){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String , String> paramas = new HashMap<>();
                paramas.put("id",SharedPrefManagerClient.getInstance(getApplicationContext()).getUserId().toString());
                paramas.put("name",nomA);
                paramas.put("email",emailA);
                paramas.put("tel",telA);
                return paramas;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
        SharedPrefManagerClient.getInstance(this).patientLogin(
                SharedPrefManagerClient.getInstance(getApplicationContext()).getUserId(),nomA,emailA,
                "Client",telA,SharedPrefManagerClient.getInstance(getApplicationContext()).getUserDate());
    }
}
