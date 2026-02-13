package utils;

import java.util.Arrays;

public class Cipher {

    private final String normalKey;
    private final String cypherKey;

    // Constructor
    public Cipher(String normalKey, String cypherKey) {

        validateKeys(normalKey, cypherKey);

        this.normalKey = normalKey;
        this.cypherKey = cypherKey;
    }

    // Public method to decrypt full text
    public String decryptText(String encryptedText) {

        if (encryptedText == null) {
            throw new IllegalArgumentException("Encrypted text cannot be null.");
        }

        StringBuilder decrypted = new StringBuilder();

        for (int i = 0; i < encryptedText.length(); i++) {
            char current = encryptedText.charAt(i);

            int indexLocation = cypherKey.indexOf(current);

            if (indexLocation == -1) {
                throw new IllegalArgumentException(
                        "Decryption error at position " + i +
                                ": character '" + current + "' not found in cipher key."
                );
            }

            decrypted.append(normalKey.charAt(indexLocation));
        }

        return decrypted.toString();
    }

    // Validation
    private void validateKeys(String normalKey, String cypherKey) {

        if (normalKey == null || cypherKey == null) {
            throw new IllegalArgumentException("Keys cannot be null.");
        }

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
