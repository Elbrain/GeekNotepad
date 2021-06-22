package uk.org.websolution.geeknotepad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.res.Configuration;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AddNoteFragment.NoteController, ListOfNotesFragment.ShowNoteController, ListOfNotesFragment.DeleteNoteController, ListOfNotesFragment.EditNoteController, EditNoteFragment.NoteController {

    protected ArrayList<NoteEntity> notesList = new ArrayList<>();
    private static final String NOTE_KEY = "noteKey";
    private BottomNavigationView bottomNav;
    private int currentId = 0;
    private boolean isLandscape = false;
    private int navSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        if (isLandscape) { //в случае поворота экрана гарантируем, что fragment контейнер будет занимать список
            ListOfNotesFragment listOfNotesFragment = ListOfNotesFragment.newInstance(notesList);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, listOfNotesFragment)
                    .addToBackStack(null)
                    .commit();
        }

        if (savedInstanceState == null) {  //В случае первого запуска отображаем предзаполненый лист
            ListOfNotesFragment listOfNotesFragment = ListOfNotesFragment.newInstance(notesList);
            openFragment(listOfNotesFragment);
        }

        bottomNav = findViewById(R.id.bottom_nav);
        navSelected = bottomNav.getSelectedItemId();
        init();
    }

    void openFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    void openFragment(Fragment fragment, boolean isLandscape) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_details, fragment)
                .addToBackStack(null)
                .commit();
    }

    private void init() {
        notesList.add(new NoteEntity("Initial note", "Text of initial pre-made note", "2021.05.06"));  //вручную создаём 2 записи.
        notesList.add(new NoteEntity("First note", "Text of first pre-made note", "2021.06.01"));
        bottomNav.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {

                case R.id.menu_list:
                    ListOfNotesFragment listOfNotesFragment = ListOfNotesFragment.newInstance(notesList);
                    openFragment(listOfNotesFragment);
                    return true;

                case R.id.menu_add:
                    if (isLandscape) {
                        openFragment(new AddNoteFragment(), isLandscape);
                    } else {
                        openFragment(new AddNoteFragment());
                    }
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
        for (NoteEntity thisNote: notesList) {
            if (note.equals(thisNote)){
                notesList.remove(thisNote);
                break;
            }
        }
    }

    @Override
    public void editNote(NoteEntity note) {
        for (int i = 0; i < notesList.size(); i++) {
            if (notesList.get(i).equals(note)){
                currentId = i;
            }
        }
        EditNoteFragment editNoteFragment = EditNoteFragment.newInstance(note);
        if (isLandscape){
            openFragment(editNoteFragment, isLandscape);
        } else openFragment(editNoteFragment);
    }

    @Override
    public void edit(NoteEntity note) {
        notesList.set(currentId, note);
        ListOfNotesFragment listOfNotesFragment = ListOfNotesFragment.newInstance(notesList);
        openFragment(listOfNotesFragment);
    }
}