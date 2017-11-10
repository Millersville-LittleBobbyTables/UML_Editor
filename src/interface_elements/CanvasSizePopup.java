package interface_elements;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CanvasSizePopup {

	private Stage stage;
	
	private double popupWidth = 250, popupHeight = 120;
	
	CanvasSizePopup (Stage owner)
	{
		stage = new Stage ();
		GridPane pane = new GridPane ();
		pane.setPadding(new Insets (15));
		Scene scene = new Scene (pane);
		
		Text widthLabel = new Text ("Width:");
		GridPane.setConstraints(widthLabel, 0, 0);
		TextField widthField = new TextField ();
		widthField.setMaxWidth(100);
		GridPane.setConstraints(widthField, 1, 0);
		
		Text heightLabel = new Text ("Height:");
		GridPane.setConstraints(heightLabel, 2, 0);
		TextField heightField = new TextField ();
		heightField.setMaxWidth(100);
		GridPane.setConstraints(heightField, 3, 0);
		
		//GridPane.setConstraints(child, columnIndex, rowIndex, columnspan, rowspan, halignment, valignment, hgrow, vgrow, margin);
		
		Button submit = new Button ("Submit");
		GridPane.setConstraints(submit, 3, 1, 1, 1, HPos.RIGHT, VPos.CENTER);
		
		pane.setHgap(20);
		pane.setVgap(10);
		pane.getChildren().addAll(widthLabel, widthField, heightLabel, heightField, submit);
		
		stage.setScene(scene);
		stage.initOwner(owner);
		stage.initModality(Modality.WINDOW_MODAL);
		stage.setTitle("Change Canvas Size");
		stage.setResizable(false);
		stage.show ();
	}
	
}
