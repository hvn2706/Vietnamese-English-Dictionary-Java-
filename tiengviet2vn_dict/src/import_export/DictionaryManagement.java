package import_export;
import java.util.Scanner;
import words_handler.Dictionary;
import words_handler.Word;

public class DictionaryManagement {
    private Dictionary dict = new Dictionary();

    public void insertFromCommandline() {
        System.out.print("Input: ");
        Scanner sc = new Scanner(System.in, "UTF-8");
        int n = sc.nextInt();

        for (int i = 0; i < n; ++i) {
            String target = sc.next();
            String explain = sc.next();
            Word word = new Word(target, explain);
            dict.addWord(word);
        }
        sc.close();
    }

    public Dictionary getDict() {
        return dict;
    }
}
