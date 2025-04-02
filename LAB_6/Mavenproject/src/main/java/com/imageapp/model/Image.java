package com.imageapp.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Image {
    private String name;
    private Date date;
    private List<String> tags;
    private String location;

    public Image(String name, Date date, List<String> tags, String location) {
        this.name = name;
        this.date = date;
        this.tags = new ArrayList<>(tags);
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Image{" +
                "name='" + name + '\'' +
                ", date=" + date +
                ", tags=" + tags +
                ", location='" + location + '\'' +
                '}';
    }
}
