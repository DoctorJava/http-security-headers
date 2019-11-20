package com.websecuritylab.tools.headers;

import java.io.*; 
import java.util.*;
import java.util.stream.Collectors;


import com.websecuritylab.tools.headers.exceptions.InvalidUrlException;
import com.websecuritylab.tools.headers.exceptions.SiteNotFoundException;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

import org.apache.commons.collections4.map.CaseInsensitiveMap;
import org.apache.commons.validator.routines.UrlValidator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class UrlHandler {
    private static final Logger logger = LoggerFactory.getLogger( UrlHandler.class );  
	private URL _url;

	public UrlHandler(String urlStr) throws InvalidUrlException, MalformedURLException {
        boolean valid = validateUrl(urlStr);

        if (!valid)  throw new InvalidUrlException ();

       _url = new URL(urlStr);
    }

	public String getRawHeaders() throws MalformedURLException, SiteNotFoundException{
		StringBuffer sb = new StringBuffer();
		try {
			URLConnection conn = _url.openConnection();
			for (Map.Entry<String, List<String>> entry : conn.getHeaderFields().entrySet()) {
			    System.out.println(entry.toString());
			    sb.append(entry.toString().replace("=",": ") + "\n");
			}
			return sb.toString();
		} catch (IOException e) {
			throw new SiteNotFoundException(e);
		}
	}

	public Map<String, List<String>> getHeaderMap() throws MalformedURLException, SiteNotFoundException{
		try {
			URLConnection conn = _url.openConnection();
			//get all headers
			Map<String, List<String>> map = conn.getHeaderFields();
			for (Map.Entry<String, List<String>> entry : map.entrySet()) {
				System.out.println("Key : " + entry.getKey() +  " ,Value : " + entry.getValue());
			}
			return map;
		} catch (IOException e) {
			throw new SiteNotFoundException(e);
		}
	}
	
	public String getWebPageContent() {
		String content;
		try (InputStream is = _url.openStream();
			 BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) 
		{
			content = br.lines().collect(Collectors.joining(System.lineSeparator()));
		} catch (IOException ex) {
			content = String.format("Cannot read webpage %s", ex);
			logger.error(null, ex);
		}

		return content;
	}

	private boolean validateUrl(String urlStr) {

		UrlValidator urlValidator = new UrlValidator();

		return urlValidator.isValid(urlStr);
	}
	
	
	//
	// Static Methods
	//
	
	public static CaseInsensitiveMap<String, List<String>> generateHeaderMap(String rawHeaders) {
		//HashMap<String, List<String>> headerMap = new HashMap<>();
		CaseInsensitiveMap<String, List<String>> headerMap = new CaseInsensitiveMap<>();
		String[] lines = rawHeaders.split("\\r?\\n");
		for (String line : lines) {
			int i = line.indexOf(":");
			if (i < 0) continue;
			List<String> values = Arrays.asList(line.substring(i + 1).split(";"));
			headerMap.put(line.substring(0, i), values);
		}
		return headerMap;
	}

	public static String generateRawHeaders(Map<String, List<String>> headerMap) {
		StringBuffer sb = new StringBuffer();
		for (Map.Entry<String, List<String>> entry : headerMap.entrySet()) {
			System.out.println(entry.toString());
			sb.append(entry.toString().replace("=", ": ").replace("[", "").replace("]", "") + "\n");			// Default MapEntry to String has = and brackets [...]
		}
		return sb.toString();
	}
}
