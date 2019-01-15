package Control.Logic;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.json.simple.DeserializationException;
import org.json.simple.JsonArray;
import org.json.simple.JsonObject;
import org.json.simple.Jsoner;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import Model.Consts;
import Model.Transaction;
import Model.Consts.Manipulation;

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
	 * fetches all Transactions from DB file ORDERED BY fee. That Where not yet chosen to be in a block
	 * @return ArrayList of Transactions.
	 */
	public ArrayList<Transaction> getAllTransactions() {
		ArrayList<Transaction> results = new ArrayList<Transaction>();
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(Consts.SQL_SEL_TRANSACTION_NULLBLOCK);
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
	
	/**
	 * exports Transactions from db to xml.
	 */
    public void exportTransactionsToXML() {
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
                    PreparedStatement stmt = conn.prepareStatement(
                            Consts.SQL_SEL_TRANSACTIONTOEXPORT);
                    ResultSet rs = stmt.executeQuery()) {
                
                // create document object.
                Document doc = DocumentBuilderFactory.newInstance()
                        .newDocumentBuilder().newDocument();
                
                // push root element into document object.
                Element rootElement = doc.createElement("Transactions_info");
                rootElement.setAttribute("exportDate", LocalDateTime.now().toString());
                doc.appendChild(rootElement);
                
                ResultSetMetaData rsmd = rs.getMetaData();
                int colCount = rsmd.getColumnCount();

                while (rs.next()) {
                  Element row = doc.createElement("Transaction");
                  rootElement.appendChild(row);
                  for (int i = 1; i <= colCount; i++) {
                    String columnName = rsmd.getColumnName(i);
                    Object value = rs.getObject(i);
                    Element node = doc.createElement(columnName);
                    node.appendChild(doc.createTextNode(value.toString()));
                    row.appendChild(node);
                  }
                }
                
                DOMSource source = new DOMSource(doc);
                File file = new File("xml/exportTx.xml");
                file.getParentFile().mkdir(); // create xml folder if doesn't exist.
                StreamResult result = new StreamResult(file);
                TransformerFactory factory = TransformerFactory.newInstance();
                
                // IF CAUSES ISSUES, COMMENT THIS LINE.
              //  factory.setAttribute("indent-number", 2);
                //
                
                Transformer transformer = factory.newTransformer();
                
                // IF CAUSES ISSUES, COMMENT THESE 2 LINES.
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
                //
                
                transformer.transform(source, result);
                
                System.out.println("Transactions data exported successfully!");
            } catch (SQLException | NullPointerException | ParserConfigurationException
                    | TransformerException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
	/**
	 * imports transactions from json to db.
	 * @param path json filepath.
	 */
    public void importTransactionsFromJSON(String path) {
    	try (FileReader reader = new FileReader(new File(path))) {
    		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    		JsonObject doc = (JsonObject) Jsoner.deserialize(reader);
    		JsonArray txs = (JsonArray) doc.get("Txs_info");
    		Iterator<Object> iterator = txs.iterator();
    		int errors = 0;
    		while (iterator.hasNext()) {
    			JsonObject obj = (JsonObject) iterator.next();
    			Transaction c = new Transaction(Integer.parseInt(obj.get("transactionID").toString()),
    					Integer.parseInt(obj.get("size").toString()), 
    					(String) obj.get("type"),
    					Double.parseDouble( obj.get("fee").toString()), 
    					(String) obj.get("blockAddress"));
    			if (!manipulateTx(c, Manipulation.INSERT) && 
						!manipulateTx(c, Manipulation.UPDATE))
					errors++;
    		}
    		
			System.out.println((errors == 0) ? "transactions data imported successfully!" : 
				String.format("transactions data imported with %d errors!", errors));
    	} catch (IOException | DeserializationException e) {
    		e.printStackTrace();
    	}
    }

    /**
     * performs data manipulation in db on given customer.
     * @param c customer to be manipulated.
     * @param manipulation manipulation type.
     * @return success or failure.
     */
    public boolean manipulateTx(Transaction c, Manipulation manipulation) {
    	try {
    		Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
    		try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
    				CallableStatement stmt = conn.prepareCall(
    						(manipulation.equals(Manipulation.UPDATE)) ? 
    								Consts.SQL_UPD_TRANSACTIONS_BLOCK : 
    									(manipulation.equals(Manipulation.INSERT)) ? 
    											Consts.SQL_ADD_TRANSACTION : 
    												Consts.SQL_ADD_TRANSACTION)) { //
    			allocateTransactionParams(stmt, c);
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
     * fills statement's placeholders with transcation's field values.
     * @param stmt statement object.
     * @param tx customer.
     * @param m manipulation type.
     * @throws SQLException
     */
    private void allocateTransactionParams(CallableStatement stmt, Transaction tx) throws SQLException {
    	int i = 1;
    	
    	stmt.setInt(i++, tx.getTransactionID());
		stmt.setInt(i++, tx.getSize()); // can't be null
		stmt.setString(i++, tx.getType()); // can't be null
		stmt.setDouble(i++, tx.getFee()); // can't be null
		stmt.setString(i++, null); // Set the default block address to null

		java.sql.Date sqlDate = new java.sql.Date(tx.getAdditionTime().getTime());
		
		stmt.setDate(i++, sqlDate);
		stmt.setDate(i++, sqlDate);

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
