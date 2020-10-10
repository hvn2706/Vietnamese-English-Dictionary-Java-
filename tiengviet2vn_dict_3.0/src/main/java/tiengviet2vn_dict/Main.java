package tiengviet2vn_dict;

import tiengviet2vn_dict.import_export.DictionaryCommandline;
import tiengviet2vn_dict.import_export.DictionaryManagement;
import tiengviet2vn_dict.ui.DictionaryApplication;

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