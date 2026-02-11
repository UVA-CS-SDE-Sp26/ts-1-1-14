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
        return "";
    }
    public String getFilesForDisplay() {
       return "";
    }
}
