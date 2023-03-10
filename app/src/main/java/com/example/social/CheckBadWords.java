package com.example.social;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;

public class CheckBadWords {

    String sentence;
    ArrayList<String> badWords ;

    public CheckBadWords(String sentence, ArrayList<String> badWords) {
        this.sentence = sentence;
        this.badWords = badWords;
    }

    public static boolean checkBadWords(String sentence, ArrayList<String> badWords) {
        HashSet<String> set = new HashSet<String>(badWords);
        String[] words = sentence.split("\\s+");
        for (String word : words) {
            if (set.contains(word)) {
                return true;
            }
        }
        return false;
    }

    public static ArrayList<String> loadBadWordsFromTextfile(InputStream inputStream) {
        ArrayList<String> badWords = new ArrayList<String>();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                badWords.add(line.trim());
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return badWords;
    }
}