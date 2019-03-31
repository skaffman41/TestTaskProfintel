package com.skaffman.testtaskprofintel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NotesActivity extends AppCompatActivity {

    public static final String NOTE_ID = "note_id";

    private RecyclerView notes_rv;
    private FloatingActionButton create_fab;
    private Toolbar toolbar;
    public static List<Note> notes;
    private static NotesAdapter notesAdapter;
    public static int noteId = 10;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        gson = new Gson();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        notes_rv = findViewById(R.id.notes_rv);
        create_fab = findViewById(R.id.create_fab);
        notes = new ArrayList<>();

        setItems(notes);

        notesAdapter = new NotesAdapter(onNoteClickListener);
        notesAdapter.setNotes(notes);
        notes_rv.setLayoutManager(new LinearLayoutManager(this));
        notes_rv.setAdapter(notesAdapter);

        create_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotesActivity.this, AddNoteActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setItems(List<Note> notes) {
        try {
            String jsonStart = jsonToStringFromAssetFolder("profinteltest.json", this);
            Note[] notesArray = gson.fromJson(jsonStart, Note[].class);
            List<Note> noteList = new ArrayList<>();
            noteList = Arrays.asList(notesArray);
            notes.addAll(noteList);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String jsonToStringFromAssetFolder(String fileName, Context context) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(context.getAssets().open(fileName), "UTF-8"));

        StringBuilder stringBuilder = new StringBuilder();
        String line = bufferedReader.readLine();
        while (line != null) {
            stringBuilder.append(line);
            line = bufferedReader.readLine();
        }

        bufferedReader.close();
        return stringBuilder.toString();
    }

    public static void addItem(Note note) {
        notes.add(note);
        notesAdapter.notifyDataSetChanged();
    }

    public static void changeItem(Note note, int position) {
        notes.add(position, note);
        notes.remove(position + 1);
        notesAdapter.notifyDataSetChanged();
    }

    private final NotesAdapter.OnNoteClickListener onNoteClickListener = new NotesAdapter.OnNoteClickListener() {
        @Override
        public void onNoteClick(int id) {
            Intent intent = new Intent(NotesActivity.this, AddNoteActivity.class);
            intent.putExtra(NOTE_ID, id);
            startActivity(intent);
        }
    };
}