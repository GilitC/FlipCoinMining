package Model;

import java.io.File;

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

	/*----------------------------------------- USER QUERIES -----------------------------------------*/
	public static final String SQL_SEL_MINERS = "SELECT * FROM TblMiner";
	public static final String SQL_DEL_MINER = "{ call qryDelMiner(?) }";
	public static final String SQL_INS_MINER = "{ call qryInsMiner(?,?,?,?,?) }";
	public static final String SQL_UPD_MINER = "{ call qryUpdMiner(?,?,?,?,?) }";

	/*----------------------------------------- BLOCK QUERIES --------------------------------------------*/
	public static final String SQL_SEL_BLOCK = "SELECT * FROM TblBlock"; 
	
	public static String blockByMinerID(String minerAddress) {
		String SQL_SEL_BLOCKBYMINER = "SELECT TblBlock.blockAddress, TblBlock.creationDate, TblBlock.creationHour, TblBlock.size\r\n" + 
				"FROM TblBlock Where minerAddress =" + minerAddress;
		return SQL_SEL_BLOCKBYMINER;
	}
	
	/*----------------------------------------- TRANSACTION QUERIES --------------------------------------------*/
	public static final String SQL_SEL_TRANSACTION = "SELECT * FROM TblTransaction ORDER BY TblTransaction.fee DESC"; //Order By Fee
	
	public static String transBYBlock(String blockAddress) {
		String SQL_SEL_TRANSBYBLOCKA= "SELECT * FROM TblTransaction\r\n" + 
				" Where blockAddress =" + blockAddress;
		return SQL_SEL_TRANSBYBLOCKA;
	}
	
	public static String removeTrans(int transactionID) {
		String SQL_DELETE_TRANSACTION= "DELETE FROM TblTransaction\r\n" + 
				" Where transactionID =" + transactionID;
		return SQL_DELETE_TRANSACTION;
	}
	 
	public static final String SQL_ADD_TRANSACTION = "INSERT INTO TblTransaction ( transactionID, size, type, fee, blockAddress, additionTime, additionDate ) VALUES ( ? , ? , ? , ? , ? , ? , ? )";
	
	/*----------------------------------------- RECOMMENDATION QUERIES --------------------------------------------*/
//	public static final String SQL_SEL_RECOMMENDATION = "SELECT * FROM TblRecommendation";
//	
//	public static String usersByRecc(int recID) {
//		String SQL_SEL_USERSBYRECID = "SELECT TblUserGetRecommendation.levelOfImportance, TblUserGetRecommendation.publicAddress, TblUserGetRecommendation.userSignature, TblUserGetRecommendation.recommendId\r\n" + 
//				"FROM TblUserGetRecommendation Where recommendId =" + recID;
//		return SQL_SEL_USERSBYRECID;
//	}
//	
//	public static final String SQL_UPD_RECOMMENDATION = "UPDATE TblRecommendation SET TblRecommendation.dateCreated = ?, TblRecommendation.chanceChosen = ?, TblRecommendation.amountTaxRecommended = ?, TblRecommendation.publicAddress = ?, TblRecommendation.userSignature = ? WHERE TblRecommendation.recommendId=?" ;			
//	public static final String SQL_ADD_RECOMMENDATION = "INSERT INTO TblRecommendation ( dateCreated, chanceChosen, amountTaxRecommended, publicAddress, userSignature ) VALUES ( ? , ? , ? , ? , ? )";
//	public static final String SQL_SENDRECTOUSER = "INSERT INTO TblUserGetRecommendation ( levelOfImportance, publicAddress, userSignature, recommendId ) VALUES ( ? , ? , ? , ? )";


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
