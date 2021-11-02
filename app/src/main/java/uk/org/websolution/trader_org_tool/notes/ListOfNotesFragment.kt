package uk.org.websolution.trader_org_tool.notes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import uk.org.websolution.trader_org_tool.R;

public class ListOfNotesFragment extends Fragment {

    private static final String ARG_NOTES = "ARG_NOTES";
    private RecyclerView recyclerView;
    private FloatingActionButton addNoteFab;

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
        addNoteFab = view.findViewById(R.id.fab_add_note);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        generateList();
        initFab();
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

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);
    }

    private void initFab(){
        addNoteFab.setOnClickListener(v -> {
            AddNoteFragment addNoteFragment = new AddNoteFragment();
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, addNoteFragment)
                    .addToBackStack(null)
                    .commit();
        });
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