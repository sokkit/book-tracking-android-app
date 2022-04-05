package com.example.mob_dev_portfolio;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.mob_dev_portfolio.data.Book;
import com.example.mob_dev_portfolio.data.BookDB;

import java.util.ArrayList;
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
//                get read books from database
                List<Book> readBooks = db.bookDao().getBooksByStatus(1);
//                create new thread to update list
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ArrayList<String> readBooksString = new ArrayList<>();
                        if (readBooks.size() > 0) {
                            for (Book b:
                                 readBooks) {
//                                add string version of book to list
                                readBooksString.add(b.parseBook());
                            }
                            ListAdapter adapter=new ListAdapter(getContext(), readBooks);
                            ListView lv = (ListView) getView().findViewById(R.id.custom_list_reading);
                            lv.setAdapter(adapter);
                            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                    Bundle bundle = new Bundle();
                                    int currentBook = readBooks.get(i).getBookId();
                                    bundle.putInt("current book", currentBook);
//                                    ref switch fragment from within fragment. Adapted to add bundle
//                                    https://stackoverflow.com/a/13217087/14457259
                                    Fragment newFragment = new BookInfoFragment();
                                    newFragment.setArguments(bundle);
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