package com.example.notebookdb;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.notebookdb.fragments.NoteAddFragment;
import com.example.notebookdb.fragments.NoteDetailsFragment;
import com.example.notebookdb.fragments.NoteListFragment;
import com.example.notebookdb.models.Note;
import com.example.notebookdb.services.DbService;

import java.util.ArrayList;

@RequiresApi(api = Build.VERSION_CODES.N)
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    DbService dbService;
    ArrayList<Note> notes;
    ArrayList<String> tags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbService = new DbService(this);
        tags = Note.getTags();
        notes = dbService.getNoteList();

        setFragmentResultListeners();
        showNoteListFragment();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbService.close();
    }

    private void setFragmentResultListeners() {
        getSupportFragmentManager().setFragmentResultListener(
                "inputNoteRequest",
                this,
                new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        String title = result.getString("title");
                        String tag = result.getString("tag");
                        String fullText = result.getString("fullText");
                        dbService.saveNote(new Note(title, tag, fullText));
                        notes = dbService.getNoteList();
                        showNoteListFragment();
                    }
                }
        );

        getSupportFragmentManager().setFragmentResultListener(
                "inputNoteTitleRequest",
                this,
                new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        String noteName = result.getString("noteTitle");
                        showNoteDetailsFragment(noteName);
                    }
                }
        );
    }

    private void showNoteListFragment() {
        Bundle args = new Bundle();
        args.putSerializable("notes", Note.getNoteNameList(notes));
        showCustomFragment(new NoteListFragment(), args);
    }

    private void showNoteAddFragment() {
        Bundle args = new Bundle();
        args.putSerializable("tags", tags);
        showCustomFragment(new NoteAddFragment(), args);
    }

    private void showNoteDetailsFragment(String noteName) {
        Bundle args = new Bundle();
        args.putSerializable("note", Note.getNoteByName(notes, noteName));
        showCustomFragment(new NoteDetailsFragment(), args);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.showFullList:
                showNoteListFragment();
                break;
            case R.id.addNote:
                showNoteAddFragment();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showCustomFragment(Fragment fragment, Bundle args) {
        fragment.setArguments(args);
        this.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frgContainerView, fragment)
                .commit();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.btnBack) {
            showNoteListFragment();
        }
    }
}