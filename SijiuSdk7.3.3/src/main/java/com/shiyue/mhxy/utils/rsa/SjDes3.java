

package com.shiyue.mhxy.utils.rsa;

import java.security.Key;  



import javax.crypto.Cipher;  

import javax.crypto.SecretKeyFactory;  

import javax.crypto.spec.DESedeKeySpec;  

import javax.crypto.spec.IvParameterSpec;

import com.shiyue.mhxy.config.AppConfig;

public class SjDes3 {
	// 密钥

	private  static String secretKey =AppConfig.appId+AppConfig.appKey+"andriod8f43-bc28ffcbe494-4445d5d5-1f0b-3014-49you";
    
	// 向量

	private final static String iv = "01234567";

	// 加解密统一使用的编码方式

	private final static String encoding = "utf-8";

	/**
	 * 
	 * 3DES加密
	 * 
	 * 
	 * 
	 * @param plainText
	 *            普通文本
	 * 
	 * @return
	 * 
	 * @throws Exception
	 */

	public static String encode(String plainText) throws Exception {

		Key deskey = null;
//Log.i("kk","hh"+secretKey);
		DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());

		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");

		deskey = keyfactory.generateSecret(spec);

		Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");

		IvParameterSpec ips = new IvParameterSpec(iv.getBytes());

		cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);

		byte[] encryptData = cipher.doFinal(plainText.getBytes(encoding));
   
		return SjBase64.encode(encryptData);

	}

	/**
	 * 
	 * 3DES解密
	 * 
	 * 
	 * 
	 * @param encryptText
	 *            加密文本
	 * 
	 * @return
	 * 
	 * @throws Exception
	 */

	public static String decode(String encryptText) throws Exception {

		Key deskey = null;

		DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());

		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");

		deskey = keyfactory.generateSecret(spec);

		Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");

		IvParameterSpec ips = new IvParameterSpec(iv.getBytes());

		cipher.init(Cipher.DECRYPT_MODE, deskey, ips);

		byte[] decryptData = cipher.doFinal(SjBase64.decode(encryptText));

		return new String(decryptData, encoding);

	}

}
