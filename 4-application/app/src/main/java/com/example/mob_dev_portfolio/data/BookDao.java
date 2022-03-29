package com.example.mob_dev_portfolio.data;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

public interface BookDao {

    @Query("SELECT * FROM Book")
    List<Book> getAllBooks();

    @Query("SELECT * FROM Book WHERE title = :title LIMIT 1")
    Book getByTitle(String title);

    @Insert
    void insertAll(Book... books);

    @Delete
    void delete(Book book);


}
