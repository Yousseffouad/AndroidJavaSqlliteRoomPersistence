package com.xavier.sqliteexample.persistence;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.xavier.sqliteexample.models.Note;

import java.util.List;

@Dao
public interface NoteDao {

    @Insert
    long[] insertNotes(Note... note);

    @Query("SELECT * FROM notes")
    LiveData<List<Note>> getNotes();

    @Delete
    int deleteNote(Note... note);

    @Update
    int updateNote(Note... note);
}
