package com.myapp.imageapp.model;

import java.time.LocalDate;
import java.util.List;

public class Image {
    private String name;
    private LocalDate date;
    private List<String> tags;
    private String filePath; // location in the file system

    public Image(String name, LocalDate date, List<String> tags, String filePath) {
        this.name = name;
        this.date = date;
        this.tags = tags;
        this.filePath = filePath;
    }

    // Getters
    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public List<String> getTags() {
        return tags;
    }

    public String getFilePath() {
        return filePath;
    }

    @Override
    public String toString() {
        return "Image{" +
                "name='" + name + '\'' +
                ", date=" + date +
                ", tags=" + tags +
                ", filePath='" + filePath + '\'' +
                '}';
    }
}
