package import_export;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import words_handler.Dictionary;
import words_handler.Word;

public class DictionaryManagement {
    private final Dictionary dict = new Dictionary();

    public Dictionary getDict() {
        return dict;
    }

    public void insertFromFile() {
        try {
            File input = new File("../data/en.txt");
            File input_ = new File("../data/vi.txt");
            Scanner sc = new Scanner(input, "UTF-8");
            Scanner sc_ = new Scanner(input_, "UTF-8");

            while (sc.hasNext() && sc_.hasNextLine()) {
                String target = sc.nextLine();
                String explain = sc_.nextLine();
                Word word = new Word(target, explain);
                dict.addWord(word);
            }

            sc.close();
            sc_.close();
        } catch (IOException e) {
            System.out.println("File not found");
        }
    }

    public void dictionaryExportToFile() {
        try {
            FileWriter file = new FileWriter("../data/output.txt");
            file.write("No    | English            | Vietnamese\n");

            dict.sortDictionary();
            for (int i = 0; i < dict.getLength(); ++i) {
                String no = String.valueOf(i + 1);
                for (int j = 0; j < 6; ++j) {
                    if (j >= no.length()) {
                        file.write(' ');
                    } else {
                        file.write(no.charAt(j));
                    }
                }
                file.write("| ");

                String target = dict.getWord(i).getWord_target();
                for (int j = 0; j < 19; ++j) {
                    if (j >= target.length()) {
                        file.write(' ');
                    } else {
                        file.write(target.charAt(j));
                    }
                }
                file.write("| ");

                String explain = dict.getWord(i).getWord_explain();
                file.write(explain + "\n");
            }
            file.close();
        } catch (Exception e) {
            System.out.println("No path found!");
        }
    }
}
