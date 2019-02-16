package View.miner;

import Control.Logger;
import Control.Logic.BusinessCLogic;
import Control.Logic.MinerLogic;
import Exceptions.EmailNotValidException;
import Exceptions.MissingInputException;
import View.WindowManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;

public class SignUpMinerController {

	@FXML
	private TextField cphone;

	@FXML
	private Button clearButton;

	@FXML
	private PasswordField pass;

	@FXML
	private TextField name;

	@FXML
	private TextField cname;

	@FXML
	private AnchorPane addReco;

	@FXML
	private Button signUpButton;

	@FXML
	private TextField generatedUsername;

	@FXML
	private TextField cemail;

	@FXML
	private Label labelSuccess;

	@FXML
	private CheckBox checkBoxCompany;

	@FXML
	private TextField email;

	/**
	 * goes back to previous page
	 * 
	 * @param event back button is pressed
	 */
	@FXML
	void goBack(ActionEvent event) {
		WindowManager.goBack();
	}

	@FXML
	void showCompanyFields(ActionEvent event) {
		if(checkBoxCompany.isSelected()) {
			cname.setVisible(true);
			cphone.setVisible(true);
			cemail.setVisible(true);

		}
		else
		{
			cname.setVisible(false);
			cphone.setVisible(false);
			cemail.setVisible(false);
		}
	}

	@FXML
	/**
	 * Adds a new Miner once the sign up button is pressed
	 * 
	 * @param event  button is pressed
	 * @throws IOException           input exception might occur
	 * @throws MissingInputException missing input exception 
	 * @throws EmailNotValidException if email is not valid
	 *    
	 */
	void addUser(ActionEvent event) throws MissingInputException, EmailNotValidException {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Sign Up");
		alert.setHeaderText("");

		String nameMiner = name.getText();
		String psw = pass.getText();
		String emailAdd = email.getText();


		try {

			if (nameMiner.isEmpty()) {
				throw new MissingInputException("Name");
			}
			if (psw.isEmpty()) {
				throw new MissingInputException("Password");
			}
			if (emailAdd.isEmpty()) {
				throw new MissingInputException("Email");
			}

			if(!isEmail(emailAdd)) {
				throw new EmailNotValidException();
			}
			

			if(checkBoxCompany.isSelected()) {

				String cnameComp = cname.getText();
				String companyphone = cname.getText();
				String companyEmail= cemail.getText(); 

				if (cnameComp.isEmpty()) {
					throw new MissingInputException("Company Name");
				}
				if (companyphone.isEmpty()) {
					throw new MissingInputException("Company Phone");
				}
				if (companyEmail.isEmpty()) {
					throw new MissingInputException("Company Email");
				}
				if(!isEmail(companyEmail)) {
					throw new EmailNotValidException();
				}
			}


			//Add miner
			String generateUsername = MinerLogic.getInstance().addMiner(nameMiner, psw, 0.0, emailAdd);
			if (generateUsername!=null) {
				if(checkBoxCompany.isSelected()) {
					String cnameComp = cname.getText();
					String companyphone = cname.getText();
					String companyEmail= cemail.getText(); 
					BusinessCLogic.getInstance().addBusinessCompany(generateUsername, cnameComp, companyphone, companyEmail);
				}
				Logger.log("Successful sign up");
				initialize();

				labelSuccess.setText("Thank you for signing up! Your generated Unique username is:");
				
				if(!MinerLogic.getInstance().getALLMiners().isEmpty()) //Makes sure miner was added to database
				{

					generatedUsername.setText(generateUsername);
					//Show user the new generated value
					generatedUsername.setVisible(true);
				}

			} else {
				Logger.log("Error signing up user");
				alert.setHeaderText("Sign Up Failed");
				alert.setContentText("We are sorry, please try again later.");
				alert.show();
			}


		} catch (MissingInputException e) {
		}
		catch (EmailNotValidException e) {
		}
	}

	public void initialize() {
		generatedUsername.setVisible(false);

		name.setText("");
		pass.setText("");	
		email.setText("");
		cname.setText("");
		cphone.setText("");
		cemail.setText("");
		
		cname.setVisible(false);
		cphone.setVisible(false);
		cemail.setVisible(false);

		labelSuccess.setText("");


		cphone.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("^([0-9]+\\\\.?[0-9]*|[0-9]*\\\\.[0-9]+)$")) {
				cphone.setText(newValue.replaceAll("^([0-9]+\\\\.?[0-9]*|[0-9]*\\\\.[0-9]+)$", ""));
			}
		});


		name.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("\\sa-zA-Z*")) {
				name.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
			}
		});

		cname.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("\\sa-zA-Z*")) {
				cname.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
			}
		});

	}

	//Checks if email is valid
	public boolean isEmail(String s) {
		return s.matches("^[-0-9a-zA-Z.+_]+@[-0-9a-zA-Z.+_]+\\.[a-zA-Z]{2,4}");
	}

	/**
	 * Clears everything in the form.
	 * @param event when clear button is pressed.
	 */
	@FXML
	void clearForm(ActionEvent event) {
		initialize();
	}


}
