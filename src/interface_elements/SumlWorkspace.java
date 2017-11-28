package interface_elements;

import application.Main;
import interface_elements.SumlToolBar.EditMode;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import uml_elements.ArrowContainer;
import uml_elements.UMLLayout;

public class SumlWorkspace
{
	private Pane pane;
	private ArrowContainer container;
	
	public SumlWorkspace (double width, double height)
	{
		pane = new Pane ();
		pane.setMinWidth(width);
		pane.setMaxWidth(width);
		pane.setMinHeight(height);
		pane.setMaxHeight(height);
		pane.setBackground(new Background (
				new BackgroundFill (Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
		
        EventHandler<MouseEvent> consumeUnlessMouse = e ->
        {
        	if (Main.toolBar.currentEditMode () != EditMode.MOUSE)
        	{
        		e.consume();
        	}
        };

        // Disallow deleting boxes unless in mouse mode
        pane.addEventFilter(MouseEvent.MOUSE_PRESSED, consumeUnlessMouse);
        // Disallow dragging boxes and arrows allowed unless in mouse mode
        pane.addEventFilter(MouseEvent.MOUSE_DRAGGED, consumeUnlessMouse);
        // Centers clicking logic will be based on the current mode we are in
        container = new ArrowContainer(pane, Main.scene);
        pane.setOnMouseClicked( e ->
        {
        	if (e.getX() > 0 && e.getX() < pane.getWidth() &&
        		e.getY() > 0 && e.getY() < pane.getHeight())
        	{
	        	if (Main.toolBar.currentEditMode() == EditMode.ADD_CLASS)
	        	{
	        		new UMLLayout(pane, e.getX(), e.getY());
	        		e.consume();
	        	}
	        	else if (Main.toolBar.currentEditMode() == EditMode.ADD_ARROW)
	        	{
	        		container.addArrow(Main.toolBar.currentConnector(), e.getX(), e.getY());
	        		e.consume();
	        	}
        	}
        });
	}
	
	public void setSize (double width, double height)
	{
		pane.setMinSize(width, height);
		pane.setMaxSize(width, height);
	}
	
	public void setWidth (double width)
	{
		pane.setMinWidth(width);
		pane.setMaxWidth(width);
	}
	
	public void setHeight (double height)
	{
		pane.setMinHeight(height);
		pane.setMaxHeight(height);
	}
	
	public double getWidth ()
	{
		return pane.getWidth();
	}
	
	public double getHeight ()
	{
		return pane.getHeight();
	}
	
	public Pane getWorkspace ()
	{
		return pane;
	}
}
