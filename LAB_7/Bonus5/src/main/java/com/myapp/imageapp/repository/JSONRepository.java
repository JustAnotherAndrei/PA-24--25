package com.myapp.imageapp.repository;

import com.myapp.imageapp.exception.RepositoryException;
import com.myapp.imageapp.model.Image;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JSONRepository implements ImageRepository {
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
            writer.write("[\n");
            for (int i = 0; i < images.size(); i++) {
                Image image = images.get(i);
                String jsonImage = "  {" +
                        "\"name\": \"" + image.getName() + "\"," +
                        "\"date\": \"" + image.getDate() + "\"," +
                        "\"tags\": " + image.getTags().toString() + "," +
                        "\"filePath\": \"" + image.getFilePath() + "\"" +
                        "}";
                writer.write(jsonImage);
                if (i < images.size() - 1) {
                    writer.write(",");
                }
                writer.write("\n");
            }
            writer.write("]");
        } catch (IOException e) {
            throw new RepositoryException("Error saving repository to JSON file.", e);
        }
    }
}
