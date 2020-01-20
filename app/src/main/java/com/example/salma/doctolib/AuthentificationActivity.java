package com.example.salma.doctolib;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.salma.doctolib.Utils.Constants;
import com.example.salma.doctolib.Utils.RequestHandler;
import com.example.salma.doctolib.Utils.SharedPrefManager;
import com.example.salma.doctolib.Utils.SharedPrefManagerClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AuthentificationActivity extends AppCompatActivity {
    private EditText memail , mpassword;
    private Button authentificationBtn,registerBtn;
    RadioGroup radioGroup;
    RadioButton radioButton, option1 ,option2;
    TextView textView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentification);
        radioGroup = findViewById(R.id.radioGroup);
        option1 = findViewById(R.id.radio_medecin);
        option2 = findViewById(R.id.radio_client);
        memail = findViewById(R.id.activity_authentification_login_username);
        mpassword = findViewById(R.id.activity_authentification_login_password);
        registerBtn =(Button) findViewById(R.id.goRegisterbtn);
        authentificationBtn =(Button) findViewById(R.id.activity_authentification_login_btn);
        authentificationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (option1.isChecked()){
                    attemptLoginMedecin(v);
                    Intent detIntent = new Intent(getApplicationContext(), CompteMedecin.class);
                    startActivity(detIntent);
                    finish();
                }
                else if (option2.isChecked()){
                    attemptLoginClient(v);
                    Intent detIntent = new Intent(getApplicationContext(), ComptePatient.class);
                    startActivity(detIntent);
                    finish();
                }
            }
        });
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detIntent = new Intent(getApplicationContext(), MedecinOrClient.class);
                startActivity(detIntent);
            }
        });

    }
    private void attemptLoginClient(final View view){
        final String email = memail.getText().toString();
        final String password =mpassword.getText().toString();

        StringRequest stringRequest =  new StringRequest(Request.Method.POST,
                Constants.loginUrlC,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                            if (!jsonObject.getBoolean("error")) {

                                SharedPrefManagerClient.getInstance(getApplicationContext()).patientLogin(
                                        jsonObject.getInt("id"),
                                        jsonObject.getString("nom"),
                                        jsonObject.getString("email"),
                                        "Client",
                                        jsonObject.getString("telephone"),
                                        jsonObject.getString("dateDeNaissance")
                                );
                            }
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
                Map<String ,String > params = new HashMap<>();
                params.put("email",email);
                params.put("password",password);
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

    }
    private void attemptLoginMedecin(View view){

        final String email = memail.getText().toString();
        final String password =mpassword.getText().toString();

        StringRequest stringRequest =  new StringRequest(Request.Method.POST,
                Constants.loginUrlM,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                            if (!jsonObject.getBoolean("error")) {
                                SharedPrefManager.getInstance(getApplicationContext()).userLogin(
                                        jsonObject.getInt("id"),
                                        jsonObject.getString("nom"),
                                        jsonObject.getString("email"),
                                        "Medecin",
                                        jsonObject.getString("telephone"),
                                        jsonObject.getString("adresse"),
                                        jsonObject.getInt("prixDeConsultation")
                                );
                            }
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
                Map<String ,String > params = new HashMap<>();
                params.put("email",email);
                params.put("password",password);
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
    public void checkButton(View v){
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
    }

}
