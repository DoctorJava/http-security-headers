package com.websecuritylab.tools.headers.servlets;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.websecuritylab.tools.headers.model.Rule;
import com.websecuritylab.tools.headers.model.RulesTest;

/**
 * Servlet implementation class MaintainRules
 */
public class MaintainRulesServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger( CheckHeadersServlet.class );  
	private static final long serialVersionUID = 1L;
	private static final String JSP_SHOW_RULES = "/WEB-INF/jsp/showRules.jsp";     
	private static final String JSON_READ_RULE  = "/opt/apps/data/json/activeTest.json";     
	private static final String JSON_WRITE_RULE = "/opt/apps/data/json/savedTest.json";     
       
    public MaintainRulesServlet() {
        super();
    }

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(JSP_SHOW_RULES);

		Gson gson = new Gson();

		try (Reader reader = new FileReader(JSON_READ_RULE)) {
			RulesTest test = gson.fromJson(reader, RulesTest.class);
			//List<Rule> rules = new ArrayList<>();
			//rules.add(new Rule("Bingo Rule", true));

			//RulesTest test = new RulesTest("Some Test", rules);

			req.setAttribute("test", test);

		} catch (IOException e) {
			e.printStackTrace();
		}

		dispatcher.forward(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(JSP_SHOW_RULES);

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
        
        List<Rule> rules = new ArrayList<>();
        rules.add(new Rule("Some Rule", true));
        rules.add(new Rule("Nother Rule", false));
        rules.add(new Rule("Nother Nother Rule", true));

        RulesTest test = new RulesTest("Some Test", rules);
        
        try (FileWriter writer = new FileWriter(JSON_WRITE_RULE)) {
        	System.out.println("Writing JSON:" + writer);
            gson.toJson(test, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
 
	}

}
