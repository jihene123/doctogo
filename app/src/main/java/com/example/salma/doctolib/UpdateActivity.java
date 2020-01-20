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
import com.example.salma.doctolib.Utils.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.salma.doctolib.Utils.Constants.URL_ROOT;

public class UpdateActivity extends AppCompatActivity {
    EditText nom , adr ,  email , tel , prix;
    String nomA, emailA , adrA , telA , prixA ;
    Button confirmBtn , annuleBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);


        nom = (EditText ) findViewById(R.id.nom_edt);
        email = (EditText ) findViewById(R.id.email_edt);
        adr = (EditText ) findViewById(R.id.addrr_edt);
        tel = (EditText ) findViewById(R.id.phone_edt);
        prix = (EditText ) findViewById(R.id.consultation_prix_edt);
        confirmBtn = findViewById(R.id.details_action_add_btn);
        annuleBtn = findViewById(R.id.details_action_annuler_btn);

        nom.setText(SharedPrefManager.getInstance(this).getUserNom());
        email.setText(SharedPrefManager.getInstance(this).getUserEmail());
        adr.setText(SharedPrefManager.getInstance(this).getUserAdresse());
        tel.setText(SharedPrefManager.getInstance(this).getUserTel());
        prix.setText(SharedPrefManager.getInstance(this).getUserPrix().toString());

        confirmBtn = findViewById(R.id.details_action_add_btn);
        annuleBtn = findViewById(R.id.details_action_annuler_btn);



        nomA = nom.getText().toString();
        emailA = email.getText().toString();
        adrA = adr.getText().toString();
        telA = tel.getText().toString();
        prixA = prix.getText().toString();

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });
        annuleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent i = new Intent(getApplicationContext(), CompteMedecin.class);
                    startActivity(i);
                    finish();
            }
        });
    }
    public void update(){
        nomA = nom.getText().toString();
        emailA = email.getText().toString();
        adrA = adr.getText().toString();
        telA = tel.getText().toString();
        prixA = prix.getText().toString();


        String urla= URL_ROOT +"updateUserInfo.php";
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
                paramas.put("id",SharedPrefManager.getInstance(getApplicationContext()).getUserId().toString());
                paramas.put("name",nomA);
                paramas.put("adresse",adrA);
                paramas.put("email",emailA);
                paramas.put("tel",telA);
                paramas.put("prix",prixA);
                return paramas;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
        SharedPrefManager.getInstance(this).userLogin(
                SharedPrefManager.getInstance(getApplicationContext()).getUserId(),nomA,emailA,
                "Medecin",telA,adrA,Integer.parseInt(prixA));
    }
}
