package com.shiyue.mhxy.utils.rsa;

import java.util.Map;

public class RASTester {

	 static String publicKey;  
	    static String privateKey;  
	  
	    static {  
	        try {  
	            Map<String, Object> keyMap = RSAUtils.genKeyPair();  
	         publicKey = RSAUtils.getPublicKey(keyMap);  
	        privateKey = RSAUtils.getPrivateKey(keyMap); 
	       // publicKey="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC+lSTeGZH2QN2jz4Cgt8Wqk/8mU+RTpUOUxoOoXlag1RBx1Sx9RPhxBJlprk79p4M/aWW88vSlpCaVefVtXYDR0kpOe+VY1AL9AYWVxUh9JZ4NpmdkOf9nVcSrAEoydm0IhBlA/CzuSqWi5X0KaNwDaLwWb1LR8OXsXoJ8l8mWwwIDAQAB";
	        // privateKey="MIICeQIBADANBgkqhkiG9w0BAQEFAASCAmMwggJfAgEAAoGBAL6VJN4ZkfZA3aPPgKC3xaqT/yZT5FOlQ5TGg6heVqDVEHHVLH1E+HEEmWmuTv2ngz9pZbzy9KWkJpV59W1dgNHSSk575VjUAv0BhZXFSH0lng2mZ2Q5/2dVxKsASjJ2bQiEGUD8LO5KpaLlfQpo3ANovBZvUtHw5exegnyXyZbDAgMBAAECgYEAr7YEWr1KhLcDYg9jMUqd9QokOSspnTEGoPlx016/EeO/GKSJMynOwSyTYQszisvRxzoecdmyU7GHXVMnQ2Ds7WvbcuNkIRWmxFa4nTkk2zNF6KByvvFwLiW4LQXF6B+uV7+ZNqvfhCoD/j2wki8jfWkuuAaKnTda/axHMi+zRYECQQD73iC2GjZyur4amJQPK6d+kDlJ0dYyyUvQa0vd6mfoPnQDOIqayBaueSwWIpLI/L7eUuP9CDFryQtdBvWqD/dBAkEAwbWcrybn0eaxiPZacZLZXzXO8g12hYoXT1h0DTLvy1rnVUOspNfKZcBZMjPxT4+QEknoTShSnSbJ5sHitfZxAwJBANMlU2z2KqEh1k77jFvvb9oVVEGDbTtkL2+JE6/1W6iB+sXcd63sgb9Ai+n+j+l4oRZGjSTJ4oyGnUUemYI5IkECQQCA9JNrcv4PGYIFCOPrCfTV0m+Dan0Fp4mfE+amRsumWEz60UOktdeS53s51aSG767czgDtJLPi1MjCaz6vHnHbAkEA4NxLLg6UCAoCpXMgqqZHWMgbMwNNFr9diCWP/tZ5OJmWYHgn7zfqMXa/RNaethjdG1biIkj5h7qm6XDBBqGuxw==";
	             //publicKey="S3AahIW6+IYtvXWjoVGzxbP2SeNZKuVyZX42b8Ku1yYmj5+lkHf2JbKK1YMDREUcI+cr2Sxy5v+8nMV7Z1r+T3DZAy5Zo+GKWxlfeGz0u0zbmgrawnajirT4koQTgGZGR40LwmsGSBUkFEA1JzDQ793pXTuRmSB+DqSsr1NKFXU=";
	          // privateKey="rVOXop3qqTYxfn2cANF9eeeJTh+icPNsbaIFIju7Aa1+jlyr/8v7vli4Wfa3BLlqJjwHxHDdgs+0/XyQON7gO91kaErwWmPwGDDBDBFDlVYivCKq3TTAkRU/FkQ7d3mBYokknO0WxmB7V3jhcLF5yr4SBDxN80/WDHz3BO8qta0=";
	            
	           System.err.println("公钥: \n\r" + publicKey);  
	            System.err.println("私钥： \n\r" + privateKey);  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	    }  
	      
	    public static void main(String[] args) throws Exception {  
	        test();  
	       // testSign();  
	    }  
	  
	    static void test() throws Exception {  
	        System.err.println("公钥加密——私钥解密");  
	       String source = "这是一行没有任何意义的文字，你看完了等于没看，不是吗？";  
	      // String source = "201201010000332102112351305danaotiangong81000491405409672quanfudiyimingN23433424214262425433465424123451";  
	       // String source = "werwrw201201010000332102112351305danaotiangong81000491405409672quanfudiyimingN23433424214262425433465424123451";  
	        System.out.println("\r加密前文字：\r\n" + source);  
	        byte[] data =source.getBytes("utf-8")  ;
	        byte[] encodedData = RSAUtils.encryptByPublicKey(data,publicKey );  
	        System.out.println("加密后文字：\r\n" + Base64Utils.encode(encodedData));  
	        byte[] decodedData = RSAUtils.decryptByPrivateKey(encodedData, privateKey);  
	        String target = new String(decodedData);  
	        System.out.println("解密后文字: \r\n" + target);  
	        System.out.println("私钥加密后：\r\n"+RSAUtils.sign(decodedData, target));
	    }  
	  
	    static void testSign() throws Exception {  
	        System.err.println("私钥加密——公钥解密");  
	        String source = "这是一行测试RSA数字签名的无意义文字";  
	        System.out.println("原文字：\r\n" + source);  
	        byte[] data = source.getBytes();  
	        byte[] encodedData = RSAUtils.encryptByPrivateKey(data, privateKey);  
	        System.out.println("加密后：\r\n" + encodedData);  
	        byte[] decodedData = RSAUtils.decryptByPublicKey(encodedData, publicKey);  
	        String target = new String(decodedData);  
	        System.out.println("解密后: \r\n" + target);  
	        System.err.println("私钥签名——公钥验证签名");  
	        String sign = RSAUtils.sign(encodedData, privateKey);  
	        System.err.println("签名:\r" + sign);  
	        boolean status = RSAUtils.verify(encodedData, publicKey, sign);  
	        System.err.println("验证结果:\r" + status);  
	    }  
	
}
