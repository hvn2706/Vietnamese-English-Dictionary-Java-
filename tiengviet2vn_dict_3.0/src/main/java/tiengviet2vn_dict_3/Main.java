package tiengviet2vn_dict;

import import_export.DictionaryCommandline;
import import_export.DictionaryManagement;
import ui.DictionaryApplication;

import com.google.cloud.translate.*;

public class Main {
    /**
     * dictionary2vn.
     * @param args do nothing.
     */
    public static void main(String[] args) {
        DictionaryCommandline cmd = new DictionaryCommandline();
        DictionaryManagement mn = cmd.dictionaryAdvance();
        DictionaryApplication app = new DictionaryApplication(cmd, mn);
        app.runApplication();
    }
}