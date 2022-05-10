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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.mob_dev_portfolio.data.Book;
import com.example.mob_dev_portfolio.data.BookDB;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ReadFragment extends Fragment {

    ExecutorService executor;
    Spinner sortSpinner;
    Boolean ascending;
    ImageView sortswitch;

    public ReadFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_read, container, false);
    }


    public void onViewCreated(View view, @Nullable Bundle savedInstance) {
        sortSpinner = (Spinner) getView().findViewById(R.id.sort_spinner);
        this.executor = Executors.newFixedThreadPool(4);
        Context context = getContext();
        BookDB db = BookDB.getInstance(context);
        ascending = true;
        sortswitch = (ImageView) getView().findViewById(R.id.reading_sort_switch);

        executor.execute(new Runnable() {
            @Override
            public void run() {
//                get read books from database
                List<Book> readBooks = db.bookDao().getBooksByStatus(1);

//                create new thread to update list
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (readBooks.size() > 0) {
                            ListAdapter adapter=new ListAdapter(getContext(), readBooks);
                            ListView lv = (ListView) getView().findViewById(R.id.custom_list_reading);
                            lv.setAdapter(adapter);
                            sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    if (sortSpinner.getSelectedItem().toString().equals("Title")) {
                                        System.out.println("title clicked");
                                        Collections.sort(readBooks, compareByTitle);
                                        adapter.notifyDataSetChanged();
                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });
                            sortswitch.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    String itemSelected = sortSpinner.getSelectedItem().toString();
                                    if (ascending == true) {
                                        ascending = false;
                                    } else {
                                        ascending = true;
                                    }
                                    switch (itemSelected) {
                                        case "Author":
                                            Collections.sort(readBooks, compareByAuthor);
                                            break;
                                        case "Title":
                                            Collections.sort(readBooks, compareByTitle);
                                            break;
                                        case "Date":
                                            Collections.sort(readBooks, compareByDate);
                                            break;
                                    }
                                    adapter.notifyDataSetChanged();
                                }

                            });
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

    Comparator<Book> compareByTitle= new Comparator<Book>() {
        @Override
        public int compare(Book o1, Book o2) {
            if (ascending == true) {
                return o1.getTitle().compareTo(o2.getTitle());
            } else {
                return o2.getTitle().compareTo(o1.getTitle());
            }
        }
    };

    Comparator<Book> compareByAuthor= new Comparator<Book>() {
        @Override
        public int compare(Book o1, Book o2) {
            if (ascending == true) {
                return o1.getAuthors().compareTo(o2.getAuthors());
            } else {
                return o2.getAuthors().compareTo(o1.getAuthors());
            }
        }
    };

    Comparator<Book> compareByDate= new Comparator<Book>() {
        @Override
        public int compare(Book o1, Book o2) {
            if (ascending == true) {
                return stringDateConverter(o1.getDateCompleted()).compareTo(stringDateConverter(o2.getDateCompleted()));
            } else {
                return stringDateConverter(o2.getDateCompleted()).compareTo(stringDateConverter(o1.getDateCompleted()));
            }
        }
    };

    public LocalDate stringDateConverter(String date) {
//        reference
//        taken from https://www.baeldung.com/java-string-to-date
//        last accessed 17/03/2022
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        LocalDate dateAns = LocalDate.parse(date, formatter);
//        end of reference
        return dateAns;
    }

}