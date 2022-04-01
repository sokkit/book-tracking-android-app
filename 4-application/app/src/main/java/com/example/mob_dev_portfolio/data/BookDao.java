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

    @Query("UPDATE Book SET status = :status WHERE bookId = :bookId")
    void updateReadingStatus(int status, int bookId);

    @Query("UPDATE Book SET date_started = :dateStarted WHERE bookId = :bookId")
    void updateDateStarted(String dateStarted, int bookId);

    @Query("UPDATE Book SET date_completed = :dateCompleted WHERE bookId = :bookId")
    void updateDateCompleted(String dateCompleted, int bookId);

    @Query("UPDATE Book SET review = :review WHERE bookId = :bookId")
    void updateReview(String review, int bookId);

}
