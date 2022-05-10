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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
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

public class ReadingFragment extends Fragment {

    ExecutorService executor;
    Spinner sortSpinner;
    Boolean ascending;
    ImageView sortSwitch;

    public ReadingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reading, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstance) {
        sortSpinner = (Spinner) getView().findViewById(R.id.sort_spinner);
        this.executor = Executors.newFixedThreadPool(4);
        Context context = getContext();
        BookDB db = BookDB.getInstance(context);
        ascending = true;
        sortSwitch = (ImageView) getView().findViewById(R.id.reading_sort_switch);


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
                            Collections.sort(readingBooks, compareByTitle);
                            adapter.notifyDataSetChanged();
                            sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    String selectedItem = sortSpinner.getSelectedItem().toString();
                                    switch (selectedItem) {
                                        case "Author":
                                            Collections.sort(readingBooks, compareByAuthor);
                                            break;
                                        case "Title":
                                            Collections.sort(readingBooks, compareByTitle);
                                            break;
                                        case "Date":
                                            Collections.sort(readingBooks, compareByDate);
                                            break;
                                    }
                                    adapter.notifyDataSetChanged();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });
                            sortSwitch.setOnClickListener(new View.OnClickListener() {
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
                                            Collections.sort(readingBooks, compareByAuthor);
                                            break;
                                        case "Title":
                                            Collections.sort(readingBooks, compareByTitle);
                                            break;
                                        case "Date":
                                            Collections.sort(readingBooks, compareByDate);
                                            break;
                                    }
                                    adapter.notifyDataSetChanged();
                                }

                            });
                            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                    Bundle bundle = new Bundle();
                                    int currentBook = readingBooks.get(i).getBookId();
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
                return stringDateConverter(o1.getDateStarted()).compareTo(stringDateConverter(o2.getDateStarted()));
            } else {
                return stringDateConverter(o2.getDateStarted()).compareTo(stringDateConverter(o1.getDateStarted()));
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