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

    public void dictionaryBasic() {
        DictionaryManagement mn = new DictionaryManagement();
        System.out.println("tiengviet2vn_dict_0.0");
        System.out.println("Input format: [ID number] [Word in English] [Word in Vietnamese]");
        System.out.println("If input is not in correct format, the application is going to crashed!");
        System.out.println("Press Ctrl+C twice to exit.");
        System.out.println("----------------------------");
        mn.insertFromCommandline();
        this.showAllWords(mn.getDict());
    }
}
