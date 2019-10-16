package com.myproject.keeplearn;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private static final String TAG_FRAGMENT_CATEGORIES = "tag_frag_categories";
    private static final String TAG_FRAGMENT_DASHBOARD = "tag_frag_dashboard";
    private static final String TAG_FRAGMENT_PROFILE = "tag_frag_profile";
    private List<BottomBarFragment> fragments = new ArrayList<>(3);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_nav);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.bottombaritem_categories:
                        switchFragment(0, TAG_FRAGMENT_CATEGORIES);
                        return true;
                    case R.id.bottombaritem_dashboard:
                        switchFragment(1, TAG_FRAGMENT_DASHBOARD);
                        return true;
                    case R.id.bottombaritem_profile:
                        switchFragment(2, TAG_FRAGMENT_PROFILE);
                        return true;
                }
                return false;
            }
        });

        buildFragmentsList();
        switchFragment(0, TAG_FRAGMENT_CATEGORIES);


    }

    private void switchFragment(int pos, String tag) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_fragmentholder, fragments.get(pos), tag)
                .commit();
    }

    private void buildFragmentsList() {
        BottomBarFragment categoriesFragment = buildFragment("Categories");
        BottomBarFragment dashboardFragment = buildFragment("Dashboard");
        BottomBarFragment profileFragment = buildFragment("Profile");

        fragments.add(categoriesFragment);
        fragments.add(dashboardFragment);
        fragments.add(profileFragment);
    }

    private BottomBarFragment buildFragment(String title) {
        BottomBarFragment fragment = new BottomBarFragment();
        Bundle bundle = new Bundle();
        bundle.putString(BottomBarFragment.ARG_TITLE, title);
        fragment.setArguments(bundle);
        return fragment;
    }

}