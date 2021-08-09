package uk.org.websolution.trader_org_tool.notes;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.material.button.MaterialButton;

import uk.org.websolution.trader_org_tool.R;

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
        MaterialButton saveNote = view.findViewById(R.id.button_save_note);

        saveNote.setOnClickListener(v -> {
            String str1 = noteTitle.getText().toString();
            String str2 = noteText.getText().toString();
            if(str1.equalsIgnoreCase("") || str2.equalsIgnoreCase(""))
            {
                if (str1.equalsIgnoreCase("")) noteTitle.setError("Please enter the title");
                if (str2.equalsIgnoreCase("")) noteText.setError("Please enter note text");
            }
            else {
                NoteController controller = (NoteController) getActivity();
                newNote = new NoteEntity(noteTitle.getText().toString(), noteText.getText().toString());
                assert controller != null;
                requireActivity().getSupportFragmentManager().popBackStack();
                controller.addNote(newNote);
            }

        });
    }

    public interface NoteController {
        void addNote(NoteEntity note);
    }
}