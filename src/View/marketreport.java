/**
 * Sample Skeleton for 'viewOrders.fxml' Controller Class
 */

package View;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import Control.Logic.MinerLogic;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class marketreport {
	private static Integer row_id = 1;
	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="viewRecommend"
	private AnchorPane viewRecommend; // Value injected by FXMLLoader

	@FXML
	private TableView<RowData> tblMarket;

	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() {
		assert viewRecommend != null : "fx:id=\"viewRecommend\" was not injected: check your FXML file 'viewOrders.fxml'.";
		row_id = 1;
		List<TableColumn<RowData, ?>> colList = new ArrayList<>();
		colList.add(new TableColumn<RowData, Integer>("Hour"));
		colList.get(0).setCellValueFactory(new PropertyValueFactory<>("hour"));
		colList.add(new TableColumn<RowData, Double>("# Blocks"));
		colList.get(1).setCellValueFactory(new PropertyValueFactory<>("numBlocks"));
		colList.add(new TableColumn<RowData, Double>("# Miners"));
		colList.get(2).setCellValueFactory(new PropertyValueFactory<>("numMiners"));
		colList.add(new TableColumn<RowData, Double>("# Transactions"));
		colList.get(3).setCellValueFactory(new PropertyValueFactory<>("numTransactions"));

		ResultSet resultSet = MinerLogic.getInstance().generateDailyMarketReport();
		List<RowData> resultList = new ArrayList<>();
		if(resultSet != null)
		{
			try {
				while (resultSet.next()) {
					resultList.add(new RowData(resultSet.getInt("Hour"), resultSet.getInt("blockCount"), resultSet.getInt("minerCount"), resultSet.getInt("countTransactions")));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	

		tblMarket.getColumns().addAll(colList);

		ObservableList<RowData> data = FXCollections.observableArrayList(resultList);

		tblMarket.setItems(data);
	}

	public static class RowData {
		private final SimpleIntegerProperty id;
		private final SimpleIntegerProperty hour;
		private final SimpleIntegerProperty numBlocks;
		private final SimpleIntegerProperty numMiners;
		private final SimpleIntegerProperty numTransactions;

		private RowData(Integer hour, Integer numBlocks, Integer numMiners, Integer numTransactions) {
			this.id = new SimpleIntegerProperty(row_id++);
			this.hour = new SimpleIntegerProperty(hour);
			this.numBlocks = new SimpleIntegerProperty(numBlocks == null ? 0 : numBlocks);
			this.numMiners = new SimpleIntegerProperty(numMiners == null ? 0 : numMiners);
			this.numTransactions = new SimpleIntegerProperty(numTransactions == null ? 0 : numTransactions);

		}

		public Integer getId() {
			return this.id.get();
		}

		public Integer getHour() {
			return this.hour.get();
		}

		public Integer getNumBlocks() {
			return this.numBlocks.get();
		}

		public Integer getNumMiners() {
			return this.numMiners.get();
		}

		public Integer getNumTransactions() {
			return this.numTransactions.get();
		}
	}

}