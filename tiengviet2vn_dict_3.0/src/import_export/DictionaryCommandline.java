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

    public String dictionarySearchExact(String s, Dictionary dict) {
        int len = 0;
        for (int i = 0; i < dict.getLength(); ++i) {
            Word tmp = dict.getWord(i);
            if (tmp.getWord_target().toLowerCase().equals(s.toLowerCase())) {
                return tmp.getWord_explain();
            }
        }
        return "";
    }

    public String[] dictionarySearchSamePrefix(String s, Dictionary dict) {
        String[] rt = new String[1000];
        int len = 0;
        for (int i = 0; i < dict.getLength() && len < 1000; ++i) {
            Word tmp = dict.getWord(i);
            if (tmp.getWord_target().toLowerCase().startsWith(s.toLowerCase())) {
                rt[len] = tmp.getWord_target();
                len++;
            }
        }
        return rt;
    }

    public DictionaryManagement dictionaryAdvance() {
        DictionaryManagement mn = new DictionaryManagement();
        mn.insertFromFile();
        mn.dictionaryExportToFile();
        // mn.dictionaryLookup();
        return mn;
    }
}
