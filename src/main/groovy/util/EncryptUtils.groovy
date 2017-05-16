package util

import java.security.MessageDigest

class EncryptUtils {

    static byte[] encryptSHA256(String msg) {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(msg.getBytes("UTF-8"));
        return hash
    }
}
