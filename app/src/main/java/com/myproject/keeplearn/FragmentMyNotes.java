package com.myproject.keeplearn;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FragmentMyNotes extends Fragment  implements customAdapter.onIemClickListener  {
    private RecyclerView.Adapter adapter;
    private ArrayList<Course> CoursesList=new ArrayList<Course>();
    public static final String SHARED_PREFS = "sharedPrefs";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview =inflater.inflate(R.layout.fragment_mycourse, container, false);
        RecyclerView recyclerView = (RecyclerView) rootview.findViewById(R.id.recyclerView);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARED_PREFS, 0);
        String a =sharedPreferences.getString("id", "");
        //setting the layout manager, most important thing
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        spacingItemDecorator itemDecorator =new spacingItemDecorator(30);
        recyclerView.addItemDecoration(itemDecorator);
        adapter=new customAdapter(CoursesList,getContext(),this);
        recyclerView.setAdapter(adapter);
        makeApiCall(a);
        return rootview;
    }

    @Override
    public void onItemClicked(int position) {

        Intent intent = new Intent(getActivity(), eachCourseDetail.class);
        intent.putExtra("course",CoursesList.get(position));
        startActivity(intent);
    }
    private void makeApiCall(String a) {

        //making progress bar
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        //ends here
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url ="https://apj-learning.herokuapp.com/myCourses";
        CoursesList.clear();
        Map<String,String> paramId=new HashMap<String,String>();
        paramId.put("EmailId",a);
        Toast.makeText(getActivity(), a, Toast.LENGTH_SHORT).show();
        Log.v("id",a);
        JSONObject paramJson=new JSONObject(paramId);


        JsonObjectRequest objectRequest =new JsonObjectRequest(Request.Method.POST, url, paramJson, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                int noOfCourses=response.length();
                JSONObject temp = null;
//                Toast.makeText(Explore.this, response.toString(), Toast.LENGTH_SHORT).show();
                Toast.makeText(getContext(), Integer.toString(noOfCourses), Toast.LENGTH_SHORT).show();
                for(int i=0;i<noOfCourses;i++){
                    try {
                        temp=response.getJSONObject("course"+Integer.toString(i+1));
                        String codeStr= null;
                        try {
                            codeStr = temp.getString("Code");
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }
                        String name= null;
                        try {
                            name = temp.getString("Name");
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }
                        String html_url= null;
                        try {
                            html_url = temp.getString("imageUrl");
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }
                        String instructorId= null;
                        try {
                            instructorId = temp.getString("InstructorId");
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }
                        String description= null;
                        try {
                            description = temp.getString("Description");
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }
                        CoursesList.add(new Course(codeStr,name,html_url,instructorId,description));
                    } catch (JSONException e) {
                        Toast.makeText(getContext(), "error in parsing", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }
//                    Toast.makeText(Explore.this,temp.toString(), Toast.LENGTH_LONG).show();
                }
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Error from Volley", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
        // Add the request to the RequestQueue.
        queue.add(objectRequest);
        Toast.makeText(getActivity(), Integer.toString(CoursesList.size()), Toast.LENGTH_SHORT).show();

    }
}
