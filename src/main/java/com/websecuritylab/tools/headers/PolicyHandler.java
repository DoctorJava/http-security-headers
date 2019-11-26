package com.websecuritylab.tools.headers;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.websecuritylab.tools.headers.model.Policy;
import com.websecuritylab.tools.headers.model.Reference;
import com.websecuritylab.tools.headers.model.Rule;
import com.websecuritylab.tools.headers.model.Rule.CONTAINS_TYPE;
import com.websecuritylab.tools.headers.servlets.MaintainRulesServlet;

public class PolicyHandler {
    private static final Logger logger = LoggerFactory.getLogger( PolicyHandler.class );  
    
	public static Policy createDefaultPolicy() {
		
		
		List<Rule> rules = new ArrayList<>();

		List<String> containsAll_Content = Arrays.asList( "text/html", "charset=UTF-8");
        rules.add(new Rule("Content-Type", containsAll_Content, CONTAINS_TYPE.ALL));
		//List<String> containsOnly_Content = Arrays.asList( "text/html; charset=UTF-8");
        //rules.add(new Rule("Content-Type", containsOnly_Content, CONTAINS_TYPE.ONLY));
        
		//String containsExact_Cache = "no-store";
		List<String> contains_Cache = Arrays.asList("no-store");
        //rules.add(new Rule("Cache-Control", containsExact_Cache)); 
        rules.add(new Rule("Cache-Control", contains_Cache, CONTAINS_TYPE.ALL));
        
        Rule ruleHSTS = new Rule("strict-transport-security", true);
		List<Reference> refHSTS = new ArrayList<>();
		refHSTS.add(new Reference("OWASP HSTS Cheatsheet","https://cheatsheetseries.owasp.org/cheatsheets/HTTP_Strict_Transport_Security_Cheat_Sheet.html"));		
		refHSTS.add(new Reference("Wikipedia: HSTS","https://en.wikipedia.org/wiki/HTTP_Strict_Transport_Security"));
		ruleHSTS.setReferences(refHSTS);
        rules.add(ruleHSTS);
        
		List<String> containsAny_Frame = Arrays.asList("SAMEORIGIN", "DENY");
        rules.add(new Rule("x-frame-options" , containsAny_Frame, CONTAINS_TYPE.ANY));
       
		List<String> containsAny_Xss = Arrays.asList("1");
        rules.add(new Rule("X-XSS-Protection", containsAny_Xss, CONTAINS_TYPE.ANY));
        
		List<String> contains_Options = Arrays.asList("nosniff");
        rules.add(new Rule("x-content-type-options", contains_Options, CONTAINS_TYPE.ONLY));
         return new Policy("Default Policy", rules);
	}
	
	public static Policy savedPolicy(String filename) {
		Policy policy = null;	
		try {
			policy = PolicyHandler.readPolicy(filename);
		} catch (Exception e) {
			policy = PolicyHandler.createDefaultPolicy();							// File not found
		}
		if (policy == null ) policy = PolicyHandler.createDefaultPolicy();			// File is empty
		return policy;
	}

	private static Policy readPolicy(String filename) throws FileNotFoundException {
		Gson gson = new Gson();
		Policy policy = null;
		try (Reader reader = new FileReader(filename)) {
    		policy = gson.fromJson(reader, Policy.class);
        } catch (IOException e) {
            e.printStackTrace();
        }      
		return policy;
	}
	
	public static void writePolicy(Policy policy, String filename) throws IOException {
		//Gson gson = new Gson();
//		Gson gson = new GsonBuilder().setPrettyPrinting().create();
//		
//		FileWriter writer = new FileWriter(filename);
//		System.out.println("Writing JSON:" + writer);
//		gson.toJson(policy, writer);
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(filename)) {
        	System.out.println("Writing JSON:" + writer);
            gson.toJson(policy, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
