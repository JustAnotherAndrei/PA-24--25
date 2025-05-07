// File: src/main/java/dictionary/Dictionary.java

package dictionary;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Dictionary {
    private final Set<String> words = new HashSet<>();
    private final PrefixTree trie = new PrefixTree();

    public Dictionary(String filePath) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        for (String word : lines) {
            word = word.trim().toUpperCase();
            if (!word.isEmpty()) {
                words.add(word);
                trie.insert(word);
            }
        }
    }

    public boolean contains(String word) {
        return words.contains(word.toUpperCase());
    }

    public List<String> searchPrefixParallel(String prefix) {
        return words.parallelStream()
                .filter(w -> w.startsWith(prefix.toUpperCase()))
                .toList();
    }

    public List<String> searchPrefixWithTrie(String prefix) {
        return trie.getWordsWithPrefix(prefix.toUpperCase());
    }

    public PrefixTree getTrie() {
        return trie;
    }
}
