package com.myproject.keeplearn;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

public class login extends AppCompatActivity {

    private EditText id,pd;
    private String FirstName="",LastName="";
    public static final String SHARED_PREFS = "sharedPrefs";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        id=(EditText) findViewById(R.id.et_StudentId);
        pd=(EditText) findViewById(R.id.et_Password);
    }

    public void Signin(View view){
        String idStr=id.getText().toString();
        String pdStr=pd.getText().toString();
        checkUser(idStr,pdStr);
    }

    private void storeAndLaunch(String idstr,String pdstr) {

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

    private void checkUser(final String idstr,final String pdstr) {
        FirstName="";
        LastName="";
        RequestQueue queue= Volley.newRequestQueue(this);
        Map<String,String> creds=new HashMap<String,String>();
        creds.put("EmailId",idstr);
        creds.put("Password",pdstr);
        JSONObject postJson=new JSONObject(creds);
        Toast.makeText(this, postJson.toString(), Toast.LENGTH_SHORT).show();
        String url="https://apj-learning.herokuapp.com/signin";
        //making progress bar
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        // progress bar ends here
        JsonObjectRequest objectRequest =new JsonObjectRequest(Request.Method.POST, url, postJson, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
//                result=response;
                try {
                    FirstName=response.getString("FirstName");
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
                try {
                    LastName=response.getString("LastName");
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
                Toast.makeText(login.this, response.toString(), Toast.LENGTH_SHORT).show();
                if(FirstName.length()!=0 && LastName.length()!=0){
                    storeAndLaunch(idstr,pdstr);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(login.this, "Error from Volley", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
        queue.add(objectRequest);
    }

}
