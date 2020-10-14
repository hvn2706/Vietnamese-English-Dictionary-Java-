package import_export;

import words_handler.Dictionary;

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

    public void dictionaryAdvance() {
        System.out.println("tiengviet2vn_dict_1.0");
        System.out.println("Press Ctrl+C twice to exit.");
        System.out.println("----------------------------");
        DictionaryManagement mn = new DictionaryManagement();
        mn.insertFromFile();
        this.showAllWords(mn.getDict());
        mn.dictionaryLookup();
    }
}
