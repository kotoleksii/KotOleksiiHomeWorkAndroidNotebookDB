package com.example.notebookdb.models;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.notebookdb.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Note implements Serializable {
    private String title;
    private String tag;
    private String fullText;

    public Note(String title, String tag, String fullText) {
        this.title = title;
        this.tag = tag;
        this.fullText = fullText;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getFullText() {
        return fullText;
    }

    public void setFullText(String text) {
        this.fullText = fullText;
    }

    public static ArrayList<String> getTags() {
        ArrayList<String> tags = new ArrayList<>();

        tags.add("FOOD");
        tags.add("BEAUTY");
        tags.add("FINE ART");
        tags.add("MUSIC");
        tags.add("SPORTS");
        tags.add("SCIENCE");
        tags.add("POLITICS");

        return tags;
    }

    public static int getTagImage(String tag) {
        int img = 0;

        switch (tag) {
            case "FOOD":
                img = R.drawable.food;
                break;
            case "BEAUTY":
                img = R.drawable.beauty;
                break;
            case "FINE ART":
                img = R.drawable.fine_art;
                break;
            case "MUSIC":
                img = R.drawable.music;
                break;
            case "SPORTS":
                img = R.drawable.sports;
                break;
            case "SCIENCE":
                img = R.drawable.science;
                break;
            case "POLITICS":
                img = R.drawable.politics;
                break;
        }
        return img;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static ArrayList<String> getNoteNameList(ArrayList<Note> notes) {
        List<String> noteNameList = notes
                .stream()
                .map(e -> e.getTitle())
                .collect(Collectors.toList());

        return (ArrayList<String>) noteNameList;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static Note getNoteByName(ArrayList<Note> notes, String noteName) {
        return notes
                .stream()
                .filter(n -> n.getTitle().equals(noteName))
                .findFirst()
                .orElse(null);
    }
}

