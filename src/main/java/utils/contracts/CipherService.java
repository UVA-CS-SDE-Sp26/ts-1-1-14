package utils.contracts;

public interface CipherService {
    char decryptCharacter(char encryptedCharacter, String normalKey, String cipherKey);

    String decryptText(String encryptedText, String normalKey, String cipherKey);

    String decryptText(String encryptedText, String keyFileText);

}
