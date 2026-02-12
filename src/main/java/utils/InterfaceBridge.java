package utils;

import utils.contracts.CipherService;
import utils.contracts.FileRepository;
import utils.contracts.InterfaceBridgeService;

public class InterfaceBridge implements InterfaceBridgeService {

    private final FileRepository fileRepo;
    private final CipherService cipher;

    public InterfaceBridge(FileRepository fr, CipherService c) {
        this.fileRepo = fr;
        this.cipher = c;
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
        String fileContents = fileRepo.readFileFromData(selection);
        if (fileContents == null) {
            return "File not found.";
        }

        return this.cipher.decryptText(fileContents, cipher);
    } catch (Exception e) {
        return "Error processing file.";
    }
    }

    public String getFilesForDisplay() {
        StringBuilder sb = new StringBuilder();
        int count = 1;


        // creates 0 padded numbered list of files numbered from 1, e.g. "01 file.txt"
        for (String file : fileRepo.listFilesInData()) {
            sb.append(String.format("%02d %s\n", count, file));
            count++;
        }

        if (sb.isEmpty()) {
            sb.append("No files found.\n");
        }

        return sb.toString();
    }
}
