package org.lccgymnastics.ezscore.api;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.LogManager;

import javax.persistence.EntityManager;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.lccgymnastics.ezscore.model.Competitor;
import org.lccgymnastics.ezscore.model.Globals;
import org.lccgymnastics.ezscore.model.MSConstants.Category;
import org.lccgymnastics.ezscore.model.MSConstants.EventType;
import org.lccgymnastics.ezscore.model.Meet;

/**
 * Servlet implementation class EZScoreServlet
 */
@WebServlet("/ezscore")
public class EZScoreServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserTransaction _ut;


    /**
     * Default constructor. 
     */
    public EZScoreServlet() {}

    @Override
    public void init(ServletConfig config) throws ServletException {
    	_ut = new UserTransactionImpl();
    	super.init(config);
    }
    
    @Override
    public void destroy() {
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String act = request.getParameter("action");
		if ("create".equals(act)) {
			response.getWriter().write(createMeet());
		} else if ("getInitParameters".equals(act)) {
			response.getWriter().write(getInitParameters(request));
		} else if ("list".equals(act)) {
			response.getWriter().write(listMeets());
		} else if ("getMeet".equals(act)) {
			int meetId = Integer.parseInt(request.getParameter("meetID"));
			response.getWriter().write(showMeet(meetId));
		} else if ("newMeet".equals(act)) {
			String desc = request.getParameter("description");
			String date = request.getParameter("date");
			String teams = request.getParameter("teams");
			response.getWriter().write(newMeet(desc, date, teams));
		} else if ("deleteMeet".equals(act)) {
			int meetId = Integer.parseInt(request.getParameter("meetID"));
			response.getWriter().write(deleteMeet(meetId));
		} else if ("addCompetitor".equals(act)) {
			int meetId = Integer.parseInt(request.getParameter("meetID"));
			String name = request.getParameter("name");
			String number = request.getParameter("number");
			String team = request.getParameter("team");
			String category = request.getParameter("category");
			response.getWriter().write(addCompetitor(meetId,name,number,team,category));
		} else if ("updateCompetitor".equals(act)) {
			int meetId = Integer.parseInt(request.getParameter("meetID"));
			String name = request.getParameter("name");
			String number = request.getParameter("number");
			String team = request.getParameter("team");
			String category = request.getParameter("category");
			response.getWriter().write(updateCompetitor(meetId,number,name,team,category));
		} else if ("deleteCompetitor".equals(act)) {
			int meetId = Integer.parseInt(request.getParameter("meetID"));
			String number = request.getParameter("number");
			response.getWriter().write(deleteCompetitor(meetId,number));
		} else if ("updateScore".equals(act)) {
			int meetId = Integer.parseInt(request.getParameter("meetID"));
			String competitorNumber = request.getParameter("competitorNumber");
			String event = request.getParameter("event");
			String score = request.getParameter("score");
			response.getWriter().write(updateScore(meetId, competitorNumber, event, score));
		} else if ("individualResults".equals(act)) {
			int meetId = Integer.parseInt(request.getParameter("meetID"));
			response.getWriter().write(individualResults(meetId));
		} else if ("teamResults".equals(act)) {
			int meetId = Integer.parseInt(request.getParameter("meetID"));
			response.getWriter().write(teamResults(meetId));
		} else {
			response.getWriter().write("<html><body><p>No action</body></html>");
		}
	}	
	private String getInitParameters(HttpServletRequest req) throws ServletException {
		ServletContext context = req.getServletContext();
    	Enumeration<String> names = context.getInitParameterNames();
    	Map<String,String> initParams = new HashMap<String,String>();
		while (names.hasMoreElements()) {
			String name = names.nextElement();
			String value = context.getInitParameter(name);
			initParams.put(name, value);
		}
		return Globals.GSON.toJson(initParams);
	}
	private String individualResults(int meetId) throws ServletException {
		try {
			_ut.start();
			EntityManager em = _ut.getEntityManager();
			Meet meet = Meet.getMeet(em, meetId);
			return meet.getIndividualResults().toJSON();
		} catch (Exception e) {
			LogManager.getLogManager().getLogger("").log(Level.SEVERE, "Caught exception", e);
			throw new ServletException(e);
		} finally {
			_ut.close();
		}
	}

	private String teamResults(int meetId) throws ServletException {
		try {
			_ut.start();
			EntityManager em = _ut.getEntityManager();
			Meet meet = Meet.getMeet(em, meetId);
			return meet.getTeamResults().toJSON();
		} catch (Exception e) {
			LogManager.getLogManager().getLogger("").log(Level.SEVERE, "Caught exception", e);
			throw new ServletException(e);
		} finally {
			_ut.close();
		}
	}

	private String updateScore(int meetId, String competitorNumber, String event, String score) throws ServletException {
		try {
			EventType e = EventType.valueOf(event);
			Double s = score.isEmpty() ? null : Double.parseDouble(score);
			_ut.start();
			EntityManager em = _ut.getEntityManager();
			Meet meet = Meet.getMeet(em, meetId);
			Competitor c = meet.getCompetitor(competitorNumber);
			c.updateScore(e, s);
			em.persist(meet);
			_ut.commit();
			return c.toJSON();
		} catch (Exception e) {
			LogManager.getLogManager().getLogger("").log(Level.SEVERE, "Caught exception", e);
			throw new ServletException(e);
		} finally {
			_ut.close();
		}
	}
	private String addCompetitor(int meetId, String name, String number, String team, String category) throws ServletException {
		try {
			Category c = Category.valueOf(category);
			_ut.start();
			EntityManager em = _ut.getEntityManager();
			Meet meet = Meet.getMeet(em, meetId);
			meet.addCompetitor(new Competitor(number, name, team, c));
			em.persist(meet);
			_ut.commit();
			return meet.toJSON();
		} catch (Exception e) {
			throw new ServletException(e);
		} finally {
			_ut.close();
		}
	}
	private String updateCompetitor(int meetId, String number, String name, String team, String category) throws ServletException {
		try {
			Category c = Category.valueOf(category);
			_ut.start();
			EntityManager em = _ut.getEntityManager();
			Meet meet = Meet.getMeet(em, meetId);
			meet.updateCompetitor(number, name, team, c);
			em.persist(meet);
			_ut.commit();
			return meet.toJSON();
		} catch (Exception e) {
			throw new ServletException(e);
		} finally {
			_ut.close();
		}
	}
	private String deleteCompetitor(int meetId, String number) throws ServletException {
		try {
			_ut.start();
			EntityManager em = _ut.getEntityManager();
			Meet meet = Meet.getMeet(em, meetId);
			meet.deleteCompetitor(number);
			em.persist(meet);
			_ut.commit();
			return meet.toJSON();
		} catch (Exception e) {
			throw new ServletException(e);
		} finally {
			_ut.close();
		}
	}
	private String newMeet(String desc, String dateStr, String teams) throws ServletException {
		try {
			Date date = DateFormat.getDateInstance(DateFormat.SHORT).parse(dateStr);
			_ut.start();
			EntityManager em = _ut.getEntityManager();
			Meet meet = new Meet(desc,date,teams.split(","));
			em.persist(meet);
			_ut.commit();
			return meet.toJSON();
		} catch (Exception e) {
			throw new ServletException(e);
		} finally {
			_ut.close();
		}
	}
	private String deleteMeet(int meetId) throws ServletException {
		try {
			_ut.start();
			EntityManager em = _ut.getEntityManager();
			Meet meet = Meet.getMeet(em, meetId);
			em.remove(meet);
			_ut.commit();
			List<Meet> meets = Meet.getAllMeets(em);
			return Globals.GSON.toJson(meets);
		} catch (Exception e) {
			throw new ServletException(e);
		} finally {
			_ut.close();
		}
	}
	private String showMeet(int meetId) throws ServletException {
		try {
			_ut.start();
			EntityManager em = _ut.getEntityManager();
			Meet meet = Meet.getMeet(em, meetId);
			for (Competitor c : meet.getCompetitors()) {
				c.updateAAScore();
			}
			return meet.toJSON();
		} catch (Exception e) {
			throw new ServletException(e);
		} finally {
			_ut.close();
		}
	}
	
	private String listMeets() throws ServletException {
		try {
			_ut.start();
			EntityManager em = _ut.getEntityManager();
			List<Meet> meets = Meet.getAllMeets(em);
			return Globals.GSON.toJson(meets);
		} catch (Exception e) {
			throw new ServletException(e);
		} finally {
			_ut.close();
		}
	}
	private String createMeet() throws ServletException {
		try {
			_ut.start();
			EntityManager em = _ut.getEntityManager();
			Meet m = new Meet("Test meet", new Date(), new String[] {"LCC", "NYY"});
			m.addCompetitor(new Competitor("123", "Bugs Bunny", "LCC", Category.VO));
			m.addCompetitor(new Competitor("124", "Daffy Duck", "LCC", Category.VO));
			m.addCompetitor(new Competitor("125", "Porky Pig", "LCC", Category.VO));
			m.addCompetitor(new Competitor("126", "Roger Rabbit", "LCC", Category.VC));
			m.addCompetitor(new Competitor("127", "Mickey Mouse", "LCC", Category.VC));
			m.addCompetitor(new Competitor("128", "Donald Duck", "LCC", Category.VC));
			m.addCompetitor(new Competitor("129", "Spongebob Squarepants", "LCC", Category.VC));
			m.addCompetitor(new Competitor("130", "Patrick Star", "LCC", Category.VC));
			m.addCompetitor(new Competitor("131", "Squidward Tentacles", "LCC", Category.VC));
	
			m.addCompetitor(new Competitor("200", "Derek Jeter", "NYY", Category.VO));
			m.addCompetitor(new Competitor("201", "Mark Teixiera", "NYY", Category.VO));
			m.addCompetitor(new Competitor("202", "Brett Gardner", "NYY", Category.VO));
			m.addCompetitor(new Competitor("203", "Ichiro Suzuki", "NYY", Category.VC));
			m.addCompetitor(new Competitor("204", "Carlos Beltran", "NYY", Category.VC));
			m.addCompetitor(new Competitor("205", "Jacoby Ellsbury", "NYY", Category.VC));
			m.addCompetitor(new Competitor("206", "Kelly Johnson", "NYY", Category.VC));
			m.addCompetitor(new Competitor("207", "Brian Roberts", "NYY", Category.VC));
			m.addCompetitor(new Competitor("208", "CC Sabathia", "NYY", Category.VC));
	
			m.getCompetitor("123").updateScore(EventType.VAULT, 9.1);
			m.getCompetitor("124").updateScore(EventType.VAULT, 9.075);
			m.getCompetitor("125").updateScore(EventType.VAULT, 8.55);
			m.getCompetitor("126").updateScore(EventType.VAULT, 8.05);
			m.getCompetitor("127").updateScore(EventType.VAULT, 8.1);
			m.getCompetitor("128").updateScore(EventType.VAULT, 8.225);
	
			m.getCompetitor("123").updateScore(EventType.BARS, 9.1);
			m.getCompetitor("124").updateScore(EventType.BARS, 9.075);
			m.getCompetitor("125").updateScore(EventType.BARS, 8.55);
			m.getCompetitor("126").updateScore(EventType.BARS, 8.05);
			m.getCompetitor("129").updateScore(EventType.BARS, 8.1);
			m.getCompetitor("130").updateScore(EventType.BARS, 8.5);
			m.getCompetitor("131").updateScore(EventType.BARS, 8.225);
	
			m.getCompetitor("123").updateScore(EventType.BEAM, 9.5);
			m.getCompetitor("124").updateScore(EventType.BEAM, 9.275);
			m.getCompetitor("125").updateScore(EventType.BEAM, 8.25);
			m.getCompetitor("126").updateScore(EventType.BEAM, 8.15);
			m.getCompetitor("129").updateScore(EventType.BEAM, 8.0);
	
			m.getCompetitor("123").updateScore(EventType.FLOOR, 9.4);
			m.getCompetitor("124").updateScore(EventType.FLOOR, 9.6);
			m.getCompetitor("126").updateScore(EventType.FLOOR, 9.35);
			m.getCompetitor("129").updateScore(EventType.FLOOR, 8.8);
			m.getCompetitor("130").updateScore(EventType.FLOOR, 8.225);
			m.getCompetitor("131").updateScore(EventType.FLOOR, 9.225);
			
			m.getCompetitor("200").updateScore(EventType.VAULT, 8.1);
			m.getCompetitor("201").updateScore(EventType.VAULT, 9.375);
			m.getCompetitor("202").updateScore(EventType.VAULT, 8.8);
			m.getCompetitor("203").updateScore(EventType.VAULT, 9.3);
			m.getCompetitor("204").updateScore(EventType.VAULT, 9.1);
			m.getCompetitor("205").updateScore(EventType.VAULT, 9.225);
			m.getCompetitor("206").updateScore(EventType.VAULT, 9.5);
			m.getCompetitor("207").updateScore(EventType.VAULT, 9.6);
			m.getCompetitor("208").updateScore(EventType.VAULT, 9.8);
	
			m.getCompetitor("200").updateScore(EventType.BARS, 8.1);
			m.getCompetitor("201").updateScore(EventType.BARS, 7.275);
			m.getCompetitor("202").updateScore(EventType.BARS, 8.8);
			m.getCompetitor("203").updateScore(EventType.BARS, 9.3);
			m.getCompetitor("204").updateScore(EventType.BARS, 9.1);
			m.getCompetitor("205").updateScore(EventType.BARS, 9.0);
			m.getCompetitor("206").updateScore(EventType.BARS, 9.4);
			m.getCompetitor("207").updateScore(EventType.BARS, 9.125);
			m.getCompetitor("208").updateScore(EventType.BARS, 9.8);
	
			m.getCompetitor("200").updateScore(EventType.BEAM, 6.1);
			m.getCompetitor("201").updateScore(EventType.BEAM, 7.275);
			m.getCompetitor("202").updateScore(EventType.BEAM, 9.2);
			m.getCompetitor("203").updateScore(EventType.BEAM, 9.125);
			m.getCompetitor("204").updateScore(EventType.BEAM, 8.875);
			m.getCompetitor("205").updateScore(EventType.BEAM, 9.1);
			m.getCompetitor("206").updateScore(EventType.BEAM, 9.05);
			m.getCompetitor("207").updateScore(EventType.BEAM, 9.075);
			m.getCompetitor("208").updateScore(EventType.BEAM, 9.1);
	
			m.getCompetitor("200").updateScore(EventType.FLOOR, 9.6);
			m.getCompetitor("201").updateScore(EventType.FLOOR, 7.275);
			m.getCompetitor("202").updateScore(EventType.FLOOR, 6.2);
			m.getCompetitor("203").updateScore(EventType.FLOOR, 8.125);
			m.getCompetitor("204").updateScore(EventType.FLOOR, 9.875);
			m.getCompetitor("205").updateScore(EventType.FLOOR, 9.5);
			m.getCompetitor("206").updateScore(EventType.FLOOR, 9.125);
			m.getCompetitor("207").updateScore(EventType.FLOOR, 9.375);
			m.getCompetitor("208").updateScore(EventType.FLOOR, 9.175);
	
			em.persist(m);
			_ut.commit();
			return "<html><body>Created Meet: "+m.getID()+"</body></html>";
		} catch (Exception e) {
			throw new ServletException(e);
		} finally {
			_ut.close();
		}

	}
}
