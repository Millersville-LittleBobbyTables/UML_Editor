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
import uml_elements.Arrow;
import uml_elements.ArrowSelector;
import uml_elements.UMLLayout;

public class SumlWorkspace
{
	private Pane pane;
	
	private double width, height;
	
	public SumlWorkspace (double width, double height)
	{
		pane = new Pane ();
		pane.setMinWidth(width);
		pane.setMaxWidth(width);
		pane.setMinHeight(height);
		pane.setMaxHeight(height);
		pane.setBackground(new Background (
				new BackgroundFill (Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
		this.width = width;
		this.height = height;
		

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
        pane.setOnMouseClicked( e ->
        {
        	if (e.getX() > 0 && e.getX() < width &&
        		e.getY() > 0 && e.getY() < height)
        	{
	        	if (Main.toolBar.currentEditMode() == EditMode.ADD_CLASS)
	        	{
	        		UMLLayout uml = new UMLLayout(pane, e.getX(), e.getY());
	        		e.consume();
	        	}
	        	else if (Main.toolBar.currentEditMode() == EditMode.ADD_ARROW)
	        	{
	                Arrow arrow = ArrowSelector.getArrowSelected(
	                    ArrowSelector.getIndex(Main.toolBar.currentConnector()), pane.getScene());
	                pane.getChildren().addAll(arrow.getLine(), arrow.getTriangle());
	                arrow.setPosition(e.getX(), e.getY(), e.getX() + 50, e.getY());
	        	}
        	}
        });
	}
	
	public Pane getWorkspace ()
	{
		return pane;
	}
}
