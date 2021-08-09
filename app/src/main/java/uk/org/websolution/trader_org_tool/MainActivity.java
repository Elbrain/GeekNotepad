package uk.org.websolution.trader_org_tool;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import uk.org.websolution.trader_org_tool.calculator.CalculatorFragment;
import uk.org.websolution.trader_org_tool.invoices.InvoiceFragment;
import uk.org.websolution.trader_org_tool.notes.AddNoteFragment;
import uk.org.websolution.trader_org_tool.notes.EditNoteFragment;
import uk.org.websolution.trader_org_tool.notes.ListOfNotesFragment;
import uk.org.websolution.trader_org_tool.notes.NoteEntity;
import uk.org.websolution.trader_org_tool.notes.NoteInfoFragment;

public class MainActivity extends AppCompatActivity implements AddNoteFragment.NoteController, ListOfNotesFragment.ShowNoteController, ListOfNotesFragment.DeleteNoteController, ListOfNotesFragment.EditNoteController, EditNoteFragment.NoteController {

    protected ArrayList<NoteEntity> notesList = new ArrayList<>();
    private static final String NOTE_KEY = "noteKey";
    public static BottomNavigationView bottomNav;
    private int currentId = 0;
    private boolean isLandscape = false;
    private int navSelected;
    private final String NOTE_TABLE_NAME = "Notes table";
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(getApplicationContext());

        authMethod(savedInstanceState);
        bottomNav = findViewById(R.id.bottom_nav);
        navSelected = bottomNav.getSelectedItemId();
        init();
    }

    private void authMethod(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            initialLaunch();
            AuthFragment authFragment = AuthFragment.newInstance();
            openFragment(authFragment);

        }

    }

    public void initialLaunch() {
        db.collection(NOTE_TABLE_NAME).get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {
                notesList.add(documentSnapshot.toObject(NoteEntity.class));
            }
        });
        ListOfNotesFragment listOfNotesFragment = ListOfNotesFragment.newInstance(notesList);
        openFragment(listOfNotesFragment);
    }

    void openFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    private void init() {
        bottomNav.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {

                case R.id.menu_list:
                    ListOfNotesFragment listOfNotesFragment = ListOfNotesFragment.newInstance(notesList);
                    openFragment(listOfNotesFragment);
                    return true;

                case R.id.menu_calc:
                    openFragment(new CalculatorFragment());
                    return true;

                case R.id.menu_invoices:
                    openFragment(new InvoiceFragment());
                    return true;
            }
            return false;
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle state) {
        super.onSaveInstanceState(state);
        state.putParcelableArrayList(NOTE_KEY, notesList);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.containsKey(NOTE_KEY)) {
            notesList.clear();
            notesList.addAll(savedInstanceState.getParcelableArrayList(NOTE_KEY));
        }
    }

    @Override
    public void addNote(NoteEntity note) {
        notesList.add(note);
        db.collection(NOTE_TABLE_NAME).document(note.getId()).set(note);
        ListOfNotesFragment listOfNotesFragment = ListOfNotesFragment.newInstance(notesList);
        openFragment(listOfNotesFragment);
        bottomNav.setSelectedItemId(navSelected);
    }

    @Override
    public void showNote(NoteEntity note) {
        NoteInfoFragment noteInfoFragment = NoteInfoFragment.newInstance(note);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(isLandscape ? R.id.fragment_container_details : R.id.fragment_container, noteInfoFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void deleteNote(NoteEntity note) {
        notesList.remove(note);
        db.collection(NOTE_TABLE_NAME).document(note.getId()).delete();
    }

    @Override
    public void editNote(NoteEntity note) {
        for (int i = 0; i < notesList.size(); i++) {
            if (notesList.get(i).equals(note)) {
                currentId = i;
            }
        }
        EditNoteFragment editNoteFragment = EditNoteFragment.newInstance(note);
        openFragment(editNoteFragment);
    }

    @Override
    public void edit(NoteEntity note) {
        notesList.set(currentId, note);
        db.collection(NOTE_TABLE_NAME).document(note.getId()).delete();
        db.collection(NOTE_TABLE_NAME).document(note.getId()).set(note);
        ListOfNotesFragment listOfNotesFragment = ListOfNotesFragment.newInstance(notesList);
        openFragment(listOfNotesFragment);
    }
}