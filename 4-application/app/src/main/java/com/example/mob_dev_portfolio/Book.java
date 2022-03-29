package com.example.mob_dev_portfolio;

public class Book {

    private String title;
    private String author;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
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
}
