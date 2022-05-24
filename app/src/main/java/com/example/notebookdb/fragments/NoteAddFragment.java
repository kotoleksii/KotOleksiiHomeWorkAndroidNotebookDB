package com.example.notebookdb.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import com.example.notebookdb.MainActivity;
import com.example.notebookdb.R;

import java.util.ArrayList;

public class NoteAddFragment extends Fragment {
    private ArrayList<String> tags;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tags = (ArrayList<String>) getArguments().getSerializable("tags");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_add, container, false);

        EditText etTitle = view.findViewById(R.id.etTitle);
        EditText etFullText = view.findViewById(R.id.etFullText);
        Spinner spTags = view.findViewById(R.id.spTags);
        Button btnAdd = view.findViewById(R.id.btnAdd);
        Button btnBack = view.findViewById(R.id.btnBack);

        if (tags != null) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getContext(), R.layout.custom_spinner_item, tags);
            spTags.setAdapter(adapter);
        }

        btnBack.setOnClickListener((MainActivity) this.getActivity());
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();

                args.putString("title", etTitle.getText().toString());
                args.putString("tag", spTags.getSelectedItem().toString());
                args.putString("fullText", etFullText.getText().toString());

                getParentFragmentManager().setFragmentResult("inputNoteRequest", args);
            }
        });

        return view;
    }
}
