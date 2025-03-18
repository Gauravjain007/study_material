package com.study.cryptography;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.Security;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class CryptographyImpl {

    private static final String MSG = "RandomStringMessage";
    static {
        System.out.println("Message: " + MSG);
        Provider provider = new BouncyCastleProvider();
        Security.addProvider(provider);
    }

    public static void getSecurityProviders() {
        for (Provider provider : Security.getProviders()) {
            System.out.println(provider.getName() + " " + provider.getVersionStr());
        }
    }

    /**
     * Generates a SHA-256 hash code from the message MSG.
     * 
     * @throws NoSuchAlgorithmException if the algorithm is not available
     */
    public static void generateHashCodes() throws NoSuchAlgorithmException {
        // Common algorithms: MD5 (insecure), SHA-1 (weak), SHA-256, SHA-512
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] digest = md.digest(MSG.getBytes());
        String hash = Base64.getEncoder().encodeToString(digest);
        System.out.println("Generated Hash: " + hash);
    }

    /**
     * Generates a symmetric key and uses it to encrypt message using
     * AES/GCM/NoPadding encryption.
     */
    public static void doSymmetricEncryption() {
        try {
            // Generate a symmetric key
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(256); // 256-bit key
            SecretKey secretKey = keyGenerator.generateKey();

            // Use the symmetric key for encryption using a Cipher
            // Common algorithms: AES, DES (obsolete), 3DES (deprecated)
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            byte[] iv = cipher.getParameters().getParameterSpec(GCMParameterSpec.class).getIV();
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new GCMParameterSpec(128, iv));
            byte[] encrypted = cipher.doFinal(MSG.getBytes());
            System.out.println("Encrypted: " + Arrays.toString(encrypted));

            // Decryption
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new GCMParameterSpec(128, iv));
            byte[] decrypted = cipher.doFinal(encrypted);
            System.out.println("Decrypted: " + Base64.getEncoder().encodeToString(decrypted));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Performs asymmetric encryption using RSA algorithm. Generates a RSA key
     * pair and uses the public key to encrypt the message MSG. Then it decrypts
     * the message using the private key and prints the encrypted and decrypted
     * messages.
     */
    public static void doAsymmetricEncryption() {
        try {
            // Common algorithms: RSA, DSA, ECDSA, ECDH
            // Generate RSA key pair
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            // Get public and private keys
            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();

            System.out.println("\nPublic Key: " + Base64.getEncoder().encodeToString(publicKey.getEncoded()));
            System.out.println("\nPrivate Key: " + Base64.getEncoder().encodeToString(privateKey.getEncoded()));

            // Encrypt with public key
            Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWITHSHA-256ANDMGF1PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encrypted = cipher.doFinal(MSG.getBytes());

            // Decrypt with private key
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decrypted = cipher.doFinal(encrypted);

            System.out.println("\nEncrypted: " + Base64.getEncoder().encodeToString(encrypted));
            System.out.println("\nDecrypted: " + new String(decrypted));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            generateHashCodes();
            doSymmetricEncryption();
            doAsymmetricEncryption();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
