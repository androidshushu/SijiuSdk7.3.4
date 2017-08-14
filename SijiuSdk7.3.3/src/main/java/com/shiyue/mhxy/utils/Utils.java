package com.shiyue.mhxy.utils;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Utils {

	// UTF-8 encoding
	private static final String ENCODING_UTF8 = "UTF-8";
	private static final String LOGTAG = "Utils";

	public static boolean isNetworkAvailable(Context context) {

		ConnectivityManager conn = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = conn.getActiveNetworkInfo();
		if (info != null) {
			return info.isAvailable();
		}

		return false;
	}

	public static String NetWork_Type(Context context) {
		ConnectivityManager conn = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = conn.getActiveNetworkInfo();
		String type = info.getTypeName();
		return type;

	}

	/**
	 * 判断MOBILE网络是否可用
	 * 
	 * @param context
	 * @return
	 * @throws Exception
	 */
	public static boolean isMobileDataEnable(Context context) throws Exception {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		boolean isMobileDataEnable = false;

		isMobileDataEnable = connectivityManager.getNetworkInfo(
				ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();

		return isMobileDataEnable;
	}

	/**
	 * 判断wifi 是否可用
	 * 
	 * @param context
	 * @return
	 * @throws Exception
	 */
	public static boolean isWifiDataEnable(Context context) throws Exception {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		boolean isWifiDataEnable = false;
		isWifiDataEnable = connectivityManager.getNetworkInfo(
				ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
		return isWifiDataEnable;
	}

	/**
	 * 
	 * Get UTF8 bytes from a string
	 * 
	 * 
	 * @param string
	 *            String
	 * @return UTF8 byte array, or null if failed to get UTF8 byte array
	 */
	public static byte[] getUTF8Bytes(String string) {
		if (string == null)
			return new byte[0];

		try {
			return string.getBytes(ENCODING_UTF8);
		} catch (UnsupportedEncodingException e) {
			/*
			 * If system doesn't support UTF-8, use another way
			 */
			try {
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				DataOutputStream dos = new DataOutputStream(bos);
				dos.writeUTF(string);
				byte[] jdata = bos.toByteArray();
				bos.close();
				dos.close();
				byte[] buff = new byte[jdata.length - 2];
				System.arraycopy(jdata, 2, buff, 0, buff.length);
				return buff;
			} catch (IOException ex) {
				return new byte[0];
			}
		}
	}

	/**
	 * 
	 * Get string in UTF-8 encoding
	 * 
	 * 
	 * @param b
	 *            byte array
	 * @return string in utf-8 encoding, or empty if the byte array is not
	 *         encoded with UTF-8
	 */
	public static String getUTF8String(byte[] b) {
		if (b == null)
			return "";
		return getUTF8String(b, 0, b.length);
	}

	/**
	 * 
	 * Get string in UTF-8 encoding
	 * 
	 */
	public static String getUTF8String(byte[] b, int start, int length) {
		if (b == null) {
			return "";
		} else {
			try {
				return new String(b, start, length, ENCODING_UTF8);
			} catch (UnsupportedEncodingException e) {
				return "";
			}
		}
	}


}
