package uk.org.websolution.geeknotepad;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class NoteViewHolder extends RecyclerView.ViewHolder {
    private final TextView textViewTitle;
    private final TextView textViewDate;
    private final CardView cardView;
    private NoteEntity noteEntity;

    public NoteViewHolder(@NonNull ViewGroup parent, @Nullable NotesAdapter.OnItemClickListener clickListener) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false));
        cardView = (CardView) itemView;
        textViewTitle = itemView.findViewById(R.id.title_text_view);
        textViewDate = itemView.findViewById(R.id.date_text_view);
        cardView.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onItemClick(noteEntity);
            }
        });
    }

    public void bind(NoteEntity noteEntity) {
        this.noteEntity = noteEntity;
        cardView.setCardBackgroundColor(noteEntity.getColour());
        textViewTitle.setText(noteEntity.getTitle());
        textViewDate.setText(noteEntity.getDate());
    }
}
