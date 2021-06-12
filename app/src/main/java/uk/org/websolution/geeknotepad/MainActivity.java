package uk.org.websolution.geeknotepad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NoteController, ShowNoteController {

    protected ArrayList<NoteEntity> notesList = new ArrayList<>();
    private static final String NOTE_KEY = "noteKey";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) { //в случае поворота экрана гарантируем, что fragment контейнер будет занимать список
            ListOfNotesFragment listOfNotesFragment = ListOfNotesFragment.newInstance(notesList);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, listOfNotesFragment)
                    .addToBackStack(null)
                    .commit();
        }
        if (savedInstanceState == null) {  //В случае первого запуска отображаем предзаполненый лист
            ListOfNotesFragment listOfNotesFragment = ListOfNotesFragment.newInstance(notesList);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, listOfNotesFragment)
                    .addToBackStack(null)
                    .commit();
        }
        init();

    }

    private void init() {
        notesList.add(new NoteEntity("Initial note", "Text of initial pre-made note", "2021.05.06"));  //вручную создаём 2 записи.
        notesList.add(new NoteEntity("First note", "Text of first pre-made note", "2021.06.01"));
        boolean isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;


        findViewById(R.id.button_new_note).setOnClickListener(v -> {                                 //Открывает новый фрагмент при нажатии на кнопку добавить фрагмент.
            AddNoteFragment addNoteFragment = new AddNoteFragment();
            ListOfNotesFragment listOfNotesFragment = ListOfNotesFragment.newInstance(notesList);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(isLandscape ? R.id.fragment_container_details : R.id.fragment_container, addNoteFragment)
                    .addToBackStack(null)
                    .commit();
        });

        findViewById(R.id.button_show_list).setOnClickListener(v -> {                                  //Открывает список
            ListOfNotesFragment listOfNotesFragment = ListOfNotesFragment.newInstance(notesList);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, listOfNotesFragment)
                    .addToBackStack(null)
                    .commit();
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
    }

    @Override
    public void showNote(NoteEntity note) {
        boolean isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        NoteInfoFragment noteInfoFragment = NoteInfoFragment.newInstance(note);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(isLandscape ? R.id.fragment_container_details : R.id.fragment_container, noteInfoFragment)
                .addToBackStack(null)
                .commit();

    }
}