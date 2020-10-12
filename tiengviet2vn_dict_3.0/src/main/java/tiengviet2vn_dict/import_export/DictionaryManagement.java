package tiengviet2vn_dict.import_export;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

import tiengviet2vn_dict.words_handler.Dictionary;
import tiengviet2vn_dict.words_handler.Word;

public class DictionaryManagement {
    private final Dictionary dict = new Dictionary();
    private final ArrayList<String> inputContent = new ArrayList<>(); // for deleting new words purpose

    public Dictionary getDict() {
        return dict;
    }

    public void insertFromFile() {
        try {
            File sr = new File("./data/AnhViet.dict");
            Scanner scr = new Scanner(sr, "UTF-8");
            String target = "";
            String explain = "";

            File ig = new File("./data/remove.txt");
            Scanner igsc = new Scanner(ig, "UTF-8");
            ArrayList<Integer> ignoreContent = new ArrayList<>();

            while (igsc.hasNext()) {
                ignoreContent.add(igsc.nextInt());
            }

            int index = 0;
            int start = 1;
            String s;
            boolean check;
            while (scr.hasNextLine()) {
                s = scr.nextLine();
                inputContent.add(s);
                index++;
                if (s.isEmpty()) {
                    continue;
                } else if (s.charAt(0) == '@') {
                    if (!target.equals("") && !explain.equals("")) {
                        check = true;
                        for (int tmp : ignoreContent) {
                            if (start == tmp) {
                                check = false;
                                break;
                            }
                        }
                        if (check) {
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
                    start = index;
                } else {
                    explain += s + "\n";
                }
            }
            check = true;
            for (int tmp : ignoreContent) {
                if (start == tmp) {
                    check = false;
                    break;
                }
            }
            if (check) {
                dict.addWord(new Word(target, explain));
            }
            scr.close();
            igsc.close();
        } catch (IOException e) {
            System.out.println("File not found");
        }
    }

    public void addToFile(String target, String explain) {
        inputContent.add("");
        String fullWord = "@" + target + " " + explain;
        String adding = "";
        for (int i = 0; i < fullWord.length(); ++i) {
            if (fullWord.charAt(i) == '\n') {
                inputContent.add(adding);
                adding = "";
            } else {
                adding += fullWord.charAt(i);
            }
        }

        dict.sortDictionary();

        try {
            BufferedWriter file = new BufferedWriter(
                new OutputStreamWriter(
                new FileOutputStream("./data/AnhViet.dict", true), StandardCharsets.UTF_8));

            file.append("\n@").append(target).append(" ");
            file.append(explain);

            file.flush();
            file.close();
        } catch (Exception ev) {
            System.out.println("No path found!");
        }
    }

    public void deleteFromFile(String target, String explain) {
        try {
            BufferedWriter ignore = new BufferedWriter(
                new OutputStreamWriter(
                new FileOutputStream("./data/remove.txt", true), StandardCharsets.UTF_8));

            String tmp_target = "";
            String tmp_explain = "";

            int index = 0;
            int start = 1;
            for (String s : inputContent) {
                index++;
                if (s.equals("")) {
                    continue;
                } else if (s.charAt(0) == '@') {
                    if (tmp_target.equals(target) && tmp_explain.equals(explain)) {
                        ignore.append(Integer.toString(start)).append("\n");
                    }
                    start = index;
                    for (int j = 0; j < s.length(); ++j) {
                        if (s.charAt(j) == '/') {
                            tmp_target = s.substring(1, j - 1);
                            tmp_explain = s.substring(j) + "\n"; // substring from i to end.
                            break;
                        }
                    }
                } else {
                    tmp_explain += s + "\n";
                }
            }
            if (tmp_target.equals(target) && tmp_explain.equals(explain)) {
                ignore.append(Integer.toString(start)).append("\n");
            }
            if (tmp_target.equals(target)) {
                System.out.print("this" + tmp_explain);
                System.out.print(explain + "hello");
                /*System.out.println(inputContent.get(inputContent.size() - 4));
                System.out.println(inputContent.get(inputContent.size() - 3));
                System.out.println(inputContent.get(inputContent.size() - 2));
                System.out.println(inputContent.get(inputContent.size() - 1));*/
            }

            dict.removeWord(target, explain);
            ignore.flush();
            ignore.close();
        } catch (Exception ev) {
            System.out.println("No path found in management!");
        }
    }

    public void editFromFile(String target, String explain, String editExplain) {
        deleteFromFile(target, explain);
        addToFile(target, editExplain);
    }
}
