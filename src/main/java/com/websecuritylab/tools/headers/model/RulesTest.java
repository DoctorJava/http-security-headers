package com.websecuritylab.tools.headers.model;

import java.util.List;

public class RulesTest {
    private String name;
    private List<Rule> rules;
    
	public RulesTest(String name, List<Rule> rules) {
		this.name = name;
		this.rules = rules;
	}

	public String getName() {
		return name;
	}

	public List<Rule> getRules() {
		return rules;
	}
    
	
}
