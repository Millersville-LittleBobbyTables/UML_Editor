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
import javafx.stage.Stage;

public final class SumlMenuBar {

	public SumlMenuBar(Stage owner, Pane parent) {
		MenuBar menuBar = new MenuBar ();
		
		Menu fileMenu = new Menu ("File");
        Menu editMenu = new Menu ("Edit");
        MenuItem changeCanvasSizeMenuItem = new MenuItem ("Change Canvas Size");
        changeCanvasSizeMenuItem.setOnAction(e ->
        {
        	CanvasSizePopup changeCanvasSizePopup = new CanvasSizePopup (owner);
        	changeCanvasSizePopup.show ();
        });
        editMenu.getItems().add(changeCanvasSizeMenuItem);
        
        // Setting up menuBar formatting
        menuBar.setBackground(new Background (
        		new BackgroundFill (Color.SILVER, CornerRadii.EMPTY, Insets.EMPTY)));
        //menuBar.setBorder(botGreyBorder);
        menuBar.getMenus().addAll (fileMenu, editMenu);
        parent.getChildren().add(menuBar);
	}

}
