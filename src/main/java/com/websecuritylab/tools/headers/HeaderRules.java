package com.websecuritylab.tools.headers;

import java.util.List;
import java.util.Map;

public class HeaderRules {
	public static enum HEADER_NAME 
	{ 
		CONTENT_TYPE("Content-Type"), CACHE_CONTROL("Cache-Control");
		private String _name;
		HEADER_NAME(String name) { _name = name; }
		public String toString() { return ""+_name; }
	}
	
	public static boolean hasHeader(Map<String, List<String>> headerMap, HEADER_NAME check) {
		System.out.println("Looking for Name: " + check.toString());

		for(String name : headerMap.keySet()){
			if ( name == null ) continue;
			System.out.println("Checking Name: " + name);
			if ( name.equalsIgnoreCase(check.toString()) ) return true;
		}
		return false;
	}

}
