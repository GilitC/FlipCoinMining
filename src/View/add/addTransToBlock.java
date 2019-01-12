package View.add;

import Control.Logic.TransactionLogic;

import java.util.ArrayList;
import java.util.ListIterator;

import Control.SysData;
import Control.Logic.BlockLogic;
import Control.Logic.MinerLogic;
import Exceptions.ListNotSelectedException;
import Model.Transaction;
import Model.Block;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import View.WindowManager;


public class addTransToBlock {


    @FXML
    private ComboBox<Block> comboBoxBlock;

    @FXML
    private Button back;

    @FXML
    private ListView<Transaction> listTransactions;

    @FXML
    private AnchorPane viewRecommend;

    @FXML
    private Button buttonAdd;

    @FXML
    void goToAddTransToBl(ActionEvent event) throws ListNotSelectedException{
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
			
			if (TransactionLogic.getInstance().updateTransaction(transactionID, blockAddress)
					 && MinerLogic.getInstance().updateMinerDigitalProfit(minerAddress, newProfit)) {
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
    
    //Pick what transactions to show to the user
	    @FXML
	    void showRowDetails(ActionEvent event) {
	    	//Save the size of chosen block
	    	int blockchosenSize = comboBoxBlock.getSelectionModel().getSelectedItem().getSize();
	    	//Create an array list with all Transactions
	    	ArrayList<Transaction> arr = TransactionLogic.getInstance().getAllTransactions();
	    	
	    	//Show only those Transactions with a size smaller than the block and emove the others
	    	ListIterator<Transaction> iter = arr.listIterator();
	    	while(iter.hasNext()){
	    	    if(iter.next().getSize()>blockchosenSize){
	    	        iter.remove();
	    	    }
	    	}
	    	
	        ObservableList<Transaction> trnsLst = FXCollections.observableArrayList(arr);
	        listTransactions.setItems(trnsLst);
	    }
	    
	    //Initialize the combobox with blocks that belong to the logged in miner
	    public void initialize() {
	        comboBoxBlock.getItems().setAll(BlockLogic.getInstance().getsBlockbyMiner(SysData.getInstance().getLoggedInMiner().getUniqueAddress()));
	    }
	    
	    @FXML
	    void goBack(ActionEvent event) {
	    	WindowManager.goBack();
	    }
}


