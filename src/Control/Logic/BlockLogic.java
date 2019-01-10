package Control.Logic;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import Model.Block;
import Model.Consts;

public class BlockLogic {
	private static BlockLogic _instance;

	private BlockLogic() {
	}

	public static BlockLogic getInstance() {
		if (_instance == null)
			_instance = new BlockLogic();
		return _instance;
	}

//	/**
//	 * fetches all Transactions from DB file ORDERED BY fee.
//	 * @return ArrayList of Transactions.
//	 */
//	public ArrayList<Block> getAllBlocks() {
//		ArrayList<Block> results = new ArrayList<Block>();
//		try {
//			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
//			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
//					PreparedStatement stmt = conn.prepareStatement(Consts.SQL_SEL_TRANSACTION);
//					ResultSet rs = stmt.executeQuery()) {
//				while (rs.next()) {
//					int i = 1;
//					results.add(new Transaction(rs.getInt(i++), rs.getInt(i++),
//							rs.getString(i++), rs.getDouble(i++), rs.getString(i++), rs.getDate(i++), rs.getDate(i++)));
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		}
//		return results;
//	}
	
	/**
	 * fetches all Blocks from DB file that are of a chosen Miner
	 * @return ArrayList of blocks that belong to the chosen miner
	 */
	public ArrayList<Block> getsBlockbyMiner(String minerAddress) {
		ArrayList<Block> results = new ArrayList<Block>();
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(Consts.blockByMinerID(minerAddress));
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					results.add(new Block(rs.getString(i++), rs.getDate(i++),
							rs.getDate(i++), rs.getInt(i++), rs.getString(i++), rs.getString(i++)));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return results;
	}
	
	/*----------------------------------------- ADD / REMOVE / UPDATE EMPLOYEE METHODS --------------------------------------------*/

//	/**
//	 * Adding a new Transaction to the databse (Usually will be adding the imported transactions from FlipCoin Transfer)
//	 * return true if the insertion was successful, else - return false
//     * @return 
//	 */
//	public boolean addBlock(int transactionID, int size, String type, double fee, String blockAddress, Date additionTime,
//			Date additionDate) {
//		try {
//			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
//			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
//					CallableStatement stmt = conn.prepareCall(Consts.SQL_ADD_TRANSACTION)) {
//				
//				int i = 1;
//				
//				stmt.setInt(i++, transactionID); // can't be null
//				stmt.setInt(i++, size); // can't be null
//				stmt.setString(i++, type); // can't be null
//				stmt.setDouble(i++, fee); // can't be null
//				stmt.setString(i++, blockAddress); // can't be null
//
//				
//				if (additionTime != null)
//					stmt.setDate(i++, new java.sql.Date(additionTime.getTime()));
//				else
//					stmt.setNull(i++, java.sql.Types.DATE);
//				
//				if (additionDate != null)
//					stmt.setDate(i++, new java.sql.Date(additionDate.getTime()));
//				else
//					stmt.setNull(i++, java.sql.Types.DATE);
//				stmt.executeUpdate();
//				return true;
//				
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		}
//		return false;
//	}

}
