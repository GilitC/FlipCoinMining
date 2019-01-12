package Control.Logic;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import model.Customer;
import model.Consts.Manipulation;

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
                
                while (rs.next()) {     // run on all TRANSACTION records..
                    // create transaction element.
                    Element tx = doc.createElement("Transaction");
                    
                    // assign key.
                    Attr attr = doc.createAttribute("ID");
                    attr.setValue(rs.getString(1));
                    tx.setAttributeNode(attr);
                    
                    // push elements to customer.
                    for (int i = 2; i <= rs.getMetaData().getColumnCount(); i++) {
                        Element element = doc.createElement(
                                rs.getMetaData().getColumnName(i)); // push element to doc.
                        rs.getObject(i); // for wasNull() check..
                        element.appendChild(doc.createTextNode(
                                rs.wasNull() ? "" : rs.getString(i)));  // set element value.
                        tx.appendChild(element);  // push element to customer.
                    }
                    
                    // push customer to document's root element.
                    rootElement.appendChild(tx);
                }
                
                // write the content into xml file
                
                DOMSource source = new DOMSource(doc);
                File file = new File("xml/exportTx.xml");
                file.getParentFile().mkdir(); // create xml folder if doesn't exist.
                StreamResult result = new StreamResult(file);
                TransformerFactory factory = TransformerFactory.newInstance();
                
                // IF CAUSES ISSUES, COMMENT THIS LINE.
                factory.setAttribute("indent-number", 2);
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
    		JsonObject doc = (JsonObject) Jsoner.deserialize(reader);
    		JsonArray txs = (JsonArray) doc.get("Txs_info");
    		Iterator<Object> iterator = txs.iterator();
    		int errors = 0;
    		while (iterator.hasNext()) {
    			JsonObject obj = (JsonObject) iterator.next();
    			Transaction c = new Transaction((int) obj.get("transactionID"),
    					(int) obj.get("size"), 
    					(String) obj.get("type"),
    					(double) obj.get("fee"), 
    					(String) obj.get("blockAddress"),
    					(Date) obj.get("additionTime"), 
    					(Date) obj.get("additionDate");
    			if (!manipulateCustomer(c, Manipulation.INSERT) && 
						!manipulateCustomer(c, Manipulation.UPDATE))
					errors++;
    		}
    		
			System.out.println((errors == 0) ? "customers data imported successfully!" : 
				String.format("customers data imported with %d errors!", errors));
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
    public boolean manipulateCustomer(Customer c, Manipulation manipulation) {
    	try {
    		Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
    		try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
    				CallableStatement stmt = conn.prepareCall(
    						(manipulation.equals(Manipulation.UPDATE)) ? 
    								Consts.UPDATE_CUSTOMER : 
    									(manipulation.equals(Manipulation.INSERT)) ? 
    											Consts.INSERT_CUSTOMER : 
    												Consts.DELETE_CUSTOMER)) {
    			allocateCustomerParams(stmt, c, manipulation);
    			stmt.executeUpdate();
    			return true;
    		} catch (SQLException e) {
//    			e.printStackTrace();
    		}
    	} catch (ClassNotFoundException e) {
//    		e.printStackTrace();
    	}
    	
    	return false;
    }
    
    /**
     * fills statement's placeholders with customer's field values.
     * @param stmt statement object.
     * @param c customer.
     * @param m manipulation type.
     * @throws SQLException
     */
    private void allocateCustomerParams(CallableStatement stmt, Customer c, Manipulation m) throws SQLException {
    	int i = 1;
    	
    	if (!m.equals(Manipulation.UPDATE)) {
    		stmt.setString(i++, c.getCustomerID());
    		
    		if (m.equals(Manipulation.DELETE))
    			return;
    	}
    	
    	stmt.setString(i++, c.getCompanyName());
    	
    	if (c.getContactName() == null)
    		stmt.setNull(i++, java.sql.Types.VARCHAR);
    	else
    		stmt.setString(i++, c.getContactName());
    	
    	if (c.getContactTitle() == null)
    		stmt.setNull(i++, java.sql.Types.VARCHAR);
    	else
    		stmt.setString(i++, c.getContactTitle());
    	
    	if (c.getAddress() == null)
    		stmt.setNull(i++, java.sql.Types.VARCHAR);
    	else
    		stmt.setString(i++, c.getAddress());
    	
    	if (c.getCity() == null)
    		stmt.setNull(i++, java.sql.Types.VARCHAR);
    	else
    		stmt.setString(i++, c.getCity());
    	
    	if (c.getCountry() == null)
    		stmt.setNull(i++, java.sql.Types.VARCHAR);
    	else
    		stmt.setString(i++, c.getCountry());
    	
    	if (c.getPhone() == null)
    		stmt.setNull(i++, java.sql.Types.VARCHAR);
    	else
    		stmt.setString(i++, c.getPhone());
    	
    	if (c.getFax() == null)
    		stmt.setNull(i++, java.sql.Types.VARCHAR);
    	else
    		stmt.setString(i++, c.getFax());
    	
    	if (m.equals(Manipulation.UPDATE))
    		stmt.setString(i, c.getCustomerID());
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
