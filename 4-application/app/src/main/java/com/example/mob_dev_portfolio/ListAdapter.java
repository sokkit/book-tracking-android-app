package com.example.mob_dev_portfolio;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mob_dev_portfolio.data.Book;
import com.example.mob_dev_portfolio.data.BookDB;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ListAdapter extends ArrayAdapter<Book> {

    private final Context context;
    ExecutorService executor;

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
        ImageView bookCover = (ImageView) rowView.findViewById(R.id.custom_list_cover);
        if (book.getStatus() == 0) {
            dateText.setText("Started on: " + book.getDateStarted());
        } else if (book.getStatus() == 1) {
            dateText.setText("Finished on " + book.getDateCompleted());
        } else if (book.getStatus() == 2) {
            dateText.setText("Added on " + book.getDateAdded());
        }
        titleText.setText(book.getTitle());
        authorsText.setText(book.getAuthors());

        Picasso.get().load(book.getThumbnail()).error(R.drawable.cover_not_found).fit().into(bookCover);

        return rowView;
    }
}
