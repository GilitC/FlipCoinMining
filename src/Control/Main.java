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
import Control.Logic.RiddleLogic;
import Model.Consts;
import Model.Riddle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperReport;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	
	
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
			
			//Check if there are any unsolved Riddles who's solution date is today - if there is, update the status to Irrelevant
			ArrayList<Riddle> arr = RiddleLogic.getInstance().getALLRiddles();

			//Show only those riddles who are between today and the given solution Time
			//And were not solved yet
			ListIterator<Riddle> iter = arr.listIterator();
			Date today = new Date();
			while(iter.hasNext()){
				//Don't show the following riddles
				if(iter.next().getSolutionTime().equals(today) && iter.next().getStatus()=="UnSolved"){
					RiddleLogic.getInstance().updateRiddlestatus("Irrelevannt", iter.next().getRiddleNumber());
				}
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception {
		
		Logger.initializeMyFileWriter();
		Logger.log("Welcome");
		launch(args);
	}

	
}
