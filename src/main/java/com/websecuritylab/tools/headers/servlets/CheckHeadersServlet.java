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
import com.websecuritylab.tools.headers.HeaderRules;
import com.websecuritylab.tools.headers.UrlHandler;
import com.websecuritylab.tools.headers.HeaderRules.HEADER;
import com.websecuritylab.tools.headers.constants.DoPostParams;
import com.websecuritylab.tools.headers.constants.ReqAttributes;
import com.websecuritylab.tools.headers.exceptions.InvalidUrlException;
import com.websecuritylab.tools.headers.exceptions.SiteNotFoundException;
import com.websecuritylab.tools.headers.model.Report;
import com.websecuritylab.tools.headers.model.ReportItem;
import com.websecuritylab.tools.headers.model.RulesTest;

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

				Map<String, List<String>> headers = handler.getHeaderMap();
		        res.setContentType("text/plain;charset=UTF-8");
 		        
		        HeaderRules rules = new HeaderRules(headers);
		                
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(JSP_SHOW_REPORT);	

				req.setAttribute("report", getReport(rules, handler.getRawHeaders()));
				
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
		
        HeaderRules rules = new HeaderRules(testHeaders);
        
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(JSP_SHOW_REPORT);	
        
		req.setAttribute("report", getReport(rules,testHeaders));
		
		dispatcher.forward(req, res);

	}
	
	private Report getReport(HeaderRules rules, String rawHeaders) {
        List<ReportItem> items = new ArrayList<>();
        for (HEADER header : HEADER.values()) { 
        	System.out.println("Getting Report: " + header + ": "+ rules.getValues(header) + ": "+  rules.isCompliant(header));
            items.add(new ReportItem(header, rules.getValues(header), rules.isRequired(header), rules.isCompliant(header)));
        }
 		
        return new Report("Report Name", items, rawHeaders);

	}
	
	private void writeJson(RulesTest rules) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(rules);

		try (FileWriter writer = new FileWriter("WebContent/json/staff.json")) {
			gson.toJson(rules, writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
