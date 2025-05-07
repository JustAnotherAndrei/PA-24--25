// File: src/main/java/dictionary/TrieNode.java

package dictionary;

import java.util.*;

public class TrieNode {
    private final Map<Character, TrieNode> children = new HashMap<>();
    private boolean endOfWord;

    public Map<Character, TrieNode> getChildren() {
        return children;
    }

    public boolean isEndOfWord() {
        return endOfWord;
    }

    public void setEndOfWord(boolean value) {
        this.endOfWord = value;
    }
}
