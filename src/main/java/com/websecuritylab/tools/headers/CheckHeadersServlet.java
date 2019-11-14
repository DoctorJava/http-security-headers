package com.websecuritylab.tools.headers;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.websecuritylab.tools.headers.HeaderRules.HEADER_NAME;
import com.websecuritylab.tools.headers.constants.DoPostParams;
import com.websecuritylab.tools.headers.constants.ReqAttributes;
import com.websecuritylab.tools.headers.exceptions.InvalidUrlException;
import com.websecuritylab.tools.headers.exceptions.SiteNotFoundException;

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
 		        
		        String  content = "<h1>Headers</h1>";
		        if ( HeaderRules.hasHeader(headers, HEADER_NAME.CONTENT_TYPE)) content += "<p>Got Header: " +  HEADER_NAME.CONTENT_TYPE;
		        if ( HeaderRules.hasHeader(headers, HEADER_NAME.CACHE_CONTROL)) content += "<p>Got Header: " +  HEADER_NAME.CACHE_CONTROL;
		                
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(JSP_SHOW_REPORT);	

				req.setAttribute("report", content);
				req.setAttribute(ReqAttributes.RAW_HEADERS, handler.getRawHeaders());
				
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
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(JSP_SHOW_REPORT);	
        
		req.setAttribute(ReqAttributes.RAW_HEADERS, testHeaders);
		
		dispatcher.forward(req, res);

	}

}
