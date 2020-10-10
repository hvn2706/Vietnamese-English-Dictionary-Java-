package import_export;

import java.util.*;
import java.net.*;
import java.io.*;

import javax.sound.sampled.*;

import words_handler.Dictionary;
import words_handler.Word;

import com.google.cloud.translate.*;
import com.google.protobuf.ByteString;
import com.google.cloud.texttospeech.v1.*;

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
        if(sentence.isEmpty()) {
            return "";
        }
        Translate translate = TranslateOptions.getDefaultInstance().getService();
        Translation translation = translate.translate(
            sentence,
            Translate.TranslateOption.sourceLanguage(
                translate.detect(sentence).getLanguage()),
            Translate.TranslateOption.targetLanguage("vi")
        );
        try {
            speak(translation.getTranslatedText(), "vi");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return translation.getTranslatedText();
    }

    public static void speak(String sentence, String langCode) throws Exception {
        try (TextToSpeechClient textToSpeechClient = TextToSpeechClient.create()) {
            SynthesisInput input = SynthesisInput.newBuilder().setText(sentence).build();
            
            VoiceSelectionParams voice = VoiceSelectionParams
            .newBuilder()
            .setLanguageCode(langCode)
            .setSsmlGender(SsmlVoiceGender.NEUTRAL)
            .build();
            
            AudioConfig audioConfig = AudioConfig
            .newBuilder()
            .setAudioEncoding(AudioEncoding.LINEAR16)
            .build();

            SynthesizeSpeechResponse response = textToSpeechClient
            .synthesizeSpeech(input, voice, audioConfig);

            InputStream stream = new ByteArrayInputStream(response.getAudioContent().toByteArray());
            AudioInputStream sound = AudioSystem.getAudioInputStream(stream);

            Clip clip = AudioSystem.getClip();
            clip.open(sound);
            clip.start();
        }
    }
}
