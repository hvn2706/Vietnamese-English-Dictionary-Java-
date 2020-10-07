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
            File input = new File("../data/AnhViet.dict");
            Scanner sc = new Scanner(input, "UTF-8");

            String target = "";
            String explain = "";
            String tmp = "";

            while (sc.hasNextLine()) {
                tmp = sc.nextLine();
                if (tmp.isEmpty()) {
                    continue;
                } else if (tmp.charAt(0) == '@') {
                    if (!target.equals("") && !explain.equals("")) {
                        dict.addWord(new Word(target, explain));
                    }

                    for (int i = 0; i < tmp.length(); ++i) {
                        if (tmp.charAt(i) == '/') {
                            target = tmp.substring(1, i - 1);
                            explain = tmp.substring(i) + "\n"; // substring from i to end.
                            break;
                        }
                    }
                } else {
                    explain += tmp + "\n";
                }
            }
            dict.addWord(new Word(target, explain));
            sc.close();
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

    public void deleteFromFile(String remove) {
        try {

        } catch (Exception ev) {
            System.out.println("No path found!");
        }
    }
}
