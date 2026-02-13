package utils;

import java.nio.file.Path;

import utils.contracts.FileRepository;

import utils.contracts.InterfaceBridgeService;

import java.util.List;

public class InterfaceBridge implements InterfaceBridgeService {

    private final FileRepository fileRepo;
    private final FileRepository cipherRepo;

    public InterfaceBridge() {
        // default constructor uses real FileHandler
        this.fileRepo = new FileHandler(Path.of("data"));
        this.cipherRepo = new FileHandler(Path.of("ciphers"));
    }

    // for testing — lets us inject a mock FileRepository
    InterfaceBridge(FileRepository fr, FileRepository cr) {
        this.fileRepo = fr;
        this.cipherRepo = cr;
    }

    public String requestFileContents(String selection, String cipher) {
        if (selection == null || selection.isEmpty()) {
            return "Invalid file selection.";
        }
        if (cipher == null || cipher.isEmpty()) {
            // use default cipher if not provided
            cipher = "key.txt";
        }

        try {
        // convert selection number (e.g. "01") to actual filename
        int index = Integer.parseInt(selection) - 1;
        List<String> files = fileRepo.listFiles();
        if (index < 0 || index >= files.size()) {
            return "File not found.";
        }
        String filename = files.get(index);

        String fileContents = fileRepo.readFile(filename);

        // get normal key and cipher key for cipher
        String keyContents;
        try {
            keyContents = cipherRepo.readFile(cipher);
        } catch (Exception e) {
            return "Invalid cipher key.";
        }

String[] keys = keyContents.split("\\R"); // any newline style
for (int i = 0; i < keys.length; i++) keys[i] = keys[i].trim();

if (keys.length != 2) { 
        return "Invalid cipher key.";
        }

        Cipher cipherObj = new Cipher(keys[0], keys[1]);

        return cipherObj.decryptText(fileContents);

    } catch (NumberFormatException e) {
        return "Invalid file selection.";
    } catch (IllegalArgumentException e) {
        return "Invalid cipher key.";
    } catch (Exception e) {
        return "Error processing file.";
    }
    }

    public String getFilesForDisplay() {
        StringBuilder sb = new StringBuilder();
        int count = 1;

        List<String> files;

        try {
            files = fileRepo.listFiles();
        } catch (Exception e) {
            return "Error retrieving file list.";
        }

        // creates 0 padded numbered list of files numbered from 1, e.g. "01 file.txt"
        for (String file : files) {
            sb.append(String.format("%02d %s\n", count, file));
            count++;
        }

        if (sb.isEmpty()) {
            sb.append("No files found.\n");
        }

        return sb.toString();
    }
}
