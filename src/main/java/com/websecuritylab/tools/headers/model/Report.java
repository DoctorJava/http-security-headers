package com.websecuritylab.tools.headers.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public final class Report {
	private String _name;
	private String _url;
	private String _date;
	private Policy _policy;
	private List<ReportItem> _items;
	private String _rawHeaders;

	public Report(String name, Policy policy, List<ReportItem> items, String rawHeaders) {
		_name = name;
		
		_date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		_policy = policy;

		_items = items;
		_rawHeaders = rawHeaders;
	}


	public Policy getPolicy() {
		return _policy;
	}
	public String getName() {
		return _name;
	}
	public String getDate() {
		return _date;
	}
	public List<ReportItem> getItems() {
		return _items;
	}
	public String getRawHeaders() {
		return _rawHeaders;
	}
	public String getUrl() {
		return _url;
	}

	//
	// Setters
	//
	public void setUrl(String url) {
		_url = url;
	}
	


}
