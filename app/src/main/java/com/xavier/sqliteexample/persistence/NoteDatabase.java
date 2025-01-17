package com.xavier.sqliteexample.persistence;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.xavier.sqliteexample.models.Note;

@Database(entities =  {Note.class},version = 1)
public abstract class NoteDatabase extends RoomDatabase {
     private static final String NOTE_DATABASE = "note_db";
     private static NoteDatabase instance;

     static NoteDatabase getInstance(final Context context){
         if (instance == null){
             instance = Room.databaseBuilder(context.getApplicationContext()
             ,NoteDatabase.class,NOTE_DATABASE).build();
         }
         return instance;
     }

     public abstract NoteDao getNoteDao();
}
