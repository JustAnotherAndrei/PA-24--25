package com.myapp.imageapp.repository;

import com.myapp.imageapp.exception.RepositoryException;
import com.myapp.imageapp.model.Image;
import java.util.List;

public interface ImageRepository {
    void addImage(Image image) throws RepositoryException;
    List<Image> getAllImages();
    void saveRepository(String filePath) throws RepositoryException;
}
