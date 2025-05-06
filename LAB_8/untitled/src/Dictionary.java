import java.io.*;
import java.util.*;

public class Dictionary {
    private final Set<String> words = new HashSet<>();

    public Dictionary(String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String word;
            while ((word = br.readLine()) != null) {
                words.add(word.trim().toUpperCase());
            }
        }
    }

    public boolean isValidWord(String word) {
        return words.contains(word.toUpperCase());
    }

    public Set<String> getWords() {
        return words;
    }
}
