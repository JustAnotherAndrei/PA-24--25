package com.myapp.imageapp.repository;

import com.myapp.imageapp.exception.RepositoryException;
import com.myapp.imageapp.model.Image;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// Helper wrapper to allow serialization
class ImageWrapper implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Image> images;

    public ImageWrapper(List<Image> images) {
        this.images = images;
    }

    public List<Image> getImages() {
        return images;
    }
}

public class BinaryRepository implements ImageRepository {
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
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            // Wrap the list in a serializable wrapper
            oos.writeObject(new ImageWrapper(images));
        } catch (IOException e) {
            throw new RepositoryException("Error saving repository to binary file.", e);
        }
    }
}
