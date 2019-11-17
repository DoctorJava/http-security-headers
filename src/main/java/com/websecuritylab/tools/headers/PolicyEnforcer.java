package com.websecuritylab.tools.headers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.websecuritylab.tools.headers.model.Headers;
import com.websecuritylab.tools.headers.model.Policy;
import com.websecuritylab.tools.headers.model.Rule;
import com.websecuritylab.tools.headers.servlets.MaintainRulesServlet;

public class PolicyEnforcer {
    private static final Logger logger = LoggerFactory.getLogger( PolicyEnforcer.class );  

	private Headers _headers;
	
	public PolicyEnforcer(Headers headers) {
		_headers = headers;
	}
    
//	private Policy _policy;
//	
//	public PolicyEnforcer(Policy policy) {
//		_policy = policy;
//	}


//	public List<String> getValueList(HEADER header){
//		return _headerMap.get(header.toString());
//	}
//	public String[] getValues(HEADER header){
//		if ( getValueList(header) == null ) return null;
//		else return getValueList(header).toArray(new String[0]);
//	}	
	




	public boolean isPresent(Rule rule) {
		return ( _headers.getValues( rule.getName() ) != null );
	}	
	
	
//	public boolean isRequired(HEADER header) {
//		return header.required();
//	}	
//	public Boolean isCompliant(HEADER header) {
//		if ( !header.required() ) return null;				// This is for optional headers that are neither COMPLIANT nor NON-COMLIANT
//		else if ( isPresent(header) ) return true;
//		else return false;
//	}
//	public boolean isPresent(HEADER header) {
//		for(String name : _headerMap.keySet()){
//			if ( name == null ) continue;
//			if ( name.equalsIgnoreCase(header.toString()) ) return true;
//		}
//		return false;
//	}
	
}
