package uk.org.websolution.geeknotepad;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NoteInfo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoteInfo extends Fragment {

    private static final String ARG_NOTE = "ARG_NOTE";

    private NoteEntity note;
    TextView textViewTitle;
    TextView textViewBody;
    TextView textViewDate;

    public NoteInfo() {
        // Required empty public constructor
    }

    public static NoteInfo newInstance(NoteEntity note) {
        NoteInfo fragment = new NoteInfo();
        Bundle args = new Bundle();
        args.putParcelable(ARG_NOTE, note);
        fragment.setArguments(args);
        fragment.getArguments();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            note = getArguments().getParcelable(ARG_NOTE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note_info, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textViewTitle = view.findViewById(R.id.NoteTitle);
        textViewBody = view.findViewById(R.id.NoteBody);
        textViewDate = view.findViewById(R.id.NoteDate);

        textViewTitle.setText(note.getTitle());
        textViewBody.setText(note.getText());
        textViewDate.setText(note.getDate());
    }
}