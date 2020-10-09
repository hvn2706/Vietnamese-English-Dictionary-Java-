package tiengviet2vn_dict.import_export;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import tiengviet2vn_dict.words_handler.Dictionary;
import tiengviet2vn_dict.words_handler.Word;

public class DictionaryManagement {
    private final Dictionary dict = new Dictionary();

    public Dictionary getDict() {
        return dict;
    }

    public void insertFromFile() {
        try {
            ArrayList<String> inputContent = new ArrayList<>(Files.readAllLines(Paths.get("./data/AnhViet.dict"), StandardCharsets.UTF_8));
            String target = "";
            String explain = "";

            ArrayList<String> ignore = new ArrayList<>(Files.readAllLines(Paths.get("./data/remove.txt"), StandardCharsets.UTF_8));
            String ignoreContent = "";

            for (int i = 0; i < ignore.size(); ++i) {
                ignoreContent += (ignore.get(i) + "\n");
            }

            for (String s : inputContent) {
                if (s.isEmpty()) {
                    continue;
                } else if (s.charAt(0) == '@') {
                    if (!target.equals("") && !explain.equals("")) {
                        if (!ignoreContent.contains(target) && !ignoreContent.contains(explain)) {
                            dict.addWord(new Word(target, explain));
                        }
                    }

                    for (int j = 0; j < s.length(); ++j) {
                        if (s.charAt(j) == '/') {
                            target = s.substring(1, j - 1);
                            explain = s.substring(j) + "\n"; // substring from i to end.
                            break;
                        }
                    }
                } else {
                    explain += s + "\n";
                }
            }
            dict.addWord(new Word(target, explain));
        } catch (IOException e) {
            System.out.println("File not found");
        }
    }

    public void deleteFromFile(String target, String explain) {
        try {
            Writer ignore = new BufferedWriter(new FileWriter("./data/remove.txt", true));
            ignore.append("\n\n@").append(target).append(" ").append(explain);
            dict.removeWord(target, explain);
            ignore.close();
        } catch (Exception ev) {
            System.out.println("No path found!");
        }
    }
}
