package com.websecuritylab.tools.headers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.websecuritylab.tools.headers.model.Rule;

//
//
//
// strict-transport-security: max-age=31536000; includeSubDomains; preload
//


public class HeaderRules {
	public static enum HEADER 
	{ 
		CONTENT_TYPE(new Rule("Content-Type", true)), 
		CACHE_CONTROL(new Rule("Cache-Control", true)), 
		STRICT_TRANSPORT_SECURITY(new Rule("strict-transport-security", true)),
		X_XSS_PROTECTION(new Rule("X-XSS-Protection", false))
		;
		
		private Rule _rule;
		HEADER(Rule rule) { _rule = rule; }
		public String toString() { return _rule.getName(); }
		public boolean required() { return _rule.isRequired(); }
	}
	
	private Map<String, List<String>> _headerMap;
	
	public HeaderRules(Map<String, List<String>> headerMap) {
		_headerMap = headerMap;
	}

	public HeaderRules(String rawHeaders) {
		_headerMap = new HashMap<String, List<String>>();
		String[] lines = rawHeaders.split("\\r?\\n");
		for ( String line: lines) {
			int i = line.indexOf(":");
			if ( i < 0 ) continue;
			List<String> values = Arrays.asList(line.substring(i+1).split(";"));
			_headerMap.put(line.substring(0,i), values);
		}
		System.out.println("Got lines: " + lines.length);
	}	

	public List<String> getValueList(HEADER header){
		return _headerMap.get(header.toString());
	}
	public String[] getValues(HEADER header){
		if ( getValueList(header) == null ) return null;
		else return getValueList(header).toArray(new String[0]);
	}	
	public boolean isRequired(HEADER header) {
		return header.required();
	}	
	public Boolean isCompliant(HEADER header) {
		if ( !header.required() ) return null;				// This is for optional headers that are neither COMPLIANT nor NON-COMLIANT
		else if ( isPresent(header) ) return true;
		else return false;
	}
	public boolean isPresent(HEADER header) {
		for(String name : _headerMap.keySet()){
			if ( name == null ) continue;
			if ( name.equalsIgnoreCase(header.toString()) ) return true;
		}
		return false;
	}

}
