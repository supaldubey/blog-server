package in.cubestack.apps.blog.util;

import org.jose4j.base64url.internal.apache.commons.codec.binary.Base64;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;


public class EncryptionHelper {

    private static final int ITERATIONS = 256;
    private static final int KEY_LENGTH = 256;

    public static String getHash(final char[] password, final byte[] salt) {
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
            SecretKey key = skf.generateSecret(spec);
            return Base64.encodeBase64String(key.getEncoded());
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }
}
