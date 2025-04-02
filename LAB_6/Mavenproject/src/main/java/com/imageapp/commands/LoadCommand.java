package com.imageapp.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.imageapp.exceptions.InvalidDataException;
import com.imageapp.model.Image;
import com.imageapp.model.ImageCollection;

import java.io.File;
import java.util.List;

public class LoadCommand implements Command {
    private ImageCollection collection;
    private ObjectMapper objectMapper = new ObjectMapper();

    public LoadCommand(ImageCollection collection) {
        this.collection = collection;
    }

    @Override
    public void execute(String[] args) {
        // Expected argument: file path
        if (args.length < 1) {
            throw new InvalidDataException("File path required for load command.");
        }
        String filePath = args[0];
        try {
            CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(List.class, Image.class);
            List<Image> images = objectMapper.readValue(new File(filePath), listType);
            collection.getImages().clear();
            collection.getImages().addAll(images);
            System.out.println("Images loaded successfully from " + filePath);
        } catch (Exception e) {
            System.err.println("Error loading images: " + e.getMessage());
        }
    }
}