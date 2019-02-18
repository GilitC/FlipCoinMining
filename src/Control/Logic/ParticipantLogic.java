package Control.Logic;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import Model.Consts;
import Model.Participant;

public class ParticipantLogic {
	private static ParticipantLogic _instance;

	private ParticipantLogic() {
	}

	public static ParticipantLogic getInstance() {
		if (_instance == null)
			_instance = new ParticipantLogic();
		return _instance;
	}
	
	
	/**
	 * fetches all participants in specified lottery from DB file.
	 * @return ArrayList of participants.
	 */
	public ArrayList<Participant> getALLParticipantsInLottery(int lotID) {
		ArrayList<Participant> results = new ArrayList<Participant>();
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					) {
				PreparedStatement stmt = conn.prepareStatement(Consts.SQL_SEL_PARTICIPANTS_INLOT);
				stmt.setInt(1, lotID);	
				ResultSet rs = stmt.executeQuery();	
				while (rs.next()) {
					int i = 1;
					results.add(new Participant(rs.getInt(i++), rs.getString(i++), rs.getString(i++)));
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
	 * Adding a new participant with the parameters received from the form.
	 * return true if the insertion was successful, else - return false
     * @return 
	 */
	public boolean addParticipant( int lotteryNumber, String uniqueAddress, String isWinner) {
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					CallableStatement stmt = conn.prepareCall(Consts.SQL_ADD_PARTICIPANT)) {
				
				int i = 1;
				stmt.setInt(i++, lotteryNumber); // can't be null
				stmt.setString(i++, uniqueAddress); // can't be null
				stmt.setString(i++, isWinner); // can't be null
				
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
	 * Editing an existing participants status in a lottery with the parameters received 
	 * return true if the update was successful, else - return false
	 * @return 
	 */
	public boolean updateParticipantsstatus(String isWinner , int lotteryNumber, String uniqueAddress) {
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					CallableStatement stmt = conn.prepareCall(Consts.SQL_UPD_PARTICIPANT_STATUS)) {
				int i = 1;

				stmt.setString(i++, isWinner); // can't be null
				
				stmt.setInt(i++, lotteryNumber); // can't be null
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
