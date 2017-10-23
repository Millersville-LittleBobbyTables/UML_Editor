import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.geometry.Insets;

import uml_class.UMLLayout;

public class Main extends Application 
{
    private String currentConnector = "Association";
    private int num_elements = 0;
    private final static double window_width = 1200;
    private final static double window_height = 900;

    public static void main(String[] args) 
    {
        launch(args);
    }

    /**
    * Start is a method that is inherited from Application
    * that will be run after the initial window is setup, and
    * functions as the psuedo main function for JavaFX applications
    */
    @Override
    public void start(Stage primaryStage) throws Exception 
    {
        BorderPane layout = new BorderPane();
        Scene scene = new Scene( layout, window_width, window_height );
        GridPane topGrid = new GridPane();
        Pane center = new Pane();

        topGrid.setPadding( new Insets( 10, 10, 10, 10 ));
        topGrid.setVgap( 8 );
        topGrid.setHgap( 10 );
        layout.setTop( topGrid );
        layout.setCenter( center );

        Button addTextBoxButton = new Button("Add Textbox");
        addTextBoxButton.setOnAction( e ->
        {
            int x = (210 * (num_elements % 5)) + 10;
            int y = (110 * (num_elements / 5)) + 10;
            UMLLayout uml = new UMLLayout(center, x, y);
            num_elements++;
        });
        GridPane.setConstraints( addTextBoxButton, 0, 0 );

        ChoiceBox<String> connectorSelector = new ChoiceBox<>();
        connectorSelector.getItems().addAll("Dependency", "Association", 
            "Generalization", "Aggregation");
        connectorSelector.setValue( currentConnector );
        connectorSelector.getSelectionModel().selectedItemProperty()
            .addListener(( v, oldValue, newValue ) -> 
        {
            if ( oldValue != newValue ) currentConnector = newValue;
        });
        GridPane.setConstraints( connectorSelector, 1, 0 );
        
        Button useConnectorButton = new Button("Use Connector");
        useConnectorButton.setOnAction( e -> 
        {
            System.out.println("Use the " + currentConnector + " Connector");
        });
        GridPane.setConstraints( useConnectorButton, 2, 0 );

        topGrid.getChildren().addAll( addTextBoxButton, connectorSelector, 
            useConnectorButton );
        primaryStage.setScene(scene);
        primaryStage.setTitle("UML Editor");
        primaryStage.show();
    }
}