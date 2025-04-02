package com.imageapp.commands;

import com.imageapp.exceptions.InvalidDataException;
import com.imageapp.model.Image;
import com.imageapp.model.ImageCollection;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class UpdateCommand implements Command {
    private ImageCollection collection;

    public UpdateCommand(ImageCollection collection) {
        this.collection = collection;
    }

    @Override
    public void execute(String[] args) {
        // Expected arguments: existing name, new name, new date, new tags (comma-separated), new location
        if (args.length < 5) {
            throw new InvalidDataException("Insufficient arguments for update command.");
        }
        String existingName = args[0];
        String newName = args[1];
        String newDateString = args[2];
        String newTagsString = args[3];
        String newLocation = args[4];

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date newDate;
        try {
            newDate = sdf.parse(newDateString);
        } catch (ParseException e) {
            throw new InvalidDataException("Invalid date format. Use yyyy-MM-dd.");
        }

        String[] tagsArray = newTagsString.split(",");
        Image updatedImage = new Image(newName, newDate, Arrays.asList(tagsArray), newLocation);
        try {
            collection.updateImage(existingName, updatedImage);
            System.out.println("Image updated successfully: " + updatedImage);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
