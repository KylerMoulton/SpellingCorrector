package spell;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class SpellCorrector implements ISpellCorrector{
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
        return null;
    }
}
