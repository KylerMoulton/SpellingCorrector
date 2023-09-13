package spell;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class SpellCorrector implements ISpellCorrector {
    private Trie trie;

    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {
        trie = new Trie();
        var file = new File(dictionaryFileName);
        if (file.exists()) {
            var scanner = new Scanner(file);
            while (scanner.hasNext()) {
                var text = scanner.next();
                trie.add(text);
            }
        }
    }

    @Override
    public String suggestSimilarWord(String inputWord) {
        if (trie.find(inputWord) != null) {
            return trie.find(inputWord).toString();
        }
        StringBuilder editWord = new StringBuilder();
        editWord.append(inputWord);
        Set<String> setOfWords = new HashSet<>();
        setOfWords.addAll(deletionDistance(editWord));
        setOfWords.addAll(transpositionDistance(editWord));
        setOfWords.addAll(alterationDistance(editWord));
        setOfWords.addAll(insertionDistance(editWord));
        List<Node> foundWords = new ArrayList<>();
        for (String word : setOfWords){
            if (trie.find(word)!=null) {
                foundWords.add((Node)trie.find(word));
            }
        }
        if (foundWords.isEmpty())
        return null;
    }
    public Set<String> deletionDistance(StringBuilder inputString) {
        Set<String> deletionSet = new HashSet<>();
        for (int i = 0; i < inputString.length(); i++) {
            StringBuilder tempString = new StringBuilder();
            tempString.append(inputString);
            tempString.deleteCharAt(i);
            deletionSet.add(tempString.toString());
        }
        return deletionSet;
    }
    public Set<String> transpositionDistance(StringBuilder inputString) {
        Set<String> deletionSet = new HashSet<>();
        for (int i = 0; i < inputString.length() - 1; i++) {
            StringBuilder tempString = new StringBuilder();
            tempString.append(inputString);
            char tempChar1 = tempString.charAt(i);
            char tempChar2 = tempString.charAt(i + 1);
            tempString.setCharAt(i, tempChar2);
            tempString.setCharAt(i + 1, tempChar1);
            deletionSet.add(tempString.toString());
        }
        return deletionSet;
    }
    public Set<String> alterationDistance(StringBuilder inputString) {
        Set<String> alterationSet = new HashSet<>();
        for (int i = 0; i < inputString.length(); i++) {
            StringBuilder tempString = new StringBuilder();
            tempString.append(inputString);
            for (char alphabet = 'a'; alphabet <= 'z'; alphabet++) {
                tempString.setCharAt(i, alphabet);
                alterationSet.add(tempString.toString());
            }
        }
        return alterationSet;
    }
    public Set<String> insertionDistance(StringBuilder inputString) {
        Set<String> insertionSet = new HashSet<>();
        for (int i = 0; i < inputString.length()+1; i++) {
            StringBuilder tempString = new StringBuilder();
            tempString.append(inputString);
            for (char alphabet = 'a'; alphabet <= 'z'; alphabet++) {
                tempString.insert(i, alphabet);
                insertionSet.add(tempString.toString());
                tempString.deleteCharAt(i);
            }
        }
        return insertionSet;
    }
}