package View.miner;

import Control.Logic.TransactionLogic;

import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.ListIterator;

import Control.SysData;
import Control.Logic.BlockLogic;
import Control.Logic.MinerLogic;
import Control.Logic.RiddleLogic;
import Exceptions.ListNotSelectedException;
import Model.Transaction;
import Model.Block;
import Model.Riddle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import View.WindowManager;


public class solveRiddlesController {

	@FXML
	private TextArea txtSolution;

	@FXML
	private Button back;

	@FXML
	private ComboBox<Riddle> comboBoxRiddles;

	@FXML
	private Button buttonSolve;

	@FXML
	private AnchorPane viewRecommend;

	@FXML
	private Label lblEnterResult;

    @FXML
    void showTxtArea(ActionEvent event) {
    	lblEnterResult.setVisible(true);
		txtSolution.setVisible(true);
    }

	@FXML
	void goToCheckSolution(ActionEvent event) throws ListNotSelectedException, InputMismatchException{
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Solve Riddle");
		alert.setHeaderText("");

		try {

			if (comboBoxRiddles.getSelectionModel().isEmpty()) {
				throw new ListNotSelectedException("Riddle");
			}

			if (txtSolution.getText().isEmpty()) {
				throw new InputMismatchException("Enter your solution");
			}

			int riddleId = comboBoxRiddles.getSelectionModel().getSelectedItem().getRiddleNumber();
			String result = txtSolution.getText();
			
			if (RiddleLogic.getInstance().checkIfSolutionMatchesRiddle(riddleId, result)) {
				alert.setHeaderText("Congratulations!");
				alert.setContentText("You have solved the riddle correctly!");
				alert.show();			
				initialize();
			} else {
				alert.setHeaderText("Sorry");
				alert.setContentText("The answer is incorrect, please try again later.");
				alert.show();
			}


		} catch (ListNotSelectedException e) {
		}
		 catch (InputMismatchException e) {
			}
	}



	//Initialize the combobox with blocks that belong to the logged in miner
	public void initialize() {
		ArrayList<Riddle> arr = RiddleLogic.getInstance().getALLRiddles();

		//Show only those riddles who are between today and the given solution Time
		//And were not solved yet
		ListIterator<Riddle> iter = arr.listIterator();
		Date today = new Date();
		while(iter.hasNext()){
			//Don't show the followikng riddles
			if(iter.next().getSolutionTime().before(today) || iter.next().getStatus()=="Solved"  || iter.next().getStatus()=="Irrelevant"){
				iter.remove();
			}
		}
		
		lblEnterResult.setVisible(false);
		txtSolution.setVisible(false);
		txtSolution.setText("");
		comboBoxRiddles.getItems().setAll(arr);
		
		//Forces text only
		txtSolution.textProperty().addListener((observable, oldValue, newValue) -> {
		    if (!newValue.matches("[a-z A-Z]+")) {
		    	txtSolution.setText(newValue.replaceAll("[^a-z A-Z]", ""));
		    }
		});
		}
	

	@FXML
	void goBack(ActionEvent event) {
		WindowManager.goBack();
	}
}


