package View.add;

import Control.Logic.BonusLogic;
import Exceptions.MissingInputException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import View.WindowManager;


public class addBonusController {


	@FXML
	private Label lblSuccess;

	@FXML
	private Button buttonClear;

	@FXML
	private Button back;

	@FXML
	private AnchorPane viewRecommend;

	@FXML
	private Button buttonAdd;

	@FXML
	private TextArea txtAreaDesc;


	@FXML
	void goToClearForm(ActionEvent event) {
		initialize();
	}

	public void initialize() {
		lblSuccess.setVisible(false);
		txtAreaDesc.setText("");
		
		//Forces text only
		txtAreaDesc.textProperty().addListener((observable, oldValue, newValue) -> {
		    if (!newValue.matches("[a-z A-Z]+")) {
		    	txtAreaDesc.setText(newValue.replaceAll("[^a-z A-Z]", ""));
		    }
		});
 
	}


	@FXML
	void goToAddLot(ActionEvent event) throws MissingInputException{
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Add Bonus");
		alert.setHeaderText("");

		String descrip = txtAreaDesc.getText();

		try {

			if (descrip.isEmpty()) {
				throw new MissingInputException("description");
			}

			if (BonusLogic.getInstance().addBonus(descrip)) {		
				initialize();
				lblSuccess.setText("Successfully Added Bonus!");
				lblSuccess.setVisible(true);
			} else {
				alert.setHeaderText("Unable to add.");
				alert.setContentText("Bonus wasn't added.");
				alert.show();
			}


		} catch (MissingInputException e) {
		}

	}


	@FXML
	void goBack(ActionEvent event) {
		WindowManager.goBack();
	}
}


