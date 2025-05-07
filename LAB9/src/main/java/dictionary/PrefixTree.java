// File: src/main/java/dictionary/PrefixTree.java

package dictionary;

import java.util.*;

public class PrefixTree {
    private final TrieNode root = new TrieNode();

    public void insert(String word) {
        TrieNode current = root;
        for (char ch : word.toCharArray()) {
            current = current.getChildren()
                    .computeIfAbsent(ch, c -> new TrieNode());
        }
        current.setEndOfWord(true);
    }

    public List<String> getWordsWithPrefix(String prefix) {
        TrieNode current = root;
        for (char ch : prefix.toCharArray()) {
            current = current.getChildren().get(ch);
            if (current == null) return Collections.emptyList();
        }
        List<String> result = new ArrayList<>();
        collectWords(current, prefix, result);
        return result;
    }

    private void collectWords(TrieNode node, String prefix, List<String> result) {
        if (node.isEndOfWord()) {
            result.add(prefix);
        }
        for (Map.Entry<Character, TrieNode> entry : node.getChildren().entrySet()) {
            collectWords(entry.getValue(), prefix + entry.getKey(), result);
        }
    }

    public TrieNode getRoot() {
        return root;
    }
}

