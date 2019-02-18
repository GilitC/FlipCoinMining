package View.admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;
import Control.Logic.*;

	public class reportsController {

	    @FXML
	    private AnchorPane recoMenu;

	    @FXML
	    private DatePicker datepicksend;

	    @FXML
	    private Button addRec;

	    @FXML
	    private DatePicker datepickstart;


	    @FXML
	    void goToProduceTransRPT(ActionEvent event) {
	    	
	    	java.sql.Date s;
	    	java.sql.Date d;
	    	
	    	if(datepickstart.getValue()==null)
	    		s = new java.sql.Date(2019-1900, 1, 5);
	    	else
	    		s = java.sql.Date.valueOf(datepickstart.getValue());
	    	
	    	if(datepicksend.getValue()==null)
	    		d = new java.sql.Date(2019-1900, 1, 5);
	    	else
	    		d = java.sql.Date.valueOf(datepicksend.getValue());
	    	
	    	System.out.println("Trying to open report");
	    	MinerLogic.getInstance().compileDominantMinerReport(s,d).setVisible(true);
	    }

	
}
