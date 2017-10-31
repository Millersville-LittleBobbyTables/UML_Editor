import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
	
    private String currentConnector = ArrowSelector.ArrowType[0];
    private double window_width = 1200;
    private double window_height = 900;

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
        GridPane topGrid = new GridPane();
        Pane center = new Pane();

        // Set up topGrid formatting
        topGrid.setPadding( new Insets(10));
        topGrid.setHgap(10);
        topGrid.setBackground(new Background (
    		new BackgroundFill (Color.LIGHTGREY, CornerRadii.EMPTY, Insets.EMPTY)));
        
        // Set up layout
        layout.setBackground(new Background(
    		new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        layout.setTop( topGrid );
        layout.setCenter( center );

        ToggleGroup editingModes = new ToggleGroup ();
        
        // Set up the 'mouse mode' ToggleButton
        ImageView mouseModeImage = new ImageView(new Image ("mouse.png"));
        mouseModeImage.setFitWidth(32);
        mouseModeImage.setFitHeight(32);
        ToggleButton mouseModeButton = new ToggleButton("", mouseModeImage);
        GridPane.setConstraints(mouseModeButton, 0, 0);
        topGrid.getChildren().add(mouseModeButton);
        mouseModeButton.setToggleGroup(editingModes);
        mouseModeButton.setTooltip(new Tooltip ("Mouse mode"));
        mouseModeButton.setSelected(true);
        
        // Set up the 'add class mode' ToggleButton
        ImageView addClassModeImage = new ImageView(new Image ("AddClass.png"));
        addClassModeImage.setFitWidth (32);
        addClassModeImage.setFitHeight (32);
        ToggleButton addClassModeButton = new ToggleButton ("", addClassModeImage);
        GridPane.setConstraints(addClassModeButton, 1, 0);
        topGrid.getChildren().add(addClassModeButton);
        addClassModeButton.setToggleGroup(editingModes);
        addClassModeButton.setTooltip(new Tooltip ("Add class mode"));
        
        // Set up the 'add arrow mode' ToggleButton
        ImageView addArrowModeImage = new ImageView(new Image ("AddArrow.png"));
        addArrowModeImage.setFitWidth (32);
        addArrowModeImage.setFitHeight (32);
        ToggleButton addArrowModeButton = new ToggleButton ("", addArrowModeImage);
        GridPane.setConstraints(addArrowModeButton, 2, 0);
        topGrid.getChildren().add(addArrowModeButton);
        addArrowModeButton.setToggleGroup(editingModes);
        addArrowModeButton.setTooltip(new Tooltip ("Add arrow mode"));
        
        
        // Set up the connectorSelector ChoiceBox
        ChoiceBox<String> arrowTypeSelector = new ChoiceBox<>();
        arrowTypeSelector.getItems().addAll(ArrowSelector.ArrowType);
        arrowTypeSelector.setValue( ArrowSelector.ArrowType[0] );
        arrowTypeSelector.getSelectionModel().selectedItemProperty()
            .addListener(( v, oldValue, newValue ) -> 
        {
            if ( oldValue != newValue ) currentConnector = newValue;
        });
        arrowTypeSelector.setPrefHeight(30);
        GridPane.setConstraints( arrowTypeSelector, 3, 0 );
        topGrid.getChildren().add(arrowTypeSelector);
        arrowTypeSelector.setTooltip(new Tooltip ("Arrow type"));
        
        // Disallow deleting boxes unless in mouse mode
        center.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> 
        {
        	if (!mouseModeButton.isSelected())
        	{
        		e.consume();
        	}
        });
        // Disallow dragging boxes and arrows allowed unless in mouse mode
        center.addEventFilter(MouseEvent.MOUSE_DRAGGED, e -> 
        {
        	if (!mouseModeButton.isSelected())
        	{
        		e.consume();
        	}
        });
        // Centers clicking logic will be based on the current mode we are in
        center.setOnMouseClicked( e ->
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
        });
        
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
        primaryStage.setTitle("UML Editor");
        primaryStage.show();
    }
}