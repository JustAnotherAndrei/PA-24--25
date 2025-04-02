package com.imageapp.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imageapp.exceptions.InvalidDataException;
import com.imageapp.model.ImageCollection;

import java.io.File;

public class SaveCommand implements Command {
    private ImageCollection collection;
    private ObjectMapper objectMapper = new ObjectMapper();

    public SaveCommand(ImageCollection collection) {
        this.collection = collection;
    }

    @Override
    public void execute(String[] args) {
        // Expected argument: file path
        if (args.length < 1) {
            throw new InvalidDataException("File path required for save command.");
        }
        String filePath = args[0];
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), collection.getImages());
            System.out.println("Images saved successfully to " + filePath);
        } catch (Exception e) {
            System.err.println("Error saving images: " + e.getMessage());
        }
    }
}
