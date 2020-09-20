import import_export.DictionaryCommandline;
import words_handler.Dictionary;
import words_handler.Word;
import import_export.DictionaryManagement;

public class Main {
    /**
     * dictionary2vn.
     * @param args do nothing.
     */
    public static void main(String[] args) {
        DictionaryManagement mn = new DictionaryManagement();
        DictionaryCommandline cmd = new DictionaryCommandline();
        mn.insertFromCommandline();
        cmd.showAllWords(mn.getDict());
    }
}
