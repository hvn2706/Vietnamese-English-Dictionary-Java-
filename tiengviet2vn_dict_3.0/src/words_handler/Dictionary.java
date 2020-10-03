package words_handler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Dictionary {
    private ArrayList<Word> words = new ArrayList<>();

    public int getLength() {
        return words.size();
    }

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

    public void removeWord(int index) {
        words.remove(index);
    }

    public boolean existed(Word w) {
        for (int i = 0; i < words.size(); ++i) {
            if (w.getWord_target().equals(words.get(i).getWord_target())) {
                return true;
            }
        }

        return false;
    }

    public void sortDictionary() {
        Collections.sort(words, new cmpWord());
    }
}

class cmpWord implements Comparator<Word> {
    public int compare(Word w1, Word w2) {
        return w1.getWord_target().compareTo(w2.getWord_target());
    }
}