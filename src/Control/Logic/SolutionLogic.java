package Control.Logic;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import Model.Consts;


public class SolutionLogic {
	private static SolutionLogic _instance;

	private SolutionLogic() {
	}

	public static SolutionLogic getInstance() {
		if (_instance == null)
			_instance = new SolutionLogic();
		return _instance;
	}


	/*----------------------------------------- ADD / REMOVE / UPDATE SOLUTION METHODS --------------------------------------------*/

	/**
	 * Adding a new solution with the parameters received
	 * return true if the insertion was successful, else - return false
     * @return 
	 */
	public boolean addSolution(int riddleNumber, int solutionNumber, String result) {
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					CallableStatement stmt = conn.prepareCall(Consts.SQL_ADD_SOLUTION)) {
				
				int i = 1;

				stmt.setInt(i++, riddleNumber); // can't be null
				stmt.setInt(i++, solutionNumber); // can't be null
				stmt.setString(i++, result); // can't be null
			
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
