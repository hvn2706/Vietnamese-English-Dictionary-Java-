import import_export.DictionaryCommandline;
import import_export.DictionaryManagement;
import ui.DictionaryApplication;

public class Main {
    /**
     * dictionary2vn.
     * @param args do nothing.
     */
    public static void main(String[] args) {
        DictionaryCommandline cmd = new DictionaryCommandline();
        DictionaryManagement mn = cmd.dictionaryAdvance();
        DictionaryApplication.runApplication(cmd, mn);
    }
}
