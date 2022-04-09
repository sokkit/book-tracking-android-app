package com.example.mob_dev_portfolio;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mob_dev_portfolio.data.Book;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HomeListAdapter extends ArrayAdapter<BookSearch> {

    private final Context context;

    public HomeListAdapter(Context context, List<BookSearch> bookArrayList) {

        super(context, R.layout.list_item, bookArrayList);

        this.context = context;

    }

    public View getView (int position, View view, ViewGroup parent) {

        BookSearch book = getItem(position);

        LayoutInflater inflater= LayoutInflater.from(getContext());
        View rowView=inflater.inflate(R.layout.home_list_item, null,true);

        TextView titleText = (TextView) rowView.findViewById(R.id.tv_home_title);
        TextView authorsText = (TextView) rowView.findViewById(R.id.tv_home_authors);
        ImageView bookCover = (ImageView) rowView.findViewById(R.id.iv_home_cover);
        titleText.setText(book.getTitle());
        authorsText.setText(book.parseAuthor());

        Picasso.get().load(book.getThumbnail()).error(R.drawable.cover_not_found).fit().into(bookCover);

        return rowView;
    }

}
