package com.imageapp.model;

import com.imageapp.exceptions.ImageNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class ImageCollection {
    private List<Image> images;

    public ImageCollection() {
        this.images = new ArrayList<>();
    }

    public void addImage(Image image) {
        images.add(image);
    }

    public void removeImage(String name) throws ImageNotFoundException {
        boolean removed = images.removeIf(img -> img.getName().equalsIgnoreCase(name));
        if (!removed) {
            throw new ImageNotFoundException("Image with name " + name + " not found.");
        }
    }

    public void updateImage(String name, Image newImageData) throws ImageNotFoundException {
        for (int i = 0; i < images.size(); i++) {
            if (images.get(i).getName().equalsIgnoreCase(name)) {
                images.set(i, newImageData);
                return;
            }
        }
        throw new ImageNotFoundException("Image with name " + name + " not found.");
    }

    public List<Image> getImages() {
        return images;
    }
}

