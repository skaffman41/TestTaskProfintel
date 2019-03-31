package com.skaffman.testtaskprofintel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Date;

public class AddNoteActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextInputEditText title_et, text_et;
    private RadioGroup rg_color;
    private MaterialRadioButton rb_white, rb_red, rb_green, rb_blue;
    private int color;
    private NestedScrollView nsw_add;
    private int id;
    private int white;
    private int red;
    private int green;
    private int blue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        title_et = findViewById(R.id.title_et);
        text_et = findViewById(R.id.text_et);

        rg_color = findViewById(R.id.rg_color);
        rb_white = findViewById(R.id.rb_white);
        rb_red = findViewById(R.id.rb_red);
        rb_green = findViewById(R.id.rb_green);
        rb_blue = findViewById(R.id.rb_blue);

        nsw_add = findViewById(R.id.nsw_add);

        id = getIntent().getIntExtra(NotesActivity.NOTE_ID, -1);

        if (id != -1) {
            fillNote(NotesActivity.notes.get(id));
        }

        white = getResources().getColor(R.color.item_white);
        red = getResources().getColor(R.color.item_red);
        green = getResources().getColor(R.color.item_green);
        blue = getResources().getColor(R.color.item_blue);

        rg_color.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int color = 0;

                switch (checkedId) {
                    case R.id.rb_white:
                        color = white;
                        break;
                    case R.id.rb_red:
                        color = red;
                        break;
                    case R.id.rb_green:
                        color = green;
                        break;
                    case R.id.rb_blue:
                        color = blue;
                        break;
                    default:
                        color = white;
                        break;
                }
                nsw_add.setBackgroundColor(color);
                rg_color.setBackgroundColor(white);
            }
        });
    }

    private void fillNote(Note note) {
        title_et.setText(note.getTitle());
        text_et.setText(note.getText());
        nsw_add.setBackgroundColor(note.getColor());
        rg_color.setBackgroundColor(white);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.create_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                saveNote();
                return true;
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private int colorChanged() {

        switch (rg_color.getCheckedRadioButtonId()) {
            case R.id.rb_white:
                color = white;
                break;
            case R.id.rb_red:
                color = red;
                break;
            case R.id.rb_green:
                color = green;
                break;
            case R.id.rb_blue:
                color = blue;
                break;
            default:
                color = white;
                break;
        }
        return color;
    }

    private void saveNote() {
        String title = title_et.getText().toString().trim();
        String text = text_et.getText().toString().trim();

        boolean isCorrect = true;

        if (title.isEmpty() || text.isEmpty() || rg_color.getCheckedRadioButtonId() == -1) {
            isCorrect = false;
        }

        if (isCorrect) {
            if (id != -1) {
                colorChanged();
                NotesActivity.changeItem(new Note(id, title, text, new Date().toString(), color), id);
                Toast.makeText(this, "Note changed", Toast.LENGTH_SHORT).show();
            } else {
                colorChanged();
                NotesActivity.addItem(new Note(NotesActivity.noteId, title, text, new Date().toString(), color));
                Toast.makeText(this, "Note added", Toast.LENGTH_SHORT).show();
            }

            finish();
        } else {
            Toast.makeText(this, "Fill title and text", Toast.LENGTH_SHORT).show();
        }
    }
}
