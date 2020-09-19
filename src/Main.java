import words_handler.Word;

public class Main {
    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        Word word = new Word("hien", "deptrai");
        System.out.println(word.getWord_target() + " " + word.getWord_explain());
    }
}
