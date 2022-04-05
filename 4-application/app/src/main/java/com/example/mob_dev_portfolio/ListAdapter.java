package com.example.mob_dev_portfolio;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mob_dev_portfolio.data.Book;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends ArrayAdapter<Book> {

    private final Context context;

    public ListAdapter(Context context, List<Book> bookArrayList) {

        super(context, R.layout.list_item, bookArrayList);

        this.context = context;

    }

    public View getView (int position, View view, ViewGroup parent) {

        Book book = getItem(position);

        LayoutInflater inflater= LayoutInflater.from(getContext());
        View rowView=inflater.inflate(R.layout.list_item, null,true);

        TextView titleText = (TextView) rowView.findViewById(R.id.reading_title);
        TextView authorsText = (TextView) rowView.findViewById(R.id.reading_authors);
        TextView dateText = (TextView) rowView.findViewById(R.id.reading_date);
        if (book.getStatus() == 0) {
            dateText.setText("Started on: " + book.getDateStarted());
        } else if (book.getStatus() == 1) {
            dateText.setText("Finished on " + book.getDateCompleted());
        }
//        if (book.getDateCompleted() != null) {
//            dateText.setText("Finished on: " + book.getDateCompleted());
//        } else if (book.getDateCompleted() != null && book.getStatus() == 0){
//            dateText.setText("Started on: " + book.getDateStarted());
//        }
        titleText.setText(book.getTitle());
        authorsText.setText(book.getAuthors());

        return rowView;
    }
}
