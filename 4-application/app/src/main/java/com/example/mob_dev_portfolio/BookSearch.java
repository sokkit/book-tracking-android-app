package com.example.mob_dev_portfolio;

import android.os.Parcel;
import android.os.Parcelable;

public class BookSearch implements Parcelable {

    private String title;
    private String author;
    private String isbn;
    private String smallThumbnail;

    public BookSearch(String title, String author, String isbn, String smallThumbnail) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.smallThumbnail = smallThumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getSmallThumbnail() {
        return smallThumbnail;
    }

    public void setSmallThumbnail(String smallThumbnail) {
        this.smallThumbnail = smallThumbnail;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                '}';
    }

//    Remove unnecessary punctuation from strong provided by API
    public String parseBook() {
        String removePunc = author.replaceAll("[\\[$|]|\"]", "");
        String removeSpeechmark = removePunc.replace("\"", "");
        String spaceAfterComma = removeSpeechmark.replace(",", ", ");
        String res = title + " by: " + spaceAfterComma;
        return res;
    }

    public String parseAuthor() {
        String removePunc = author.replaceAll("[\\[$|]|\"]", "");
        String removeSpeechmark = removePunc.replace("\"", "");
        String spaceAfterComma = removeSpeechmark.replace(",", ", ");
        return spaceAfterComma;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
