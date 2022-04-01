package com.example.mob_dev_portfolio;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.mob_dev_portfolio.data.Book;
import com.example.mob_dev_portfolio.data.BookDB;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BookInfoFragment extends Fragment implements View.OnClickListener {

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
        // If user reaches page from Reading, Read, or TBR sections
        if (this.getArguments().containsKey("current book")) {
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
                            Spinner readingStatus = (Spinner) getView().findViewById(R.id.book_info_status_spinner);
                            titleText.setText(bookToView.getTitle());
                            authorText.setText(bookToView.parseAuthor());
                            Button submitButton = (Button) getView().findViewById(R.id.book_info_submit_button);


                            submitButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    System.out.println("button clicked");
                                    String choice = readingStatus.getSelectedItem().toString();
                                    System.out.println("choice: " + choice);
                                    int newStatus = 0;
                                    if (choice.equals("Read")) {
                                        newStatus = 1;
                                    } else if (choice.equals("TBR")) {
                                        newStatus = 2;
                                    }
                                    db.bookDao().updateReadingStatus(newStatus, currentBook);
                                }
                            });
                        }
                    });
                }
            });
            // If user reaches page from home page
        } else if (this.getArguments().containsKey("potential book")) {
            BookSearch currentBook = this.getArguments().getParcelable("potential book");
            System.out.println("current book: " + currentBook);

//        Get book info from database
            Context context = getContext();
            BookDB db = BookDB.getInstance(context);
            this.executor = Executors.newFixedThreadPool(4);

            executor.execute(new Runnable() {
                @Override
                public void run() {
//                get book using id from bundle
                    Book bookToView = new Book(currentBook.parseAuthor(), currentBook.getTitle(), 3, null, null, "", 0);
                    System.out.println(bookToView);

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            TextView titleText = (TextView) getView().findViewById(R.id.book_info_title);
                            TextView authorText = (TextView) getView().findViewById(R.id.book_info_authors);
                            Spinner readingStatus = (Spinner) getView().findViewById(R.id.book_info_status_spinner);
                            titleText.setText(bookToView.getTitle());
                            authorText.setText(bookToView.parseAuthor());
                            Button submitButton = (Button) getView().findViewById(R.id.book_info_submit_button);


                            submitButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    System.out.println("button clicked");
                                    String choice = readingStatus.getSelectedItem().toString();
                                    System.out.println("choice: " + choice);
                                    int newStatus = 0;
                                    if (choice.equals("Read")) {
                                        newStatus = 1;
                                    } else if (choice.equals("TBR")) {
                                        newStatus = 2;
                                    }
                                    bookToView.setStatus(newStatus);
                                    db.bookDao().insertAll(bookToView);
                                }
                            });
                        }
                    });
                }
            });
        }


    }

    @Override
    public void onClick(View view) {

    }
}