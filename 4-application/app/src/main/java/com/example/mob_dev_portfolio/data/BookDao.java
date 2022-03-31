package com.example.mob_dev_portfolio.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface BookDao {

    @Query("SELECT * FROM Book")
    List<Book> getAllBooks();

    @Query("SELECT * FROM Book WHERE title = :title LIMIT 1")
    Book getByTitle(String title);

    @Insert
    void insertAll(Book... books);

    @Delete
    void delete(Book book);

    @Query("DELETE FROM Book")
    void deleteAllBooks();

    @Query("SELECT * FROM Book WHERE status = :status")
    List<Book>getBooksByStatus(int status);

    @Query("SELECT * FROM Book WHERE bookId = :bookId")
    Book getByBookId(int bookId);

}
