package com.myapp.imageapp.repository;

import com.myapp.imageapp.exception.RepositoryException;
import com.myapp.imageapp.model.Image;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlainTextRepository implements ImageRepository {
    private List<Image> images = new ArrayList<>();

    @Override
    public void addImage(Image image) throws RepositoryException {
        if (image == null) {
            throw new RepositoryException("Cannot add a null image");
        }
        images.add(image);
    }

    @Override
    public List<Image> getAllImages() {
        return images;
    }

    @Override
    public void saveRepository(String filePath) throws RepositoryException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Image image : images) {
                writer.write(image.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RepositoryException("Error saving repository to plain text file.", e);
        }
    }
}
