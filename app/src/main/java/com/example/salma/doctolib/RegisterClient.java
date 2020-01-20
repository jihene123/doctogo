package com.example.salma.doctolib;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.salma.doctolib.Utils.RequestHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.salma.doctolib.Utils.Constants.registerUrlC;

public class RegisterClient extends AppCompatActivity {

    private EditText et_nom,et_email,et_tel,et_pass, et_cpass,et_date;
    private String nom,email,tel,pass,cpass,date;
    Button RegisterBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_client);
        et_nom = (EditText) findViewById(R.id.nomEdit);
        et_email = (EditText) findViewById(R.id.emailEdit);
        et_tel = (EditText) findViewById(R.id.telEdit);
        et_pass = (EditText) findViewById(R.id.passEdit);
        et_cpass = (EditText) findViewById(R.id.cpassEdit);
        et_date = (EditText) findViewById(R.id.dateEdit) ;
        RegisterBtn = (Button) findViewById(R.id.registerBtn);
        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }
    public void register(){
        intialize();
        if(!validate()){
            Toast.makeText(this,"Signup has Failed",Toast.LENGTH_SHORT).show();
        }
        else {
            onSignupSuccess();
        }
    }
    public void onSignupSuccess(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                registerUrlC,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                            if (!jsonObject.getBoolean("error")){
                                Intent deIntent = new Intent(getApplicationContext() , ComptePatient.class);
                                startActivity(deIntent);
                                finish();
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
                Map<String,String> params = new HashMap<>();
                params.put("email",email);
                params.put("password",pass);
                params.put("telephone",tel);
                params.put("nom",nom);
                params.put("dateDeNaissance",date);
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest); // bech tetb3ath lel serveur

    }
    public boolean validate(){
        boolean valid = true ;
        if(nom.isEmpty() || nom.length()>45) {
            et_nom.setError("Please Enter valid name");
            valid= false;
        }
        if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            et_email.setError("Please Enter Valid Email Address");
            valid = false;
        }
        if(pass.isEmpty() || pass.length()<8) {
            et_pass.setError("Please Enter Valid Password");
            valid = false;
        }
        if(cpass.isEmpty() || !cpass.equals(pass)) {
            et_cpass.setError("Please Repeat the password correctly");
            valid = false;
        }
        if(tel.isEmpty()) {
            et_tel.setError("Please Enter Phone Number");
            valid = false;
        }
        if(date.isEmpty()) {
            et_date.setError("Please Enter your Birth date");
            valid = false;
        }
        return valid ;
    }
    public void intialize(){
        nom = et_nom.getText().toString().trim();
        email = et_email.getText().toString().trim();
        tel = et_tel.getText().toString().trim();
        pass = et_pass.getText().toString().trim();
        cpass = et_cpass.getText().toString().trim();
        date= et_date.getText().toString().trim();
    }
}
