package com.xavier.sqliteexample.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.xavier.sqliteexample.adapters.NoteRecycleViewAdapter;
import com.xavier.sqliteexample.interfaces.NoteOnclickListener;
import com.xavier.sqliteexample.models.Note;
import com.xavier.sqliteexample.persistence.NoteRepository;
import com.xavier.sqliteexample.R;
import com.xavier.sqliteexample.utils.RecycleViewItemDecorator;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NoteOnclickListener, View.OnClickListener {

    private static final String TAG = "MainActivity";
//    vars
    private ArrayList<Note> mNote = new ArrayList<>();
    private NoteRecycleViewAdapter mNoteRecycleViewAdapter;
    private RecycleViewItemDecorator mRecycleViewItemDecorator;
    private NoteRepository mNoteRepository;

//    widgets
    private RecyclerView mRecyclerView;
    private LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
    private FloatingActionButton mFloatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: started");
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recycle_view);
        mFloatingActionButton = findViewById(R.id.fab_add);
        mNoteRepository = new NoteRepository(this);
        initRecyclerView();
        setOnclickLister();
        retrieveNotes();
    }
    private void retrieveNotes(){
        mNoteRepository.retrieveNotesTask().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                if (mNote.size() > 0 ){
                    mNote.clear();
                }
                if (notes !=null){
                    mNote.addAll(notes);
                }
                mNoteRecycleViewAdapter.notifyDataSetChanged();

            }
        });

    }

    private void inserFakeNotes() {
        for (int i =0; i < 1000; i++){
            Note note = new Note();
            note.setTitle("title # " + i);
            note.setContent("content # " + i);
            note.setTime_stamp("30 April");
            mNote.add(note);
        }
        mNoteRecycleViewAdapter.notifyDataSetChanged();
    }

    private void setOnclickLister(){
        mFloatingActionButton.setOnClickListener(this);
    }

    private void initRecyclerView(){
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecycleViewItemDecorator = new RecycleViewItemDecorator(10);
        mRecyclerView.addItemDecoration(mRecycleViewItemDecorator);
        new ItemTouchHelper(itemTouchHelperCallBack).attachToRecyclerView(mRecyclerView);
        mNoteRecycleViewAdapter = new NoteRecycleViewAdapter(mNote, this);
        mRecyclerView.setAdapter(mNoteRecycleViewAdapter);

    }
    @Override
    public void onNoteClicked(int position) {
        Log.d(TAG, "onNoteClicked: "+mNote.get(position));
        Intent intent = new Intent(this, NoteActivity.class);
        intent.putExtra(getString(R.string.selected_note),mNote.get(position));
        startActivity(intent);
        Toast.makeText(this, ""+mNote.get(position).getTitle(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: Called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: Called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: Called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: Called");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab_add:
                Intent intent = new Intent(this, NoteActivity.class);
                startActivity(intent);
                break;
        }

    }

    private void deleteNote(Note note){
        mNote.remove(note);
        mNoteRecycleViewAdapter.notifyDataSetChanged();
        mNoteRepository.deleteNote(note);

    }
    private ItemTouchHelper.SimpleCallback itemTouchHelperCallBack = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            deleteNote(mNote.get(viewHolder.getAdapterPosition()));

        }
    };
}
