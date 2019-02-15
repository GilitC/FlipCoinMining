package View.admin;


import Control.Logic.RiddleLogic;
import Exceptions.ListNotSelectedException;
import Model.Riddle;
import Model.RiddleLevel;
import View.WindowManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;

public class editRiddlesLvlController {

	@FXML
    private TextField pbdate;

    @FXML
    private TextField pbtime;

    @FXML
    private ComboBox<Riddle> comboRiddles;

    @FXML
    private ComboBox<RiddleLevel> comboLevel;

    @FXML
    private AnchorPane addReco;

    @FXML
    private Button back;

    @FXML
    private TextField riddleStatus;

    @FXML
    private TextField numberRiddle;

    @FXML
    private Button saveButton;

    @FXML
    private Label labelSuccess;

    @FXML
    private TextField solutiontm;

    @FXML
    private TextArea desc;
	
    @FXML
    void goBack(ActionEvent event) {
    	WindowManager.goBack();
    }

    @FXML
    void saveProduct(ActionEvent event) throws ListNotSelectedException{
    	Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Edit Riddle");
		alert.setHeaderText("");
		Riddle myRid = comboRiddles.getSelectionModel().getSelectedItem();
		
		try {

			if (comboLevel.getSelectionModel().isEmpty()) {
				throw new ListNotSelectedException("Riddle Level");
			}

			
			if (RiddleLogic.getInstance().updateRiddleLevelID(comboLevel.getSelectionModel().getSelectedItem().getLevelCode(), myRid.getRiddleNumber())) {
				labelSuccess.setText("Riddle level updated successfully!");		
				initialize();
			} else {
				alert.setHeaderText("Unable to add.");
				alert.setContentText("Riddle wasn't added.");
				alert.show();
			}


		} catch (ListNotSelectedException e) {
		} 

    }

    
    @FXML
    void showProdDetails(ActionEvent event) {
    	Riddle myRid = comboRiddles.getSelectionModel().getSelectedItem();
    	
    	if (myRid == null) {
    		return;
    	}
    	int riddleID = RiddleLogic.getInstance().getALLRiddles().indexOf(myRid);
    	Riddle r = RiddleLogic.getInstance().getALLRiddles().get(riddleID);

    	
    	numberRiddle.setText((String.valueOf(r.getRiddleNumber())));
		pbdate.setText(String.valueOf(r.getPublishDate()));
		pbtime.setText(String.valueOf(r.getPublishTime()));
		solutiontm.setText(String.valueOf(r.getSolutionTime()));
    	desc.setText(r.getDescription());
    	riddleStatus.setText(r.getStatus());
    	comboLevel.getItems().setAll((RiddleLogic.getInstance().getALLRiddleLevels()));
    	saveButton.setVisible(true);
    	
    	numberRiddle.setVisible(true);
		pbdate.setVisible(true);
		pbtime.setVisible(true);
		solutiontm.setVisible(true);
    	desc.setVisible(true);
    	riddleStatus.setVisible(true);
    	comboLevel.setVisible(true);
    	saveButton.setVisible(true);
    }

    //Initialize the combobox with the available riddles
    public void initialize() {
    	comboRiddles.getItems().setAll(RiddleLogic.getInstance().getALLRiddles());
    	comboLevel.getItems().setAll(RiddleLogic.getInstance().getALLRiddleLevels());
		
		numberRiddle.setVisible(false);
		pbdate.setVisible(false);
		pbtime.setVisible(false);
		solutiontm.setVisible(false);
    	desc.setVisible(false);
    	riddleStatus.setVisible(false);
    	comboLevel.setVisible(false);
    	saveButton.setVisible(false);
    }

}