package uk.org.websolution.geeknotepad;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.button.MaterialButton;

public class AddNote extends Fragment {

    private NoteEntity newNote;
    EditText noteTitle, noteText, noteDate;
    MaterialButton saveNote;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_note, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        noteTitle = view.findViewById(R.id.editTextAddNoteTitle);
        noteText = view.findViewById(R.id.editTextTextNoteBody);
        noteDate = view.findViewById(R.id.editTextNoteDate);
        saveNote = view.findViewById(R.id.buttonSaveNote);
        saveNote.setOnClickListener(v -> {
            NoteController controller = (NoteController) getActivity();
            newNote = new NoteEntity(noteTitle.getText().toString(), noteText.getText().toString(), noteDate.getText().toString());
            controller.addNote(newNote);

        });
    }

}