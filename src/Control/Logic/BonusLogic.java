package Control.Logic;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import Model.Consts;

public class BonusLogic {
	private static BonusLogic _instance;

	private BonusLogic() {
	}

	public static BonusLogic getInstance() {
		if (_instance == null)
			_instance = new BonusLogic();
		return _instance;
	}

//	/**
//	 * fetches all lotteries from DB file.
//	 * @return ArrayList of lotteries.
//	 */
//	public ArrayList<Lottery> getALLLotteries() {
//		ArrayList<Lottery> results = new ArrayList<Lottery>();
//		try {
//			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
//			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
//					PreparedStatement stmt = conn.prepareStatement(Consts.SQL_SEL_LOTTERIES);
//					ResultSet rs = stmt.executeQuery()) {
//				while (rs.next()) {
//					int i = 1;
//					results.add(new Lottery(rs.getInt(i++), rs.getDate(i++), rs.getInt(i++), 
//							rs.getInt(i++),rs.getInt(i++)));
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		}
//		return results;
//	}
	
	/*----------------------------------------- ADD / REMOVE / UPDATE USER METHODS --------------------------------------------*/

	/**
	 * Adding a new Bonus with the parameters received from the form.
	 * return true if the insertion was successful, else - return false
     * @return 
	 */
	public boolean addBonus(String description) {
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					CallableStatement stmt = conn.prepareCall(Consts.SQL_ADD_BONUS)) {
				
				int i = 1;
				stmt.setString(i++, description); // can't be null
				
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
