package com.skaffman.testtaskprofintel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    private final OnNoteClickListener onNoteClickListener;
    private List<Note> notes = new ArrayList<>();

    public NotesAdapter(OnNoteClickListener onNoteClickListener) {
        this.onNoteClickListener = onNoteClickListener;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    @androidx.annotation.NonNull
    @Override
    public ViewHolder onCreateViewHolder(@androidx.annotation.NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_note, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@androidx.annotation.NonNull ViewHolder viewHolder, int i) {
        viewHolder.bind(notes.get(i));
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title_tv, date_tv;
        CardView item_card;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title_tv = itemView.findViewById(R.id.title_tv);
            date_tv = itemView.findViewById(R.id.date_tv);
            item_card = itemView.findViewById(R.id.item_card);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id = (int) v.getTag();
                    onNoteClickListener.onNoteClick(id);
                }
            });
        }

        public void bind(Note note) {
            title_tv.setText(note.getTitle());
            date_tv.setText(note.getDate());
            item_card.setCardBackgroundColor(note.getColor());

            int id = note.getId();
            itemView.setTag(id);
        }
    }

    public interface OnNoteClickListener {

        void onNoteClick(int noteId);

    }
}
