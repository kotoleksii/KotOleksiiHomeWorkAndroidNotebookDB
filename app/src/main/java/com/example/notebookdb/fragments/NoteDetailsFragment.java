package com.example.notebookdb.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.notebookdb.MainActivity;
import com.example.notebookdb.R;
import com.example.notebookdb.models.Note;

public class NoteDetailsFragment extends Fragment {
    Note note;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            note = (Note) getArguments().getSerializable("note");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_details, container, false);

        Button btnClose = view.findViewById(R.id.btnBack);
        btnClose.setOnClickListener((MainActivity) this.getActivity());

        if (note != null) {
            TextView txtTitle = view.findViewById(R.id.txtTitle);
            TextView txtTag = view.findViewById(R.id.txtTag);
            TextView txtFullText = view.findViewById(R.id.txtFullText);
            ImageView imgTag = view.findViewById(R.id.imgTag);

            txtTitle.setText(txtTitle.getText() + ": " + note.getTitle());
            txtTag.setText(txtTag.getText() + ": " + note.getTag());
            txtFullText.setText(note.getFullText());
            imgTag.setImageResource(Note.getTagImage(note.getTag()));
        }

        return view;
    }
}
