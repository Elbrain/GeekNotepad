package uk.org.websolution.geeknotepad;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class NoteViewHolder extends RecyclerView.ViewHolder implements MenuItem.OnMenuItemClickListener, PopupMenu.OnMenuItemClickListener {
    private final TextView textViewTitle;
    private final TextView textViewDate;
    private final CardView cardView;
    private NoteEntity noteEntity;
    private final NotesAdapter.OnItemClickListener clickListener;

    public NoteViewHolder(@NonNull ViewGroup parent, @Nullable NotesAdapter.OnItemClickListener clickListener) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false));
        cardView = (CardView) itemView;
        textViewTitle = itemView.findViewById(R.id.title_text_view);
        textViewDate = itemView.findViewById(R.id.date_text_view);
        this.clickListener = clickListener;
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
        itemView.setOnLongClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(itemView.getContext(), itemView);
            popupMenu.inflate(R.menu.note_item_menu);
            popupMenu.setOnMenuItemClickListener(this);
            popupMenu.show();
            return false;
        });
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.edit_note) {
            clickListener.onEditClicked(noteEntity);
        } else if (item.getItemId() == R.id.delete_note) {
            new AlertDialog.Builder(cardView.getContext())
                    .setTitle(R.string.warning)
                    .setMessage(R.string.warning_message)
                    .setCancelable(true)
                    .setPositiveButton(R.string.yes, (d,i) ->{
                        clickListener.onDeleteClicked(noteEntity);
                    })
                    .setNegativeButton(R.string.no, (d,i) ->{
                        return;
                    })
                    .show();
        }
        return false;
    }
}