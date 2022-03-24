package com.example.mob_dev_portfolio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.home);

    }
    HomeFragment homeFragment = new HomeFragment();
    ReadingFragment readingFragment = new ReadingFragment();
    ReadFragment readFragment = new ReadFragment();
    TbrFragment tbrFragment = new TbrFragment();
    TrophiesFragment trophiesFragment = new TrophiesFragment();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.home:
                getSupportFragmentManager().beginTransaction().replace(R.id.bottomNavigationView, homeFragment).commit();
                return true;

            case R.id.reading:
                getSupportFragmentManager().beginTransaction().replace(R.id.bottomNavigationView, readingFragment).commit();
                return true;

            case R.id.read:
                getSupportFragmentManager().beginTransaction().replace(R.id.bottomNavigationView, readFragment).commit();
                return true;

            case R.id.tbr:
                getSupportFragmentManager().beginTransaction().replace(R.id.bottomNavigationView, tbrFragment).commit();
                return true;

            case R.id.trophies:
                getSupportFragmentManager().beginTransaction().replace(R.id.bottomNavigationView, trophiesFragment).commit();
                return true;
        }
        return false;
    }
}
