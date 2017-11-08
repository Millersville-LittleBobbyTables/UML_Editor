package application;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.paint.Color;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
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
	
    private String currentConnector = ArrowSelector.ArrowType[0];
    public static double window_width = 1200;
    public static double window_height = 900;

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
        
        HBox toolBar = new HBox();
        // For if we decide on adding tabs
        // TabPane tabBar = new TabPane ();
        //VBox top = new VBox (menuBar, toolBar, tabBar);
        VBox top = new VBox (menuBar, toolBar);
        Pane center = new Pane();
        
        // Set up layout
        layout.setBackground(new Background(
        		new BackgroundFill(Color.DARKGREY, CornerRadii.EMPTY, Insets.EMPTY)));
        layout.setTop(top);
        layout.setCenter(center);
        
        Border botGreyBorder = new Border (new BorderStroke (
        		Color.GREY, Color.GREY, Color.GREY, Color.GREY,
        		BorderStrokeStyle.SOLID, BorderStrokeStyle.NONE, BorderStrokeStyle.SOLID,
        		BorderStrokeStyle.NONE, CornerRadii.EMPTY, BorderStroke.THIN, Insets.EMPTY));
        
        // Set up toolBar formatting
        toolBar.setPadding( new Insets(5));
        toolBar.setSpacing(10);
        toolBar.setBackground(new Background (
    		new BackgroundFill (Color.LIGHTGREY, CornerRadii.EMPTY, Insets.EMPTY)));
        toolBar.setBorder(botGreyBorder);
        
        ToggleGroup editingModes = new ToggleGroup ();
        
        // Set up the 'mouse mode' ToggleButton
        ImageView mouseModeImage = new ImageView(new Image ("mouse.png"));
        mouseModeImage.setFitWidth(20);
        mouseModeImage.setFitHeight(20);
        ToggleButton mouseModeButton = new ToggleButton("", mouseModeImage);
        mouseModeButton.setToggleGroup(editingModes);
        mouseModeButton.setTooltip(new Tooltip ("Mouse mode"));
        mouseModeButton.setSelected(true);
        mouseModeButton.setOnMouseClicked(e ->
        {
        	mouseModeButton.setSelected(true);
        });
        
        // Set up the 'add class mode' ToggleButton
        ImageView addClassModeImage = new ImageView(new Image ("AddClass.png"));
        addClassModeImage.setFitWidth (20);
        addClassModeImage.setFitHeight (20);
        ToggleButton addClassModeButton = new ToggleButton ("", addClassModeImage);
        addClassModeButton.setToggleGroup(editingModes);
        addClassModeButton.setTooltip(new Tooltip ("Add class mode"));
        addClassModeButton.setOnMouseClicked(e ->
        {
        	addClassModeButton.setSelected(true);
        });
        
        // Set up the 'add arrow mode' ToggleButton
        ImageView addArrowModeImage = new ImageView(new Image ("AddArrow.png"));
        addArrowModeImage.setFitWidth (22);
        addArrowModeImage.setFitHeight (22);
        ToggleButton addArrowModeButton = new ToggleButton ("", addArrowModeImage);
        addArrowModeButton.setToggleGroup(editingModes);
        addArrowModeButton.setTooltip(new Tooltip ("Add arrow mode"));
        addArrowModeButton.setOnMouseClicked(e ->
        {
        	addArrowModeButton.setSelected(true);
        });
        
        // Set up the connectorSelector ChoiceBox
        ChoiceBox<String> arrowTypeSelector = new ChoiceBox<>();
        arrowTypeSelector.getItems().addAll(ArrowSelector.ArrowType);
        arrowTypeSelector.setValue( ArrowSelector.ArrowType[0] );
        arrowTypeSelector.getSelectionModel().selectedItemProperty()
            .addListener(( v, oldValue, newValue ) -> 
        {
            if ( oldValue != newValue ) currentConnector = newValue;
        });
        arrowTypeSelector.setPrefHeight(20);
        arrowTypeSelector.setTooltip(new Tooltip ("Arrow type"));
        
        toolBar.getChildren().addAll(mouseModeButton, addClassModeButton,
        		addArrowModeButton, arrowTypeSelector);
        
        EventHandler<MouseEvent> consumeUnlessMouse = e ->
        {
        	if (!mouseModeButton.isSelected())
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
	        	if (addClassModeButton.isSelected())
	        	{
	        		UMLLayout uml = new UMLLayout(center);
	        		uml.setPosition (e.getX(), e.getY());
	        		e.consume();
	        	}
	        	else if (addArrowModeButton.isSelected())
	        	{
	                Arrow arrow = ArrowSelector.getArrowSelected(
	                    ArrowSelector.getIndex(currentConnector), scene);
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