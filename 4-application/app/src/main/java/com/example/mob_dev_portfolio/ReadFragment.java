package com.example.mob_dev_portfolio;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mob_dev_portfolio.data.Book;
import com.example.mob_dev_portfolio.data.BookDB;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ReadFragment extends Fragment {

    ExecutorService executor;

    public ReadFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_read, container, false);
    }


    public void onViewCreated(View view, @Nullable Bundle savedInstance) {
        this.executor = Executors.newFixedThreadPool(4);

        Context context = getContext();
        BookDB db = BookDB.getInstance(context);

        executor.execute(new Runnable() {
            @Override
            public void run() {
                List<Book> books = db.bookDao().getBooksByStatus(1);
                if (books.size() > 0) {
                    for (Book b:
                         books) {
                        System.out.println(b.toString());
                    }
                }
            }
        });

    }

}