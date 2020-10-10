package tiengviet2vn_dict.words_handler;

public class Word {
    private String word_target;
    private String word_explain;

    public Word() {
        word_target = "";
        word_explain = "";
    }

    public Word(String target, String explain) {
        this.word_target = target;
        this.word_explain = explain;
    }

    // create new word that has the same properties with another word w.
    public Word(Word w) {
        this.word_target = w.word_target;
        this.word_explain = w.word_explain;
    }

    public void copyWord(Word w) {
        this.word_target = w.word_target;
        this.word_explain = w.word_explain;
    }

    public void setWord_target(String word_target) {
        this.word_target = word_target;
    }

    public String getWord_target() {
        return word_target;
    }

    public void setWord_explain(String word_explain) {
        this.word_explain = word_explain;
    }

    public String getWord_explain() {
        return word_explain;
    }
}

