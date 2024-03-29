package com.chen.my_project.util;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * PKI工具类
 * 
 * @author ChenDuochuang
 * @date 2018年11月2日
 */
public final class PKIUtils {

    /** 日志 */
    private static final Logger logger = LogManager.getLogger(PKIUtils.class);

    private PKIUtils() {
    }

    /**
     * 用证书的私钥签名
     * @param in 证书库
     * @param storePassword 证书库密码
     * @param keyPassword 证书密码
     * @param key 钥别名
     * @param data 待签名数据
     * @return 签名
     * @author ChenDuochuang
     */
    public static byte[] signature(InputStream in, String storePassword, String keyPassword, String key, byte[] data) {
        try {
            // 获取证书私钥
            PrivateKey privateKey = getPrivateKey(in, storePassword, keyPassword, key);
            Signature signet = Signature.getInstance("MD5withRSA");
            signet.initSign(privateKey);
            signet.update(data);
            byte[] signed = signet.sign(); // 对信息的数字签名
            return signed;
        } catch (Exception ex) {
            logger.error("签名失败", ex);
        }
        return null;
    }

    /**
     * 用证书的公钥验证签名
     * @param in 证书
     * @param data 原始数据
     * @param signatureData 对原始数据的签名
     * @return
     * @author ChenDuochuang
     */
    public static boolean verifySignature(InputStream in, byte[] data, byte[] signatureData) {
        try {
            // 获取证书公钥
            PublicKey key = getPublicKey(in);
            Signature signet = Signature.getInstance("MD5withRSA");
            signet.initVerify(key);
            signet.update(data);
            boolean result = signet.verify(signatureData);
            return result;
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException ex) {
            logger.error("验证签名失败", ex);
        }
        return false;
    }

    /**
     * 加密数据
     * @param key 公钥或私钥
     * @param data 待加密数据
     * @return
     * @author ChenDuochuang
     */
    public static byte[] encrypt(Key key, byte[] data) {
        try {
            // 定义算法：RSA
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            // 正式执行加密操作
            byte encryptedData[] = cipher.doFinal(data);
            return encryptedData;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException
                | BadPaddingException ex) {
            logger.error("加密数据失败", ex);
        }
        return null;
    }

    /**
     * 解密数据
     * @param key 公钥或私钥
     * @param data 待解密数据
     * @return 明文
     * @author ChenDuochuang
     */
    public static byte[] decrypt(Key key, byte[] data) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, key);
            // 解密后的数据
            byte[] result = cipher.doFinal(data);
            return result;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException
                | BadPaddingException ex) {
            logger.error("解密数据失败", ex);
        }
        return null;
    }

    /**
     * 用证书的公钥加密
     * @param in 证书
     * @param data 待加密数据
     * @return 密文
     * @author ChenDuochuang
     */
    public static byte[] encryptWithPublicKey(InputStream in, byte[] data) {
        try {
            // 获取证书公钥
            PublicKey key = getPublicKey(in);

            byte encryptedData[] = encrypt(key, data);
            return encryptedData;
        } catch (Exception ex) {
            logger.error("用证书的公钥加密失败", ex);
        }
        return null;
    }

    /**
     * 
     * 用证书的公钥解密
     * @param in 证书
     * @param data 待解密数据
     * @return 明文
     * @author ChenDuochuang
     */
    public static byte[] decryptWithPublicKey(InputStream in, byte[] data) {
        try {
            // 获取证书公钥
            PublicKey key = getPublicKey(in);
            // 解密后的数据
            byte[] result = decrypt(key, data);
            return result;
        } catch (Exception ex) {
            logger.error("用证书的公钥解密失败", ex);
        }
        return null;
    }

    /**
     * 用证书的私钥加密
     * @param in 证书库
     * @param storePassword 证书库密码
     * @param keyPassword 证书密码
     * @param key 钥别名
     * @param data 待加密数据
     * @return 密文
     * @author ChenDuochuang
     */
    public static byte[] encryptWithPrivateKey(InputStream in, String storePassword, String keyPassword, String key,
            byte[] data) {
        try {
            // 获取证书私钥
            PrivateKey privateKey = getPrivateKey(in, storePassword, keyPassword, key);

            byte encryptedData[] = encrypt(privateKey, data);
            return encryptedData;
        } catch (Exception ex) {
            logger.error("用证书的私钥加密失败", ex);
        }
        return null;
    }

    /**
     * 
     * 用证书的私钥解密
     * @param in 证书库
     * @param storePassword 证书库密码
     * @param keyPassword 证书密码
     * @param key 钥别名
     * @param data 待解密数据
     * @return 明文
     * @author ChenDuochuang
     */
    public static byte[] decryptWithPrivateKey(InputStream in, String storePassword, String keyPassword, String key,
            byte[] data) {
        try {
            // 获取证书私钥
            PrivateKey privateKey = getPrivateKey(in, storePassword, keyPassword, key);
            // 解密后的数据
            byte[] result = decrypt(privateKey, data);
            return result;
        } catch (Exception ex) {
            logger.error("用证书的私钥解密失败", ex);
        }
        return null;
    }

    /**
     * 获取证书私钥
     * @param in 证书库
     * @param storePassword 证书库密码
     * @param keyPassword 证书密码
     * @param key 钥别名
     * @return 私钥
     * @author ChenDuochuang
     */
    private static PrivateKey getPrivateKey(InputStream in, String storePassword, String keyPassword, String key) {
        try {
            // 加载证书库
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(in, storePassword.toCharArray());
            // 获取证书私钥
            PrivateKey privateKey = (PrivateKey) ks.getKey(key, keyPassword.toCharArray());
            return privateKey;
        } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException
                | UnrecoverableKeyException ex) {
            logger.error("获取证书私钥失败", ex);
        }
        return null;
    }

    /**
     * 获取证书公钥
     * @param in 证书
     * @return 公钥
     * @author ChenDuochuang
     */
    private static PublicKey getPublicKey(InputStream in) {
        try {
            // 用证书的公钥加密
            CertificateFactory factory = CertificateFactory.getInstance("X.509");
            Certificate cert = factory.generateCertificate(in);
            // 得到证书文件携带的公钥
            PublicKey key = cert.getPublicKey();
            return key;
        } catch (CertificateException ex) {
            logger.error("获取证书公钥失败", ex);
        }
        return null;
    }

}
