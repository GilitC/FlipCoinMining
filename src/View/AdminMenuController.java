package View;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import Control.SysData;
import Control.Logic.MinerLogic;
import Control.Logic.TransactionLogic;
import Exceptions.ListNotSelectedException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import utils.NameToWindow;

public class AdminMenuController implements Initializable{

	@FXML
	private FlowPane menupane;

	@FXML
	private Button logout;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		WindowManager.setContentPane(this.menupane);
		loadUI(NameToWindow.WELCOMESCREEN);
	}


	public void loadUI(NameToWindow ui)
	{
		WindowManager.openWindow(ui);
	}

	public FlowPane getMenupane() {
		return menupane;
	}

	@FXML
	private void GoToHomePage(ActionEvent event) {
		loadUI(NameToWindow.WELCOMESCREEN);
	}
	
	@FXML
	private void GoToImporttoFCM(ActionEvent event) {
		loadUI(NameToWindow.WELCOMESCREEN);
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Import");
		alert.setHeaderText("");

		TransactionLogic.getInstance().importTransactionsFromJSON("json/txs.json");
		alert.setHeaderText("Success");
		alert.setContentText("Imported Transactions From JSON Successfully!");
		alert.show();			
	}
	
	@FXML
	private void GoToExporttoFCT(ActionEvent event) {
		loadUI(NameToWindow.WELCOMESCREEN);
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Export");
		alert.setHeaderText("");

		TransactionLogic.getInstance().exportTransactionsToXML();
		alert.setHeaderText("Success");
		alert.setContentText("Exported Transactions in XML File to FlipCoin Transfer Successfully!");
		alert.show();	
	}

		
    @FXML
    void GoToVIewTransRep(ActionEvent event) {
    	loadUI(NameToWindow.MENU_VIEWRECOMMENDEDTRANS);
    }

    @FXML
    void GoToAddTransToBlock(ActionEvent event) {
    	loadUI(NameToWindow.MENU_ADDTRANSTOBLOCK);
    }


	@FXML
	void GoToLogin(ActionEvent event) throws IOException {
		Stage stage = (Stage) menupane.getScene().getWindow();
        SysData.getInstance().setLoggedInMiner(null);

		stage.close();
		FXMLLoader load = new FXMLLoader(getClass().getResource("/view/login.fxml"));
		Stage primaryStage = new Stage();
		Parent root = load.load();
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}

