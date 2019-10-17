package com.myproject.keeplearn;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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

public class eachCourseDetail extends AppCompatActivity {
    Button enrollCourse, visitCourse;
    ImageView courseImg;
    TextView title, description,courseUrl;
    public static final String SHARED_PREFS = "sharedPrefs";
    Course course;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_each_course_detail);
        enrollCourse=findViewById(R.id.Enroll);
        visitCourse=findViewById(R.id.ViewCourse);
        title=findViewById(R.id.tv_title);
        courseImg=findViewById(R.id.iv_courseImg);
        description=findViewById(R.id.tv_description);
        courseUrl=findViewById(R.id.tv_courseUrl);
        renderPage();
        enrollCourse.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                // get the id from sharedPreferences and call api enrollMe
                enrollAPI();
            }
        });
        visitCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebView webview= new WebView(eachCourseDetail.this);
                setContentView(webview);
//                webview.loadUrl("https://www.google.co.in/");
                WebSettings webSettings = webview.getSettings();
                webSettings.setJavaScriptEnabled(true);
                loadPage(webview);
            }
        });
    }
    private void loadPage(WebView wv){
        wv.loadUrl(course.getImageUrl());
        WebClientClass webViewClient = new WebClientClass();
        wv.setWebViewClient(webViewClient);
        WebChromeClient webChromeClient=new WebChromeClient();
        wv.setWebChromeClient(webChromeClient);
        setContentView(wv);
//        adapter.notifyDataSetChanged();
    }
    private void renderPage() {
        Intent intent = getIntent();
        course=(Course)intent.getSerializableExtra("course");
        title.setText(course.getTitle());
//        instead of courseImg set image from picasso
        courseImg.setImageResource(R.drawable.books);
        description.setText(course.getCourseDescription());
//        Toast.makeText(this, course.getImageUrl(), Toast.LENGTH_SHORT).show();
        courseUrl.setText(course.getImageUrl());
    }
    public class WebClientClass extends WebViewClient {
        ProgressDialog pd = null;

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            pd = new ProgressDialog(eachCourseDetail.this);
            pd.setTitle("Please wait");
            pd.setMessage("Page is loading..");
            pd.show();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            pd.dismiss();
        }
    }

    public class WebChromeClass extends WebChromeClient {
    }

    void enrollAPI(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String a = sharedPreferences.getString("id", "");
        String courseId=course.getId();
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        //ends here
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://apj-learning.herokuapp.com/enrollMe";
//        CoursesList.clear();
        Map<String,String> paramId=new HashMap<String,String>();
        paramId.put("EmailId",a);
        paramId.put("CourseId",courseId);
//        Toast.makeText(this, a, Toast.LENGTH_SHORT).show();
//        Log.v("id",a);
        JSONObject paramJson=new JSONObject(paramId);

        JsonObjectRequest objectRequest =new JsonObjectRequest(Request.Method.POST, url, paramJson, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

//                adapter.notifyDataSetChanged();
                progressDialog.dismiss();
                Toast.makeText(eachCourseDetail.this, "Successufully registered", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error from Volley", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
        // Add the request to the RequestQueue.
        queue.add(objectRequest);
    }
}
