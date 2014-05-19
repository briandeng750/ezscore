package org.lccgymnastics.ezscore.api;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

import org.apache.commons.lang3.StringUtils;
import org.lccgymnastics.ezscore.model.Competitor;
import org.lccgymnastics.ezscore.model.Globals;
import org.lccgymnastics.ezscore.model.IndividualResults;
import org.lccgymnastics.ezscore.model.MSConstants.Category;
import org.lccgymnastics.ezscore.model.MSConstants.EventType;
import org.lccgymnastics.ezscore.model.Meet;
import org.lccgymnastics.ezscore.model.PrintResult;
import org.lccgymnastics.ezscore.model.Team;
import org.lccgymnastics.ezscore.model.TeamMember;

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
		try {
			String act = request.getParameter("action");
			if ("logout".equals(act)) {
				request.logout();
				response.setContentType("text/html");
				PrintWriter w = response.getWriter(); 
				w.write("<html><body><p>You have been logged out of EZScore. Goodbye!<p><a href=\"index.html\">Click here to login again</a></body></html>");
				w.close();
			} else if ("addTeam".equals(act)) {
				String name = request.getParameter("teamName");
				response.getWriter().write(addTeam(name));
			} else if ("deleteTeam".equals(act)) {
				String name = request.getParameter("teamName");
				response.getWriter().write(deleteTeam(name));
			} else if ("getTeams".equals(act)) {
				response.getWriter().write(getTeams());
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
				String homeTeam = request.getParameter("homeTeam");
				response.getWriter().write(newMeet(desc, date, teams, homeTeam));
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
				int minOptionalScores = Integer.parseInt(request.getParameter("minOptionalScores"));
				response.getWriter().write(teamResults(meetId, minOptionalScores));
			} else if ("printResults".equals(act)) {
				int meetId = Integer.parseInt(request.getParameter("meetID"));
				int minOptionalScores = Integer.parseInt(request.getParameter("minOptionalScores"));
				String page = request.getParameter("page");
				response.getWriter().write(printResults(meetId, minOptionalScores, page));
			} else if ("getTeamMembers".equals(act)) {
				String teamName = request.getParameter("teamName");
				response.getWriter().write(getTeamMembers(teamName));
			} else if ("addTeamMember".equals(act)) {
				String teamName = request.getParameter("teamName");
				String name = request.getParameter("name");
				String number = request.getParameter("number");
				String category = request.getParameter("category");
				response.getWriter().write(addTeamMember(teamName, number, name, category));
			} else if ("updateTeamMember".equals(act)) {
				String teamName = request.getParameter("teamName");
				String name = request.getParameter("name");
				String number = request.getParameter("number");
				String category = request.getParameter("category");
				response.getWriter().write(updateTeamMember(teamName,number,name,category));
			} else if ("deleteTeamMember".equals(act)) {
				String teamName = request.getParameter("teamName");
				String number = request.getParameter("number");
				response.getWriter().write(deleteTeamMember(teamName,number));
			} else if ("importTeamMembers".equals(act)) {
				String teamName = request.getParameter("teamName");
				String text = request.getParameter("text");
				response.getWriter().write(importTeamMembers(teamName,text));
			} else {
				response.getWriter().write("{ message: 'no action specified'}");
			}
		} catch (ServletException e) {
	        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	        response.getWriter().write(e.getRootCause().getMessage());
	        response.flushBuffer();				
		}
	}	
	private String addTeamMember(String teamName, String number, String name, String category) throws ServletException {
		try {
			_ut.start();
			EntityManager em = _ut.getEntityManager();
			TeamMember newComp = new TeamMember(number, name, Category.valueOf(category));
			Team team = Team.getTeam(em, teamName);
			team.addMember(newComp);
			em.persist(team);
			_ut.commit();
			return getTeamMembers(teamName);
		} catch (Exception e) {
			LogManager.getLogManager().getLogger("").log(Level.SEVERE, "Caught exception", e);
			throw new ServletException(e);
		} finally {
			_ut.close();
		}
	}

	private String updateTeamMember(String teamName, String number, String name, String category) throws ServletException {
		try {
			_ut.start();
			EntityManager em = _ut.getEntityManager();
			Team team = Team.getTeam(em, teamName);
			TeamMember editComp = team.getMember(number);
			if (editComp!=null) {
				editComp.setName(name);
				editComp.setCategory(Category.valueOf(category));
			}
			_ut.commit();
			return getTeamMembers(teamName);
		} catch (Exception e) {
			LogManager.getLogManager().getLogger("").log(Level.SEVERE, "Caught exception", e);
			throw new ServletException(e);
		} finally {
			_ut.close();
		}
	}
	
	private String deleteTeamMember(String teamName, String number) throws ServletException {
		try {
			_ut.start();
			EntityManager em = _ut.getEntityManager();
			Team team = Team.getTeam(em,teamName);
			TeamMember removeMember = team.removeMember(number);
			if (removeMember != null) {
				em.remove(removeMember);
				_ut.commit();
			} else {
				throw new IllegalArgumentException("Cannot find home team competitor by number: "+number);
			}
			return getTeamMembers(teamName);
		} catch (Exception e) {
			LogManager.getLogManager().getLogger("").log(Level.SEVERE, "Caught exception", e);
			throw new ServletException(e);
		} finally {
			_ut.close();
		}
	}
	
	private String importTeamMembers(String teamName, String text) throws ServletException {
		try {
			_ut.start();
			EntityManager em = _ut.getEntityManager();
			Team team = Team.getTeam(em, teamName);
			for (String line: text.split("\\n")) {
				processTeamMemberLine(em, team, line);
			}			
			em.persist(team);
			_ut.commit();
			return getTeamMembers(teamName);
		} catch (Exception e) {
			LogManager.getLogManager().getLogger("").log(Level.SEVERE, "Caught exception", e);
			throw new ServletException(e);
		} finally {
			_ut.close();
		}
	}
	
	private void processTeamMemberLine(EntityManager em, Team team, String line) {
		String[] tokens = line.split("\\s+");
		if (!StringUtils.isNumeric(tokens[0])) {
			LogManager.getLogManager().getLogger("").log(Level.WARNING, "import team member, cannot process line: " + line);
			return;
		}
		String number = tokens[0];
		Category category;
		try {
			category = Category.valueOf(tokens[tokens.length-1]);
		} catch (IllegalArgumentException e) {
			LogManager.getLogManager().getLogger("").log(Level.WARNING, "import team member, cannot process line: " + line);
			return;
		}
		String name = "";
		for (int i=1; i<tokens.length-1; i++) {
			if (name.length()>0) name += " ";
			name += tokens[i];
		}
		TeamMember member = new TeamMember(number, name, category);
		team.addMember(member);
		LogManager.getLogManager().getLogger("").log(Level.FINE, "imported team member: " + member.toString());
	}

	private String getTeamMembers(String teamName) throws ServletException {
		try {
			_ut.start();
			EntityManager em = _ut.getEntityManager();
			Team team = Team.getTeam(em, teamName);
			List<TeamMember> allCompetitors = team.getMembers();
			Collections.sort(allCompetitors, new Comparator<TeamMember>() {
				@Override
				public int compare(TeamMember o1, TeamMember o2) {
					return o1.getNumber().compareTo(o2.getNumber());
				}
			});
			return Globals.GSON.toJson(allCompetitors);
		} catch (Exception e) {
			LogManager.getLogManager().getLogger("").log(Level.SEVERE, "Caught exception", e);
			throw new ServletException(e);
		} finally {
			_ut.close();
		}
	}

	private String getInitParameters(HttpServletRequest req) throws ServletException {
		try {
			_ut.start();
			EntityManager em = _ut.getEntityManager();
			List<String> teams = Team.getTeams(em);
	    	Map<String,String> initParams = new HashMap<String,String>();
			ServletContext context = req.getServletContext();
	    	initParams.put("HOMETEAM", context.getInitParameter("HOMETEAM"));
			if (teams.isEmpty()) {
				Team t;
		    	String teamList = context.getInitParameter("TEAMS");
		    	if (teamList!=null && teamList.length()>0) {
		    		String[] teamAry = teamList.split(",");
		    		for (int i=0; i<teamAry.length; i++) {
		    			t = new Team(teamAry[i]);
		    			em.persist(t);
		    		}
		    		_ut.commit();
		    	}
		    	initParams.put("TEAMS", teamList);
			} else {
				initParams.put("TEAMS", StringUtils.join(teams,","));
			}
			return Globals.GSON.toJson(initParams);
		} catch (Exception e) {
			LogManager.getLogManager().getLogger("").log(Level.SEVERE, "Caught exception", e);
			throw new ServletException(e);
		} finally {
			_ut.close();
		}
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

	private String teamResults(int meetId, int minOptionalScores) throws ServletException {
		try {
			_ut.start();
			EntityManager em = _ut.getEntityManager();
			Meet meet = Meet.getMeet(em, meetId);
			return meet.getTeamResults(minOptionalScores).toJSON();
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
	private String newMeet(String desc, String dateStr, String teams, String homeTeam) throws ServletException {
		try {
			Date date = DateFormat.getDateInstance(DateFormat.SHORT).parse(dateStr);
			_ut.start();
			EntityManager em = _ut.getEntityManager();
			Meet meet = new Meet(desc,date,teams.split(","));
			Set<String> theTeams = meet.getTeams();
			for (String theTeam : theTeams) {
				Team t = Team.getTeam(em, theTeam);
				meet.loadTeamMembers(t);
			}
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
	
	private String printResults(int meetId, int minOptionalScores, String page) throws ServletException {
		try {
			_ut.start();
			EntityManager em = _ut.getEntityManager();
			Meet m = Meet.getMeet(em, meetId);
			String header = m.getDescription() + " ("+m.getDate()+") - ";
			Category cat;
			EventType evt;
			if ("jvteam".equals(page)) {
				header = "Junior Varsity Team Results";
				PrintResult pr = new PrintResult(Category.JV, m.getTeamResults(minOptionalScores), header);
				return pr.toJSON();
			} else if ("varsityteam".equals(page)) {
				header = "Varsity Team Results";
				PrintResult pr = new PrintResult(Category.VARSITY, m.getTeamResults(minOptionalScores), header);
				return pr.toJSON();
			} else {
				if ("jvVault".equals(page)) {
					cat = Category.JV;
					evt = EventType.VAULT;
					header += "Junior Varsity Vault";
				} else if ("jvBars".equals(page)) {
					cat = Category.JV;
					evt = EventType.BARS;
					header += "Junior Varsity Bars";
				} else if ("jvBeam".equals(page)) {
					cat = Category.JV;
					evt = EventType.BEAM;
					header += "Junior Varsity Beam";
				} else if ("jvFloor".equals(page)) {
					cat = Category.JV;
					evt = EventType.FLOOR;
					header += "Junior Varsity Floor";
				} else if ("jvAA".equals(page)) {
					cat = Category.JV;
					evt = EventType.ALLAROUND;
					header += "Junior Varsity All-Around";
				} else if ("vcVault".equals(page)) {
					cat = Category.VC;
					evt = EventType.VAULT;
					header += "Varsity Compulsory Vault";
				} else if ("vcBars".equals(page)) {
					cat = Category.VC;
					evt = EventType.BARS;
					header += "Varsity Compulsory Bars";
				} else if ("vcBeam".equals(page)) {
					cat = Category.VC;
					evt = EventType.BEAM;
					header += "Varsity Compulsory Beam";
				} else if ("vcFloor".equals(page)) {
					cat = Category.VC;
					evt = EventType.FLOOR;
					header += "Varsity Compulsory Floor";
				} else if ("vcAA".equals(page)) {
					cat = Category.VC;
					evt = EventType.ALLAROUND;
					header += "Varsity Compulsory All-Around";
				} else if ("voVault".equals(page)) {
					cat = Category.VO;
					evt = EventType.VAULT;
					header += "Varsity Optional Vault";
				} else if ("voBars".equals(page)) {
					cat = Category.VO;
					evt = EventType.BARS;
					header += "Varsity Optional Bars";
				} else if ("voBeam".equals(page)) {
					cat = Category.VO;
					evt = EventType.BEAM;
					header += "Varsity Optional Beam";
				} else if ("voFloor".equals(page)) {
					cat = Category.VO;
					evt = EventType.FLOOR;
					header += "Varsity Optional Floor";
				} else if ("voAA".equals(page)) {
					cat = Category.VO;
					evt = EventType.ALLAROUND;
					header += "Varsity Optional All-Around";
				} else {
					throw new Exception("Invalid printResults page! "+page);
				}
				IndividualResults ir = m.getIndividualResults();
				PrintResult pr = new PrintResult(ir.getResults(cat,evt), header);
				return pr.toJSON();
			}
		} catch (Exception e) {
			throw new ServletException(e);
		} finally {
			_ut.close();
		}		
	}
	
	private String getTeams() throws ServletException {
		try {
			_ut.start();
			EntityManager em = _ut.getEntityManager();
			List<String> teams = Team.getTeams(em);
			return Globals.GSON.toJson(teams);
		} catch (Exception e) {
			throw new ServletException(e);
		} finally {
			_ut.close();
		}
	}
	
	private String addTeam(String name) throws ServletException {
		try {
			_ut.start();
			EntityManager em = _ut.getEntityManager();
			Team team = new Team(name);
			em.persist(team);
			_ut.commit();
			return getTeams();
		} catch (Exception e) {
			throw new ServletException(e);
		} finally {
			_ut.close();
		}
	}

	private String deleteTeam(String name) throws ServletException {
		try {
			_ut.start();
			EntityManager em = _ut.getEntityManager();
			Team team = Team.getTeam(em,name);
			if (team!=null) {
				em.remove(team);
				_ut.commit();
			}
			return getTeams();
		} catch (Exception e) {
			throw new ServletException(e);
		} finally {
			_ut.close();
		}
	}
}
