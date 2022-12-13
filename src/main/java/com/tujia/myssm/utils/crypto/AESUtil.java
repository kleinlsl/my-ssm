package com.tujia.myssm.utils.crypto;

import java.nio.charset.Charset;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: hongliu_6
 * @create: 2020/3/26 15:43
 * @description: AES加解密工具类
 */
public class AESUtil {
    private static final Logger logger = LoggerFactory.getLogger(AESUtil.class);
    public static String aesKey = "AD42F6697B035B75";
    // 偏移量
    private static int offset = 16;
    private static String algorithm = "AES";
    private static Charset charset = Charset.forName("UTF-8");
    private static String transformation = "AES/CBC/PKCS5Padding";

    /**
     * 加密
     *
     * @param content
     * @return
     */
    public static String encrypt(String content) {
        return encrypt(content, aesKey);
    }

    /**
     * 解密
     *
     * @param content
     * @return
     */
    public static String decrypt(String content) {
        return decrypt(content, aesKey);
    }

    /**
     * 加密
     *
     * @param content 需要加密的内容
     * @param key     加密密码
     * @return
     */
    public static String encrypt(String content, String key) {
        try {
            SecretKeySpec skey = new SecretKeySpec(key.getBytes(), algorithm);
            IvParameterSpec iv = new IvParameterSpec(key.getBytes(), 0, offset);
            Cipher cipher = Cipher.getInstance(transformation);
            byte[] byteContent = content.getBytes(charset);
            cipher.init(Cipher.ENCRYPT_MODE, skey, iv);// 初始化
            byte[] result = cipher.doFinal(byteContent);
            return Base64.getEncoder().encodeToString(result); // 加密
        } catch (Exception e) {
            logger.error("AES encrypt exception, content:{}", content, e);
            return null;
        }
    }

    /**
     * AES（256）解密
     *
     * @param content 待解密内容
     * @param key     解密密钥
     * @return 解密之后
     * @throws Exception
     */
    public static String decrypt(String content, String key) {
        try {
            SecretKeySpec skey = new SecretKeySpec(key.getBytes(), algorithm);
            IvParameterSpec iv = new IvParameterSpec(key.getBytes(), 0, offset);
            Cipher cipher = Cipher.getInstance(transformation);
            cipher.init(Cipher.DECRYPT_MODE, skey, iv);// 初始化
            byte[] result = cipher.doFinal(Base64.getDecoder().decode(content));
            return new String(result); // 解密
        } catch (Exception e) {
            logger.error("AES decrypt exception, content:{}", content, e);
            return null;
        }
    }

    public static void main(String[] args) throws Exception {
        String s = "hello world";
        // 加密
        System.out.println("加密前：" + s);
        String encryptResultStr = encrypt(s);
        System.out.println("加密后：" + encryptResultStr);
        // 解密
        System.out.println("解密后：" + decrypt(encryptResultStr));
    }

}