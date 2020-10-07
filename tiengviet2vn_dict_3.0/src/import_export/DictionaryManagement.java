package import_export;

import java.io.*;
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
            File ignore = new File("../data/remove.txt");
            Scanner sc = new Scanner(input, "UTF-8");
            Scanner igsc = new Scanner(ignore, "UTF-8");

            String target = "";
            String explain = "";
            String tmp;

            String ignoreContent = "";
            while (igsc.hasNextLine()) {
                ignoreContent += (igsc.nextLine() + "\n");
            }

            while (sc.hasNextLine()) {
                tmp = sc.nextLine();
                if (tmp.isEmpty()) {
                    continue;
                } else if (tmp.charAt(0) == '@') {
                    if (!target.equals("") && !explain.equals("")) {
                        if (!ignoreContent.contains(target) && !ignoreContent.contains(explain)) {
                            dict.addWord(new Word(target, explain));
                        }
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

    public void deleteFromFile(String target, String explain) {
        try {
            Writer ignore = new BufferedWriter(new FileWriter("../data/remove.txt", true));
            ignore.append("\n\n@").append(target).append(" ").append(explain);
            dict.removeWord(target, explain);
            ignore.close();
        } catch (Exception ev) {
            System.out.println("No path found!");
        }
    }
}
