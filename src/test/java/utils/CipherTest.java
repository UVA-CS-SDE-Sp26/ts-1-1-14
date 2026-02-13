package utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CipherTest {

    private static final String NORMAL =
            "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

    private static final String SHIFT1 =
            "bcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890a";

    private static final String REVERSE =
            new StringBuilder(NORMAL).reverse().toString();

    // 1
    @Test
    public void decrypt_shift_basic() {
        Cipher c = new Cipher(NORMAL, SHIFT1);
        assertEquals("abc", c.decryptText("bcd"));
    }

    // 2 (updated: spaces/punctuation are NOT in the key -> should throw)
    @Test
    public void decrypt_shift_withSpacesAndPunctuation_throws() {
        Cipher c = new Cipher(NORMAL, SHIFT1);
        assertThrows(IllegalArgumentException.class,
                () -> c.decryptText("b b!"));
    }

    // 3
    @Test
    public void decrypt_shift_fullKeyProperty() {
        Cipher c = new Cipher(NORMAL, SHIFT1);
        assertEquals(NORMAL, c.decryptText(SHIFT1));
    }

    // 4
    @Test
    public void decrypt_reverse_fullKeyProperty() {
        Cipher c = new Cipher(NORMAL, REVERSE);
        assertEquals(NORMAL, c.decryptText(REVERSE));
    }

    // 5
    @Test
    public void decrypt_reverse_singleChar() {
        Cipher c = new Cipher(NORMAL, REVERSE);
        assertEquals("a", c.decryptText(REVERSE.substring(0, 1)));
    }

    // 6
    @Test
    public void decrypt_emptyString() {
        Cipher c = new Cipher(NORMAL, SHIFT1);
        assertEquals("", c.decryptText(""));
    }

    // 7 (updated: unknown characters should throw, not be preserved)
    @Test
    public void decrypt_unknownCharacter_throws() {
        Cipher c = new Cipher(NORMAL, SHIFT1);
        assertThrows(IllegalArgumentException.class,
                () -> c.decryptText("b🙂"));
    }

    // 8
    @Test
    public void constructor_badLength() {
        assertThrows(IllegalArgumentException.class,
                () -> new Cipher("abc", "ab"));
    }

    // 9
    @Test
    public void constructor_badCharacterSet() {
        assertThrows(IllegalArgumentException.class,
                () -> new Cipher("abc", "abd"));
    }

    // 10
    @Test
    public void decrypt_mixedCharacters() {
        Cipher c = new Cipher(NORMAL, SHIFT1);
        assertEquals("Hello1", c.decryptText("Ifmmp2"));
    }

    // 11 (optional but recommended: your Cipher throws on null input)
    @Test
    public void decrypt_nullText_throws() {
        Cipher c = new Cipher(NORMAL, SHIFT1);
        assertThrows(IllegalArgumentException.class,
                () -> c.decryptText(null));
    }
}
