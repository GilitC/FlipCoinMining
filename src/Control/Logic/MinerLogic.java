package Control.Logic;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import Model.Miner;
import Model.Consts;

public class MinerLogic {
	private static MinerLogic _instance;

	private MinerLogic() {
	}

	public static MinerLogic getInstance() {
		if (_instance == null)
			_instance = new MinerLogic();
		return _instance;
	}

	/**
	 * fetches all users from DB file.
	 * @return ArrayList of miners.
	 */
	public ArrayList<Miner> getALLMiners() {
		ArrayList<Miner> results = new ArrayList<Miner>();
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(Consts.SQL_SEL_MINERS);
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					results.add(new Miner(rs.getString(i++), rs.getString(i++), rs.getString(i++), 
							rs.getDouble(i++),rs.getString(i++)));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return results;
	}
	
	/*----------------------------------------- ADD / REMOVE / UPDATE USER METHODS --------------------------------------------*/

	/**
	 * Adding a new Miner with the parameters received from the form.
	 * return true if the insertion was successful, else - return false
     * @return 
	 */
	public boolean addMiner(String uniqueAddress, String name, String password, double digitalProfit,
	String email) {
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					CallableStatement stmt = conn.prepareCall(Consts.SQL_INS_MINER)) {
				
				int i = 1;
				stmt.setString(i++, uniqueAddress); // can't be null
				stmt.setString(i++, name); // can't be null
				stmt.setString(i++, password); // can't be null
				stmt.setDouble(i++, digitalProfit); // can't be null
				if (email != null)
					stmt.setString(i++, email);
				else
					stmt.setNull(i++, java.sql.Types.VARCHAR);
				
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
	 * Delete the selected Miner in form.
	 * Return true if the deletion was successful, else - return false
	 * @param userID 
     * @return boolean - if the user was removed from the DB
	 */
	public boolean removeMiner(String uniqueAddress) {
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					CallableStatement stmt = conn.prepareCall(Consts.SQL_DEL_MINER)) {
				
				stmt.setString(1, uniqueAddress);
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
	 * Editing a exist miner with the parameters received from the form.
	 * return true if the update was successful, else - return false
     * @return 
	 */
	public boolean editMiner(String uniqueAddress, String name, String password, double digitalProfit,
			String email) {
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					CallableStatement stmt = conn.prepareCall(Consts.SQL_UPD_MINER)) {
				int i = 1;


				stmt.setString(i++, uniqueAddress); // can't be null
				stmt.setString(i++, name); // can't be null
				stmt.setString(i++, password); // can't be null
				stmt.setDouble(i++, digitalProfit); // can't be null
				if (email != null)
					stmt.setString(i++, email);
				else
					stmt.setNull(i++, java.sql.Types.VARCHAR);
				
				
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
	 * Editing a exist miner with the parameters received from the form.
	 * return true if the update was successful, else - return false
     * @return 
	 */
	public boolean updateMinerDigitalProfit(double digitalProfit, String uniqueAddress) {
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					CallableStatement stmt = conn.prepareCall(Consts.SQL_UPD_MINER_PROFIT)) {
				int i = 1;

				stmt.setDouble(i++, digitalProfit); // can't be null
				stmt.setString(i++, uniqueAddress); // can't be null
				
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
