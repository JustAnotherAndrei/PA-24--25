package com.myapp.imageapp;

import com.myapp.imageapp.exception.RepositoryException;
import com.myapp.imageapp.manager.ImageManager;
import com.myapp.imageapp.model.Image;
import com.myapp.imageapp.repository.BinaryRepository;
import com.myapp.imageapp.repository.ImageRepository;
import com.myapp.imageapp.repository.JSONRepository;
import com.myapp.imageapp.repository.PlainTextRepository;

import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        // Choose a repository implementation:
        ImageRepository repository = new PlainTextRepository();
        // Alternatively, you could use:
        // ImageRepository repository = new BinaryRepository();
        // ImageRepository repository = new JSONRepository();

        ImageManager manager = new ImageManager(repository);
        String imagesDirectory = "C:\\Users\\Andrei\\OneDrive\\Pictures"; // update with your actual image folder path

        try {
            // Add all images from the directory (and its subdirectories) to the repository
            manager.addAll(imagesDirectory);

            // Display all images added
            System.out.println("Images in repository:");
            for (Image image : repository.getAllImages()) {
                System.out.println(image);
            }

            // Save the repository to a file (update file name/extension based on repository type)
            String savePath = "images_repository.txt"; // adjust for your chosen format
            repository.saveRepository(savePath);
            System.out.println("Repository saved to " + savePath);

            // Determine maximal groups of images with at least one common tag
            List<Set<Image>> groups = manager.getMaximalImageGroupsByTag();
            System.out.println("\nMaximal groups of images with at least one common tag:");
            int groupNumber = 1;
            for (Set<Image> group : groups) {
                System.out.println("Group " + groupNumber + ":");
                for (Image image : group) {
                    System.out.println("  " + image.getName() + " with tags " + image.getTags());
                }
                groupNumber++;
            }
        } catch (RepositoryException e) {
            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

