package com.bootdo.testDemo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


//添加省市区到数据库
public class ProvinceUtil {

	  public static String sendGet(String id) {
		  Map<String, Object> resultMap = new HashMap<String, Object>();

	        // 参数解释：lng：经度，lat：维度。KEY：腾讯地图key，get_poi：返回状态。1返回，0不返回
	        String urlString = "http://apis.map.qq.com/ws/district/v1/getchildren?&id="+id+"&key=AO5BZ-BXO6P-YKWD4-V3WMD-AOR4E-DNF2I";
	        String result = "";
	        try {
	            URL url = new URL(urlString);
	            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	            conn.setDoOutput(true);
	            // 腾讯地图使用GET
	            conn.setRequestMethod("GET");
	            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
	            String line;
	            // 获取地址解析结果
	            while ((line = in.readLine()) != null) {
	                result += line + "\n";
	            }
	            in.close();
	        } catch (Exception e) {
	            e.getMessage();
	        }
	      
	        return result;
	    }
	  public static String sendGets() {
		  Map<String, Object> resultMap = new HashMap<String, Object>();

	        // 参数解释：lng：经度，lat：维度。KEY：腾讯地图key，get_poi：返回状态。1返回，0不返回
	        String urlString = "http://apis.map.qq.com/ws/district/v1/list?key=AO5BZ-BXO6P-YKWD4-V3WMD-AOR4E-DNF2I";
	        String result = "";
	        try {
	            URL url = new URL(urlString);
	            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	            conn.setDoOutput(true);
	            // 腾讯地图使用GET
	            conn.setRequestMethod("GET");
	            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
	            String line;
	            // 获取地址解析结果
	            while ((line = in.readLine()) != null) {
	                result += line + "\n";
	            }
	            in.close();
	        } catch (Exception e) {
	            e.getMessage();
	        }
	      
	        return result;
	    }

}
