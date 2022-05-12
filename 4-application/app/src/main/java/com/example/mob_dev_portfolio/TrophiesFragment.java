package com.example.mob_dev_portfolio;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.mob_dev_portfolio.data.Book;
import com.example.mob_dev_portfolio.data.BookDB;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class TrophiesFragment extends Fragment {

    ExecutorService executor;
    List<String> titles;
    List<Integer> images;
    Adapter adapter;

    public TrophiesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trophies, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstance) {
        RecyclerView dataList = (RecyclerView) getView().findViewById(R.id.dataList);

        titles = new ArrayList<>();
        images = new ArrayList<>();

        titles.add("Read your first book");
        titles.add("Read 5 books");
        titles.add("Read 10 books");
        titles.add("Read 50 books");
        titles.add("Read 100 books");
        titles.add("Read a book in a week");
        titles.add("Read a book in a day");
        titles.add("Add 10 books to TBR");



        images.add(R.drawable.ic_baseline_exposure_plus_1_24);
        images.add(R.drawable.ic_baseline_looks_5_24);
        images.add(R.drawable.ic_baseline_front_hand_24);
        images.add(R.drawable.ic_baseline_star_24);
        images.add(R.drawable.ic_baseline_auto_awesome_24);
        images.add(R.drawable.ic_baseline_calendar_view_week_24);
        images.add(R.drawable.ic_baseline_access_time_24);
        images.add(R.drawable.ic_baseline_playlist_add_check_24);
        System.out.println(images);

        // Check for trophies
        Context context = getContext();
        BookDB db = BookDB.getInstance(context);
        this.executor = Executors.newFixedThreadPool(4);
        executor.execute(new Runnable() {
            @Override
            public void run() {
                List<Book> readBooks = db.bookDao().getBooksByStatus(1);

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (readBooks.size() > 0){
                            images.set(0, R.drawable.ic_baseline_exposure_plus_1_24_gold);
                        }
                        if (readBooks.size() > 4) {
                            images.set(1, R.drawable.ic_baseline_looks_5_24_gold);
                        } if (readBooks.size() > 9) {
                            images.set(2, R.drawable.ic_baseline_front_hand_24_gold);
                        }
                        if (readBooks.size() > 49) {
                            images.set(3, R.drawable.ic_baseline_star_24_gold);
                        }
                        if (readBooks.size() > 99) {
                            images.set(4, R.drawable.ic_baseline_auto_awesome_24_gold);
                        }
                        boolean completedInWeek = false;
                        boolean completedInDay = false;
                        for (Book b: readBooks) {
                            try {
                                if (differenceInDays(b.getDateStarted(), b.getDateCompleted()) < 8) {
                                    completedInWeek = true;
                                }
                                if (differenceInDays(b.getDateStarted(), b.getDateCompleted()) <= 1) {
                                    completedInDay = true;
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        if (completedInWeek) {
                            images.set(5, R.drawable.ic_baseline_calendar_view_week_24_gold);
                        }
                        if (completedInDay) {
                            images.set(6, R.drawable.ic_baseline_access_time_24_gold);
                        }
                        // Check SharedPreferences for TBR trophy
                        SharedPreferences prefs = getContext().getSharedPreferences("prefs", Context.MODE_PRIVATE);
                        boolean tenTBR = prefs.getBoolean("tenTbr", false);
                        System.out.println(tenTBR);
                        if (tenTBR) {
                            images.set(7, R.drawable.ic_baseline_playlist_add_check_24_gold);
                        }

                        adapter = new Adapter(getContext(), titles, images);
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
                        dataList.setLayoutManager(gridLayoutManager);
                        dataList.setAdapter(adapter);
                    }
                });
            }
        });

        adapter = new Adapter(getContext(), titles, images);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        dataList.setLayoutManager(gridLayoutManager);
        dataList.setAdapter(adapter);



    }

    public long differenceInDays(String date1, String date2) throws ParseException {
        // Ref get difference between two dates
        // Taken from https://stackoverflow.com/a/28692025/14457259
        SimpleDateFormat format = new SimpleDateFormat("d/M/yyyy");

        Date d1 = null;
        Date d2 = null;

        d1 = format.parse(date1);
        d2 = format.parse(date2);

        long diff = d2.getTime() - d1.getTime();
        long diffDays = diff / (24 * 60 * 60 * 1000);
        return diffDays;
        // End of reference
    }

}