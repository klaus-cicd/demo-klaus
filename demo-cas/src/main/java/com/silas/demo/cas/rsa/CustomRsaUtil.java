package com.silas.demo.cas.rsa;

import cn.hutool.core.io.FileUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.*;

/**
 * @author Klaus
 */
@Slf4j
public class CustomRsaUtil {

    private static String publicKeyPath = "/Users/silas/.ssh/id_rsa.pub";
    private static String privateKeyPath = "/Users/silas/.ssh/id_rsa";

    // public static String encrypt() {
    //     byte[] data = "Hello, World!".getBytes();
    //
    //     String privateKey = FileUtil.readString(privateKeyPath, StandardCharsets.UTF_8);
    //     String publicKey = FileUtil.readString(publicKeyPath, StandardCharsets.UTF_8);
    //
    //     // RSAKey rsaKey = RSAUtil.generateKeyPair(privateKey, publicKey);
    //     // RSA rsa = new RSA(rsaKey.getPrivateKey(), rsaKey.getPublicKey());
    //
    //     return new String(rsa.encrypt(data, KeyType.PublicKey));
    // }

    public static String decrypt(String encryptedData) {
        String privateKey = FileUtil.readString(privateKeyPath, StandardCharsets.UTF_8);
        RSA rsa = new RSA(null, privateKey);
        byte[] decryptedData = rsa.decrypt(encryptedData, KeyType.PrivateKey);

        return new String(decryptedData);
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        // 生成密钥对
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        SecureRandom secureRandom = new SecureRandom();
        keyPairGenerator.initialize(2048, secureRandom);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        // 获取公钥和私钥
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        // 要加密的数据
        String plainText = "Hello, World!";

        // 使用公钥加密数据
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());

        // 使用私钥解密数据
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

        // 输出解密后的数据
        String decryptedText = new String(decryptedBytes);
        System.out.println("Decrypted Text: " + decryptedText);
    }
}
