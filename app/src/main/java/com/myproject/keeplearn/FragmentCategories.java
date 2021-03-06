package com.myproject.keeplearn;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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


public class FragmentCategories extends Fragment implements customAdapter.onIemClickListener  {
    private RecyclerView.Adapter adapter;
    private ArrayList<Course> CoursesList=new ArrayList<Course>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview =inflater.inflate(R.layout.fragment_categories, container, false);
        RecyclerView recyclerView = (RecyclerView) rootview.findViewById(R.id.recyclerView);

//setting the layout manager, most important thing
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        spacingItemDecorator itemDecorator =new spacingItemDecorator(30);
        recyclerView.addItemDecoration(itemDecorator);
        adapter=new customAdapter(CoursesList,getContext(),this);
        recyclerView.setAdapter(adapter);

        makeApiCall();
        return rootview;
    }

    @Override
    public void onItemClicked(int position) {

        Intent intent = new Intent(getActivity(), eachCourseDetail.class);
        intent.putExtra("course",CoursesList.get(position));
        startActivity(intent);
    }
    private void makeApiCall() {
        //making progress bar
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        //ends here
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url ="https://apj-learning.herokuapp.com/allCourses";
        CoursesList.clear();
//        Map<String,String> paramId=new HashMap<String,String>();
//        paramId.put("StudentId",a);
//        Toast.makeText(getActivity(), a, Toast.LENGTH_SHORT).show();
//        Log.v("id",a);
//        JSONObject paramJson=new JSONObject(paramId);


        JsonObjectRequest objectRequest =new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                int noOfCourses=response.length();
                JSONObject temp = null;
                String codeStr,name,html_url,instructorId,description;
                System.out.println(response.toString());
                codeStr=name=html_url=instructorId=description=null;
//                Toast.makeText(Explore.this, response.toString(), Toast.LENGTH_SHORT).show();
                Toast.makeText(getContext(), Integer.toString(noOfCourses), Toast.LENGTH_SHORT).show();
                for(int i=0;i<noOfCourses;i++){
                    try {
                        temp=response.getJSONObject("course"+Integer.toString(i+1));

                         codeStr= null;
                        try {
                            codeStr = temp.getString("Code");
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }
                         name= null;
                        try {
                            name = temp.getString("Name");
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }
                         html_url= null;
                        try {
                            html_url = temp.getString("imageUrl");
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }
                         instructorId= null;
                        try {
                            instructorId = temp.getString("InstructorId");
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }
                         description= null;
                        try {
                            description = temp.getString("Description");
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }

                    } catch (JSONException e) {
                        Toast.makeText(getContext(), "error in parsing", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }
//                    Toast.makeText(Explore.this,temp.toString(), Toast.LENGTH_LONG).show();
                    CoursesList.add(new Course(codeStr,name,html_url,instructorId,description));
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