package com.shiyue.mhxy.pay;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

public class SignUtils {

	public static  String PARTNER ="" ;//"2088211033999180";
	public static  String SELLER ="" ;//"zhoubl@49app.com";
	public static  String RSA_PRIVATE =""; //"MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAMAlZntF89HvlDH4isKatJ327dSwYCWye8WiymAN+Wi3HIyZiVxNKGghhhla9Ki76RYNeap8YHFnvQYkAq1kINXGf/swlKz7sGJgrWta91P3gS6OQQm8Unt7+UGZB7LuNnbP2dJf0zogcaqBI8gTYHT6I7M7W+k76rElOsKAyBP1AgMBAAECgYAnk8yPGbKGDH4O6O3T9b+8Rc9pHsc/HItoAwcCT/6OdiEYSMvFSlNHV5higpygYMw68Z2c4y+OTFrf9+zhQB2O7vF9e58ZBIgheafAMFrX8GKC7TNwM/v/B2XLgPcHddSWjH5upg/0UR8Qrm/5YPwWcIDnP6S6p8zDt6Y6X+N6ZQJBAPjmBrQf6gSxLc9WrdiJnjLYpTQJKsgv2p16u0bE8dO0a1NmYIjn7To2EMMPzQvdG3xkgTh4YO3WDufYaWtxz78CQQDFoNmXsmcSDmVKjzzysm5rf5FNzwJe/uY23xyxwVVZxa7+z/gZDjAIvZ++onHEGtkNpFi1Cl2k5DoUYl3SfYlLAkEAz0JUwLd2mpOjruzh9Neb+YE4CB2+F1bp94rU2fhz2zN4z8kHh9mBTKDq0lOdDkz/b1UN2aU8KbC/VsZ5+nOaZwJBAJHPQFtk/BjdWCxvjDdAIKndf/ZZclVmWbJIYOJhUzid2qQUCudpiVUZmt/K+IcIi/Y9uFkyYa8D2Dor7S8dAgsCQQDo6x6CaZacsKqDoSKdoa4Mu/iuyxpRpPyu90Bd3PGxw+fAzU9fYGBbndEBBbqmGlNBAffUUu9vmaKqm0zZ2HCL";
//	public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";

	private static final String ALGORITHM = "RSA";

	private static final String SIGN_ALGORITHMS = "SHA1WithRSA";

	private static final String DEFAULT_CHARSET = "UTF-8";

	public static String sign(String content, String privateKey) {
		try {
			PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(
					Base64.decode(privateKey));
			KeyFactory keyf = KeyFactory.getInstance(ALGORITHM,"BC");
			PrivateKey priKey = keyf.generatePrivate(priPKCS8);

			java.security.Signature signature = java.security.Signature
					.getInstance(SIGN_ALGORITHMS);

			signature.initSign(priKey);
			signature.update(content.getBytes(DEFAULT_CHARSET));

			byte[] signed = signature.sign();

			return Base64.encode(signed);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}
