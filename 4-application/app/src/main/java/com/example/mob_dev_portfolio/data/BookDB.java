package com.example.mob_dev_portfolio.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Book.class, Quote.class}, version = 1)
public abstract class BookDB extends RoomDatabase {
    private static BookDB INSTANCE;

    public abstract BookDao bookDao();

//    https://stackoverflow.com/questions/51857873/trying-to-get-an-existing-database-room-android
    public static BookDB getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (BookDB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context,
                            BookDB.class, "book-db")
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
