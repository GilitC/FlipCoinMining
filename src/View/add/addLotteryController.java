package View.add;

import Control.Logic.LotteryLogic;
import Exceptions.MissingInputException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import View.WindowManager;


public class addLotteryController {


	@FXML
	private TextField txtBonuses;

	@FXML
	private TextField txtWinners;

	@FXML
	private Label lblSuccess;

	@FXML
	private Button buttonClear;

	@FXML
	private DatePicker ltDate;

	@FXML
	private Button back;

	@FXML
	private TextField txtParticipants;

	@FXML
	private AnchorPane viewRecommend;

	@FXML
	private Button buttonAdd;


	@FXML
	void goToClearForm(ActionEvent event) {
		initialize();
	}
	
	void initialize() {
		lblSuccess.setVisible(false);
		txtParticipants.setText("");
		txtWinners.setText("");
		txtBonuses.setText("");
		ltDate.setValue(null);
		
		//Forces numbers only
		txtParticipants.textProperty().addListener((observable, oldValue, newValue) -> {
		    if (!newValue.matches("\\d*")) {
		    	txtParticipants.setText(newValue.replaceAll("[^\\d]", ""));
		    }
		});
		
		//Forces numbers only
		txtWinners.textProperty().addListener((observable, oldValue, newValue) -> {
		    if (!newValue.matches("\\d*")) {
		    	txtWinners.setText(newValue.replaceAll("[^\\d]", ""));
		    }
		});
		
		//Forces numbers only
		txtBonuses.textProperty().addListener((observable, oldValue, newValue) -> {
		    if (!newValue.matches("\\d*")) {
		    	txtBonuses.setText(newValue.replaceAll("[^\\d]", ""));
		    }
		});
	}

	@FXML
	void goToAddLot(ActionEvent event) throws MissingInputException{
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Add Lottery");
		alert.setHeaderText("");

		String numParti = txtParticipants.getText();
		String numWinners = txtWinners.getText();;
		String numBonuses = txtBonuses.getText();;
		
		
		try {

			if (ltDate.getValue() == null) {
				throw new MissingInputException("Lottery Date");
			}

			if (numParti.isEmpty()) {
				throw new MissingInputException("Number of participants");
			}
			
			if (numWinners.isEmpty()) {
				throw new MissingInputException("Number of winners");
			}
			
			if (numBonuses.isEmpty()) {
				throw new MissingInputException("Number of bonuses");
			}

			java.sql.Date dateOfLottery = java.sql.Date.valueOf(ltDate.getValue());
			
			if (LotteryLogic.getInstance().addLottery(dateOfLottery, Integer.parseInt(numParti), Integer.parseInt(numWinners), Integer.parseInt(numBonuses))) {		
				initialize();
				lblSuccess.setText("Successfully Added Lottery!");
				lblSuccess.setVisible(true);
			} else {
				alert.setHeaderText("Unable to add.");
				alert.setContentText("Lottery wasn't added.");
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


