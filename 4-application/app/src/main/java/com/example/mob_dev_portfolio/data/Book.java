package com.example.mob_dev_portfolio.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Book {

    @PrimaryKey(autoGenerate = true)
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

    public Book(String authors, String title, int status, String dateStarted, String dateCompleted) {
        this.authors = authors;
        this.title = title;
        this.status = status;
        this.dateStarted = dateStarted;
        this.dateCompleted = dateCompleted;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDateStarted() {
        return dateStarted;
    }

    public void setDateStarted(String dateStarted) {
        this.dateStarted = dateStarted;
    }

    public String getDateCompleted() {
        return dateCompleted;
    }

    public void setDateCompleted(String dateCompleted) {
        this.dateCompleted = dateCompleted;
    }

    @Override
    public String toString() {
        return "Book{" +
                "authors='" + authors + '\'' +
                ", title='" + title + '\'' +
                ", dateStarted='" + dateStarted + '\'' +
                ", dateCompleted='" + dateCompleted + '\'' +
                '}';
    }
}
