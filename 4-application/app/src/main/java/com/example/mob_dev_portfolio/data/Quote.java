package com.example.mob_dev_portfolio.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Quote {

    @PrimaryKey(autoGenerate = true)
    private int quoteId;

    @ColumnInfo(name = "bookId")
    private int bookId;

    @ColumnInfo(name = "quote")
    private String quote;

    public Quote(int bookId, String quote) {
        this.bookId = bookId;
        this.quote = quote;
    }

    public int getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(int quoteId) {
        this.quoteId = quoteId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    @Override
    public String toString() {
        return "Quote{" +
                "quoteId=" + quoteId +
                ", bookId=" + bookId +
                ", quote='" + quote + '\'' +
                '}';
    }
}
