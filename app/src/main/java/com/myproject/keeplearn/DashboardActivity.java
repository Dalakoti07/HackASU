package com.myproject.keeplearn;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        //I added this if statement to keep the selected fragment when rotating the device
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragmentholder,
                    new FragmentDashboard()).commit();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.bottombaritem_categories:
                            selectedFragment = new FragmentCategories();
                            break;
                        case R.id.bottombaritem_dashboard:
                            selectedFragment = new FragmentDashboard();
                            break;
                        case R.id.bottombaritem_profile:
                            selectedFragment = new FragmentProfile();
                            break;
                        case R.id.myCourses:
                            selectedFragment= new FragmentMyNotes();
                            break;
                    }

                    assert selectedFragment != null;
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragmentholder,
                            selectedFragment).commit();

                    return true;
                }
            };
}
