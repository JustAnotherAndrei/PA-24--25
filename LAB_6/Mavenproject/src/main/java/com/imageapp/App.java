package com.imageapp;

import com.imageapp.model.ImageCollection;
import com.imageapp.shell.Shell;

public class App {
    public static void main(String[] args) {
        // Create an image collection and launch the shell
        ImageCollection collection = new ImageCollection();
        Shell shell = new Shell(collection);
        shell.run();
    }
}
