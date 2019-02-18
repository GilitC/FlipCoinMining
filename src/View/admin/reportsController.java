package View.admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;

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

		Date d1 = null, d2 = null;

		d1 = new Date(2018 - 1900, 1, 1);
		d2 = new Date(2020 - 1900, 1, 25);

		System.out.println("Trying to open report");
		MinerLogic.getInstance().compileDominantMinerReport(d1, d2).setVisible(true);
	}

}
