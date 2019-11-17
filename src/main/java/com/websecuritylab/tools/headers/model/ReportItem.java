package com.websecuritylab.tools.headers.model;

import com.websecuritylab.tools.headers.HeaderRules.HEADER;

public final class ReportItem {
	private String _headerName;
	private String[] _headerValues;
	private boolean _isRequired;			// required is TRUE or FALSE
	private Boolean _isCompliant;			// compliant is TRUE or FALSE or NULL ( if not required )
	
							
	
	public ReportItem(HEADER headerName, String[] headerValues, boolean isRequired, Boolean isCompliant) {
		_headerName = headerName.toString();
		_headerValues = headerValues;
		_isRequired = isRequired;
		_isCompliant = isCompliant;
	}

	public String getHeaderName() {
		return _headerName;
	}

	public String[] getHeaderValues() {
		return _headerValues;
	}
	

	public boolean isRequired() {
		return _isRequired;
	}

	public Boolean isCompliant() {
		return _isCompliant;
	}
	
	
}
