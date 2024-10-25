package mx.unam.dgtic.utils;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;

/**
 * @author FRANCISCO MIZTLI LOPEZ SALINAS
 * @user franciscolopez
 * @date 25/09/24
 * @project M8AP_Francisco_Lopez
 * Descripción: [...]
 */

public class passwordUtils {

    private static final Random RANDOM = new SecureRandom();
    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;

    public static byte[] generateSalt() {
        byte[] salt = new byte[16];
        RANDOM.nextBytes(salt);
        return salt;
    }

    public static byte[] hash(char[] password, byte[] salt) {
        PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
        Arrays.fill(password, Character.MIN_VALUE);
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new AssertionError("Error mientras hasheando el password: " + e.getMessage(), e);
        } finally {
            spec.clearPassword();
        }
    }

    public static String generateSecurePassword(String password) {
        byte[] salt = generateSalt();
        byte[] hash = hash(password.toCharArray(), salt);
        byte[] combined = new byte[salt.length + hash.length];
        System.arraycopy(salt, 0, combined, 0, salt.length);
        System.arraycopy(hash, 0, combined, salt.length, hash.length);
        return Base64.getEncoder().encodeToString(combined);
    }

    public static boolean verifyPassword(String password, String storedHash) {
        byte[] combined = Base64.getDecoder().decode(storedHash);
        byte[] salt = Arrays.copyOfRange(combined, 0, 16);
        byte[] hash = Arrays.copyOfRange(combined, 16, combined.length);
        byte[] testHash = hash(password.toCharArray(), salt);
        return Arrays.equals(hash, testHash);
    }

}