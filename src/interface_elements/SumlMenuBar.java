package interface_elements;

import application.Main;
import javafx.geometry.Insets;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public final class SumlMenuBar
{
	private MenuBar menuBar;
	
	public SumlMenuBar(Stage owner)
	{
		menuBar = new MenuBar ();
		
		Menu fileMenu = new Menu ("File");
        Menu editMenu = new Menu ("Edit");
        MenuItem changeWorkspaceSizeMenuItem = new MenuItem ("Change Workspace Size");
        changeWorkspaceSizeMenuItem.setOnAction(e ->
        {
        	WorkspaceSizePopup changeWorkspaceSizePopup = new WorkspaceSizePopup (owner, Main.workspace);
        	changeWorkspaceSizePopup.show ();
        });
        editMenu.getItems().add(changeWorkspaceSizeMenuItem);
        
        // Setting up menuBar formatting
        menuBar.setBackground(new Background (
        		new BackgroundFill (Color.SILVER, CornerRadii.EMPTY, Insets.EMPTY)));
        //menuBar.setBorder(botGreyBorder);
        menuBar.getMenus().addAll (fileMenu, editMenu);
	}
	
	public MenuBar getMenuBar ()
	{
		return menuBar;
	}

}
