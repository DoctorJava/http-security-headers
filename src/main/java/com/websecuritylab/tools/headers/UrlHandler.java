package com.websecuritylab.tools.headers;

import java.io.*; 
import java.util.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


public class UrlHandler {

	public Map<String, List<String>> getHeaders(String url) throws MalformedURLException, SiteNotFoundException{
		URL obj = new URL(url);
		URLConnection conn;
		try {
			conn = obj.openConnection();
			//get all headers
			Map<String, List<String>> map = conn.getHeaderFields();
			for (Map.Entry<String, List<String>> entry : map.entrySet()) {
				System.out.println("Key : " + entry.getKey() +  " ,Value : " + entry.getValue());
			}
			return map;
		} catch (IOException e) {
			throw new SiteNotFoundException(e);
		}
		

	}
}
