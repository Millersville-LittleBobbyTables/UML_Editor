package interface_elements;

import application.Main;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Popup;

public final class SumlMenuBar {

	public SumlMenuBar(Pane parent) {
		MenuBar menuBar = new MenuBar ();
		
		Menu fileMenu = new Menu ("File");
        Menu editMenu = new Menu ("Edit");
        MenuItem changeCanvasSizeMenuItem = new MenuItem ("Change Canvas Size");
        changeCanvasSizeMenuItem.setOnAction(e ->
        {
        	Popup canvasSizePopup = new Popup ();
        	
        	HBox stuff = new HBox ();
        	TextField widthTextField = new TextField ();
        	TextField heightTextField = new TextField ();
        	Button submitButton = new Button ("submit");
        	stuff.getChildren().addAll(widthTextField, heightTextField, submitButton);
        	submitButton.setOnAction(f ->
        	{
        		try
        		{
        			Main.window_width = Double.valueOf(widthTextField.getText()).doubleValue();
        		} catch (NumberFormatException nfe)
        		{
        			System.out.println ("Could not parse canvas width.");
        		}
        		try
        		{
        			Main.window_height = Double.valueOf(heightTextField.getText()).doubleValue();
        		} catch (NumberFormatException nfe)
        		{
        			System.out.println ("Could not parse canvas height.");
        		}
        		System.out.println("width: " + window_width);
        		System.out.println("height: " + window_height);
        		
        		canvasSizePopup.hide();
        	});
        	canvasSizePopup.getContent().add(stuff);
        	canvasSizePopup.centerOnScreen();
        	canvasSizePopup.show (primaryStage);
        });
        editMenu.getItems().add(changeCanvasSizeMenuItem);
        
        // Setting up menuBar formatting
        menuBar.setBackground(new Background (
        		new BackgroundFill (Color.SILVER, CornerRadii.EMPTY, Insets.EMPTY)));
        //menuBar.setBorder(botGreyBorder);
        menuBar.getMenus().addAll (fileMenu, editMenu);;
	}

}
