package com.shiyue.mhxy.utils.rsa;

import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

public class RSAUtils {
	  /**
     * 算法名称
     */
    private final static String RSA = "RSA";
    
    /**
     * 加密后的字节分隔长度
     */
    private final static int encryptSepLength = 256;
    
    /**
     * 明文字节分隔长度
     */
    private final static int plainSepLneght = 100; 
    /**
     * 
     * 公钥
     * 
     */
    private final static String publicKey="";
    /**
     * 私钥
     */
    private final static String privateKey="";
    

    /** *//** 
     * 加密算法RSA 
     */  
    public static final String KEY_ALGORITHM = "RSA";  
      
    /** *//** 
     * 签名算法 
     */  
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";  
  
    /** *//** 
     * 获取公钥的key 
     */  
    private static final String PUBLIC_KEY = "RSAPublicKey";  
      
    /** *//** 
     * 获取私钥的key 
     */  
    private static final String PRIVATE_KEY = "RSAPrivateKey";  
      
    /** *//** 
     * RSA最大加密明文大小 
     */  
    private static final int MAX_ENCRYPT_BLOCK = 117;  
      
    /** *//** 
     * RSA最大解密密文大小 
     */  
    private static final int MAX_DECRYPT_BLOCK = 128;  
  
    /** *//** 
     * <p> 
     * 生成密钥对(公钥和私钥) 
     * </p> 
     *  
     * @return 
     * @throws Exception 
     */  
    public static Map<String, Object> genKeyPair() throws Exception {  
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);  
        keyPairGen.initialize(1024);  
        KeyPair keyPair = keyPairGen.generateKeyPair();  
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();  
        Map<String, Object> keyMap = new HashMap<String, Object>(2);  
        keyMap.put(PUBLIC_KEY, publicKey);  
        keyMap.put(PRIVATE_KEY, privateKey);  
        return keyMap;  
    }  
      
    /** *//** 
     * <p> 
     * 用私钥对信息生成数字签名 
     * </p> 
     *  
     * @param data 已加密数据 
     * @param privateKey 私钥(BASE64编码) 
     *  
     * @return 
     * @throws Exception 
     */  
    public static String sign(byte[] data, String privateKey) throws Exception {  
        byte[] keyBytes = Base64Utils.decode(privateKey);  
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);  
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);  
        PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);  
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);  
        signature.initSign(privateK);  
        signature.update(data);  
        return Base64Utils.encode(signature.sign());  
    }  
  
    /** *//** 
     * <p> 
     * 校验数字签名 
     * </p> 
     *  
     * @param data 已加密数据 
     * @param publicKey 公钥(BASE64编码) 
     * @param sign 数字签名 
     *  
     * @return 
     * @throws Exception 
     *  
     */  
    public static boolean verify(byte[] data, String publicKey, String sign)  
            throws Exception {  
        byte[] keyBytes = Base64Utils.decode(publicKey);  
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);  
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);  
        PublicKey publicK = keyFactory.generatePublic(keySpec);  
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);  
        signature.initVerify(publicK);  
        signature.update(data);  
        return signature.verify(Base64Utils.decode(sign));  
    }  
  
    /** *//** 
     * <P> 
     * 私钥解密 
     * </p> 
     *  
     * @param encryptedData 已加密数据 
     * @param privateKey 私钥(BASE64编码) 
     * @return 
     * @throws Exception 
     */  
    public static byte[] decryptByPrivateKey(byte[] encryptedData, String privateKey)  
            throws Exception {  
        byte[] keyBytes = Base64Utils.decode(privateKey);  
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);  
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);  
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);  
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");  
//        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());  
        cipher.init(Cipher.DECRYPT_MODE, privateK);  
        int inputLen = encryptedData.length;  
        ByteArrayOutputStream out = new ByteArrayOutputStream();  
        int offSet = 0;  
        byte[] cache;  
        int i = 0;  
        // 对数据分段解密  
        while (inputLen - offSet > 0) {  
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {  
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);  
            } else {  
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);  
            }  
            out.write(cache, 0, cache.length);  
            i++;  
            offSet = i * MAX_DECRYPT_BLOCK;  
        }  
        byte[] decryptedData = out.toByteArray();  
        out.close();  
        return decryptedData;  
    }  
  
    /** *//** 
     * <p> 
     * 公钥解密 
     * </p> 
     *  
     * @param encryptedData 已加密数据 
     * @param publicKey 公钥(BASE64编码) 
     * @return 
     * @throws Exception 
     */  
    public static byte[] decryptByPublicKey(byte[] encryptedData, String publicKey)  
            throws Exception {  
        byte[] keyBytes = Base64Utils.decode(publicKey);  
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);  
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);  
        Key publicK = keyFactory.generatePublic(x509KeySpec);  
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");  
//        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());  
        cipher.init(Cipher.DECRYPT_MODE, publicK);  
        int inputLen = encryptedData.length;  
        ByteArrayOutputStream out = new ByteArrayOutputStream();  
        int offSet = 0;  
        byte[] cache;  
        int i = 0;  
        // 对数据分段解密  
        while (inputLen - offSet > 0) {  
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {  
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);  
            } else {  
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);  
            }  
            out.write(cache, 0, cache.length);  
            i++;  
            offSet = i * MAX_DECRYPT_BLOCK;  
        }  
        byte[] decryptedData = out.toByteArray();  
        out.close();  
        return decryptedData;  
    }  
  
    /** *//** 
     * <p> 
     * 公钥加密 
     * </p> 
     *  
     * @param data 源数据 
     * @param publicKey 公钥(BASE64编码) 
     * @return 
     * @throws Exception 
     */  
    public static byte[] encryptByPublicKey(byte[] data, String publicKey)  
            throws Exception {  
        byte[] keyBytes = Base64Utils.decode(publicKey);  
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);  
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);  
        Key publicK = keyFactory.generatePublic(x509KeySpec);  
        // 对数据加密  
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");  
//        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());  
        cipher.init(Cipher.ENCRYPT_MODE, publicK);  
        int inputLen = data.length;  
        ByteArrayOutputStream out = new ByteArrayOutputStream();  
        int offSet = 0;  
        byte[] cache;  
        int i = 0;  
        // 对数据分段加密  
        while (inputLen - offSet > 0) {  
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {  
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);  
            } else {  
                cache = cipher.doFinal(data, offSet, inputLen - offSet);  
            }  
            out.write(cache, 0, cache.length);  
            i++;  
            offSet = i * MAX_ENCRYPT_BLOCK;  
        }  
        byte[] encryptedData = out.toByteArray();  
        out.close();  
        return encryptedData;  
    }  
  
    /** *//** 
     * <p> 
     * 私钥加密 
     * </p> 
     *  
     * @param data 源数据 
     * @param privateKey 私钥(BASE64编码) 
     * @return 
     * @throws Exception 
     */  
    public static byte[] encryptByPrivateKey(byte[] data, String privateKey)  
            throws Exception {  
        byte[] keyBytes = Base64Utils.decode(privateKey);  
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);  
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);  
      //KeyFactory keyFactory = KeyFactory.getInstance("RSA/ECB/PKCS1Padding");  
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);  
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");  
//        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());  
        cipher.init(Cipher.ENCRYPT_MODE, privateK);  
       // data="abcjkasldfdsffffffffffffffffffffffffffffffffffffffffffffffffffed".getBytes("utf-8");
        int inputLen = data.length;  
        ByteArrayOutputStream out = new ByteArrayOutputStream();  
        int offSet = 0;  
        byte[] cache;  
        int i = 0;  
        // 对数据分段加密  
        while (inputLen - offSet > 0) {  
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {  
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);  
            } else {  
                cache = cipher.doFinal(data, offSet, inputLen - offSet);  
            }  
            out.write(cache, 0, cache.length);  
            i++;  
            offSet = i * MAX_ENCRYPT_BLOCK;  
        }  
        byte[] encryptedData = out.toByteArray();  
        out.close();  
       
        return encryptedData;  
    }  
  
    /** *//** 
     * <p> 
     * 获取私钥 
     * </p> 
     *  
     * @param keyMap 密钥对 
     * @return 
     * @throws Exception 
     */  
    public static String getPrivateKey(Map<String, Object> keyMap)  
            throws Exception {  
        Key key = (Key) keyMap.get(PRIVATE_KEY);  
        return Base64Utils.encode(key.getEncoded());  
    }  
  
    /** *//** 
     * <p> 
     * 获取公钥 
     * </p> 
     *  
     * @param keyMap 密钥对 
     * @return 
     * @throws Exception 
     */  
    public static String getPublicKey(Map<String, Object> keyMap)  
            throws Exception {  
        Key key = (Key) keyMap.get(PUBLIC_KEY);  
        return Base64Utils.encode(key.getEncoded());  
    }  
    public static String getPublicKey(){
//    	return "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC+lSTeGZH2QN2jz4Cgt8Wqk/8mU+RTpUOUxoOoXlag1RBx1Sx9RPhxBJlprk79p4M/aWW88vSlpCaVefVtXYDR0kpOe+VY1AL9AYWVxUh9JZ4NpmdkOf9nVcSrAEoydm0IhBlA/CzuSqWi5X0KaNwDaLwWb1LR8OXsXoJ8l8mWwwIDAQAB";
    	return "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC7zzMvQhnyq5WPn3f2ztd7iU6R7hOYS4Xaz2F1/vw+M6YASqVMjRmpDDhwZLHXqZYgJODbnJN4WS8CO/LvQpMraENkVQrJYPw2KsYukdq2RTsNXdYYXqEHT7FjXKKfxq/JvzNWTuc/xs/qSeNXR7S6Zj5N2TbdEFehl3dXN/pjXQIDAQAB" ;
    			    }
    public static String getPrivateKey(){
//    	return "MIICeQIBADANBgkqhkiG9w0BAQEFAASCAmMwggJfAgEAAoGBAL6VJN4ZkfZA3aPPgKC3xaqT/yZT5FOlQ5TGg6heVqDVEHHVLH1E+HEEmWmuTv2ngz9pZbzy9KWkJpV59W1dgNHSSk575VjUAv0BhZXFSH0lng2mZ2Q5/2dVxKsASjJ2bQiEGUD8LO5KpaLlfQpo3ANovBZvUtHw5exegnyXyZbDAgMBAAECgYEAr7YEWr1KhLcDYg9jMUqd9QokOSspnTEGoPlx016/EeO/GKSJMynOwSyTYQszisvRxzoecdmyU7GHXVMnQ2Ds7WvbcuNkIRWmxFa4nTkk2zNF6KByvvFwLiW4LQXF6B+uV7+ZNqvfhCoD/j2wki8jfWkuuAaKnTda/axHMi+zRYECQQD73iC2GjZyur4amJQPK6d+kDlJ0dYyyUvQa0vd6mfoPnQDOIqayBaueSwWIpLI/L7eUuP9CDFryQtdBvWqD/dBAkEAwbWcrybn0eaxiPZacZLZXzXO8g12hYoXT1h0DTLvy1rnVUOspNfKZcBZMjPxT4+QEknoTShSnSbJ5sHitfZxAwJBANMlU2z2KqEh1k77jFvvb9oVVEGDbTtkL2+JE6/1W6iB+sXcd63sgb9Ai+n+j+l4oRZGjSTJ4oyGnUUemYI5IkECQQCA9JNrcv4PGYIFCOPrCfTV0m+Dan0Fp4mfE+amRsumWEz60UOktdeS53s51aSG767czgDtJLPi1MjCaz6vHnHbAkEA4NxLLg6UCAoCpXMgqqZHWMgbMwNNFr9diCWP/tZ5OJmWYHgn7zfqMXa/RNaethjdG1biIkj5h7qm6XDBBqGuxw==";
    	return "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALvPMy9CGfKrlY+fd/bO13uJTpHuE5hLhdrPYXX+/D4zpgBKpUyNGakMOHBksdepliAk4Nuck3hZLwI78u9CkytoQ2RVCslg/DYqxi6R2rZFOw1d1hheoQdPsWNcop/Gr8m/M1ZO5z/Gz+pJ41dHtLpmPk3ZNt0QV6GXd1c3+mNdAgMBAAECgYAfPHLy5TeTmN4jKQ62yegk781VkBdKOSVmIx++lE43ujddzQx5Eu61owawCs9ZYydI6VlMrVoJpiufX4EkMhpv0GJDcAH12Ftd0dhmazA89SdK65vfZ0svYatC1WhGWGs19alUMcPIMWlQ+gv8Er82b/VX22VECOicXjqgcUYpgQJBAPTDyzGqL1V1GOebftJTtdv93cpZiW4ZT+D8XFR3Rf5JQMYWyV2LISWsBEOBnryfkhcmxgYz3Fdyiv7eBWVAUzkCQQDEbiDt48ygfvviKdW2KhYsc2Pt49O6E0GC7+tNbX5nsdKzC5jNGP85S6zT0Rb83ePXsWqvGS/s7NsP7r8tap1FAkEAr1urMyzTdq1LJ3v+818n9rG9+eAGFwwpb24+FAHT8qe2sXTX1Z39JIlGELtH56mHN/D+hKkZHsmgjZKxWOYp+QJAZcSw5s8Y2yYvFdOa15AigNEfusZIkaUcUdw/SpQp40tr4Dtn5QrIYETmu68ee8/yFXPj0+f2vodQT4bgIYWVCQJBAM01zqIB1YhiZeEz8neyLNQU+OQd7WqxeJtbauaDG7xnX0z9+q6CBz6yrWdkVSau2Xre4lBPCyPowPcEYziocF8=" ;
    			 }
  
}
