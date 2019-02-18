package Control;

import javafx.application.Application;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.ListIterator;
import Control.Logger;
import Control.SysData;
import Control.Logic.BonusLogic;
import Control.Logic.LotteryLogic;
import Control.Logic.MinerLogic;
import Control.Logic.ParticipantLogic;
import Control.Logic.RiddleLogic;
import Model.Bonus;
import Model.Consts;
import Model.Lottery;
import Model.Participant;
import Model.Riddle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperReport;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class Main extends Application {

	private static String USER_NAME = "FlipCoinDesign";  // GMail user name (just the part before "@gmail.com")
	private static String PASSWORD = "zydmltwgnjzpocqr"; // GMail password
	private static String RECIPIENT = "gilit.cherf@gmail.com";
	private static String MSUBJECT = "Flip Coin Mining Lottery Results";
	private static String MBODY = "Sorry, you did not win in the lottery you participated in. Good Luck Next time!";


	public static String getUSER_NAME() {
		return USER_NAME;
	}

	public static String getPASSWORD() {
		return PASSWORD;
	}

	public static String getRECIPIENT() {
		return RECIPIENT;
	}

	public static void setRECIPIENT(String rECIPIENT) {
		RECIPIENT = rECIPIENT;
	}

	public static String getMSUBJECT() {
		return MSUBJECT;
	}

	public static void setMSUBJECT(String mSUBJECT) {
		MSUBJECT = mSUBJECT;
	}

	public static String getMBODY() {
		return MBODY;
	}

	public static void setMBODY(String mBODY) {
		MBODY = mBODY;
	}

	public void start(Stage primaryStage) {
		try {

			Pane root = (Pane)FXMLLoader.load(getClass().getResource("/View/login.fxml"));
			Scene scene = new Scene(root,800,600);

			scene.getStylesheets().add(getClass().getResource("/View/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.show();

			scene.getWindow().centerOnScreen();
			primaryStage.setResizable(false);


		} catch(Exception e) {
			e.printStackTrace();
		}


	}

	public static void sendFromGMail(String from, String pass, String[] to, String subject, String body) {
		Properties props = System.getProperties();
		String host = "smtp.gmail.com";
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.user", from);
		props.put("mail.smtp.password", pass);
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");

		Session session = Session.getInstance(props);


		try {
			MimeMessage message = new MimeMessage(session);

			message.setFrom(new InternetAddress(from));
			InternetAddress[] toAddress = new InternetAddress[to.length];

			// To get the array of addresses
			for( int i = 0; i < to.length; i++ ) {
				toAddress[i] = new InternetAddress(to[i]);
			}

			for( int i = 0; i < toAddress.length; i++) {
				message.addRecipient(Message.RecipientType.TO, toAddress[i]);
			}

			message.setSubject(subject);
			message.setText(body);
			Transport transport = session.getTransport("smtp");
			transport.connect(host, from, pass);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
			System.out.println("sent email");
		}
		catch (AddressException ae) {
			ae.printStackTrace();
		}
		catch (MessagingException me) {
			me.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {

		String from = USER_NAME;
		String pass = PASSWORD;
		String[] to = { RECIPIENT }; // list of recipient email addresses
		String subject = MSUBJECT;
		String body = MBODY;

		//sendFromGMail(from, pass, to, subject, body); // Sends email to recipient

		//Check if there are any unsolved Riddles who's solution date is today - if there is, update the status to Irrelevant------------
		ArrayList<Riddle> arr = RiddleLogic.getInstance().getALLRiddles();

		//Show only those riddles who are between today and the given solution Time
		//And were not solved yet
		ListIterator<Riddle> iter = arr.listIterator();
		Date today = new Date();
		while(iter.hasNext()){
			//Don't show the following riddles
			if(iter.next().getSolutionTime().equals(today) && iter.next().getStatus()=="Unsolved"){
				RiddleLogic.getInstance().updateRiddlestatus("Irrelevannt", iter.next().getRiddleNumber());
			}
		}

		//Check if there are any Lotteries who's finish date is today---------------------------------------------------------------------

		ArrayList<Lottery> lots = LotteryLogic.getInstance().getALLLotteries();
		ListIterator<Lottery> iterlots = lots.listIterator();
		while(iterlots.hasNext()){
			//Don't show the following riddles
			if(iterlots.next().getDateLot().equals(today) && iterlots.next().getLotStatus()==false){

				//choose bonuses and send emails
				int numWinners = iterlots.next().getNumberOfWinners();
				int numBonuses = iterlots.next().getNumberOfBonuses();
				int lotID = iterlots.next().getLotteryNumber();
				int lotMaxPartici = iterlots.next().getMaxParticipants();

				ArrayList<Bonus> allBonuses = BonusLogic.getInstance().getAllBonuses(); 
				ArrayList<Bonus> chosenBonuses = new ArrayList<Bonus>(); 

				//Get all participants in this lottery
				ArrayList<Participant> pars = ParticipantLogic.getInstance().getALLParticipantsInLottery(lotID);
				//Check how many participated comparing to the max amount and give them bonuses
				if(pars.size() <= lotMaxPartici) // Everyone gets a bonus
				{
					for(int i=0; i<numBonuses; i++) {
						//pick random bonuses
						Bonus chosen = getRandomElement(allBonuses);
						chosenBonuses.add(chosen); // Add all the chosen bonuses for the email we will later send
						for(Participant p: pars) {
							//For each participant add the bonus in getbonus table
							BonusLogic.getInstance().addBonusToUserInLottery(lotID, p.getUniqueAddress(), chosen.getBonusNumber());
							//Update that they won in this lottery
							ParticipantLogic.getInstance().updateParticipantsstatus(String.valueOf(1), lotID, p.getUniqueAddress());
						}
					}
					for(Participant p: pars) { //
					String emailTosendBody = "Congratulations! You have won " + numBonuses+ "In Lottery " + lotID + ". The bonuses you have won are:" + chosenBonuses.toString() + " Enjoy!";
					
					int mEmail = MinerLogic.getInstance().getALLMiners().indexOf(p);
					String emailAddress = MinerLogic.getInstance().getALLMiners().get(mEmail).getEmail();
					
					String[] toParticipant = new String[1];
					toParticipant[0] = emailAddress;
					
					//send email to the winners:
					sendFromGMail(from, pass, toParticipant, subject, emailTosendBody);
					}
				}
				else if(pars.size() > lotMaxPartici) // only the max num get bonuses
				{
					ArrayList<Participant> chosenWinners = new ArrayList<Participant>();
					for(int i=0; i<lotMaxPartici; i++) {
						Participant chosenp = getRandomPar(pars);
						if(!chosenWinners.contains(chosenp))
							chosenWinners.add(chosenp);
					}
						
					for(int i=0; i<numBonuses; i++) {
						//pick random bonuses
						Bonus chosen = getRandomElement(allBonuses);
						chosenBonuses.add(chosen); // Add all the chosen bonuses for the email we will later send
						for(Participant p: chosenWinners) {
							//For each participant add the bonus in getbonus table
							BonusLogic.getInstance().addBonusToUserInLottery(lotID, p.getUniqueAddress(), chosen.getBonusNumber());
							//Update that they won in this lottery
							ParticipantLogic.getInstance().updateParticipantsstatus(String.valueOf(1), lotID, p.getUniqueAddress());
						}
					}
					for(Participant p: chosenWinners) { //
					String emailTosendBody = "Congratulations! You have won " + numBonuses+ "In Lottery " + lotID + ". The bonuses you have won are:" + chosenBonuses.toString() + " Enjoy!";
					
					int mEmail = MinerLogic.getInstance().getALLMiners().indexOf(p);
					String emailAddress = MinerLogic.getInstance().getALLMiners().get(mEmail).getEmail();
					
					String[] toParticipant = new String[1];
					toParticipant[0] = emailAddress;
					
					//send email to the winners:
					sendFromGMail(from, pass, toParticipant, subject, emailTosendBody);
					}
					
					//For who did not win, send email too
					for(Participant p: pars) {
						if(!chosenWinners.contains(p)) {
							int mEmail = MinerLogic.getInstance().getALLMiners().indexOf(p);
							String emailAddress = MinerLogic.getInstance().getALLMiners().get(mEmail).getEmail();
							
							String[] toParticipant = new String[1];
							toParticipant[0] = emailAddress;
							
							//send email to the winners:
							sendFromGMail(from, pass, toParticipant, subject, body);
						}
					}
					
				}
				
				//Empty bonus list at the end
				chosenBonuses.replaceAll(null);
				//Edit lottery status to remember that lottery happened and emails were sent to the winners
				LotteryLogic.getInstance().updateLotterystatus(true, iterlots.next().getLotteryNumber());
			}
		}
		Logger.initializeMyFileWriter();
		Logger.log("Welcome");
		launch(args);
	}

	// Function select an element base on index  
	// and return an element 
	public static Bonus getRandomElement(List<Bonus> list) 
	{ 
		Random rand = new Random(); 
		return list.get(rand.nextInt(list.size())); 
	} 
	
	// Function select a random participant base on index  
	// and return an element 
	public static Participant getRandomPar(List<Participant> list) 
	{ 
		Random rand = new Random(); 
		return list.get(rand.nextInt(list.size())); 
	} 

}
