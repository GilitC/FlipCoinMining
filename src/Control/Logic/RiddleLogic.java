package Control.Logic;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

	/*----------------------------------------- ADD / REMOVE / UPDATE USER METHODS --------------------------------------------*/

	/**
	 * Adding a new Riddle with the parameters received from the form.
	 * return true if the insertion was successful, else - return false
	 * @return 
	 */
	public boolean addRiddle(Date publishTime, Date publishDate, String description, Date solutionTime,
			String status, int riddleLevel) {
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					CallableStatement stmt = conn.prepareCall(Consts.SQL_ADD_RIDDLE)) {

				int i = 1;
				stmt.setDate(i++, publishTime); // can't be null
				stmt.setDate(i++, publishDate); // can't be null
				stmt.setString(i++, description); // can't be null
				stmt.setDate(i++, solutionTime); // can't be null
				stmt.setString(i++, status); // can't be null
				stmt.setInt(i++, riddleLevel); // can't be null


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
}
