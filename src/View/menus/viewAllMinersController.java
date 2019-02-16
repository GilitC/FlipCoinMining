package View.menus;


import java.util.ArrayList;

import Control.SysData;
import Control.Logic.MinerLogic;
import Model.Miner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import View.WindowManager;

public class viewAllMinersController {

	@FXML
	private ListView<Miner> listofSentUsers;

	@FXML
	private Button back;

	@FXML
	private AnchorPane viewRecommend;


	public void initialize() {
		ArrayList<Miner> allM = MinerLogic.getInstance().getALLMiners();
		if(allM!=null) {
			allM.remove(SysData.getInstance().getLoggedInMiner()); // Remove logged in miner from all miners list
			ObservableList<Miner> mins = FXCollections.observableArrayList(allM);
			listofSentUsers.setItems(mins);
		}
	}

	@FXML
	void goBack(ActionEvent event) {
		WindowManager.goBack();
	}
}


