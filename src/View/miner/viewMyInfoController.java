package View.miner;

import Control.SysData;
import Model.Miner;
import View.WindowManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class viewMyInfoController {

	@FXML
	private TextField minerProfit;

	@FXML
	private TextField minername;

	@FXML
	private TextField minerEmail;

	@FXML
	private AnchorPane addReco;

	@FXML
	private Button back;

	@FXML
	private TextField mineradd;

	@FXML
	private Label labelSuccess;

	@FXML
	void goBack(ActionEvent event) {
		WindowManager.goBack();
	}

	//Initialize the page
	public void initialize() {
		Miner minerIn = SysData.getInstance().getLoggedInMiner();
		
		minername.setText((String.valueOf(minerIn.getName())));
		minerEmail.setText(String.valueOf(minerIn.getEmail()));
		minerProfit.setText(String.valueOf(minerIn.getDigitalProfit()));
		mineradd.setText(String.valueOf(minerIn.getUniqueAddress()));

		mineradd.setVisible(true);
		minername.setVisible(true);
		minerEmail.setVisible(true);
		minerProfit.setVisible(true);

	}

}