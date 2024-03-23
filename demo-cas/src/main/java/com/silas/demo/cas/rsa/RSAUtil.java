package com.silas.demo.cas.rsa;

import com.alibaba.fastjson2.JSONObject;
import org.springframework.util.Base64Utils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;

/**
 * @author Klaus
 */
public class RSAUtil {
    public static final String KEY_ALGORITHM = "RSA";
    public static final String PUBLIC_KEY = "RSAPublicKey";
    public static final String PRIVATE_KEY = "RSAPrivateKey";
    public static final String SIGNATURE_ALGORITHM = "SHA256withRSA";

    public static String publicKey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAKzScQr1M6hWWiwNzwraVFoMxxHrGLR3Xr144hUOGCg46IkaSrriVL5BACQJ5Z1P43k+McshEpkOKSpOXFcJkNkCAwEAAQ==";
    public static String privateKey = "MIIBVgIBADANBgkqhkiG9w0BAQEFAASCAUAwggE8AgEAAkEArNJxCvUzqFZaLA3PCtpUWgzHEesYtHdevXjiFQ4YKDjoiRpKuuJUvkEAJAnlnU/jeT4xyyESmQ4pKk5cVwmQ2QIDAQABAkEAjFo7xBJu6X93q99rDf1SE+/cnAi5/5YSMv5BXagcpkx60ImxPRGG0NIeMb0WxShbT/5J7kJzW7ufceg20BFCoQIhAOGddY8sFiU/Nyhyt+37CnzQ2a+vCimvpUL4A1BWqOnjAiEAxBjVoXme9EQVlVYl3joc60EbiwPTpBTvLNsGUfRpBxMCIQCB7do735nJTYSIaLh/9ujtRKF4yYdCxoKX9JiD9cRFHQIhAIR8mukr+H7j+QkaWR9Zd+xh4q/7d+Ql2KofmJeKX+NNAiBcXRtcvtsIeIWTTFwMFBhaO8y0CEAXJ0JJBSDLU+SRaQ==";
    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 2048;

    // 获得公钥字符串
    public static String getPublicKeyStr(Map<String, Object> keyMap) throws Exception {
        // 获得map中的公钥对象 转为key对象
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        // 编码返回字符串
        return encryptBASE64(key.getEncoded());
    }


    // 获得私钥字符串
    public static String getPrivateKeyStr(Map<String, Object> keyMap) throws Exception {
        // 获得map中的私钥对象 转为key对象
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        // 编码返回字符串
        return encryptBASE64(key.getEncoded());
    }

    // 获取公钥
    public static PublicKey getPublicKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = (new BASE64Decoder()).decodeBuffer(key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    // 获取私钥
    public static PrivateKey getPrivateKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = (new BASE64Decoder()).decodeBuffer(key);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    // 解码返回byte
    public static byte[] decryptBASE64(String key) throws Exception {
        return (new BASE64Decoder()).decodeBuffer(key);
    }


    // 编码返回字符串
    public static String encryptBASE64(byte[] key) throws Exception {
        return (new BASE64Encoder()).encodeBuffer(key);
    }

    //***************************签名和验证*******************************
    public static byte[] sign(byte[] data, String privateKeyStr) throws Exception {
        PrivateKey priK = getPrivateKey(privateKeyStr);
        Signature sig = Signature.getInstance(SIGNATURE_ALGORITHM);
        sig.initSign(priK);
        sig.update(data);
        return sig.sign();
    }

    public static boolean verify(byte[] data, byte[] sign, String publicKeyStr) throws Exception {
        PublicKey pubK = getPublicKey(publicKeyStr);
        Signature sig = Signature.getInstance(SIGNATURE_ALGORITHM);
        sig.initVerify(pubK);
        sig.update(data);
        return sig.verify(sign);
    }

    //************************加密解密**************************
    public static byte[] encrypt(byte[] plainText, String publicKeyStr) throws Exception {
        PublicKey publicKey = getPublicKey(publicKeyStr);
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        int inputLen = plainText.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        int i = 0;
        byte[] cache;
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(plainText, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(plainText, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptText = out.toByteArray();
        out.close();
        return encryptText;
    }

    public static byte[] decrypt(byte[] encryptText, String privateKeyStr) throws Exception {
        PrivateKey privateKey = getPrivateKey(privateKeyStr);
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        int inputLen = encryptText.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptText, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptText, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] plainText = out.toByteArray();
        out.close();
        return plainText;
    }


    public static void main(String[] args) {
        Map<String, Object> keyMap;
        byte[] cipherText;
        String input = "Hello World!";
        try {
            keyMap = initKey();
            // String publicKey = getPublicKeyStr(keyMap);
            System.out.println("公钥------------------");
            System.out.println(publicKey);
            // String privateKey = getPrivateKeyStr(keyMap);
            System.out.println("私钥------------------");
            System.out.println(privateKey);

            System.out.println("测试可行性-------------------");
            System.out.println("明文=======" + input);

            cipherText = encrypt(input.getBytes(), publicKey);
            // 加密后的东西
            System.out.println("密文=======" + new String(cipherText));
            // 开始解密
            byte[] plainText = decrypt(cipherText, privateKey);
            System.out.println("解密后明文===== " + new String(plainText));

            System.out.println("验证签名-----------");

            TreeMap map = new TreeMap(new MComparator());
            map.put("age", 20);
            map.put("name", "张三");
            String str = JSONObject.toJSONString(map);
            System.out.println("\n原文:" + str);

            byte[] signature = sign(str.getBytes(), privateKey);
            System.out.println(Arrays.toString(Base64Utils.encode(signature)));
            boolean status = verify(str.getBytes(), signature, publicKey);
            System.out.println("验证情况：" + status);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Map<String, Object> initKey() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator
                .getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        Map<String, Object> keyMap = new HashMap<String, Object>(2);
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }

    static class MComparator implements Comparator {
        @Override
        public int compare(Object obj1, Object obj2) {
            String ele1 = (String) obj1;
            String ele2 = (String) obj2;
            return ele2.compareTo(ele1);
        }
    }

}