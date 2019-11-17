package com.websecuritylab.tools.headers.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public final class Report {
	private String _name;
	private String _date;
	private List<ReportItem> _items;
	private String _rawHeaders;

	public Report(String name, List<ReportItem> items, String rawHeaders) {
		_name = name;
		_date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));;
		_items = items;
		_rawHeaders = rawHeaders;
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
	


}
