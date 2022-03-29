package com.example.mob_dev_portfolio.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Book {

    @PrimaryKey
    private int bookId;

    @ColumnInfo(name = "authors")
    private String authors;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "status")
    private int status;

    @ColumnInfo(name = "date_started")
    private String dateStarted;

    @ColumnInfo(name = "date_completed")
    private String dateCompleted;

}
