package com.example.mob_dev_portfolio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.mob_dev_portfolio.data.Book;
import com.example.mob_dev_portfolio.data.BookDB;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;
    ExecutorService executor;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String currentBook = "currentBookKey";


    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
//        Set home as default navigation area
        bottomNavigationView.setSelectedItemId(R.id.home);

//        Build database which can be accessed by other fragments
        BookDB db = Room.databaseBuilder(
                getApplicationContext(),
                BookDB.class,
                "book-db").build();

        sharedpreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt(currentBook, 1);
        editor.commit();

        this.executor = Executors.newFixedThreadPool(4);
        executor.execute(new Runnable() {
            @Override
            public void run() {
                db.bookDao().deleteAllBooks();
                db.bookDao().insertAll(
                        new Book("John Barnes", "Book 1",
                                1, "01/03/2022",
                                "15/03/2022"),
                        new Book("Paula Poundstone", "Book 2",
                                1, "15/03/2022",
                                "20/03/2022"),
                        new Book("Terry Tibbs", "Book 3",
                                1, "20/03/2022",
                                "25/03/2022"),
                        new Book("Joe Bloggs", "Book 4",
                                0, "25/03/2022",
                                null),
                        new Book("John Barnes", "Book 5",
                                0, "20/03/2022",
                                null));
            }
        });


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
                getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
                return true;

            case R.id.reading:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, readingFragment).commit();
                return true;

            case R.id.read:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, readFragment).commit();
                return true;

            case R.id.tbr:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, tbrFragment).commit();
                return true;

            case R.id.trophies:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, trophiesFragment).commit();
                return true;
        }
        return false;
    }
}
