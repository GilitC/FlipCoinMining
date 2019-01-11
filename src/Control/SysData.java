package Control;

import Model.*;
import utils.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import Control.DBManager;

/**
 * This SysData object ~ represents the class system
 * 
 */
public class SysData {

	private static SysData _instance;	
	private Connection _connection;
	private static DBManager DB;
	private static Miner loggedInMiner ;
	
	private SysData()
	{
        try {
        	Control.Logger.log("Attempting to connect MS Access DB");
            DB = new DBManager();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SysData.class.getName()).log(Level.SEVERE, null, ex);
            return;
        } catch (SQLException ex) {
            Logger.getLogger(SysData.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        Control.Logger.log("Connection to MS Access DB was successful!");
		_connection = DB.getConnection();
	}
	
	//Create a singleton instance of SysData
	public static SysData getInstance() {
		if(_instance == null)
		{
			_instance = new SysData();
			loggedInMiner = null;
		}
		return _instance;
	}
	
	public Connection getConnection()
	{
		return _connection;
	}

	public Miner getLoggedInMiner() {
		return loggedInMiner;
	}

	public void setLoggedInMiner(Miner loggedInMiner) {
		SysData.loggedInMiner = loggedInMiner;
	}
	
	

}// ~ END OF Class SysData
