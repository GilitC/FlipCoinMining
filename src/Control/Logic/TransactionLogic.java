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
import Model.Transaction;

public class TransactionLogic {
	private static TransactionLogic _instance;

	private TransactionLogic() {
	}

	public static TransactionLogic getInstance() {
		if (_instance == null)
			_instance = new TransactionLogic();
		return _instance;
	}

	/**
	 * fetches all Transactions from DB file ORDERED BY fee.
	 * @return ArrayList of Transactions.
	 */
	public ArrayList<Transaction> getAllTransactions() {
		ArrayList<Transaction> results = new ArrayList<Transaction>();
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(Consts.SQL_SEL_TRANSACTION);
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					results.add(new Transaction(rs.getInt(i++), rs.getInt(i++),
							rs.getString(i++), rs.getDouble(i++), rs.getString(i++), rs.getDate(i++), rs.getDate(i++)));
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
	 * fetches all Transactions from DB file that are in a Chosen BLOCK
	 * @return ArrayList of Transactions in the block.
	 */
	public ArrayList<Transaction> getTransactionsInBlock(String blockAddress) {
		ArrayList<Transaction> results = new ArrayList<Transaction>();
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(Consts.transBYBlock(blockAddress));
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					results.add(new Transaction(rs.getInt(i++), rs.getInt(i++),
							rs.getString(i++), rs.getDouble(i++), rs.getString(i++), rs.getDate(i++), rs.getDate(i++)));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return results;
	}
	
	/*----------------------------------------- ADD / REMOVE / UPDATE TRANSACTION METHODS --------------------------------------------*/

	/**
	 * Adding a new Transaction to the databse (Usually will be adding the imported transactions from FlipCoin Transfer)
	 * return true if the insertion was successful, else - return false
     * @return 
	 */
	public boolean addTransaction(int transactionID, int size, String type, double fee, String blockAddress, Date additionTime,
			Date additionDate) {
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					CallableStatement stmt = conn.prepareCall(Consts.SQL_ADD_TRANSACTION)) {
				
				int i = 1;
				
				stmt.setInt(i++, transactionID); // can't be null
				stmt.setInt(i++, size); // can't be null
				stmt.setString(i++, type); // can't be null
				stmt.setDouble(i++, fee); // can't be null
				stmt.setString(i++, blockAddress); // can't be null

				
				if (additionTime != null)
					stmt.setDate(i++, new java.sql.Date(additionTime.getTime()));
				else
					stmt.setNull(i++, java.sql.Types.DATE);
				
				if (additionDate != null)
					stmt.setDate(i++, new java.sql.Date(additionDate.getTime()));
				else
					stmt.setNull(i++, java.sql.Types.DATE);
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
	 * Update the block of an existing Transaction in the databse 
	 * return true if the update was successful, else - return false
     * @return 
	 */
	public boolean updateTransaction(String blockAddress, int transactionID) {
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					CallableStatement stmt = conn.prepareCall(Consts.SQL_UPD_TRANSACTIONS_BLOCK)) {
				
				int i = 1;

				stmt.setString(i++, blockAddress); // can't be null
				stmt.setInt(i++, transactionID); // can't be null
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
	 * Delete the selected Transaction from the database (After choosing and exporting to FlipCoin Transfer)
	 * Return true if the deletion was successful, else - return false
	 * @param transactionID 
     * @return boolean - if the transaction was removed from the DB
	 */
	public boolean removeTransaction(int transactionID) {
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					CallableStatement stmt = conn.prepareCall(Consts.removeTrans(transactionID))) {
				
				stmt.setInt(1, transactionID);
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
