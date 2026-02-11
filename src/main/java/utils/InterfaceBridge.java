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
        String fileContents = fileRepo.readFileFromData(selection);
        return this.cipher.decryptText(fileContents, cipher);
    }

    public String getFilesForDisplay() {
        StringBuilder sb = new StringBuilder();
        int count = 1;
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
