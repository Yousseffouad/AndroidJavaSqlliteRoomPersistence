package com.xavier.sqliteexample.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xavier.sqliteexample.LineEditText;
import com.xavier.sqliteexample.models.Note;
import com.xavier.sqliteexample.persistence.NoteRepository;
import com.xavier.sqliteexample.R;
import com.xavier.sqliteexample.utils.Utility;

public class NoteActivity extends AppCompatActivity implements View.OnTouchListener,
GestureDetector.OnGestureListener,GestureDetector.OnDoubleTapListener, View.OnClickListener, TextWatcher {
    //Constant Variables
    private static final String TAG = "NoteActivity";
    private static final int EDIT_MODE_ENABLED = 0;
    private static final int EDIT_MODE_DISABLED = 1;
    //Changing Variables
    private Boolean isNewNote;
    private int mMode;
    //Widgets
    private LinearLayout mEdit, mView;
    private LineEditText lineEditText;
    private ImageButton mBack, mSave;
    private TextView mTv;
    private EditText mEditTitle;

    private Note mIncomingNote;
    private Note mFinalNote;
    private NoteRepository mNoteRepository;
    private GestureDetector mGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        mEdit = findViewById(R.id.edit_mode);
        mView = findViewById(R.id.view_mode);
        mBack = findViewById(R.id.back);
        mSave = findViewById(R.id.save);
        lineEditText = findViewById(R.id.line_layout);
        mTv = findViewById(R.id.tv_view);
        mEditTitle = findViewById(R.id.edit);
        mNoteRepository = new NoteRepository(this);


        if (getIncomingIntent()){
            setNewNoteProperty();
            editModeEnabled();
        }
        else {

            setNoteProperty();
            editModeDisabled();
            disableContentInteraction();
        }
        setTouchLister();
    }
    private boolean getIncomingIntent(){

        if (getIntent().hasExtra(getString(R.string.selected_note))){

            mIncomingNote = getIntent().getParcelableExtra(getString(R.string.selected_note));
            mFinalNote = new Note();
            assert mIncomingNote != null;
            mFinalNote.setTitle(mIncomingNote.getTitle());
            mFinalNote.setContent(mIncomingNote.getContent());
            mFinalNote.setTime_stamp(mIncomingNote.getTime_stamp());
            mFinalNote.setId(mIncomingNote.getId());
            mMode=EDIT_MODE_DISABLED;
            Log.d(TAG, "getIncomingIntent: "+mIncomingNote.toString());
            isNewNote = false;
            return false;
        }
        mMode =EDIT_MODE_ENABLED;
        isNewNote=true;
        return true;
    }
    private void setNoteProperty(){
        mTv.setText(mIncomingNote.getTitle());
        lineEditText.setText(mIncomingNote.getContent());
    }
    private void setNewNoteProperty() {
        mIncomingNote = new Note();
        mFinalNote = new Note();
    }

    private void setTouchLister(){
        lineEditText.setOnTouchListener(this);
        mTv.setOnClickListener(this);
        mSave.setOnClickListener(this);
        mBack.setOnClickListener(this);
        mGestureDetector = new GestureDetector(this,this);
        mEditTitle.addTextChangedListener(this);
    }

    private void editModeEnabled(){
        mEdit.setVisibility(View.VISIBLE);
        mView.setVisibility(View.GONE);
        mEditTitle.setText(mIncomingNote.getTitle());
        mEditTitle.requestFocus();
        mEditTitle.setSelection(mEditTitle.length());
        mMode = EDIT_MODE_ENABLED;
        enableContentInteraction();
    }

    private void editModeDisabled(){
        mEdit.setVisibility(View.GONE);
        mView.setVisibility(View.VISIBLE);
        mMode=EDIT_MODE_DISABLED;
        disableContentInteraction();
        String temp = lineEditText.getText().toString();
        temp.replace("/n","");
        temp.replace(" ","");
        if (temp.length() > 0){
            mFinalNote.setTitle(mEditTitle.getText().toString());
            mFinalNote.setContent(lineEditText.getText().toString());
            String timeStamp = Utility.getCurrentTime();
            mFinalNote.setTime_stamp(timeStamp);
            if (!mFinalNote.getContent().equals(mIncomingNote.getContent()) ||
                    !mFinalNote.getTitle().equals(mIncomingNote.getTitle())){
                Log.d(TAG, "editModeDisabled: edit mode disabled");
                saveChanges();
            }
        }
    }
    private void hideSoftKeyboard(){
        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = this.getCurrentFocus();
        if (view==null){
            view = new View(this);
        }
        assert inputMethodManager != null;
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
    }
    private void disableContentInteraction(){
        lineEditText.setKeyListener(null);
        lineEditText.setFocusable(false);
        lineEditText.setFocusableInTouchMode(false);
        lineEditText.setCursorVisible(false);
        lineEditText.clearFocus();
    }

    private void enableContentInteraction(){
        lineEditText.setKeyListener(new EditText(this).getKeyListener());
        lineEditText.setFocusable(true);
        lineEditText.setFocusableInTouchMode(true);
        lineEditText.setCursorVisible(true);
        lineEditText.requestFocus();
    }
    private void saveChanges(){
        if (isNewNote){
            saveNewNote();
        }
        else {
            updateNewNote();
        }
    }

    private void saveNewNote(){

        mNoteRepository.insertNote(mFinalNote);

    }
    private void updateNewNote(){

        mNoteRepository.updateNote(mFinalNote);

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        if (mMode==EDIT_MODE_DISABLED) {
            Toast.makeText(this, "Double Tap to enter edit mode!!!", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Log.d(TAG, "onDoubleTap: doubled touched");
        editModeEnabled();
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.save:
                editModeDisabled();
                hideSoftKeyboard();
                break;
            case R.id.tv_view:
                editModeEnabled();
                mEditTitle.setText(mIncomingNote.getTitle());
                mEditTitle.requestFocus();
                mEditTitle.setSelection(mEditTitle.length());
                break;
            case R.id.back:
                finish();
                break;
        }

    }

    @Override
    public void onBackPressed() {
        if (mMode == EDIT_MODE_ENABLED){
            onClick(mSave);
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(getString(R.string.selected_mode), mMode);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mMode = savedInstanceState.getInt(getString(R.string.selected_mode));
        if (mMode==EDIT_MODE_ENABLED){
            editModeEnabled();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        mTv.setText(s.toString());
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
