package View.miner;

import Control.Logic.TransactionLogic;

import java.util.ArrayList;
import java.util.ListIterator;

import Control.SysData;
import Control.Logic.BlockLogic;
import Control.Logic.MinerLogic;
import Exceptions.ListNotSelectedException;
import Model.Transaction;
import Model.Block;
import Model.Lottery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import View.WindowManager;


public class signUpToLotteryController {


	@FXML
	private Label lblSuccess;

	@FXML
	private Button back;

	@FXML
	private ComboBox<Lottery> comboBoxLotteries;

	@FXML
	private AnchorPane viewRecommend;

	@FXML
	private Button buttonAdd;

	@FXML
	void goToAddUserToLot(ActionEvent event) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Add Transaction");
		alert.setHeaderText("");

		try {

			if (listTransactions.getSelectionModel().isEmpty()) {
				throw new ListNotSelectedException("Transaction");
			}

			if (comboBoxBlock.getSelectionModel().isEmpty()) {
				throw new ListNotSelectedException("Block");
			}


			String blockAddress = comboBoxBlock.getSelectionModel().getSelectedItem().getBlockAddress();
			Integer transactionID = listTransactions.getSelectionModel().getSelectedItem().getTransactionID();

			Double transactionfee = listTransactions.getSelectionModel().getSelectedItem().getFee();
			Double newProfit = SysData.getInstance().getLoggedInMiner().getDigitalProfit() + transactionfee;
			String minerAddress = SysData.getInstance().getLoggedInMiner().getUniqueAddress();

			if (TransactionLogic.getInstance().updateTransaction(blockAddress, transactionID)
					&& MinerLogic.getInstance().updateMinerDigitalProfit(newProfit, minerAddress)) {
				alert.setHeaderText("Success");
				alert.setContentText("Added Transaction To Block succesfully!");
				alert.show();			
				initialize();
			} else {
				alert.setHeaderText("Unable to add the Transaction to the block.");
				alert.setContentText("Transaction wasn't added.");
				alert.show();
			}


		} catch (ListNotSelectedException e) {
		}
	}


	//Initialize the combobox 
	public void initialize() {
		//Checks that the Date of the lottery did not pass
		// Checks how many already signed up to this lottery and that our logged in miner is not one of them
		comboBoxBlock.getItems().setAll(BlockLogic.getInstance().getsBlockbyMiner(SysData.getInstance().getLoggedInMiner().getUniqueAddress()));
	}

	@FXML
	void goBack(ActionEvent event) {
		WindowManager.goBack();
	}
}


