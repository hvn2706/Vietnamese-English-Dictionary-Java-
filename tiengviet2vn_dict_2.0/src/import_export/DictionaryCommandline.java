package import_export;

import words_handler.Dictionary;
import words_handler.Word;

public class DictionaryCommandline {
    public void showAllWords(Dictionary dict) {
        System.out.println("No    | English            | Vietnamese");

        for (int i = 0; i < dict.getLength(); ++i) {
            String no = String.valueOf(i + 1);
            for (int j = 0; j < 6; ++j) {
                if (j >= no.length()) {
                    System.out.print(' ');
                } else {
                    System.out.print(no.charAt(j));
                }
            }
            System.out.print("| ");

            String target = dict.getWord(i).getWord_target();
            for (int j = 0; j < 19; ++j) {
                if (j >= target.length()) {
                    System.out.print(' ');
                } else {
                    System.out.print(target.charAt(j));
                }
            }
            System.out.print("| ");

            String explain = dict.getWord(i).getWord_explain();
            System.out.println(explain);
        }
    }

    public String[] dictionarySearcher(String s, Dictionary dict) {
        String[] rt = new String[1000];
        int len = 0;
        for (int i = 0; i < dict.getLength(); ++i) {
            Word tmp = dict.getWord(i);
            if (tmp.getWord_target().toLowerCase().contains(s.toLowerCase())) {
                rt[len] = tmp.getWord_explain();
                len++;
            }
        }
        return rt;
    }

    public void dictionaryAdvance() {
        System.out.println("tiengviet2vn_dict_2.0");
        System.out.println("Press Ctrl+C twice to exit.");
        System.out.println("----------------------------");
        DictionaryManagement mn = new DictionaryManagement();
        mn.insertFromFile();
        mn.dictionaryExportToFile();
        mn.dictionaryLookup();
    }
}
