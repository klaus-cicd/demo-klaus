package com.klaus.demo.helloworld.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description RAS加密工具类
 * @Author songwp
 * @Date 2022/8/8 20:34
 * @Version 1.0.0
 **/
@Slf4j
public class RSAUtil {


    private static final String KEY_ALGORITHM = "RSA";
    //密钥长度

    private static final int KEY_SIZE = 4096;

    //最大加密长度

    private static final int MAX_ENCRYPT_BLOCK = KEY_SIZE / 8 - 11;
    //最大解密长度
    private static final int MAX_DECRYPT_BLOCK = KEY_SIZE / 8;


    private static String getRsaKey() {
        return "slfjonfopunqf;lknopf23fknsdopnfwpeihfpo2fn[o2ipjopifjpojnsd[lfjp923fnnkenf;sldjfposjn";
    }


    private static byte[] decryptBASE64(String src) {
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            return decoder.decodeBuffer(src);
        } catch (Exception ex) {
            return null;
        }
    }


    /**
     * 生成公私钥
     *
     * @param keySize 密钥长度
     */
    public static Map<String, String> genKeyPair(int keySize) {
        Map<String, String> keyMap = new HashMap<>(2);
        try {
            //创建密钥对生成器
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(keySize);
            //生成密钥对
            KeyPair keyPair = kpg.generateKeyPair();
            //公钥
            PublicKey publicKey = keyPair.getPublic();
            //私钥
            PrivateKey privateKey = keyPair.getPrivate();
            keyMap.put("publicKey", Base64.encodeBase64String(publicKey.getEncoded()));
            keyMap.put("privateKey", Base64.encodeBase64String(privateKey.getEncoded()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            log.error("生成公私钥信息失败：" + e.getMessage());
        }
        return keyMap;
    }


    /**
     * 该base64方法会自动换行
     *
     * @param src
     * @return
     */
    private static String encryptBASE64(byte[] src) {
        BASE64Encoder encoder = new BASE64Encoder();

        return encoder.encode(src);
    }

    /**
     * 公钥分段加密
     *
     * @param data      源数据
     * @param publicKey 公钥(BASE64编码)
     * @param length    段长
     * @return
     * @throws Exception
     */
    private static byte[] encryptByPublicKey(byte[] data, String publicKey, int length) {
        ByteArrayOutputStream out = null;
        byte[] encryptData = null;
        try {
            byte[] keyBytes = decryptBASE64(publicKey);
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            Key publicK = keyFactory.generatePublic(x509KeySpec);
            // 对数据加密
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, publicK);
            int inputLen = data.length;
            out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段加密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > length) {
                    cache = cipher.doFinal(data, offSet, length);
                } else {
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * length;
            }
            encryptData = out.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("公钥分段加密失败：" + e.getMessage());
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return encryptData;
    }

/** */
    /**
     * <P>
     * 私钥解密
     * </p>
     *
     * @param data       已加密数据
     * @param privateKey 私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    private static byte[] decryptByPrivateKey(byte[] data, String privateKey, int length) {
        ByteArrayOutputStream out = null;
        byte[] decryptedData = null;
        try {
            byte[] keyBytes = decryptBASE64(privateKey);
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, privateK);
            int inputLen = data.length;
            out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段解密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > length) {
                    cache = cipher.doFinal(data, offSet, length);
                } else {
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * length;
            }
            decryptedData = out.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("私钥解密失败：" + e.getMessage());
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return decryptedData;
    }

    /**
     * 加密
     *
     * @param data
     * @param publicKey
     * @return
     */
    public static String encryptByPublicKey(String data, String publicKey) {
        //log.info("公钥分段加密内容："+new String(encryptByPublicKey(data.getBytes(), publicKey, MAX_ENCRYPT_BLOCK)).replaceAll("[+]", "@"));
        return encryptBASE64(encryptByPublicKey(data.getBytes(), publicKey, MAX_ENCRYPT_BLOCK)).replaceAll("[+]", "@");
    }

    /**
     * 解密
     *
     * @param data
     * @return
     */
    public static String decryptByPrivateKey(String data) {
        //log.info("私钥解密内容："+new String(decryptByPrivateKey(decryptBASE64(data.replaceAll("@", "+")), getRsaKey(), MAX_DECRYPT_BLOCK)));
        return new String(decryptByPrivateKey(decryptBASE64(data.replaceAll("@", "+")), getRsaKey(), MAX_DECRYPT_BLOCK));
    }


    public static void main(String[] args) {
        Map<String, String> keyMap = genKeyPair(KEY_SIZE);
        log.info("公钥:{}", keyMap.get("publicKey"));
        log.info("私钥:{}", keyMap.get("privateKey"));
        String encryptString = encryptByPublicKey("{\\\"allBillList\\\":[{\\\"id\\\":\\\"264786\\\",\\\"billNo\\\":\\\"WB2021051700064\\\",\\\"billTypeId\\\":13,\\\"billTypeName\\\":\\\"定损维修\\\",\\\"vehicleNo\\\":\\\"京Q683976\\\",\\\"vehicleId\\\":\\\"532928\\\",\\\"vehicleOwner\\\":\\\"北京测试公司\\\",\\\"vehicleModelName\\\":\\\"宝马\\\",\\\"orderCar\\\":0,\\\"orderCarName\\\":\\\"短租\\\",\\\"cityId\\\":null,\\\"cityName\\\":\\\"北京\\\",\\\"deptId\\\":null,\\\"deptName\\\":\\\"北京测试\\\",\\\"nowCityId\\\":null,\\\"nowCityName\\\":null,\\\"nowDeptId\\\":null,\\\"nowDeptName\\\":null,\\\"belongCityId\\\":null,\\\"belongCityName\\\":\\\"北京\\\",\\\"belongDeptId\\\":null,\\\"belongDeptName\\\":\\\"知春路店\\\",\\\"costBelongAreaName\\\":\\\"知春路大片区\\\",\\\"costBelongCityName\\\":\\\"北京\\\",\\\"costBelongDeptName\\\":\\\"知春路店\\\",\\\"createTime\\\":\\\"2021-05-17 10:11\\\",\\\"modifyTime\\\":null,\\\"billStatusId\\\":109,\\\"billStatusName\\\":\\\"方案已通过\\\",\\\"createEmpName\\\":\\\"JD\\\",\\\"createEmpDeptName\\\":\\\"北京测试\\\",\\\"pickupType\\\":2,\\\"pickupTypeName\\\":\\\"上门\\\",\\\"isDelete\\\":0,\\\"isDeleteeName\\\":\\\"否\\\",\\\"garageId\\\":null}],\\\"totalCount\\\":1}\";\n", "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAnvGvwKuIfZdWilPI+nT/Pas1N6b3Jns7D9oK698h1j8yzmpD9KnM5hHCgl4toEVg4R4KEsJTTmUe9HU1dBTQby9TnBH5w5UeQgg/Mx5sJ3w5DyAPu2njOhU/5aoSInowpa5JLoba0YIzQWn++a/jhJs50T+Wt+xy3WEbF3QYN/Y7m0ZN1C70KXlX2xOLI8BL80BMqLF3Q1dIkvxLmxalJZiEnvkALWly8pERrvvz1YX6rKWOo1wtS5AJW1xrctnOf4saQi9/Jv0PRnkuFRo9BB1RZA/vV5kPt++szrCi7qDRCokAoQfn8enB1UJNED/tFWwqxpzZ+ic+C+FfLrxZ7nXocWMQWSojrIhY7+2TYT3fwO4ZvJoKlg71R6H+gSr2p0qBye+ELIeEr83UWMhvKrcAP0FpJcWpWpJaMKGo5yjUe4u7FBKF5j+O0GJCTfkxA8aw+KTZ/PnyoEI65PGD7i4XQWrvTPkyMcfMy9/dXd8efqzh5kg3kAauHvTBLcSpjRqCFT3pz6bK0azegrGPlIEPa3W5UzZ0MkhtM+6nbrOKgzuj27glwHRBNbhs8bllzVGU4+Zxv98lUp8VvPsVMweOHB4Zz/g+dLamHcMA0xLPPHZJRwvBbsm6rWd96Wcm0Y3ZyyIGMsj0AWPBjMvbHcBCJDFTxIMZ9GOAIMuU+lcCAwEAAQ==");
        System.out.println("密文======" + encryptString);
        String decryptString = decryptByPrivateKey(encryptString);
        System.out.println("明文======" + decryptString);
    }
}