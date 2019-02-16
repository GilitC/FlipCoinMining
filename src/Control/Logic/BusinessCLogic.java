package Control.Logic;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import Model.Consts;

public class BusinessCLogic {
	private static BusinessCLogic _instance;

	private BusinessCLogic() {
	}

	public static BusinessCLogic getInstance() {
		if (_instance == null)
			_instance = new BusinessCLogic();
		return _instance;
	}

	
	/*----------------------------------------- ADD / REMOVE / UPDATE USER METHODS --------------------------------------------*/

	/**
	 * Adding a new BC with the parameters received from the form.
	 * return true if the insertion was successful, else - return false
     * @return 
	 */
	public boolean addBusinessCompany(String uniqueAddress, String contactName 	, String contactPhone,
	String contactEmail) {
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					CallableStatement stmt = conn.prepareCall(Consts.SQL_ADD_BSCompany)) {
				
				int i = 1;
				stmt.setString(i++, uniqueAddress); // can't be null
				stmt.setString(i++, contactName); // can't be null
				stmt.setString(i++, contactPhone); // can't be null
				stmt.setString(i++, contactEmail); // can't be null
				
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
