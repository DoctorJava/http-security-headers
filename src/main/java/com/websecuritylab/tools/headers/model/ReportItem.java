package com.websecuritylab.tools.headers.model;

import java.util.List;

public final class ReportItem {
	private String _headerName;
	//private String[] _headerValues;
	private List<String> _headerValues;
	private boolean _isPresent;			
	private boolean _isRequired;			// required is TRUE or FALSE
	private Boolean _isCompliant;			// compliant is TRUE or FALSE or NULL ( if not required )
	
	public ReportItem(String headerName, List<String> headerValues, boolean isPresent, boolean isRequired, Boolean isCompliant) {
		_headerName = headerName;
		_headerValues = headerValues;
		_isPresent = isPresent;
		_isRequired = isRequired;
		_isCompliant = isCompliant;
	}

	public String getHeaderName() {
		return _headerName;
	}

	public void setHeaderName(String headerName) {
		_headerName = headerName;
	}

	public List<String> getHeaderValues() {
		return _headerValues;
	}

	public void setHeaderValues(List<String> headerValues) {
		_headerValues = headerValues;
	}
	
	public boolean isPresent() {
		return _isPresent;
	}

	public void setPresent(boolean isPresent) {
		_isPresent = isPresent;
	}

	public boolean isRequired() {
		return _isRequired;
	}

	public void setRequired(boolean isRequired) {
		_isRequired = isRequired;
	}

	public Boolean isCompliant() {
		return _isCompliant;
	}

	public void setCompliant(Boolean isCompliant) {
		_isCompliant = isCompliant;
	}
}
