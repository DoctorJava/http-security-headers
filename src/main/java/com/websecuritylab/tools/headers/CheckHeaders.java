package com.websecuritylab.tools.headers;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.websecuritylab.tools.headers.constants.DoPostParams;
import com.websecuritylab.tools.headers.constants.ReqAttributes;

/**
 * Servlet implementation class CheckHeaders
 */
public class CheckHeaders extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String JSP_SHOW_REPORT = "/WEB-INF/jsp/showReport.jsp";     

    public CheckHeaders() {
        super();
        // TODO Auto-generated constructor stub
    }


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		try { 
			String testUrl = req.getParameter(DoPostParams.TEST_URL);
			
			new UrlHandler().getHeaders(testUrl);
	        res.setContentType("text/plain;charset=UTF-8");
	        	        
	        String content = new WebPageReader().setWebPageName(testUrl).getWebPageContent();
	                
	        ServletOutputStream os = res.getOutputStream();
	        os.write(content.getBytes(StandardCharsets.UTF_8));
	        //req.setAttribute(ReqAttributes.SOME_RESULT, "Bingo");
			//RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(JSP_SHOW_REPORT);	

			//dispatcher.forward(req, res);
		} catch(Exception e) {
			System.out.println("Error Dispatching:" + e);
		}
	}

}
