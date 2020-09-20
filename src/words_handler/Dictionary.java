package words_handler;

import java.util.ArrayList;

public class Dictionary {
    private ArrayList<Word> words = new ArrayList<>();

    public void addWord(Word w) {
        words.add(w);
    }
    
    public Word getWord(int index) {
        return words.get(index);
    }

    public void setWord(int index, Word w) {
        words.set(index, w);
    }

    public void removeWord(Word w) {
        words.remove(w);
    }
}
