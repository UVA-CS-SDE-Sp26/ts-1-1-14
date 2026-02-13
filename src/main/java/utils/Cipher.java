package utils;

import java.util.Arrays;

public class Cipher {

    private String normalKey;
    private String cypherKey;

    // Constructor
    public Cipher(String normalKey, String cypherKey) {
        validateKeys(normalKey, cypherKey);
        this.normalKey = normalKey;
        this.cypherKey = cypherKey;
    }

    // Public method to decrypt full text
    public String decryptText(String encryptedText) {

        StringBuilder decrypted = new StringBuilder();

        for (int i = 0; i < encryptedText.length(); i++) {
            decrypted.append(
                    decryptCharacter(encryptedText.charAt(i))
            );
        }

        return decrypted.toString();
    }

    // Private helper
    private char decryptCharacter(char encryptedCharacter) {

        int indexLocation = cypherKey.indexOf(encryptedCharacter);

        if (indexLocation == -1) {
            return encryptedCharacter;
        }

        return normalKey.charAt(indexLocation);
    }

    // Validation
    private void validateKeys(String normalKey, String cypherKey) {

        if (normalKey.length() != cypherKey.length()) {
            throw new IllegalArgumentException("Keys must be same length.");
        }

        char[] normalChars = normalKey.toCharArray();
        char[] cypherChars = cypherKey.toCharArray();

        Arrays.sort(normalChars);
        Arrays.sort(cypherChars);

        if (!Arrays.equals(normalChars, cypherChars)) {
            throw new IllegalArgumentException("Keys must contain identical characters.");
        }
    }
}
