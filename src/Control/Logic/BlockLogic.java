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

	/**
	 * fetches all blocks from DB file ORDERED BY fee.
	 * @return ArrayList of blocks.
	 */
	public ArrayList<Block> getAllBlocks() {
		ArrayList<Block> results = new ArrayList<Block>();
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(Consts.SQL_SEL_BLOCK);
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					results.add(new Block(rs.getInt(i++), rs.getDate(i++), rs.getDate(i++),
							rs.getInt(i++), rs.getInt(i++), rs.getString(i++)));
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
					results.add(new Block(rs.getInt(i++), rs.getDate(i++), rs.getDate(i++),
							rs.getInt(i++), rs.getInt(i++), rs.getString(i++)));
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
	 * Method returns the previous block address
     * @return 0 if it does not exist
	 */
	public int getPrevBlockAddress() {
		int add = 0;
			try {
				Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
				try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
						PreparedStatement stmt = conn.prepareStatement(Consts.SQL_PREVIOUS_BLOCK_ADDRESS);
						) {

					ResultSet rs = stmt.executeQuery();
					if(rs.next())
					{
						return rs.getInt("count");
					}
				} catch (SQLException e) {
					e.printStackTrace();	
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();		
			}
			return add;
	}
	
	/**
	 * Method returns the block size where the riddle level is given
     * @return 0 if it does not exist
	 */
	public int getBlockSizebyRidLVL(int levelCode) {
		int add = 0;
			try {
				Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
				try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
						PreparedStatement stmt = conn.prepareStatement(Consts.SQL_SELECT_BLOCKSIZE_BY_RIDLVL);
						) {
					stmt.setInt(1, levelCode);
					ResultSet rs = stmt.executeQuery();
					if(rs.next())
					{
						return rs.getInt("blockSize");
					}
				} catch (SQLException e) {
					e.printStackTrace();	
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();		
			}
			return add;
	}
	
	/*----------------------------------------- ADD / REMOVE / UPDATE BLOCK METHODS --------------------------------------------*/

	/**
	 * Adding a new Block to the databse (For the first user who solved a riddle correctly)
	 * return true if the insertion was successful, else - return false
     * @return 
	 */
	public boolean addBlock(int size, int previousBlock,
			String minerAddress) {
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					CallableStatement stmt = conn.prepareCall(Consts.SQL_ADD_BLOCK)) {
				
				int i = 1;

				stmt.setInt(i++, size); // can't be null
				stmt.setInt(i++, previousBlock); // can't be null
				stmt.setString(i++, minerAddress); // can't be null
				
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
