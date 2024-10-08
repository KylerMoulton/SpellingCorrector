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
            return inputWord.toLowerCase();
            //return trie.find(inputWord).toString();
        }
        StringBuilder editWord = new StringBuilder();
        editWord.append(inputWord);
        Set<String> editDistance1 = new HashSet<>();
        editDistance1.addAll(deletionDistance(editWord));
        editDistance1.addAll(transpositionDistance(editWord));
        editDistance1.addAll(alterationDistance(editWord));
        editDistance1.addAll(insertionDistance(editWord));
        if (editDistance1.isEmpty()){
            return null;
        }
        List <String> foundWords1 = new ArrayList<>();
        for (String word : editDistance1){
            if (trie.find(word)!=null) {
                foundWords1.add(word);
                //foundWords.add(trie.find(word).toString());
            }
        }
        if (foundWords1.size()==1){
            return foundWords1.get(0);
        }
        if (foundWords1.size()>1){
            return tieBreaker(foundWords1);
        }

        Set<String> editDistance2 = new HashSet<>();
        for (String word : editDistance1) {
            editDistance2.addAll(deletionDistance(new StringBuilder(word)));
            editDistance2.addAll(alterationDistance(new StringBuilder(word)));
            editDistance2.addAll(transpositionDistance(new StringBuilder(word)));
            editDistance2.addAll(insertionDistance(new StringBuilder(word)));
        }
        List <String> foundWords2 = new ArrayList<>();
        for (String word2 : editDistance2) {
            if (trie.find(word2) != null) {
                foundWords2.add(word2);
                //foundWords.add(trie.find(word).toString());
            }
        }
        if (foundWords2.size()==1){
            return foundWords2.get(0);
        }
        if (foundWords2.size()>1){
            return tieBreaker(foundWords2);
        }
        return null;
    }
    public String tieBreaker(List<String> possibleWords) {
        String curWord = possibleWords.get(0);
        List <String> sameCountWords = new ArrayList<>();
        for (int i = 0; i < possibleWords.size() - 1; i++) {
            if (trie.find(curWord).getValue() < trie.find(possibleWords.get(i)).getValue()){
                curWord = possibleWords.get(i);
            }
//            else if (trie.find(curWord).getValue() > trie.find(possibleWords.get(i)).getValue()){
//                possibleWords.remove(i);
//            }
            else if (trie.find(curWord).getValue() == trie.find(possibleWords.get(i)).getValue()){
                sameCountWords.add(curWord);
                sameCountWords.add(possibleWords.get(i));
            }
        }
        if (!sameCountWords.isEmpty()) {
            return curWord;
        }
        return sameCountWords.get(0);

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