package utils;

//**ENUM NAMETOWINDOW was created in order to neatly sort and categorize pages and their paths.

public enum NameToWindow {

	WELCOMESCREEN("welcome"),
	

	/*------------MENUS ADMIN-------------------------------------------------*/
	MENU_IMPORT("menus/reportsMenu"),
	MENU_EXPORT("menus/products"),
	ADD_LOTTERY("add/addLottery"),
	ADD_BONUS("add/addBonus"),
	EDIT_RIDDLE("admin/editRiddlesLvl"),

	
	/*------------MENUS USER-------------------------------------------------*/
	MENU_VIEWRECOMMENDEDTRANS("recommendedPairsReport"),
	MENU_ADDTRANSTOBLOCK("add/addTransToBlock"),
	MENU_VIEW("menus/recMenu"),
	VIEW_MINERS("menus/viewAllMiners"),
	VIEW_MY_INFO("miner/viewMyInfo"),
	SIGN_UP_TO_LOTTERY("miner/signUpToLottery"),
	SOLVE_RIDDLES("miner/solveRiddles"),
	QUERIES("menus/queries");
	
	
	NameToWindow(String s)
	{
		this.myPath = s;
	}
	
	private String myPath;
	
	@Override
	public String toString() {
		return myPath;
	}
}
