package com.example.mob_dev_portfolio;

import android.os.Parcel;
import android.os.Parcelable;

public class BookSearch implements Parcelable {

    private String title;
    private String author;
    private String isbn;
    private String thumbnail;
    private String description;
    private String dateAdded;

    public BookSearch(String title, String author, String isbn, String thumbnail, String description, String dateAdded) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.thumbnail = thumbnail;
        this.description = description;
        this.dateAdded = dateAdded;
    }

    protected BookSearch(Parcel in) {
        title = in.readString();
        author = in.readString();
        isbn = in.readString();
        thumbnail = in.readString();
        description = in.readString();
        dateAdded = in.readString();
    }

    public static final Creator<BookSearch> CREATOR = new Creator<BookSearch>() {
        @Override
        public BookSearch createFromParcel(Parcel in) {
            return new BookSearch(in);
        }

        @Override
        public BookSearch[] newArray(int size) {
            return new BookSearch[size];
        }
    };

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

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String smallThumbnail) {
        this.thumbnail = smallThumbnail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
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
        parcel.writeString(title);
        parcel.writeString(author);
        parcel.writeString(isbn);
        parcel.writeString(thumbnail);
        parcel.writeString(description);
        parcel.writeString(dateAdded);
    }
}
