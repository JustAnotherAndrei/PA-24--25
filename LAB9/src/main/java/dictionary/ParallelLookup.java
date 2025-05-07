// File: src/main/java/dictionary/ParallelLookup.java

package dictionary;

import java.util.*;
import java.util.stream.Collectors;

public class ParallelLookup {
    private final List<String> words;

    public ParallelLookup(Collection<String> dict) {
        // Prepare a list for parallel streaming
        this.words = new ArrayList<>(dict);
    }

    /**
     * Returns all words starting with the given prefix, case-insensitive.
     */
    public List<String> lookup(String prefix) {
        String up = prefix.toUpperCase();
        return words
                .parallelStream()
                .filter(w -> w.startsWith(up))
                .collect(Collectors.toList());
    }
}

