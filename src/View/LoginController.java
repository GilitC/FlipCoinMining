package View;

import java.io.IOException;
import Exceptions.InvalidInputException;
import Exceptions.MissingInputException;
import Model.Miner;
import Control.SysData;
import Control.Logic.MinerLogic;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;

public class LoginController {
	/**
	 * class fields
	 */
	public static Boolean override = true;

	@FXML
	private AnchorPane login;

	@FXML
	private PasswordField password;

	@FXML
	private TextField email;

	@FXML
	private Button SignIn;

	@FXML
	private Button SignUp;

	@FXML // fx:id="exitImage"
	private ImageView exitImage; // Value injected by FXMLLoader

	@FXML
	/**
	 * Confirms that user actually wants to exit program if clicks on x.exists if sure.
	 * @param event when clicked on x button
	 */
	void ExitProgram(MouseEvent event) {
		Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to exit?", ButtonType.YES, ButtonType.NO,
				ButtonType.CANCEL);
		alert.showAndWait();

		if (alert.getResult() == ButtonType.YES) {
			System.exit(0);
		} else {
			alert = new Alert(AlertType.INFORMATION, "Good choice!");
			alert.showAndWait();
		}
	}
	
	@FXML
	/**
	 * function activated when login button is pressed. checks user and password and opens a menu accordingly.
	 * @param event login button is pressed
	 * @throws InvalidInputException thrown if bad input
	 * @throws IOException
	 * @throws MissingInputException thrown is didn't input in one of the fields
	 */
	void GoToMenu(ActionEvent event) throws InvalidInputException, IOException, MissingInputException {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Login");
		alert.setHeaderText("");
		String user = email.getText();
		String pass = password.getText();	
		try {
			if(user.isEmpty()) {
				throw new MissingInputException("Please enter a username");
			}
			if(pass.isEmpty()) {
				throw new MissingInputException("please enter a password");
			}
			if (user.equals("admin") && pass.equals("admin")) {//if admin has enterred, open a menu accordingly

				Stage stage = (Stage) login.getScene().getWindow();
				stage.close();
				FXMLLoader load = new FXMLLoader(getClass().getResource("/View/AdminMenuSidebar.fxml"));

				Stage primaryStage = new Stage();
				Parent root = load.load();
				Scene scene = new Scene(root);
				primaryStage.setScene(scene);
				primaryStage.show();
			} else if (!MinerLogic.getInstance().getALLMiners().isEmpty()) {//if miners exist
				Miner mm = new Miner(user);
				if (MinerLogic.getInstance().getALLMiners().contains(mm)) {
					if (MinerLogic.getInstance().getALLMiners().get(MinerLogic.getInstance().getALLMiners().indexOf(mm)).getPassword() != null) {
						if (MinerLogic.getInstance().getALLMiners().get(MinerLogic.getInstance().getALLMiners().indexOf(mm)).getPassword().equals(pass)) {
							SysData.getInstance().setLoggedInMiner(MinerLogic.getInstance().getALLMiners().get(MinerLogic.getInstance().getALLMiners().indexOf(mm)));
										
							Stage stage = (Stage) login.getScene().getWindow();
							stage.close();
							FXMLLoader load = new FXMLLoader(getClass().getResource("/View/AdminMenuSidebar.fxml"));

							Stage primaryStage = new Stage();
							Parent root = load.load();
							Scene scene = new Scene(root);
							primaryStage.setScene(scene);
							primaryStage.show();
						}
						throw new InvalidInputException("Wrong password. Please try again.");
					}
			}
	
			} else {//if no active user has entered, popup wrong username and password.
				alert.setHeaderText("failed to login.");
				alert.setContentText("wrong username/password");
				alert.show();
			}
		}catch(NumberFormatException e) {//catch exception if trying to convert string to integer
			new InvalidInputException("numbers only in this field please");
		}catch(InvalidInputException e) {
			
		}catch(MissingInputException e) {
			
		}

	}

	@FXML
	void register(ActionEvent event) throws IOException {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Login");
		alert.setHeaderText("");
		alert.setHeaderText("Forgot Password?");
		alert.setContentText("To reset your password, Please contact us at Admin@FlipCoinMining.com. Thank you!");
		alert.show();
	}

}
