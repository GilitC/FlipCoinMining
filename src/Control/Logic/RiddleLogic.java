package Control.Logic;

import java.io.File;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.Node;

import org.joda.time.LocalDate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import Model.Riddle;
import Model.RiddleLevel;

import Model.Consts;

public class RiddleLogic {
	private static RiddleLogic _instance;

	private RiddleLogic() {
	}

	public static RiddleLogic getInstance() {
		if (_instance == null)
			_instance = new RiddleLogic();
		return _instance;
	}

	/**
	 * fetches all riddles from DB file.
	 * @return ArrayList of Riddles.
	 */
	public ArrayList<Riddle> getALLRiddles() {
		ArrayList<Riddle> results = new ArrayList<Riddle>();
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(Consts.SQL_SEL_RIDDLES);
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					results.add(new Riddle(rs.getInt(i++), rs.getDate(i++), rs.getDate(i++), 
							rs.getString(i++), rs.getDate(i++), rs.getString(i++), rs.getInt(i++)));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return results;
	}

	/**
	 * fetches all riddle levels from DB file.
	 * @return ArrayList of Riddles.
	 */
	public ArrayList<RiddleLevel> getALLRiddleLevels() {
		ArrayList<RiddleLevel> results = new ArrayList<RiddleLevel>();
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(Consts.SQL_SEL_RIDDLELEVELS);
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					results.add(new RiddleLevel(rs.getInt(i++), rs.getString(i++),
							rs.getInt(i++), rs.getInt(i++)));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return results;
	}

	/**
	 * Method gets a riddle id and possible solution and checks if they are a match
	 * return true if yes, else - return false
	 * @return 
	 */
	public Boolean checkIfSolutionMatchesRiddle(int riddleId, String result) {

		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(Consts.SQL_CHECKMATCH_RIDDLE_SOLUTIONS);
					) {
				stmt.setInt(1, riddleId);
				stmt.setString(2, result);
				
				ResultSet rs = stmt.executeQuery();
				rs.next();
				if (rs.getInt("count") > 0)
				{
					// There is a match
					return true;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Method checks if a riddle was solved using a counter
	 * returns how many people already solved it: 0 - not solved yet, any other number - solved
     * @return 
	 */
	public int howManytimesRiddlewasSolved(int riddleNumber) {
		int place=0;
			try {
				Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
				try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
						PreparedStatement stmt = conn.prepareStatement(Consts.SQL_CHECKIF_RIDDLE_WAS_SOLVED);
						) {
					stmt.setInt(1, riddleNumber);
					ResultSet rs = stmt.executeQuery();
					if(rs.next())
						return (rs.getInt("count"));
					return 0;
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		return place;
	}

	/*----------------------------------------- ADD / REMOVE / UPDATE RIDDLE METHODS --------------------------------------------*/

	/**
	 * Adding a new Riddle with the parameters received 
	 * return true if the insertion was successful, else - return false
	 * @return 
	 */
	public boolean addRiddle(String description, Date date, int riddleLevel) {
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					CallableStatement stmt = conn.prepareCall(Consts.SQL_ADD_RIDDLE)) {

				int i = 1;

				stmt.setString(i++, description); // can't be null
				stmt.setDate(i++, date);  //can't be null
				stmt.setString(i++, "Unsolved"); 
				stmt.setInt(i++, riddleLevel); // can't be null, admin can change riddle levels later


				stmt.executeUpdate();
				return true;

			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Adding a new solver to the riddle
	 * return true if the insertion was successful, else - return false
	 * @return 
	 */
	public boolean addSolverToRiddle(String uniqueAddress, int riddleNumber, int place) {
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					CallableStatement stmt = conn.prepareCall(Consts.SQL_ADD_SOLVER_TO_RIDDLE)) {

				int i = 1;
				stmt.setString(i++, uniqueAddress); // can't be null
				stmt.setInt(i++, riddleNumber); // can't be null
				stmt.setInt(i++, place); // can't be null
				
				stmt.executeUpdate();
				return true;

			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}



	/**
	 * Editing an existing riddle's level with the parameters received from the form.
	 * return true if the update was successful, else - return false
	 * @return 
	 */
	public boolean updateRiddleLevelID(int riddleLevel, int riddleNumber) {
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					CallableStatement stmt = conn.prepareCall(Consts.SQL_UPD_RIDDLE_LEVEL)) {
				int i = 1;

				stmt.setInt(i++, riddleLevel); // can't be null
				stmt.setInt(i++, riddleNumber); // can't be null

				stmt.executeUpdate();
				return true;

			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Editing an existing riddle's status with the parameters received from the form.
	 * return true if the update was successful, else - return false
	 * @return 
	 */
	public boolean updateRiddlestatus(String status, int riddleNumber) {
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					CallableStatement stmt = conn.prepareCall(Consts.SQL_UPD_RIDDLE_STATUS)) {
				int i = 1;

				stmt.setString(i++, status); // can't be null
				stmt.setInt(i++, riddleNumber); // can't be null

				stmt.executeUpdate();
				return true;

			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	/**
	 * Import transactions from XML
	 */
	public static void importRiddles() {
		try {
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
					.parse(new File("xml/importRiddles.xml"));
			doc.getDocumentElement().normalize();
			NodeList nl = doc.getElementsByTagName("Riddle");
			for (int i = 0; i < nl.getLength(); i++) {

				if (nl.item(i).getNodeType() == Node.ELEMENT_NODE) {
					Element el = (Element) nl.item(i);
					String description = el.getElementsByTagName("description").item(0).getTextContent();
					String solutionTime = el.getElementsByTagName("solutionTime").item(0).getTextContent();
					String riddleLevel = el.getElementsByTagName("riddleLevel").item(0).getTextContent();
					
					Long solutionTimeMS = Long.parseLong(solutionTime);
					
					Date d = new Date(solutionTimeMS);
					
				//	RiddleLogic.getInstance().addRiddle(description, d, Integer.parseInt(riddleLevel));
					int id = RiddleLogic.getInstance().returnLastRiddleNumAdded();
					if(id==0) {
						return;
					}
					
					NodeList solutions = el.getElementsByTagName("Solution");
					for(int j = 0 ; j < solutions.getLength(); j++)
					{
						Integer solutionNumber = 0;
						Integer result = 0;
						String solNumStr = solutions.item(j).getTextContent();
						System.out.println("RD: " + id + " J:"+j+" ::"+solNumStr);

//						SolutionLogic.getInstance().addSolution(id, solutionNumber, result);
					}
					System.out.println("Riddle: " + description + " has #"+solutions.getLength()+" answers.");
					
					
				}
			}
		} catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * method returns the id of last added riddle
	 */
	private int returnLastRiddleNumAdded() {
		int id =0;
			try {
				Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
				try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
						PreparedStatement stmt = conn.prepareStatement(Consts.SQL_LAST_RIDDLE_ID_ADDED);
						) {
		
					ResultSet rs = stmt.executeQuery();
					while(rs.next()) {
						return (rs.getInt(1));
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		
		return id;
	}

}
