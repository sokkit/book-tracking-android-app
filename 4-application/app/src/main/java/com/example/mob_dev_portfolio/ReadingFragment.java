package com.example.mob_dev_portfolio;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;

import com.example.mob_dev_portfolio.data.Book;
import com.example.mob_dev_portfolio.data.BookDB;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ReadingFragment extends Fragment {

    ExecutorService executor;

    public ReadingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reading, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstance) {
        this.executor = Executors.newFixedThreadPool(4);

        Context context = getContext();
        BookDB db = BookDB.getInstance(context);

        executor.execute(new Runnable() {
            @Override
            public void run() {
//                get reading books from database
                List<Book> readingBooks = db.bookDao().getBooksByStatus(0);
//                create new thread to update list
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ArrayList<String> readingBooksString = new ArrayList<>();
                        if (readingBooks.size() > 0) {
                            for (Book b:
                                    readingBooks) {
//                                add string version of book to list
                                readingBooksString.add(b.parseBook());
                            }

                            ListAdapter adapter=new ListAdapter(getContext(), readingBooks);
                            ListView lv = (ListView) getView().findViewById(R.id.custom_list_reading);
                            lv.setAdapter(adapter);
                            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                                    ref switch fragment from within fragment
//                                    https://stackoverflow.com/a/13217087/14457259
                                    Fragment newFragment = new BookInfoFragment();
                                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                    transaction.replace(R.id.container, newFragment);
                                    transaction.addToBackStack(null);
                                    transaction.commit();
//                                    end of reference
                                }
                            });

                        }
                    }
                });
            }
        });
    }

}