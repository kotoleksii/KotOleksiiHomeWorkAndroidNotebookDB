package com.example.notebookdb.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.notebookdb.models.Note;

import java.util.ArrayList;

public class DbService {
    public static final String DATABASE_NAME = "homework.db";
    private static final String TABLE_NAME = "notes";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_TAG = "tag";
    private static final String COLUMN_FULLTEXT = "fullText";

    SQLiteDatabase db;

    public DbService(Context context) {
        //context.deleteDatabase(DATABASE_NAME);
        db = context.openOrCreateDatabase(DATABASE_NAME, context.MODE_PRIVATE, null);
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (title TEXT, tag TEXT, fullText TEXT)"
        );
    }

    public ArrayList<Note> getNoteList() {
        ArrayList<Note> notes = new ArrayList<>();

        if (!db.isOpen())
            return notes;

        Cursor query = db.rawQuery(
                "SELECT * FROM " + TABLE_NAME + ";",
                null
        );

        if (query.moveToFirst()) {
            do {
                String title = query.getString(0);
                String tag = query.getString(1);
                String fullText = query.getString(2);
                notes.add(new Note(title, tag, fullText));
            } while (query.moveToNext());
        }
        query.close();

        return notes;
    }

    public void saveNote(Note note) {
        if (!db.isOpen())
            return;

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, note.getTitle());
        cv.put(COLUMN_TAG, note.getTag());
        cv.put(COLUMN_FULLTEXT, note.getFullText());

        db.insert(TABLE_NAME, null, cv);
    }

    public void close() {
        db.close();
    }
}

