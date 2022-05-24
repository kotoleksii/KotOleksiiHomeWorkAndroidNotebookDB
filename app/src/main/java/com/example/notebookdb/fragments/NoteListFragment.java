package com.example.notebookdb.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.notebookdb.R;

import java.util.ArrayList;

public class NoteListFragment extends Fragment {
    ArrayList<String> notes;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            notes = (ArrayList<String>) getArguments().getSerializable("notes");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_list, container, false);

        ArrayAdapter<String> adapterNotes = new ArrayAdapter<>(
                this.getContext(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                notes
        );
        ListView lvNotes = view.findViewById(R.id.lvNotes);
        lvNotes.setAdapter(adapterNotes);

        lvNotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Bundle args = new Bundle();
                args.putString("noteTitle", notes.get(position));
                getParentFragmentManager().setFragmentResult("inputNoteTitleRequest", args);
            }
        });

        return view;
    }
}
