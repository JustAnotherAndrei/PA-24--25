package com.imageapp.commands;

import com.imageapp.exceptions.InvalidDataException;
import com.imageapp.model.Image;
import com.imageapp.model.ImageCollection;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class AddCommand implements Command {
    private ImageCollection collection;

    public AddCommand(ImageCollection collection) {
        this.collection = collection;
    }

    @Override
    public void execute(String[] args) {
        // Expected arguments: name, date, tags (comma-separated), location
        if (args.length < 4) {
            throw new InvalidDataException("Insufficient arguments for add command.");
        }
        String name = args[0];
        String dateString = args[1];
        String tagsString = args[2];
        String location = args[3];

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try {
            date = sdf.parse(dateString);
        } catch (ParseException e) {
            throw new InvalidDataException("Invalid date format. Use yyyy-MM-dd.");
        }

        String[] tagsArray = tagsString.split(",");
        Image image = new Image(name, date, Arrays.asList(tagsArray), location);
        collection.addImage(image);
        System.out.println("Image added successfully: " + image);
    }
}
