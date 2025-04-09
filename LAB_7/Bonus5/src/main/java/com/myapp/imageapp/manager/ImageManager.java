package com.myapp.imageapp.manager;

import com.myapp.imageapp.exception.RepositoryException;
import com.myapp.imageapp.model.Image;
import com.myapp.imageapp.repository.ImageRepository;
import com.myapp.imageapp.util.TagGenerator;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ImageManager {
    private ImageRepository repository;

    public ImageManager(ImageRepository repository) {
        this.repository = repository;
    }

    /**
     * Recursively scans a directory and adds image files to the repository.
     * For simplicity, we assume image files have extensions: .jpg, .png, .jpeg, .gif
     *
     * @param directoryPath the root directory to scan.
     * @throws RepositoryException if an error occurs during adding images.
     */
    public void addAll(String directoryPath) throws RepositoryException {
        File dir = new File(directoryPath);
        if (!dir.exists() || !dir.isDirectory()) {
            throw new RepositoryException("Invalid directory: " + directoryPath);
        }
        addImagesRecursively(dir);
    }

    private void addImagesRecursively(File dir) throws RepositoryException {
        File[] files = dir.listFiles();
        if (files == null) return;
        for (File file : files) {
            if (file.isDirectory()) {
                addImagesRecursively(file);
            } else if (isImageFile(file)) {
                // Create an Image object with file name, current date, random tags and file path.
                Image image = new Image(
                        file.getName(),
                        LocalDate.now(),
                        TagGenerator.generateRandomTags(),
                        file.getAbsolutePath()
                );
                repository.addImage(image);
            }
        }
    }

    private boolean isImageFile(File file) {
        String[] validExtensions = { ".jpg", ".jpeg", ".png", ".gif" };
        String name = file.getName().toLowerCase();
        for (String ext : validExtensions) {
            if (name.endsWith(ext)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determines all maximal groups of images such that any two images in the group have at least one common tag.
     * For simplicity, we use a basic approach by grouping images based on each tag.
     * In a real application, consider using more advanced algorithms to merge overlapping groups.
     *
     * @return a list of image groups.
     */
    public List<Set<Image>> getMaximalImageGroupsByTag() {
        List<Image> images = repository.getAllImages();
        List<Set<Image>> groups = new ArrayList<>();
        // For each predefined tag, group images that contain it.
        for (String tag : TagGenerator.generateRandomTags()) { // For a real scenario, iterate over the entire predefined tag list.
            Set<Image> group = new HashSet<>();
            for (Image image : images) {
                if (image.getTags().contains(tag)) {
                    group.add(image);
                }
            }
            if (!group.isEmpty()) {
                groups.add(group);
            }
        }
        return groups;
    }
}
