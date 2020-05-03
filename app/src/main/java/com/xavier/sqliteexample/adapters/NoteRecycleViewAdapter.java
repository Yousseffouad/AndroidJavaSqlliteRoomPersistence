package com.xavier.sqliteexample.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.util.StringUtil;

import com.xavier.sqliteexample.interfaces.NoteOnclickListener;
import com.xavier.sqliteexample.models.Note;
import com.xavier.sqliteexample.R;
import com.xavier.sqliteexample.utils.Utility;

import java.util.ArrayList;

public class NoteRecycleViewAdapter extends RecyclerView.Adapter<NoteRecycleViewAdapter.ViewHolder> {

    private static final String TAG = "NoteRecycleViewAdapter";

    private ArrayList<Note> mNote = new ArrayList<>();
    private NoteOnclickListener mNoteOnclickListener;

    public NoteRecycleViewAdapter(ArrayList<Note> note, NoteOnclickListener noteOnclickListener) {

        this.mNote = note;
        this.mNoteOnclickListener = noteOnclickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_note_list, parent, false );
        return new ViewHolder(view, mNoteOnclickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            holder.mTvAbv.setText(mNote.get(position).getTitle().substring(0,2).toUpperCase());
            String day = mNote.get(position).getTime_stamp().substring(0,2);
            String month = mNote.get(position).getTime_stamp().substring(3,5);
            month = Utility.getNumberOfMonths(month);
            String dayYear = mNote.get(position).getTime_stamp().substring(6);
            String timeStamp = day+ " "+ month + "-" + dayYear;
            String title = mNote.get(position).getTitle();
            String firstLatter = title.substring(0, 1).toUpperCase() + title.substring(1);
            holder.mTitle.setText(firstLatter);
            holder.mTimeStamp.setText(timeStamp);

        }catch (NullPointerException e){

            Log.e(TAG, "onBindViewHolder: "+e.getMessage() );

        }
    }

    @Override
    public int getItemCount() {
        return mNote.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener {
        TextView mTitle,mTvAbv;
        TextView mTimeStamp;
        NoteOnclickListener noteOnclickListener;

        public ViewHolder(@NonNull View itemView, NoteOnclickListener noteOnclickListener) {
            super(itemView);
            mTvAbv = itemView.findViewById(R.id.note_title_icon);
            mTitle = itemView.findViewById(R.id.note_title);
            mTimeStamp = itemView.findViewById(R.id.note_time_stamp);
            this.noteOnclickListener = noteOnclickListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mNoteOnclickListener.onNoteClicked(getAdapterPosition());
        }
    }
}
