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

    // 2
    @Test
    public void decrypt_shift_withSpacesAndPunctuation() {
        Cipher c = new Cipher(NORMAL, SHIFT1);
        assertEquals("a a!", c.decryptText("b b!"));
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

    // 7
    @Test
    public void decrypt_unknownCharacterPreserved() {
        Cipher c = new Cipher(NORMAL, SHIFT1);
        assertEquals("a🙂", c.decryptText("b🙂"));
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
}
