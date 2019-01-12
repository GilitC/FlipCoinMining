package utils;

//**ENUM NAMETOWINDOW was created in order to neatly sort and categorize pages and their paths.

public enum NameToWindow {

	WELCOMESCREEN("welcome"),
	

	/*------------MENUS-------------------------------------------------*/
	MENU_IMPORT("menus/reportsMenu"),
	MENU_EXPORT("menus/products"),
	MENU_VIEWRECOMMENDEDTRANS("recommendedPairsReport"),
	MENU_ADDTRANSTOBLOCK("add/addTransToBlock"),
	MENU_VIEW("menus/recMenu"),
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
