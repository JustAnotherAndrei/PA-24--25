package com.imageapp.commands;

import com.imageapp.exceptions.InvalidDataException;
import com.imageapp.model.ImageCollection;

public class RemoveCommand implements Command {
    private ImageCollection collection;

    public RemoveCommand(ImageCollection collection) {
        this.collection = collection;
    }

    @Override
    public void execute(String[] args) {
        // Expected argument: image name
        if (args.length < 1) {
            throw new InvalidDataException("Image name required for remove command.");
        }
        String name = args[0];
        try {
            collection.removeImage(name);
            System.out.println("Image removed successfully: " + name);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
