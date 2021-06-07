package uk.org.websolution.geeknotepad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NoteController, ShowNoteController {

    protected ArrayList<NoteEntity> notesList = new ArrayList<>();
    private static final String NOTE_KEY = "noteKey";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            ListOfNotes listOfNotes = ListOfNotes.newInstance(notesList);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, listOfNotes)
                    .addToBackStack(null)
                    .commit();
        }
        init();
    }

    private void init() {
        notesList.add(new NoteEntity("Initial note", "Text of initial pre-made note", "2021.05.06"));
        notesList.add(new NoteEntity("First note", "Text of first pre-made note", "2021.06.01"));


        findViewById(R.id.button_new_note).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNote addNote = new AddNote();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, addNote)
                        .addToBackStack(null)
                        .commit();
            }
        });

        findViewById(R.id.button_show_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ListOfNotes listOfNotes = ListOfNotes.newInstance(notesList);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, listOfNotes)
                        .addToBackStack(null)
                        .commit();
            }
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
        NoteInfo noteInfo = NoteInfo.newInstance(note);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, noteInfo)
                .addToBackStack(null)
                .commit();
    }
}