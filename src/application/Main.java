package application;

import interface_elements.SumlMenuBar;
import interface_elements.SumlToolBar;
import interface_elements.SumlToolBar.EditMode;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.input.MouseEvent;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;

import uml_elements.UMLLayout;
import uml_elements.ArrowSelector;
import uml_elements.Arrow;

public class Main extends Application 
{
    private final double X_MIN = 0;
    private final double Y_MIN = 0;
    private final double X_MAX = 1200;
    private final double Y_MAX = 850;
	
    public static double window_width = 1200;
    public static double window_height = 900;
    
    public static SumlMenuBar menuBar;
    public static SumlToolBar toolBar;

    public static void main(String[] args) 
    {
        launch(args);
    }

    /**
    * Start is a method that is inherited from Application
    * that will be run after the initial window is setup, and
    * functions as the pseudo main function for JavaFX applications
    */
    @Override
    public void start(Stage primaryStage) throws Exception 
    {
        BorderPane layout = new BorderPane();
        Scene scene = new Scene( layout, window_width, window_height );
        
        menuBar = new SumlMenuBar (primaryStage);
        toolBar = new SumlToolBar ();
        // For if we decide on adding tabs
        // TabPane tabBar = new TabPane ();
        //VBox top = new VBox (menuBar, toolBar, tabBar);
        VBox top = new VBox ();
        top.getChildren().addAll(menuBar.getMenuBar(), toolBar.getToolBar());
        Pane center = new Pane();
        
        // Set up layout
        layout.setBackground(new Background(
        		new BackgroundFill(Color.DARKGREY, CornerRadii.EMPTY, Insets.EMPTY)));
        layout.setTop(top);
        layout.setCenter(center);

        EventHandler<MouseEvent> consumeUnlessMouse = e ->
        {
        	if (toolBar.currentEditMode () != EditMode.MOUSE)
        	{
        		e.consume();
        	}
        };
        // Disallow deleting boxes unless in mouse mode
        center.addEventFilter(MouseEvent.MOUSE_PRESSED, consumeUnlessMouse);
        // Disallow dragging boxes and arrows allowed unless in mouse mode
        center.addEventFilter(MouseEvent.MOUSE_DRAGGED, consumeUnlessMouse);
        // Centers clicking logic will be based on the current mode we are in
        center.setOnMouseClicked( e ->
        {
        	if (e.getX() > X_MIN && e.getX() < X_MAX &&
        		e.getY() > Y_MIN && e.getY() < Y_MAX)
        	{
	        	if (toolBar.currentEditMode() == EditMode.ADD_CLASS)
	        	{
	        		UMLLayout uml = new UMLLayout(center);
	        		uml.setPosition (e.getX(), e.getY());
	        		e.consume();
	        	}
	        	else if (toolBar.currentEditMode() == EditMode.ADD_ARROW)
	        	{
	                Arrow arrow = ArrowSelector.getArrowSelected(
	                    ArrowSelector.getIndex(toolBar.currentConnector()), scene);
	                center.getChildren().addAll(arrow.getLine(), arrow.getTriangle());
	                arrow.setPosition(e.getX(), e.getY(), e.getX() + 50, e.getY());
	        	}
        	}
        });
        /* Re-add this in when we have containers for our elements and
         * can have the text fields gain and lose focus with mouse clicks
        scene.addEventHandler(KeyEvent.ANY, e ->
        {
        	if (e.getCode() == KeyCode.C)
        	{
        		addClassModeButton.setSelected(true);
        	}
        	else if (e.getCode() == KeyCode.A)
        	{
        		addArrowModeButton.setSelected(true);
        	}
        	else if (e.getCode() == KeyCode.M)
        	{
        		mouseModeButton.setSelected(true);
        	}
        });
        */
        
        scene.widthProperty().addListener(
        (observableValue, oldSceneWidth, newSceneWidth)->
        {
            window_width = newSceneWidth.doubleValue();
            System.out.println("Width: " + window_width);
        });
        
        scene.heightProperty().addListener(
        (observableValue, oldSceneHeight, newSceneHeight)->
        {
            window_height = newSceneHeight.doubleValue();
            System.out.println("Height: " + window_height);
        });

        primaryStage.setScene(scene);
        primaryStage.setTitle("Simple UML");
        primaryStage.show();
    }
}