package uk.org.websolution.geeknotepad;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Objects;

public class EditNoteFragment extends Fragment {

    protected NoteEntity newNote;
    private static final String ARG_NOTES = "ARG_NOTES";
    private EditText noteTitle;
    private EditText noteText;
    private EditText noteDate;
    private MaterialButton saveNote;


    public static EditNoteFragment newInstance(NoteEntity note) {
        EditNoteFragment fragment = new EditNoteFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_NOTES, note);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_note, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        noteTitle = view.findViewById(R.id.edit_text_add_note_title);
        noteText = view.findViewById(R.id.edit_text_text_note_body);
        noteDate = view.findViewById(R.id.edit_text_note_date);
        saveNote = view.findViewById(R.id.button_save_note);
        if (getArguments() != null) {
            newNote = getArguments().getParcelable(ARG_NOTES);
            editNote(newNote);
        }
    }

    public void editNote(NoteEntity note) {
        noteTitle.setText(note.getTitle());
        noteText.setText(note.getText());
        noteDate.setText(note.getDate());
        saveNote.setOnClickListener(v -> {
            NoteController controller = (NoteController) getActivity();
            newNote = new NoteEntity(noteTitle.getText().toString(), noteText.getText().toString(), noteDate.getText().toString());
            assert controller != null;
            requireActivity().getSupportFragmentManager().popBackStack();
            controller.edit(newNote);
        });
    }

    public interface NoteController {
        void edit(NoteEntity note);
    }
}