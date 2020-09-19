import words_handler.Dictionary;
import words_handler.Word;

public class Main {
    /**
     * dictionary2vn.
     * @param args do nothing.
     */
    public static void main(String[] args) {
        Word word = new Word("hien", "deptrai");
        Dictionary dict = new Dictionary();
        dict.addWord(word);
        System.out.println(dict.getWord(0).getWord_target() + ": " + dict.getWord(0).getWord_explain());
    }
}
