package uk.org.websolution.trader_org_tool.notes;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import uk.org.websolution.trader_org_tool.R;

public class NoteInfoFragment extends Fragment {

    private static final String ARG_NOTE = "ARG_NOTE";
    private NoteEntity note;

    public static NoteInfoFragment newInstance(NoteEntity note) {
        NoteInfoFragment fragment = new NoteInfoFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_NOTE, note);
        fragment.setArguments(args);
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
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView textViewTitle = view.findViewById(R.id.note_title);
        TextView textViewBody = view.findViewById(R.id.note_body);
        TextView textViewDate = view.findViewById(R.id.note_date);

        textViewTitle.setText(note.getTitle());
        textViewBody.setText(note.getText());
        textViewDate.setText(note.getDate());
    }
}