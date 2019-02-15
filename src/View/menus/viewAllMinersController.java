package View.menus;


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
		ObservableList<Miner> mins = FXCollections.observableArrayList(MinerLogic.getInstance().getALLMiners());
		listofSentUsers.setItems(mins);
	}

	@FXML
	void goBack(ActionEvent event) {
		WindowManager.goBack();
	}
}


