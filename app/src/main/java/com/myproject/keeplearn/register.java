package com.myproject.keeplearn;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class register extends AppCompatActivity {

    public static final String SHARED_PREFS = "sharedPrefs";

    Button btn_register;
    private EditText tv_FName,tv_LName,tv_email,tv_confirmPass,tv_pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        tv_FName=findViewById(R.id.input_firstname);
        tv_LName=findViewById(R.id.input_lastname);
        tv_email=findViewById(R.id.input_emailid);
        tv_pass=findViewById(R.id.input_pass);
        tv_confirmPass=findViewById(R.id.confirm_input_pass);

        btn_register=findViewById(R.id.btn_login);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeAPIcall();
            }
        });
    }

    public void Login(View view){
        Intent intent=new Intent(this,login.class);
        startActivity(intent);
        finish();
    }
    private void storeAndLaunch(String idstr,String pdstr,String FirstName, String LastName) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("id", idstr);
        editor.putString("password", pdstr);
        editor.putString("FirstName",FirstName);
        editor.putString("LastName",LastName);
        editor.apply();
        Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show();

        Intent intent=new Intent(this,DashboardActivity.class);
        startActivity(intent);
        finish();
    }

    void makeAPIcall(){
        final String FName,LName,Email,Pass,CPass;
//        FName=LName=Email=Pass=CPass="";
        FName=tv_FName.getText().toString();
        LName=tv_LName.getText().toString();
        Email=tv_email.getText().toString();
        Pass=tv_pass.getText().toString();
        CPass=tv_pass.getText().toString();

        RequestQueue queue= Volley.newRequestQueue(this);
        Map<String,String> creds=new HashMap<String,String>();
        creds.put("Id",Email);
        creds.put("FirstName",FName);
        creds.put("LastName",LName);
        creds.put("Password",Pass);
        JSONObject postJson=new JSONObject(creds);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        String url="https://apj-learning.herokuapp.com/students/signup";
        JsonObjectRequest objectRequest =new JsonObjectRequest(Request.Method.POST, url, postJson, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getString("status")=="success"){
                        Toast.makeText(register.this, "You are registered", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        storeAndLaunch(Email,Pass,FName,LName);
                    }
                } catch (JSONException e) {
                    Toast.makeText(register.this, "Server Error", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(register.this, "Error from Volley", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
        queue.add(objectRequest);
    }
}
