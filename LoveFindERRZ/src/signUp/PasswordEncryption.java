package signUp;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordEncryption {
    public static String generatingTheHash(String string) {
        if (string.length() == 0) return "";
        String targetString = string;
        byte[] stringToByte = targetString.getBytes();
        byte[] digestedString = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(stringToByte);
            digestedString = md.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        String hashedString = hexToString(digestedString);
        return hashedString;
    }


    public static String hexToString(byte[] bytes) {
        StringBuffer buff = new StringBuffer();
        for (int i=0; i<bytes.length; i++) {
            int val = bytes[i];
            val = val & 0xff;  // remove higher bits, sign
            if (val<16) buff.append('0'); // leading 0
            buff.append(Integer.toString(val, 16));
        }
        return buff.toString();
    }
}
