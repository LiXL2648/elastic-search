package com.li.redis.util;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.apache.commons.io.FileUtils;

import javax.crypto.Cipher;
import java.io.File;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RsaEncryptDecryptUtil {

    // RSA算法
    private static final String ALGORITHM = "RSA";
    // 公钥文件路径
    private static final String PUBLIC_KEY_PATH = "rsa_public_key.pem";
    // 私钥文件路径
    private static final String PRIVATE_KEY_PATH = "rsa_private_key.pem";
    // 密钥长度
    private static final int KEY_SIZE = 2048;

    /**
     * 公钥加密
     * @param bytes 待加密的文本字节数组
     */
    public static String publicKeyEncrypt(byte[] bytes) throws Exception {
        // 创建Cipher对象进行加密，使用 RSA 算法
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        // 获取公钥
        Key key = generateKey(PUBLIC_KEY_PATH, 0);
        // 对Cipher对象进行初始化，第一个参数：加密模式，第二个参数：公钥
        cipher.init(Cipher.ENCRYPT_MODE, key);
        // 使用公钥进行加密
        byte[] encrypt = cipher.doFinal(bytes);
        // 使用 Base64 进行编码
        return Base64.encode(encrypt);
    }

    /**
     * 私钥解密
     * @param bytes 待解密的文本字节数组
     */
    public static String privateKeyDecrypt(byte[] bytes) throws Exception {
        // 创建Cipher对象进行加密，使用 RSA 算法
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        // 获取私钥
        Key key = generateKey(PRIVATE_KEY_PATH, 1);
        // 对Cipher对象进行初始化，第一个参数：加密模式，第二个参数：私钥
        cipher.init(Cipher.DECRYPT_MODE, key);
        // 使用Base64进行解码
        byte[] decode = Base64.decode(new String(bytes));
        // 使用私钥进行解密
        byte[] decryptByte = cipher.doFinal(decode);
        return new String(decryptByte);
    }

    /**
     * 读取公私钥
     * @param path 公私钥文件路径
     * @param keyType 公钥/私钥
     */
    private static Key generateKey(String path, int keyType) throws Exception {
        // 从公私钥文件中读取公私钥字符串
        String keyStr = FileUtils.readFileToString(new File(path));
        // 创建key工厂
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        // 使用Base64对密钥字符串进行解码
        byte[] decode = Base64.decode(keyStr);
        if (keyType == 0) { // 公钥key的规则
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decode);
            // 返回公钥对象
            return keyFactory.generatePublic(keySpec);
        } else { // 创建私钥key的规则
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decode);
            // 返回私钥对象
            return keyFactory.generatePrivate(keySpec);
        }
    }

    /**
     *
     */
    private static void generateKeyToFile() throws Exception {
        // 返回一个 KeyPairGenerator 对象，该对象为指定的算法生成公钥/私钥对
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
        // 初始化密钥对生成器
        keyPairGenerator.initialize(KEY_SIZE);
        // 生成密钥对
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        // 生成公钥
        PublicKey publicKey = keyPair.getPublic();
        // 生成私钥
        PrivateKey privateKey = keyPair.getPrivate();
        // 获取公钥字节数组
        byte[] publicKeyEncoded = publicKey.getEncoded();
        // 获取私钥字节数组
        byte[] privateKeyEncoded = privateKey.getEncoded();
        // 使用Base64对公钥/私钥的Base64进行编码
        String pubKeyEncode = Base64.encode(publicKeyEncoded);
        String priKeyEncode = Base64.encode(privateKeyEncoded);

        // 把公钥和私钥保存到文件中
        FileUtils.writeStringToFile(new File(PUBLIC_KEY_PATH), pubKeyEncode);
        FileUtils.writeStringToFile(new File(PRIVATE_KEY_PATH), priKeyEncode);
    }

    public static void main(String[] args) throws Exception {
        generateKeyToFile();
    }
}
