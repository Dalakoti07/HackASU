package com.myproject.keeplearn;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class eachCourseDetail extends AppCompatActivity {
    Button enrollCourse, visitCourse;
    ImageView courseImg;
    TextView title, description,courseUrl;
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

}
