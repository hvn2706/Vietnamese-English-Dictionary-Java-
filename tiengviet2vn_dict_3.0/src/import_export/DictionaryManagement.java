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
                Word word = new Word(target.toLowerCase(), explain.toLowerCase());
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

    public void deleteFromFile(String remove) {
        try {
            int index = 0;
            File en = new File("../data/en.txt");
            File vi = new File("../data/vi.txt");
            Scanner sc1 = new Scanner(en);
            Scanner sc2 = new Scanner(vi);
            String editen = "";
            String editvi = "";

            while (sc1.hasNextLine() && sc2.hasNextLine()) {
                String tmpen = sc1.nextLine();
                String tmpvi = sc2.nextLine();

                if (remove.equals(tmpen)) {
                    continue;
                }
                editen += tmpen;
                editen += "\n";
                editvi += tmpvi;
                editvi += "\n";
            }

            FileWriter outfile1 = new FileWriter("../data/en.txt");
            outfile1.write(editen);
            outfile1.close();
            sc1.close();

            FileWriter outfile2 = new FileWriter("../data/vi.txt");
            outfile2.write(editvi);
            outfile2.close();
            sc2.close();

            for (int i = 0; i < dict.getLength(); ++i) {
                if (dict.getWord(i).getWord_target().equals(remove)) {
                    index = i;
                    break;
                }
            }
            dict.removeWord(index);
        } catch (Exception ev) {
            System.out.println("No path found!");
        }
    }
}
