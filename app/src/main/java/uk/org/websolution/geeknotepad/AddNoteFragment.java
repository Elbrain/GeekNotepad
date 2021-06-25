package uk.org.websolution.geeknotepad;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.material.button.MaterialButton;

public class AddNoteFragment extends Fragment {

    protected NoteEntity newNote;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_note, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        EditText  noteTitle = view.findViewById(R.id.edit_text_add_note_title);
        EditText  noteText = view.findViewById(R.id.edit_text_text_note_body);
        EditText  noteDate = view.findViewById(R.id.edit_text_note_date);
        MaterialButton saveNote = view.findViewById(R.id.button_save_note);

        saveNote.setOnClickListener(v -> {
            NoteController controller = (NoteController) getActivity();
            newNote = new NoteEntity(noteTitle.getText().toString(), noteText.getText().toString(), noteDate.getText().toString());
            assert controller != null;
            requireActivity().getSupportFragmentManager().popBackStack();
            controller.addNote(newNote);

        });
    }

    public interface NoteController {
        void addNote(NoteEntity note);
    }
}