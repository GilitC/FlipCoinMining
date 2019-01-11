package View.add;

import Control.Logic.TransactionLogic;
import Control.Logic.BlockLogic;
import Exceptions.ListNotSelectedException;
import Exceptions.MissingInputException;
import Model.Transaction;
import Model.Block;
import Model.Miner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import View.WindowManager;
import utils.E_Levels;

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
		alert.setTitle("Send Recomendation");
		alert.setHeaderText("");

		try {

			if (listTransactions.getSelectionModel().isEmpty()) {
				throw new ListNotSelectedException("Transaction");
			}

			if (comboBoxBlock.getSelectionModel().isEmpty()) {
				throw new ListNotSelectedException("Block");
			}
			
			String level = comboBoxLevl.getSelectionModel().getSelectedItem().returnLevel(comboBoxLevl.getSelectionModel().getSelectedItem());
			String publicAddress = listUsers.getSelectionModel().getSelectedItem().getPublicAddress();
			String userSignature = listUsers.getSelectionModel().getSelectedItem().getUserSignature();
			Integer recommedID = listRecommendations.getSelectionModel().getSelectedItem().getRecommedID();
			
			if (RecommendationLogic.getInstance().addRecommendationToUser(level, publicAddress, userSignature, recommedID)) {
				alert.setHeaderText("Success");
				alert.setContentText("Sent Recommendation to User succesfully!");
				alert.show();			
				initialize();
			} else {
				alert.setHeaderText("Unable to send Recommendation.");
				alert.setContentText("Recommendation wasn't sent.");
				alert.show();
			}


		} catch (ListNotSelectedException e) {
		}

    }
    
	    @FXML
	    void showRowDetails(ActionEvent event) {
	        ObservableList<Miner> userLst = FXCollections.observableArrayList(UserLogic.getInstance().getALLUsers());
	        listUsers.setItems(userLst);
	    }
	    
	    public void initialize() {
	        comboBoxBlock.getItems().setAll(BlockLogic.getInstance().getsBlockbyMiner(minerAddress));
	    }
	    
	    @FXML
	    void goBack(ActionEvent event) {
	    	WindowManager.goBack();
	    }
}


