package com.example.salma.doctolib.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


public class SharedPrefManagerClient {

    private static SharedPrefManagerClient mInstance;
    private static Context mCtx;

    private static final String SHARED_PREF_NAME = "mysharedpref12";
    private static final String KEY_USER_NOM = "nom";
    private static final String KEY_USER_EMAIL = "email";
    private static final String KEY_USER_ID = "id";
    private static final String KEY_USER_TYPE = "type";
    private static final String KEY_USER_TEL = "tel";
    private static final String KEY_USER_DATE = "date";

    private SharedPrefManagerClient(Context context) {
        mCtx = context;

    }

    public static synchronized SharedPrefManagerClient getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManagerClient(context);
        }
        return mInstance;
    }

    public boolean patientLogin(int id, String nom, String email, String type,String tel, String date){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(KEY_USER_ID, id);
        editor.putString(KEY_USER_EMAIL, email);
        editor.putString(KEY_USER_NOM, nom);
        editor.putString(KEY_USER_TYPE,type);
        editor.putString(KEY_USER_TEL,tel);
        editor.putString(KEY_USER_DATE,date);
        editor.apply();

        return true;
    }

    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if(sharedPreferences.getString(KEY_USER_EMAIL, null) != null){
            return true;
        }
        return false;
    }

    public boolean logout(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }


    public String getUserNom(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_NOM, null);
    }

    public String getUserEmail(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_EMAIL, null);
    }
    public String getUserType(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_TYPE, null);
    }
    public Integer getUserId(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_USER_ID, -1);
    }
    public String getUserDate(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_DATE, null);
    }
    public String getUserTel(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_TEL, null);
    }

}