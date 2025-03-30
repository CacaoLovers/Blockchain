package ru.itis.service;

import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.stereotype.Service;
import ru.itis.dto.SignatureDto;

import javax.crypto.Cipher;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Slf4j
@Service
public class SignatureService {
    public KeyPair createKeyPair(SignatureDto signatureDto) {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(signatureDto.getAlgorithm());
            keyPairGenerator.initialize(2048);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            PrivateKey privateKey = keyPair.getPrivate();
            PublicKey publicKey = keyPair.getPublic();

            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKey.getEncoded());
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKey.getEncoded());

            Files.write(Paths.get(signatureDto.getPrivateKey()), pkcs8EncodedKeySpec.getEncoded());
            Files.write(Paths.get(signatureDto.getPublicKey()), x509EncodedKeySpec.getEncoded());

            return new KeyPair(publicKey, privateKey);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    public KeyPair loadKeyPair() {
        try {
            byte[] publicKeyHex = Files.readAllBytes(Paths.get("publiс.key"));
            byte[] privateKeyHex = Files.readAllBytes(Paths.get("private.key"));
            PublicKey publicKey = convertArrayToPublicKey(Hex.decode(publicKeyHex), "RSA");
            PrivateKey privateKey = convertArrayToPrivateKey(Hex.decode(privateKeyHex), "RSA");

            return new KeyPair(publicKey, privateKey);
        } catch (Exception e) {
            log.error("Не удалось загрузить связку ключей");
            return null;
        }
    }

    public void decryptPair(String message) {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();

            Cipher encryptCipher = Cipher.getInstance("RSA");
            encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encryptedData = encryptCipher.doFinal(message.getBytes());
            String encryptedMessage = Base64.getEncoder().encodeToString(encryptedData);
            System.out.println("Encrypted Message: " + encryptedMessage);


            Cipher decryptCipher = Cipher.getInstance("RSA");
            decryptCipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decryptedData = decryptCipher.doFinal(Base64.getDecoder().decode(encryptedMessage));
            String decryptedMessage = new String(decryptedData);
            System.out.println("Decrypted Message: " + decryptedMessage);
        } catch (Exception e) {
            System.out.println("Не удалось дешифровать данные");
        }
    }

    public static PublicKey convertArrayToPublicKey(byte encoded[], String algorithm) throws Exception {
        X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(encoded);
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        return keyFactory.generatePublic(pubKeySpec);
    }

    public static PrivateKey convertArrayToPrivateKey(byte encoded[], String algorithm) throws Exception {
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        return keyFactory.generatePrivate(keySpec);
    }
}
