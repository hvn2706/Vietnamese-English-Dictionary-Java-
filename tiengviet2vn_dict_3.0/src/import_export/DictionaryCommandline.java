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
        ArrayList<String> l = new ArrayList<String>();
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
     * @param dict he current Dictionary
     */
    public String dictionarySearchExact(String s, Dictionary dict) {
        if (s == null) {
            return "";
        }
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
        ArrayList<String> l = new ArrayList<String>();
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
            String res = resScanner.nextLine();
            res = res.substring(3, res.length()-12);
            /*response has the form of [[["something","something",null,null,1]
              3 is to remove the first three [
              12 is to remove the last 13 characters ,null,null,1]*/

            for(int i=0; i<res.length(); i++) {
                if(res.charAt(i) == ',') {
                    res = res.substring(0, i) + res.substring(i+1);
                }
            }
            int quoteCount = 0;
            for(int i=0; i<res.length(); i++) {
                if(res.charAt(i) == '"') {
                    quoteCount++;
                }
                if(quoteCount == 2) {
                    res = res.substring(1, i);
                }
            }
            // System.out.println(res);
            return res;
        } catch (MalformedURLException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }
        return "";
    }
}
