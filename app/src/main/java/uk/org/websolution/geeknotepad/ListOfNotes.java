package uk.org.websolution.geeknotepad;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ListOfNotes extends Fragment {

    private static final String ARG_NOTES = "ARG_NOTES";
    private static final String TAG = "@@@ ListOfNotes";
    private ArrayList<NoteEntity> allNotes;

    public ListOfNotes() {
    }

    public static ListOfNotes newInstance(ArrayList<NoteEntity> notesList) {
        ListOfNotes fragment = new ListOfNotes();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_NOTES, notesList);
        fragment.setArguments(args);
        fragment.getArguments();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_of_notes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            allNotes = getArguments().getParcelableArrayList(ARG_NOTES);
            LinearLayout layoutView = (LinearLayout) view;
            for (int i = 0; i < allNotes.size(); i++) {
                TextView tv = new TextView(getContext());
                tv.setText(allNotes.get(i).getTitle());
                tv.setTextSize(30);
                layoutView.addView(tv);
            }
        }
    }


}