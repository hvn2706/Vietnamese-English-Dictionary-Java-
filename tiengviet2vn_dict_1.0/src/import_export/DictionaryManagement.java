package import_export;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import words_handler.Dictionary;
import words_handler.Word;

public class DictionaryManagement {
    private Dictionary dict = new Dictionary();

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
            File input = new File("data/dictionaries.txt");
            Scanner sc = new Scanner(input);

            int n = sc.nextInt();

            for (int i = 0; i < n; ++i) {
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

    public Dictionary getDict() {
        return dict;
    }
}
