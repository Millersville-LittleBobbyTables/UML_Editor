package interface_elements;

import application.Main;
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

public class WorkspaceSizePopup
{
	private Stage stage;
	
	final private int textFieldWidth = 100;
	
	public WorkspaceSizePopup (Stage owner)
	{
		stage = new Stage ();
		GridPane pane = new GridPane ();
		Scene scene = new Scene (pane);
		
		Text widthLabel = new Text ("Width:");
		GridPane.setConstraints(widthLabel, 0, 0);
		
		TextField widthField = new TextField ();
		widthField.setMaxWidth(textFieldWidth);
		widthField.setText(Double.toString(Main.window_width));
		GridPane.setConstraints(widthField, 1, 0);
		
		Text heightLabel = new Text ("Height:");
		GridPane.setConstraints(heightLabel, 2, 0);
		
		TextField heightField = new TextField ();
		heightField.setMaxWidth(textFieldWidth);
		heightField.setText(Double.toString(Main.window_height));
		GridPane.setConstraints(heightField, 3, 0);
		
		Button submit = new Button ("Submit");
		GridPane.setConstraints(submit, 3, 1, 1, 1, HPos.RIGHT, VPos.CENTER);
		submit.setOnAction(e ->
		{
			try
		    {
				Main.window_width = Double.valueOf(widthField.getText()).doubleValue();
	    	} catch (NumberFormatException nfe)
			{
	           System.out.println ("Could not parse workspace width.");
			}
			try
			{
	           Main.window_height = Double.valueOf(heightField.getText()).doubleValue();
			} catch (NumberFormatException nfe)
			{
	           System.out.println ("Could not parse workspace height.");
			}
			System.out.println("width: " + Main.window_width);
			System.out.println("height: " + Main.window_height);
			close ();
		});
		
		pane.setPadding(new Insets (15));
		pane.setHgap(20);
		pane.setVgap(10);
		pane.getChildren().addAll(widthLabel, widthField, heightLabel, heightField, submit);
		
		stage.setScene(scene);
		stage.initOwner(owner);
		stage.initModality(Modality.WINDOW_MODAL);
		stage.setTitle("Change Workspace Size");
		stage.setResizable(false);
	}
	
	public void show ()
	{
		stage.show ();
	}
	
	public void close ()
	{
		stage.close();
	}
	
}
