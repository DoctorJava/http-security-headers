package com.websecuritylab.tools.headers.servlets;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.websecuritylab.tools.headers.PolicyEnforcer;
import com.websecuritylab.tools.headers.PolicyHandler;
import com.websecuritylab.tools.headers.UrlHandler;
import com.websecuritylab.tools.headers.constants.DoPostParams;
import com.websecuritylab.tools.headers.constants.ReqAttributes;
import com.websecuritylab.tools.headers.exceptions.InvalidUrlException;
import com.websecuritylab.tools.headers.exceptions.SiteNotFoundException;
import com.websecuritylab.tools.headers.model.Report;
import com.websecuritylab.tools.headers.model.ReportItem;
import com.websecuritylab.tools.headers.model.Rule;
import com.websecuritylab.tools.headers.model.Headers;
import com.websecuritylab.tools.headers.model.Policy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servlet implementation class CheckHeaders
 */
public class CheckHeadersServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger( CheckHeadersServlet.class );  
	private static final long serialVersionUID = 1L;
	private static final String JSP_SHOW_REPORT = "/WEB-INF/jsp/showReport.jsp";     

    public CheckHeadersServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
			String testUrl = req.getParameter(DoPostParams.TEST_URL);
			if ( !testUrl.startsWith("http://") && !testUrl.startsWith("https://")  ) testUrl = "http://" + testUrl;
			logger.info("Got testUrl: " + testUrl);
			
			try {
				UrlHandler handler = new UrlHandler(testUrl);

				Map<String, List<String>> headerMap = handler.getHeaderMap();
				Headers headers = new Headers(headerMap);
 		        
		        PolicyEnforcer enforcer = new PolicyEnforcer(headers);
		                
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(JSP_SHOW_REPORT);	

				req.setAttribute("report", getReport(enforcer, headers));
				
		        //response.setContentType("text/html;charset=UTF-8");
				dispatcher.forward(req, res);
		    } catch (SiteNotFoundException e) {
				e.printStackTrace();
			} catch (InvalidUrlException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		
	}
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		String testHeaders = req.getParameter(DoPostParams.TEST_HEADERS);
		logger.info("Got testHeaders: " + testHeaders);
		
		Headers headers = new Headers(testHeaders);
		
        PolicyEnforcer enforcer = new PolicyEnforcer(headers);
        
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(JSP_SHOW_REPORT);	
        
		req.setAttribute("report", getReport(enforcer,headers));
		
        //response.setContentType("text/html;charset=UTF-8");
		dispatcher.forward(req, res);

	}
	
//	private Report getReport(PolicyEnforcerHardcodedEnum rules, String rawHeaders) {
//        List<ReportItem> items = new ArrayList<>();
//        for (HEADER header : HEADER.values()) { 
//        	System.out.println("Getting Report: " + header + ": "+ enforcer.getValues(header) + ": "+  enforcer.isCompliant(header));
//            items.add(new ReportItem(header, enforcer.getValues(header), enforcer.isRequired(header), enforcer.isCompliant(header)));
//        } 		
//        return new Report("Report Name", items, rawHeaders);
//	}
	
	private Report getReport(PolicyEnforcer enforcer, Headers headers) {
        List<ReportItem> items = new ArrayList<>();
        
        
        
        Policy policy = PolicyHandler.createDefaultPolicy();
        
        
        
        for (Rule rule : policy.getRules()) { 
        	String header = rule.getName();
        	System.out.println("Getting Report: " + rule.getName() + ": "+ rule.isRequired() + ": "+  enforcer.isPresent(rule));
        	boolean present =  enforcer.isPresent(rule);
        	if ( enforcer.isPresent(rule) ) {
        		boolean isCompliant = true;
                items.add(new ReportItem(rule, header, headers.getValues(header),  present, isCompliant));
        	}
        	else
        	{
        		boolean isCompliant = false;
                items.add(new ReportItem(rule, header, null, present, isCompliant));
        	}
        }
 		
        return new Report("Report Name", items, headers.getRawHeaders());

	}
	
	private void writeJson(Policy rules) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(rules);

		try (FileWriter writer = new FileWriter("WebContent/json/staff.json")) {
			gson.toJson(rules, writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
