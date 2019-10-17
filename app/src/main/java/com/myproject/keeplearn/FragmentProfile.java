package com.myproject.keeplearn;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class FragmentProfile extends Fragment {
    public static final String SHARED_PREFS = "sharedPrefs";
    TextView tv_name,tv_id;
    Button btn_prof;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview =inflater.inflate(R.layout.fragment_profile, container, false);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARED_PREFS, 0);
        String idStr =sharedPreferences.getString("id", "");
        String FnameStr=sharedPreferences.getString("FirstName","");
        String LnameStr=sharedPreferences.getString("LastName","");
        tv_name=rootview.findViewById(R.id.tv_name);
        tv_id=rootview.findViewById(R.id.tv_id);
        renderPage(idStr,FnameStr,LnameStr );
        return rootview;
    }

    private void renderPage(String a,String b,String c) {
        tv_name.setText(b+" "+c);
        tv_id.setText(a);
    }
}
