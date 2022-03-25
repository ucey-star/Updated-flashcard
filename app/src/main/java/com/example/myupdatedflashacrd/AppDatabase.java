package com.example.myupdatedflashacrd;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {com.example.myupdatedflashacrd.Flashcard.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {
    public abstract FlashcardDao flashcardDao();
}
