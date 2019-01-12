package View;

import Control.Logic.TransactionLogic;

import java.util.ArrayList;
import java.util.Collections;
import Control.SysData;
import Control.Logic.BlockLogic;
import Model.Transaction;
import Model.Block;
import Model.Pair;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import View.WindowManager;


public class recommendedPairsReport {


    @FXML
    private ComboBox<Block> comboBoxBlock;

    @FXML
    private Button back;

    @FXML
    private ListView<Pair> listTransactions; // Report of recommended pairs

    @FXML
    private AnchorPane viewRecommend;

    
    //Pick what transactions to show to the user
	    @FXML
	    void showRowDetails(ActionEvent event) {
	    	//Save the size of chosen block
	    	int blockchosenSize = comboBoxBlock.getSelectionModel().getSelectedItem().getSize();
	    	
			ArrayList<Transaction> trans= TransactionLogic.getInstance().getAllTransactions();
			ArrayList<Pair> pairTrans = new ArrayList<>();
			for(int i = 0 ; i < trans.size() ; i++) {//iterate arraylist to check and put valid pairs to hashmap
				for( int j = i+1 ; j<trans.size() ; j++) {
					if((trans.get(i).getSize()+trans.get(j).getSize())<=blockchosenSize) {
						pairTrans.add(new Pair(trans.get(i), trans.get(j)));
					}
				}
			}
			//when we add it to list , it should be sorted by most beneficial fee , from the most mishtalem to the less mishtalem
			Collections.sort(pairTrans);
	    	
	        ObservableList<Pair> trnsLst = FXCollections.observableArrayList(pairTrans);
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


