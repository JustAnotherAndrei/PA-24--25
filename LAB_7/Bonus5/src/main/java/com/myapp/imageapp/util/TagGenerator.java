package com.myapp.imageapp.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class TagGenerator {
    private static final List<String> predefinedTags = Arrays.asList(
            "nature", "portrait", "landscape", "urban", "abstract", "wildlife", "macro"
    );
    private static final Random random = new Random();

    /**
     * Generates a random subset of tags.
     * @return a list of random tags.
     */
    public static List<String> generateRandomTags() {
        // Shuffle a copy of the predefined tags
        List<String> tags = new ArrayList<>(predefinedTags);
        Collections.shuffle(tags, random);
        // Choose a random number of tags between 1 and the number of available tags
        int count = random.nextInt(predefinedTags.size()) + 1;
        return tags.subList(0, count);
    }
}
