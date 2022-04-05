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

        this.executor = Executors.newFixedThreadPool(4);
        executor.execute(new Runnable() {
            @Override
            public void run() {
                db.bookDao().deleteAllBooks();
                db.bookDao().insertAll(
                        new Book("Thomas Pynchon", "Mason & Dixon",
                                1, "01/03/2022",
                                "15/03/2022", "Good book lol", 4.0F),
                        new Book("Hunter S Thompson", "Fear and Loathing in Las Vegas",
                                1, "15/03/2022",
                                "20/03/2022", "Very good haha", 4.0F),
                        new Book("Franz Kafka", "The Metamorphosis",
                                1, "20/03/2022",
                                "25/03/2022", "Weird book", 4.5F),
                        new Book("Robert Louis Stevenson", "The Strange Case of Dr Jekyll and Mr Hyde",
                                0, "25/03/2022",
                                null, "", 0.0F),
                        new Book("David Foster Wallace", "Infinite Jest",
                                0, "20/03/2022",
                                null, "", 0F),
                        new Book("Haruki Murakami", "Kafka on the Shore", 2,
                                null, null, "", 0.0F),
                        new Book("Thomas Pynchon", "Vineland", 2, "null",
                                "null", "", 0),
                        new Book("Richard Gott", "Cuba: A New History", 2,
                                null, null, "", 0.0F));
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
