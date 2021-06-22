package uk.org.websolution.geeknotepad;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ListOfNotesFragment extends Fragment {

    private static final String ARG_NOTES = "ARG_NOTES";
    private RecyclerView recyclerView;

    public static ListOfNotesFragment newInstance(ArrayList<NoteEntity> notesList) {
        ListOfNotesFragment fragment = new ListOfNotesFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_NOTES, notesList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_of_notes, container, false);
        recyclerView = view.findViewById(R.id.recycler_list_of_notes);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        generateList();

    }

    private void generateList() {
        NotesAdapter adapter = new NotesAdapter();

        if (getArguments() != null) {
            ArrayList<NoteEntity> allNotes = getArguments().getParcelableArrayList(ARG_NOTES);
            adapter.setData(allNotes);
        }

        adapter.setOnItemClickListener(new NotesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(NoteEntity note) {
                ShowNoteController controller = (ShowNoteController) getActivity();
                if (controller != null) {
                    controller.showNote(note);
                }
            }

            @Override
            public void onEditClicked(NoteEntity note) {
                EditNoteController controller = (EditNoteController) getActivity();
                if (controller != null) {
                    controller.editNote(note);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onDeleteClicked(NoteEntity note) {
                DeleteNoteController controller = (DeleteNoteController) getActivity();
                if (controller != null) {
                    controller.deleteNote(note);
                    adapter.notifyDataSetChanged();
                }
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    public interface ShowNoteController {
        void showNote(NoteEntity note);
    }

    public interface DeleteNoteController {
        void deleteNote(NoteEntity note);
    }

    public interface EditNoteController {
        void editNote(NoteEntity note);
    }

}