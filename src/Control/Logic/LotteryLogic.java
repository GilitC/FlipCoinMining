package Control.Logic;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import Model.Consts;
import Model.Lottery;

public class LotteryLogic {
	private static LotteryLogic _instance;

	private LotteryLogic() {
	}

	public static LotteryLogic getInstance() {
		if (_instance == null)
			_instance = new LotteryLogic();
		return _instance;
	}

	/**
	 * fetches all lotteries from DB file.
	 * @return ArrayList of lotteries.
	 */
	public ArrayList<Lottery> getALLLotteries() {
		ArrayList<Lottery> results = new ArrayList<Lottery>();
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(Consts.SQL_SEL_LOTTERIES);
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					results.add(new Lottery(rs.getInt(i++), rs.getDate(i++), rs.getInt(i++), 
							rs.getInt(i++),rs.getInt(i++)));
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
	 * Adding a new Lottery with the parameters received from the form.
	 * return true if the insertion was successful, else - return false
     * @return 
	 */
	public boolean addLottery(Date dateLot, int maxParticipants, int numberOfWinners, int numberOfBonuses) {
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					CallableStatement stmt = conn.prepareCall(Consts.SQL_ADD_LOTTERY)) {
				
				int i = 1;
				stmt.setDate(i++, (java.sql.Date) dateLot); // can't be null
				stmt.setInt(i++, maxParticipants); // can't be null
				stmt.setInt(i++, numberOfWinners); // can't be null
				stmt.setInt(i++, numberOfBonuses); // can't be null
				
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
