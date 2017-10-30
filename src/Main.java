import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    private String currentConnector = "Association";
    private double window_width = 1200;
    private double window_height = 900;
    private static final double menu_height = 50;

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

        topGrid.setPadding( new Insets(10));
        topGrid.setVgap(8);
        topGrid.setHgap(10);
        layout.setBackground(new Background(
            new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        layout.setTop( topGrid );
        layout.setCenter( center );

        Button addTextBoxButton = new Button("Add Textbox");
        addTextBoxButton.setOnAction( e ->
        {
            new UMLLayout(center);
        });
        GridPane.setConstraints( addTextBoxButton, 0, 0 );
        addTextBoxButton.setPrefWidth(102);
        addTextBoxButton.setPrefHeight(30);
        topGrid.getChildren().add( addTextBoxButton );

        ChoiceBox<String> connectorSelector = new ChoiceBox<>();
        connectorSelector.getItems().addAll(ArrowSelector.ArrowType);
        connectorSelector.setValue( currentConnector );
        connectorSelector.getSelectionModel().selectedItemProperty()
            .addListener(( v, oldValue, newValue ) -> 
        {
            if ( oldValue != newValue ) currentConnector = newValue;
        });
        connectorSelector.setPrefHeight(30);
        GridPane.setConstraints( connectorSelector, 1, 0 );
        
        Button useConnectorButton = new Button("Use Connector");
        useConnectorButton.setOnAction( e -> 
        {
            Arrow arrow = ArrowSelector.getArrowSelected(
                ArrowSelector.getIndex(currentConnector), scene);
            center.getChildren().addAll(arrow.getLine(), arrow.getTriangle());
        });
        useConnectorButton.setPrefWidth(114);
        useConnectorButton.setPrefHeight(30);
        GridPane.setConstraints( useConnectorButton, 2, 0 );

        ToggleGroup editingModes = new ToggleGroup ();
        
        ImageView textBoxModeImage = new ImageView(new Image ("AddTextBox.png"));
        textBoxModeImage.setFitWidth (32);
        textBoxModeImage.setFitHeight (32);
        ToggleButton addTextBoxMode = new ToggleButton ("", textBoxModeImage);
        addTextBoxMode.setPrefWidth(32);
        addTextBoxMode.setPrefHeight(32);
        GridPane.setConstraints(addTextBoxMode, 3, 0);
        topGrid.getChildren().add(addTextBoxMode);
        editingModes.getToggles().add(addTextBoxMode);
        
        ImageView addArrowModeImage = new ImageView(new Image ("AddArrow.png"));
        addArrowModeImage.setFitWidth (32);
        addArrowModeImage.setFitHeight (32);
        ToggleButton addArrowMode = new ToggleButton ("", addArrowModeImage);
        addArrowMode.setPrefWidth(32);
        addArrowMode.setPrefHeight(32);
        GridPane.setConstraints(addArrowMode, 4, 0);
        topGrid.getChildren().add(addArrowMode);
        editingModes.getToggles().add(addArrowMode);
        
        center.setOnMouseClicked( e ->
        {
        	if (addTextBoxMode.isSelected())
        	{
        		UMLLayout uml = new UMLLayout(center);
        		uml.setPosition (e.getX(), e.getY());
        		e.consume();
        	}
        	else if (addArrowMode.isSelected())
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

        topGrid.getChildren().addAll( connectorSelector, useConnectorButton );
        primaryStage.setScene(scene);
        primaryStage.setTitle("UML Editor");
        primaryStage.show();
    }
}