package import_export;

import java.net.*;
import java.io.*;
import java.util.*;

import words_handler.Dictionary;
import words_handler.Word;

public class DictionaryCommandline {
    /**
     * Shows all words in the current Dictionary in the terminal.
     * @param dict the current Dictionary
     */
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

    /**
     * Search for words contains the provided String in the current Dictionary.
     * @param s the String to be searched
     * @param dict the current Dictionary
     */
    public String[] dictionarySearcher(String s, Dictionary dict) {
        ArrayList<String> l = new ArrayList<>();
        for (int i = 0; i < dict.getLength(); ++i) {
            Word tmp = dict.getWord(i);
            if (tmp.getWord_target().toLowerCase().contains(s.toLowerCase())) {
                l.add(tmp.getWord_target());
            }
        }
        String[] rt = new String[l.size()];
        rt = l.toArray(rt);
        return rt;
    }

    /**
     * Search for the provided String in the current Dictionary.
     * @param s    the String to be searched
     * @param dict the current Dictionary
     */
    public String dictionarySearchExact(String s, Dictionary dict) {
        for (int i = 0; i < dict.getLength(); ++i) {
            Word tmp = dict.getWord(i);
            if (tmp.getWord_target().toLowerCase().equals(s.toLowerCase())) {
                return tmp.getWord_explain();
            }
        }
        return "";
    }

    /**
     * Search for words that have the same prefix with the provided String in the current Dictionary.
     * @param s    the String to be searched
     * @param dict the current Dictionary
     */
    public String[] dictionarySearchSamePrefix(String s, Dictionary dict) {
        ArrayList<String> l = new ArrayList<>();
        for (int i = 0; i < dict.getLength(); ++i) {
            Word tmp = dict.getWord(i);
            if (tmp.getWord_target().toLowerCase().startsWith(s.toLowerCase())) {
                l.add(tmp.getWord_target());
            }
        }
        String[] rt = new String[l.size()];
        rt = l.toArray(rt);
        return rt;
    }

    /**
     * Create a new DictionaryManagement object from input files and export it to another file.
     * @return DictionaryManagement
     */
    public DictionaryManagement dictionaryAdvance() {
        DictionaryManagement mn = new DictionaryManagement();
        mn.insertFromFile();
        mn.dictionaryExportToFile();
        // mn.dictionaryLookup();
        return mn;
    }

    /**
     * Translate the provided sentence into Vietnamese.
     * @param sentence The sentence to be translated
     * @return Translated sentence
     */
    public static String sentenceTranslator(String sentence) {
        if(sentence.isEmpty()) return "";
        try {
            String link = "https://translate.googleapis.com/translate_a/single?client=gtx";
            String srcLang = "auto";
            String desLang = "vi";

            link += ("&sl=" + srcLang);
            link += ("&tl=" + desLang);
            link += "&dt=t&q=";
            link += URLEncoder.encode(sentence, "UTF8");

            URL url = new URL(link);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int status = connection.getResponseCode();
            // System.out.println(status);

            Scanner resScanner = new Scanner(connection.getInputStream(), "UTF8");
            String res = ""; //Response
            String trs = ""; //Translated sentence

            while(resScanner.hasNextLine()) {
                res += resScanner.nextLine() + "\n";
            }
            //JSON Processing
            res = res.substring(1, res.length()-1);
            int count = 1; //Counts "[]"
            int count_ = 0; //Counts """"
            int left = 0; //Starting index
            for(int i=1;i<res.length();i++) {
                if(res.charAt(i) == '[') {
                    count++;
                }
                if(res.charAt(i) == ']') {
                    count--;
                }
                if(count == 0) {
                    res = res.substring(0, i+1);
                    break;
                }
            }
            res = res.substring(1, res.length()-1);
            count = 0;
            for(int i=0;i<res.length();i++) {
                if(res.charAt(i) == '[') {
                    count++;
                }
                if(res.charAt(i) == ']') {
                    count--;
                }
                if(count == 0) {
                    count_ = 0;
                } else if(count == 1) {
                    if(res.charAt(i) == '"') {
                        count_++;
                        if(count_ == 1) left = i + 1;
                        if(count_ == 2) {
                            trs += res.substring(left, i);
                        }
                    }
                }
            }
            //JSON Processing
            return trs;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
