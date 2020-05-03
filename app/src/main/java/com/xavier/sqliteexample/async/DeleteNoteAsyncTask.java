package com.xavier.sqliteexample.async;

import android.os.AsyncTask;
import android.util.Log;

import com.xavier.sqliteexample.models.Note;
import com.xavier.sqliteexample.persistence.NoteDao;

public class DeleteNoteAsyncTask extends AsyncTask<Note, Void, Void> {

    private static final String TAG = "InsertNoteAsyncTask";

    NoteDao mNoteDao;

    public DeleteNoteAsyncTask(NoteDao dao) {
        mNoteDao = dao;
    }

    @Override
    protected Void doInBackground(Note... notes) {
        Log.d(TAG, "doInBackground: " + Thread.currentThread().getName() );
        mNoteDao.deleteNote(notes);
        return null;
    }
}
