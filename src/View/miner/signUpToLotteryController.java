package View.miner;

import java.util.ArrayList;
import java.util.Date;
import Control.SysData;
import Control.Logic.LotteryLogic;
import Control.Logic.ParticipantLogic;
import Exceptions.ListNotSelectedException;
import Model.Lottery;
import Model.Miner;
import Model.Participant;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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

	private Miner mIn;

	@FXML
	void goToAddUserToLot(ActionEvent event) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Sign Up To Lottery");
		alert.setHeaderText("");

		try {

			if (comboBoxLotteries.getSelectionModel().isEmpty()) {
				throw new ListNotSelectedException("Lottery");
			}

			mIn = SysData.getInstance().getLoggedInMiner();

			Lottery l = comboBoxLotteries.getSelectionModel().getSelectedItem();
			
			//Check the participants of the lottery
			ArrayList<Participant> allParticipants= ParticipantLogic.getInstance().getALLParticipantsInLottery(l.getLotteryNumber());
			if(allParticipants.contains(mIn)){
				alert.setHeaderText("Unable to Sign Up");
				alert.setContentText("You have already signed up to this Lottery.");
				alert.show();
			}
			else if(allParticipants.size()>=l.getMaxParticipants()){
				alert.setHeaderText("Unable to Sign Up");
				alert.setContentText("Lottery is Full. Please try again.");
				alert.show();
			}
			else if (ParticipantLogic.getInstance().addParticipant(l.getLotteryNumber(), mIn.getUniqueAddress(), String.valueOf(0))) {
		
				initialize();
				lblSuccess.setText("Signed up to lottery successfully!");
				lblSuccess.setVisible(true);
			} else {
				alert.setHeaderText("Unable to Sign Up");
				alert.setContentText("Please try again later.");
				alert.show();
			}


		} catch (ListNotSelectedException e) {
		}
	}


	//Initialize the combobox 
	public void initialize() {
		//Checks that the Date of the lottery did not pass
		// Later after lottery is picked we will check how many already signed up to this lottery and that our logged in miner is not one of them
		ArrayList<Lottery> allL = LotteryLogic.getInstance().getALLLotteries();
		ArrayList<Lottery> lotsToShow = new ArrayList<Lottery>();
		for(Lottery lt : allL){
			Date today = new Date();
			if(today.before(lt.getDateLot())) {
				lotsToShow.add(lt);
			}
		}
		comboBoxLotteries.getItems().setAll(lotsToShow);
		lblSuccess.setVisible(false);
		}

	@FXML
	void goBack(ActionEvent event) {
		WindowManager.goBack();
	}
}


