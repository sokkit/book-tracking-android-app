package com.example.mob_dev_portfolio;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mob_dev_portfolio.data.Book;
import com.example.mob_dev_portfolio.data.BookDB;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BookInfoFragment extends Fragment {

    ExecutorService executor;

    public BookInfoFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_book_info, container, false);

    }

    public void onViewCreated(View view, @Nullable Bundle savedInstance) {
        int currentBook = this.getArguments().getInt("current book");
        System.out.println("current book: " + currentBook);

//        Get book info from database
        Context context = getContext();
        BookDB db = BookDB.getInstance(context);
        this.executor = Executors.newFixedThreadPool(4);

        executor.execute(new Runnable() {
            @Override
            public void run() {
//                get book using id from bundle
                Book bookToView = db.bookDao().getByBookId(currentBook);
                System.out.println(bookToView);

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView titleText = (TextView) getView().findViewById(R.id.book_info_title);
                        TextView authorText = (TextView) getView().findViewById(R.id.book_info_authors);
                        titleText.setText(bookToView.getTitle());
                        authorText.setText(bookToView.parseAuthor());
                    }
                });
            }
        });

    }

}