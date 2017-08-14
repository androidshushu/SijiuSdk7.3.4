package com.shiyue.mhxy.user;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;

import com.shiyue.mhxy.utils.Base64;

import android.os.Environment;

public class UserInfo {
	private String path = "";
	private String name = "";
	private File file;
	private File file_name;

	public UserInfo() {
		path = Environment.getExternalStorageDirectory().getAbsolutePath()
				+ "/49app/";
		name = "49app";
		file = new File(path);
		file_name = new File(path + name);
	}

	/**
	 * 判断文件是否存在
	 * 
	 * @return
	 */
	public boolean isFile() {
		String result = null;
		if(file_name.exists()){
			result = readUserInfo();
		}
		if (file_name.exists() && result != null) {
			return true;
		} else {
			if(file_name.exists() && result == null){
			//	file_name.delete();
			}
			return false;
		}
	}

	/**
	 * 把密码存入文件中
	 */
	public void saveUserInfo(String username, String pwd, String uid,
			String dString) {
//Log.i("kk","------saveUserInfo-----"+username+"---pwd---"+pwd+"---uid---"+uid+"----dString---"+dString);
		String data = "";
		if (!"".equals(dString)) {
			data = dString;
		} else {
			data = username + ":" + pwd + ":" + uid + "#";
		}
		// 首先判断user的位置和user是否重复格式是user1#user2#user3#
//		if (isFile() && "".equals(dString)) {
//			data = verfyInfo(data);
//		}
		RandomAccessFile rFile = null;
		data = Base64.encode(data.getBytes());
		try {
			if (!file.exists()) {
				file.mkdirs();
			}
			rFile = new RandomAccessFile(file_name, "rw");
			rFile.writeBytes(data);
			if (file.exists()) {
				////("log", "save successful");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rFile != null) {
				try {
					rFile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 验证用户信息是否存在 存在 排在首位 不存在 排在首位+其他信息
	 */
	private String verfyInfo(String data) {
		// TODO Auto-generated method stub
		System.out.println("----verfyInfo-----"+data);
		String username = data.split(":")[0];
		String result = "";
		int j = 0;
		String saveData = readUserInfo();
		Map<String, String> map = new HashMap<String, String>();
		map = userMap();
		if (saveData.contains(username)) {
			// 存在--判断在其中的什么位置
			// System.out.println("------存在------");
			for (int i = 0; i < map.size(); i++) {
				if ((map.get("user" + i)).contains(username)) {
					j = i;
					map.remove("user" + i);
					break;
				}
			}
			if (j == 0) {
				result = data;
			}
			if (j == 1) {
				if (map.size() == 1) {
					result = data + map.get("user0") + "#";
				}
				if (map.size() == 2) {
					result = data + map.get("user0") + "#" + map.get("user2")
							+ "#";
				}
			}
			if (j == 2) {
				result = data + map.get("user0") + "#" + map.get("user1") + "#";
			}

		} else {
			// 不存在
			// System.out.println("------不存在------");
			int num = map.size();
			if (num == 3) {
				result = data + map.get("user0") + "#" + map.get("user1") + "#";
			} else {
				result = data + saveData;
			}
		}
		return result;
	}

	/**
	 * 读取用户信息
	 * 
	 * @throws FileNotFoundException
	 */
	public String readUserInfo() {
		String result = "";
		InputStreamReader inputReader = null;
		InputStream inputStream = null;
		BufferedReader br = null;
		try {
			inputStream = new FileInputStream(file_name);
			inputReader = new InputStreamReader(inputStream);
			br = new BufferedReader(inputReader);
			StringBuffer strBuffer = new StringBuffer();
			String line = null;
			while ((line = br.readLine()) != null) {
				strBuffer.append(line);
			}
			result = strBuffer.toString();
			result = Base64.decode(result);
			br.close();
			inputReader.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 用户信息排练顺序
	 */
	public Map<String, String> userMap() {
		Map<String, String> map = new HashMap<String, String>();
		try {
			String[] total = readUserInfo().split("#");
			for (int i = 0; i < total.length; i++) {
				map.put("user" + i, total[i]);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return map;
	}

}
