package import_export;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import words_handler.Dictionary;
import words_handler.Word;

public class DictionaryManagement {
    private Dictionary dict = new Dictionary();

    public Dictionary getDict() {
        return dict;
    }

    public void insertFromCommandline() {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        for (int i = 0; i < n; ++i) {
            String target = sc.next();
            String explain = sc.next();
            Word word = new Word(target, explain);
            dict.addWord(word);
        }
        sc.close();
    }

    public void insertFromFile() {
        try {
            File input = new File("../data/dictionaries.txt");
            Scanner sc = new Scanner(input);

            while (sc.hasNext()) {
                String target = sc.next();
                String explain = sc.next();
                Word word = new Word(target, explain);
                dict.addWord(word);
            }
            sc.close();
        } catch (IOException e) {
            System.out.println("File not found");
        }
    }

    public void dictionaryLookup() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.print("\tSearch: ");
            String s = sc.nextLine();
            for (int i = 0; i < dict.getLength(); ++i) {
                Word tmp = dict.getWord(i);
                if (tmp.getWord_target().toLowerCase().contains(s.toLowerCase())) {
                    System.out.println(tmp.getWord_target() + " " + tmp.getWord_explain());
                }
            }

            if (s.equals("-1")) {
                break;
            }
        }
    }
}
