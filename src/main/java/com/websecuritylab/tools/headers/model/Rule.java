package com.websecuritylab.tools.headers.model;

import java.util.List;

public final class Rule {
	
	public static enum CONTAINS_TYPE { ANY, ALL }
	
	private String name;
	private boolean required;
	private String contains;
	private List<String> containsAny;
	private List<String> containsAll;
	private List<Reference> references;
	
	public Rule(String name, boolean required) {
		this.name = name;
		this.required = required;
	}
	public Rule(String name, String contains) {
		this.name = name;
		this.required = true;
		this.contains = contains;
	}	
	public Rule(String name,  List<String> contains, CONTAINS_TYPE type ) {
		this.name = name;
		this.required = true;
		if ( CONTAINS_TYPE.ANY.equals(type)) this.containsAny = contains;
		else if ( CONTAINS_TYPE.ALL.equals(type)) this.containsAll = contains;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isRequired() {
		return required;
	}
	public void setRequired(boolean required) {
		this.required = required;
	}
	public String getContains() {
		return contains;
	}
	public void setContains(String contains) {
		this.contains = contains;
	}
	public List<String> getContainsAny() {
		return containsAny;
	}
	public void setContainsAny(List<String> containsAny) {
		this.containsAny = containsAny;
	}
	public List<String> getContainsAll() {
		return containsAll;
	}
	public void setContainsAll(List<String> containsAll) {
		this.containsAll = containsAll;
	}
	public List<Reference> getReferences() {
		return references;
	}
	public void setReferences(List<Reference> references) {
		this.references = references;
	}


	
}
