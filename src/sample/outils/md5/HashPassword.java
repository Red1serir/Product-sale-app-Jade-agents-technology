package sample.outils.md5;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashPassword {

    public String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update(password.getBytes());
        byte[] bytes = messageDigest.digest();
        StringBuffer stringBuffer = new StringBuffer();
        for (byte b : bytes){
            stringBuffer.append(Integer.toHexString(b & 0xff));
        }
        return stringBuffer.toString();
    }
}
