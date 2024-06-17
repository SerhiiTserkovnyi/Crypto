package org.myApp.model;

public class CaesarCipher {
    private static final String EN_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz.,«»\"':!? ";
    private static final String UK_ALPHABET = "АБВГҐДЕЄЖЗИІЇЙКЛМНОПРСТУФХЦЧШЩЬЮЯабвгґдеєжзиіїйклмнопрстуфхцчшщьюя.,«»\"':!? ";

    private static final String[] EN_COMMON_WORDS = {"the", "and", "that", "have", "for", "not", "with", "you", "this", "but", "his", "they", "her", "she", "him"};
    private static final String[] UK_COMMON_WORDS = {"і", "в", "не", "на", "що", "ти", "з", "це", "як", "він", "але", "я", "все", "до", "це"};

    public String encrypt(String text, int key, boolean isUkrainian) {
        String alphabet = isUkrainian ? UK_ALPHABET : EN_ALPHABET;
        StringBuilder result = new StringBuilder();
        for (char character : text.toCharArray()) {
            int index = alphabet.indexOf(character);
            if (index != -1) {
                int newIndex = (index + key) % alphabet.length();
                if (newIndex < 0) {
                    newIndex += alphabet.length();
                }
                result.append(alphabet.charAt(newIndex));
            } else {
                result.append(character);
            }
        }
        return result.toString();
    }

    public String decrypt(String text, int key, boolean isUkrainian) {
        String alphabet = isUkrainian ? UK_ALPHABET : EN_ALPHABET;
        StringBuilder result = new StringBuilder();
        for (char character : text.toCharArray()) {
            int index = alphabet.indexOf(character);
            if (index != -1) {
                int newIndex = (index - key + alphabet.length()) % alphabet.length();
                result.append(alphabet.charAt(newIndex));
            } else {
                result.append(character);
            }
        }
        return result.toString();
    }

    public String bruteForceDecrypt(String text) {
        boolean isUkrainian = isUkrainianText(text);
        String alphabet = isUkrainian ? UK_ALPHABET : EN_ALPHABET;
        String[] commonWords = isUkrainian ? UK_COMMON_WORDS : EN_COMMON_WORDS;

        int maxMatches = 0;
        String bestDecryption = text;
        int bestKey = 0;

        for (int key = 0; key < alphabet.length(); key++) {
            String decryptedText = decrypt(text, key, isUkrainian);
            int matches = countMatches(decryptedText, commonWords);

            if (matches > maxMatches) {
                maxMatches = matches;
                bestDecryption = decryptedText;
                bestKey = key;
            }
        }

        System.out.println("Best key found: " + bestKey);
        return bestDecryption;
    }

    public boolean isUkrainianText(String text) {
        for (char c : text.toCharArray()) {
            if (UK_ALPHABET.indexOf(c) != -1) {
                return true;
            }
        }
        return false;
    }

    private int countMatches(String text, String[] commonWords) {
        int matches = 0;
        for (String word : commonWords) {
            if (text.toLowerCase().contains(word)) {
                matches++;
            }
        }
        return matches;
    }
}