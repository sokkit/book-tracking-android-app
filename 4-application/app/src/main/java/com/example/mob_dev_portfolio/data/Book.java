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

    @ColumnInfo(name = "review")
    private String review;

    @ColumnInfo(name = "rating")
    private int rating;

    public Book(String authors, String title, int status, String dateStarted, String dateCompleted, String review, int rating) {
        this.authors = authors;
        this.title = title;
        this.status = status;
        this.dateStarted = dateStarted;
        this.dateCompleted = dateCompleted;
        this.review = review;
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

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", authors='" + authors + '\'' +
                ", title='" + title + '\'' +
                ", status=" + status +
                ", dateStarted='" + dateStarted + '\'' +
                ", dateCompleted='" + dateCompleted + '\'' +
                ", review='" + review + '\'' +
                ", rating=" + rating +
                '}';
    }

    public String parseBook() {
        String removePunc = authors.replaceAll("[\\[$|]|\"]", "");
        String removeSpeechmark = removePunc.replace("\"", "");
        String spaceAfterComma = removeSpeechmark.replace(",", ", ");
        String res = title + " by: " + spaceAfterComma;
        return res;
    }

    public String parseAuthor() {
        String removePunc = authors.replaceAll("[\\[$|]|\"]", "");
        String removeSpeechmark = removePunc.replace("\"", "");
        String spaceAfterComma = removeSpeechmark.replace(",", ", ");
        return spaceAfterComma;
    }

}
