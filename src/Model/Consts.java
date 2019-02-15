package Model;

import java.io.File;

import net.ucanaccess.util.Logger;

/**
 * http://www.javapractices.com/topic/TopicAction.do?Id=2
 */
public final class Consts {
	private Consts() {
		throw new AssertionError();
	}

	protected static final String DB_FILEPATH = getDBPath();
	public static final String CONN_STR = "jdbc:ucanaccess://" + DB_FILEPATH + ";COLUMNORDER=DISPLAY";
	public static final String[] UCA_LIB = {
			"ucanaccess-3.0.7.jar",
			"lib\\commons-lang-2.6.jar",
			"lib\\commons-logging-1.1.1.jar",
			"lib\\hsqldb.jar",
			"lib\\jackcess-2.1.10.jar"
	};
	
    public enum Manipulation {
    	UPDATE, INSERT, DELETE;
    }

	/*----------------------------------------- USER QUERIES -----------------------------------------*/
	public static final String SQL_SEL_MINERS = "SELECT * FROM TblMiner";
	public static final String SQL_DEL_MINER = "{ call qryDelMiner(?) }";
	public static final String SQL_INS_MINER = "{ call qryInsMiner(?,?,?,?,?) }";
	public static final String SQL_UPD_MINER = "{ call qryUpdMiner(?,?,?,?,?) }";
	
	public static final String SQL_UPD_MINER_PROFIT = "UPDATE TblMiner SET TblMiner.digitalProfit = ? WHERE TblMiner.uniqueAddress= ? " ;	

	/*----------------------------------------- BLOCK QUERIES --------------------------------------------*/
	public static final String SQL_SEL_BLOCK = "SELECT * FROM TblBlock"; 
	
	public static String blockByMinerID(String minerAddress) {
		String SQL_SEL_BLOCKBYMINER = "SELECT * FROM TblBlock \r\n" + 
				"WHERE minerAddress IN (\""+minerAddress+"\")";
		 Logger.log(SQL_SEL_BLOCKBYMINER);
		return SQL_SEL_BLOCKBYMINER;
	}
	
	
	/*----------------------------------------- TRANSACTION QUERIES --------------------------------------------*/
	public static final String SQL_SEL_TRANSACTION = "SELECT * FROM TblTransaction ORDER BY TblTransaction.fee DESC"; //Order By Fee
	
	public static final String SQL_SEL_TRANSACTIONTOEXPORT = "SELECT *\r\n" + 
			"FROM tblTransaction\r\n" + 
			"WHERE tblTransaction.blockAddress Is Not Null\r\n" + 
			"ORDER BY tblTransaction.fee DESC\r\n" + 
			""; //Transactions to export are those who where placed into a block
	
	public static final String SQL_SEL_TRANSACTION_NULLBLOCK = "SELECT *\r\n" + 
			"FROM tblTransaction\r\n" + 
			"WHERE tblTransaction.blockAddress Is Null\r\n" + 
			"ORDER BY tblTransaction.fee DESC\r\n" + 
			""; //show transactions who are not chosen yet
	
	public static String transBYBlock(String blockAddress) {
		String SQL_SEL_TRANSBYBLOCKA= "SELECT * FROM TblTransaction\r\n" + 
				" Where blockAddress IN (\""+blockAddress+"\")";
		return SQL_SEL_TRANSBYBLOCKA;
	}
	
	public static String removeTrans(int transactionID) {
		String SQL_DELETE_TRANSACTION= "DELETE FROM TblTransaction\r\n" + 
				" Where transactionID =" + transactionID;
		return SQL_DELETE_TRANSACTION;
	}
	 
	public static final String SQL_ADD_TRANSACTION = "INSERT INTO TblTransaction ( transactionID, size, type, fee, blockAddress, additionTime, additionDate ) VALUES ( ? , ? , ? , ? , ? , ? , ? )";
	public static final String SQL_UPD_TRANSACTIONS_BLOCK = "UPDATE TblTransaction SET TblTransaction.blockAddress = ? WHERE TblTransaction.transactionID = ? " ;			

	/*----------------------------------------- LOTTERY QUERIES --------------------------------------------*/
	public static final String SQL_ADD_LOTTERY = "INSERT INTO tblLottery ( lotDate, maxParticipants, numberOfWinners, numberOfBonuses ) VALUES ( ? , ? , ? , ? )";
	public static final String SQL_SEL_LOTTERIES = "SELECT * FROM tblLottery ORDER BY tblLottery.lotDate DESC"; //Order By Date
	
	/*----------------------------------------- BONUS QUERIES --------------------------------------------*/
	public static final String SQL_ADD_BONUS = "INSERT INTO tblBonus ( description ) VALUES ( ? )";
	
	
	/*----------------------------------------- RIDDLE QUERIES --------------------------------------------*/
	public static final String SQL_SEL_RIDDLES = "SELECT * FROM tblRiddle";
	public static final String SQL_SEL_RIDDLELEVELS = "SELECT * FROM tblRiddleLevel";

	public static final String SQL_UPD_RIDDLE_LEVEL = "UPDATE tblRiddle SET tblRiddle.riddleLevel = ? WHERE tblRiddle.riddleNumber=?" ;				
	public static final String SQL_UPD_RIDDLE_STATUS = "UPDATE tblRiddle SET tblRiddle.status = ? WHERE tblRiddle.riddleNumber=?" ;			
	public static final String SQL_ADD_RIDDLE = "INSERT INTO tblRiddle ( publishTime, publishDate, description, solutionTime, status, riddleLevel ) VALUES ( ? , ? , ? , ? , ? , ? )";
	//public static final String SQL_SENDRECTOUSER = "INSERT INTO TblUserGetRecommendation ( levelOfImportance, publicAddress, userSignature, recommendId ) VALUES ( ? , ? , ? , ? )";


	/*----------------------------------------- MORE QUERIES ----------------------------------------------*/

	/**
	 * find the correct path of the DB file
     * @return the path of the DB file (from eclipse or with runnable file)
	 */
	private static String getDBPath() {
		File f = new File("src/sources/database.accdb");
		if(f.exists())
		{
			return f.getAbsolutePath();
		}
		
		return new File("sources/database.accdb").getAbsolutePath();
	}
}
