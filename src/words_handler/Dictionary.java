package words_handler;

public class Dictionary {
    private Word[] words = new Word[1000];
    private int len = 0;

    public void addWord(Word w) {
        words[len] = new Word(w);
        len++;
    }
    
    public Word getWord(int index) {
        return words[index];
    }

    public void setWord(int index, Word w) {
        words[index].copyWord(w);
    }

    public void removeWord(String target) {

    }
}
