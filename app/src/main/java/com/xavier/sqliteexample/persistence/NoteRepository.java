package com.xavier.sqliteexample.persistence;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.xavier.sqliteexample.async.DeleteNoteAsyncTask;
import com.xavier.sqliteexample.async.InsertNoteAsyncTask;
import com.xavier.sqliteexample.async.UpdateNoteAsyncTask;
import com.xavier.sqliteexample.models.Note;

import java.util.List;

public class NoteRepository {

    private NoteDatabase mNoteDatabase;
    public NoteRepository(Context context) {
        mNoteDatabase = NoteDatabase.getInstance(context);
    }

    public void  insertNote(Note note){
        new InsertNoteAsyncTask(mNoteDatabase.getNoteDao()).execute(note);
    }

    public void updateNote(Note note){
        new UpdateNoteAsyncTask(mNoteDatabase.getNoteDao()).execute(note);
    }

    public LiveData<List<Note>> retrieveNotesTask(){
        return mNoteDatabase.getNoteDao().getNotes();
    }

    public void deleteNote(Note note){
        new DeleteNoteAsyncTask(mNoteDatabase.getNoteDao()).execute(note);
    }
}
