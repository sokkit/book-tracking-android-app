package com.example.mob_dev_portfolio;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mob_dev_portfolio.data.Book;
import com.example.mob_dev_portfolio.data.BookDB;
import com.example.mob_dev_portfolio.data.Quote;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_book_info, container, false);

    }

    public void onViewCreated(View view, @Nullable Bundle savedInstance) {


        TextView titleText = (TextView) getView().findViewById(R.id.book_info_title);
        TextView authorText = (TextView) getView().findViewById(R.id.book_info_authors);
        Spinner readingStatus = (Spinner) getView().findViewById(R.id.book_info_status_spinner);
        Button submitButton = (Button) getView().findViewById(R.id.book_info_submit_button);
        EditText dateStarted = (EditText) getView().findViewById(R.id.date_started);
        EditText dateCompleted = (EditText) getView().findViewById(R.id.date_completed);
        EditText review = (EditText) getView().findViewById(R.id.book_info_review);
        RatingBar rating = (RatingBar) getView().findViewById(R.id.book_info_rating);
        ImageView smallCover = (ImageView) getView().findViewById(R.id.book_info_small_cover);
        TextView descriptionText = (TextView) getView().findViewById(R.id.book_info_description);
        TextView quoteHeading = (TextView) getView().findViewById(R.id.book_info_quotes_heading);
        EditText quoteEditText = (EditText) getView().findViewById(R.id.book_info_quote_edit_text);
        Button quoteButton = (Button) getView().findViewById(R.id.book_info_add_quote_button);
        ListView quoteListView = (ListView) getView().findViewById(R.id.book_info_quote_list);

        SharedPreferences.Editor editor = getContext().getSharedPreferences("prefs", Context.MODE_PRIVATE).edit();


        // if book isn't in collection
        if (this.getArguments().containsKey("potential book")) {
            //hide quote selection
            quoteHeading.setVisibility(View.GONE);
            quoteEditText.setVisibility(View.GONE);
            quoteButton.setVisibility(View.GONE);
            quoteListView.setVisibility(View.GONE);
            // update submit button
            submitButton.setText("Add book");
        }

        // Reference - Expandable calendar combined with EditText
        // Taken from https://www.techypid.com/datepicker-dialog-click-on-edittext-in-android/
        dateStarted.setOnClickListener(new View.OnClickListener() {
            private int mYear, mMonth, mDay;
            @Override
            public void onClick(View view) {
            final Calendar calendar = Calendar.getInstance ();
            mYear = calendar.get ( Calendar.YEAR );
            mMonth = calendar.get ( Calendar.MONTH );
            mDay = calendar.get ( Calendar.DAY_OF_MONTH );

            //show dialog
            DatePickerDialog datePickerDialog = new DatePickerDialog ( getContext(), new DatePickerDialog.OnDateSetListener () {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    dateStarted.setText ( dayOfMonth + "/" + (month + 1) + "/" + year );
                }
            }, mYear, mMonth, mDay );
            datePickerDialog.show ();
            }
        });
        dateCompleted.setOnClickListener(new View.OnClickListener() {
            private int mYear, mMonth, mDay;
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance ();
                mYear = calendar.get ( Calendar.YEAR );
                mMonth = calendar.get ( Calendar.MONTH );
                mDay = calendar.get ( Calendar.DAY_OF_MONTH );

                //show dialog
                DatePickerDialog datePickerDialog = new DatePickerDialog ( getContext(), new DatePickerDialog.OnDateSetListener () {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dateCompleted.setText ( dayOfMonth + "/" + (month + 1) + "/" + year );
                    }
                }, mYear, mMonth, mDay );
                datePickerDialog.show ();
            }
        });
        // End of reference


        // If user reaches page from Reading, Read, or TBR sections
        if (this.getArguments().containsKey("current book")) {
            int currentBook = this.getArguments().getInt("current book");
//        Get book info from database
            Context context = getContext();
            BookDB db = BookDB.getInstance(context);
            this.executor = Executors.newFixedThreadPool(4);

            // Check TBR list size for trophy
            List<Book> tbrList = db.bookDao().getBooksByStatus(2);
            if (tbrList.size() > 9) {
                editor.putBoolean("tenTbr", true);
                editor.apply();
            }

            executor.execute(new Runnable() {
                @Override
                public void run() {
                    List<Quote> quoteList;
                    // get book using id from bundle
                    Book bookToView = db.bookDao().getByBookId(currentBook);
                    // Get quotes from book id
                    quoteList = db.bookDao().getQuotesByBookId(currentBook);
                    System.out.println(quoteList);
                    // Add quotes to list
                    ArrayList<String> listContent = new ArrayList<String>();
                    for (Quote q: quoteList) {
                        listContent.add(q.getQuote());
                    }
                    ArrayAdapter<String> la = new ArrayAdapter<String>(
                            getContext(),
                            android.R.layout.simple_list_item_1,
                            listContent
                    );
                    quoteListView.setAdapter(la);

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // Hide quote list of no quotes
                            if (quoteList.size()==0) {
                                quoteListView.setVisibility(View.GONE);
                            }
                            Picasso.get().load(bookToView.getThumbnail()).error(R.drawable.cover_not_found).fit().into(smallCover);
                            review.setText(bookToView.getReview());
                            titleText.setText(bookToView.getTitle());
                            authorText.setText(bookToView.parseAuthor());
                            rating.setRating(bookToView.getRating());
                            readingStatus.setSelection(bookToView.getStatus());
                            descriptionText.setText(bookToView.getDescription());


                            if (dateStarted != null) {
                                dateStarted.setText(bookToView.getDateStarted());
                            }
                            if (dateCompleted != null) {
                                dateCompleted.setText(bookToView.getDateCompleted());
                            }

                            submitButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    String choice = readingStatus.getSelectedItem().toString();
                                    int newStatus = 0;
                                    if (choice.equals("Read")) {
                                        newStatus = 1;
                                    } else if (choice.equals("TBR")) {
                                        newStatus = 2;
                                    }

                                    // validation
                                    boolean validValues = true;
                                    if (choice.equals("Read")) {
                                        if (dateCompleted.getText().toString().equals("") || choice.equals("Read") && dateStarted.getText().toString().equals("")) {
                                            Toast.makeText(getContext(), "Must fill in dates for read book", Toast.LENGTH_SHORT).show();
                                            dateCompleted.setText(bookToView.getDateCompleted());
                                            dateStarted.setText(bookToView.getDateStarted());
                                            validValues = false;
                                        } else if (dateBefore(dateCompleted.getText().toString(), dateStarted.getText().toString())) {
                                            Toast.makeText(getContext(), "Date completed can't be before date started", Toast.LENGTH_SHORT).show();
                                            dateCompleted.setText(bookToView.getDateCompleted());
                                            dateStarted.setText(bookToView.getDateStarted());
                                            validValues = false;
                                        }
                                    }
                                    if (choice.equals("Reading")) {
                                        if (!dateCompleted.getText().toString().equals("") || dateStarted.getText().toString().equals("")) {
                                            Toast.makeText(getContext(), "Invalid dates for reading book", Toast.LENGTH_SHORT).show();
                                            dateCompleted.setText("");
                                            dateStarted.setText(bookToView.getDateStarted());
                                            validValues = false;
                                        }
                                    }
                                    if (choice.equals("TBR")) {
                                        if (!dateCompleted.getText().toString().equals("") || !dateStarted.getText().toString().equals("")) {
                                            Toast.makeText(getContext(), "No dates needed for To Be Read books", Toast.LENGTH_SHORT).show();
                                            dateCompleted.setText("");
                                            dateStarted.setText("");
                                            validValues = false;
                                        }
                                    }

                                    if (validValues) {
                                        bookToView.setReview(review.getText().toString());
                                        bookToView.setStatus(newStatus);
                                        bookToView.setDateStarted(dateStarted.getText().toString());
                                        bookToView.setDateCompleted(dateCompleted.getText().toString());
                                        bookToView.setRating(rating.getRating());
                                        db.bookDao().insertAll(bookToView);
                                    }
                                    // end of validation

                                    Float ratingValue = (Float) rating.getRating();
                                    db.bookDao().updateReview(review.getText().toString(), currentBook);
                                    db.bookDao().updateDateStarted(dateStarted.getText().toString(), currentBook);
                                    db.bookDao().updateDateCompleted(dateCompleted.getText().toString(), currentBook);
                                    db.bookDao().updateReadingStatus(newStatus, currentBook);
                                    db.bookDao().updateRating(ratingValue, currentBook);
                                }
                            });
                            quoteButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    quoteListView.setVisibility(View.VISIBLE);
//                                    quoteListView.requestLayout();
                                    String quoteToAdd = quoteEditText.getText().toString();
                                    System.out.println("quote: " + quoteToAdd);
                                    System.out.println("submitted quote for: " + currentBook);
                                    Quote dbQuote = new Quote(currentBook, quoteToAdd);
                                    db.bookDao().insertAll(dbQuote);
                                    listContent.add(dbQuote.getQuote());
                                    la.notifyDataSetChanged();
                                }
                            });
                        }
                    });
                }
            });


            // If user reaches page from home page
        } else if (this.getArguments().containsKey("potential book")) {
            BookSearch currentBook = this.getArguments().getParcelable("potential book");
            currentBook.setDateAdded(getTodaysDate());
            Picasso.get().load(currentBook.getThumbnail()).error(R.drawable.cover_not_found).fit().into(smallCover);

//        Get book info from database
            Context context = getContext();
            BookDB db = BookDB.getInstance(context);
            this.executor = Executors.newFixedThreadPool(4);

            executor.execute(new Runnable() {
                @Override
                public void run() {
//                get book using id from bundle
                    Book bookToView = new Book(currentBook.parseAuthor(), currentBook.getTitle(), 3, null, null,
                            "", 0, currentBook.getThumbnail(), currentBook.getDescription(), currentBook.getDateAdded());

                    // Check TBR list size for trophy
                    List<Book> tbrList = db.bookDao().getBooksByStatus(2);
                    if (tbrList.size() > 9) {
                        editor.putBoolean("tenTbr", true);
                        editor.apply();
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            titleText.setText(bookToView.getTitle());
                            authorText.setText(bookToView.parseAuthor());
                            descriptionText.setText(bookToView.getDescription());

                            submitButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    String choice = readingStatus.getSelectedItem().toString();
                                    // update reading status from spinner
                                    int newStatus = 0;
                                    if (choice.equals("Read")) {
                                        newStatus = 1;
                                    } else if (choice.equals("TBR")) {
                                        newStatus = 2;
                                    }

                                    // validation
                                    boolean validValues = true;
                                    if (choice.equals("Read")) {
                                        if (dateCompleted.getText().toString().equals("") || choice.equals("Read") && dateStarted.getText().toString().equals("")) {
                                            Toast.makeText(getContext(), "Must fill in dates for read book", Toast.LENGTH_SHORT).show();
                                            clearDates(dateCompleted, dateStarted);
                                            validValues = false;
                                        } else if (dateBefore(dateCompleted.getText().toString(), dateStarted.getText().toString())) {
                                            Toast.makeText(getContext(), "Date completed can't be before date started", Toast.LENGTH_SHORT).show();
                                            clearDates(dateCompleted, dateStarted);
                                            validValues = false;
                                        }
                                    }
                                    if (choice.equals("Reading")) {
                                        if (!dateCompleted.getText().toString().equals("") || dateStarted.getText().toString().equals("")) {
                                            Toast.makeText(getContext(), "Invalid dates for reading book", Toast.LENGTH_SHORT).show();
                                            clearDates(dateCompleted, dateStarted);
                                            validValues = false;
                                        }
                                    }
                                    if (choice.equals("TBR")) {
                                        if (!dateCompleted.getText().toString().equals("") || !dateStarted.getText().toString().equals("")) {
                                            Toast.makeText(getContext(), "No dates needed for To Be Read books", Toast.LENGTH_SHORT).show();
                                            clearDates(dateCompleted, dateStarted);
                                            validValues = false;
                                        }
                                    }

                                    if (validValues) {
                                        int finalNewStatus = newStatus;
                                        List<Book> dupeCheck = db.bookDao().getBooksByTitle(bookToView.getTitle());
                                        if (dupeCheck.size() != 0) {
                                            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    switch (which){
                                                        case DialogInterface.BUTTON_POSITIVE:
                                                            //Yes button clicked
                                                            bookToView.setReview(review.getText().toString());
                                                            bookToView.setStatus(finalNewStatus);
                                                            bookToView.setDateStarted(dateStarted.getText().toString());
                                                            bookToView.setDateCompleted(dateCompleted.getText().toString());
                                                            bookToView.setRating(rating.getRating());
                                                            db.bookDao().insertAll(bookToView);
                                                            Toast.makeText(getContext(), "Book added!", Toast.LENGTH_SHORT).show();
                                                            break;

                                                        case DialogInterface.BUTTON_NEGATIVE:
                                                            //No button clicked
                                                            break;
                                                    }
                                                }
                                            };

                                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                            builder.setMessage("Book already add. Are you sure you want to add this book?").setPositiveButton("Yes", dialogClickListener)
                                                    .setNegativeButton("No", dialogClickListener).show();
                                        }

                                    }
                                    // end of validation

                                }

                                private void clearDates(EditText dateCompleted, EditText dateStarted) {
                                    dateCompleted.setText("");
                                    dateStarted.setText("");
                                }
                            });
                        }
                    });
                }
            });
        }


    }

    public String getTodaysDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date today = new Date();
        return formatter.format(today);
    }

    public boolean dateBefore(String date1, String date2) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        LocalDate formattedDate1 = LocalDate.parse(date1, formatter);
        LocalDate formattedDate2 = LocalDate.parse(date2, formatter);
        return formattedDate1.isBefore(formattedDate2);
    }

    @Override
    public void onClick(View view) {

    }
}