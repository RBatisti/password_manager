package util;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AESManager {
    public static String encrypt(byte[] key, String text) {
        try {
            SecretKey secretKey = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES", new BouncyCastleProvider());

            IvParameterSpec ivSpec = Random.generateIV();

            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
            byte[] encrypted = cipher.doFinal(text.getBytes());
            byte[] encryptedWithIV = new byte[ivSpec.getIV().length + encrypted.length];

            System.arraycopy(ivSpec.getIV(), 0, encryptedWithIV, 0, ivSpec.getIV().length);
            System.arraycopy(encrypted, 0, encryptedWithIV, ivSpec.getIV().length, encrypted.length);

            return Base64.getEncoder().encodeToString(encryptedWithIV);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static String decrypt(byte[] key, String encryptedText, Cipher cipher) {
        try {
            byte[] encriptedWithIV = Base64.getDecoder().decode(encryptedText);
            byte[] iv = new byte[16];

            System.arraycopy(encriptedWithIV, 0, iv, 0, iv.length);
            IvParameterSpec ivSpec = new IvParameterSpec(iv);

            byte[] encriptedBytes = new byte[encriptedWithIV.length - iv.length];
            System.arraycopy(encriptedWithIV, iv.length, encriptedBytes, 0, encriptedBytes.length);

            SecretKey secretKey = new SecretKeySpec(key, "AES");

            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
            byte[] decrypted = cipher.doFinal(encriptedBytes);

            return new String(decrypted);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
