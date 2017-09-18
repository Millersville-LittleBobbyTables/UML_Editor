import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.geometry.Insets;
 
public class Main extends Application
{   
    private Stage window;
    private BorderPane layout;
    private GridPane topGrid;
    private GridPane centerGrid;
    private int num_elements = 0;

    public static void main( String[] args ) 
    {
        launch(args);
    }
    
    @Override
    public void start( Stage primaryStage ) 
    {
        window = primaryStage;

        topGrid = new GridPane();
        topGrid.setPadding( new Insets( 10, 10, 10, 10 ));
        topGrid.setVgap( 8 );
        topGrid.setHgap( 10 );

        centerGrid = new GridPane();
        centerGrid.setPadding( new Insets( 10, 10, 10, 10 ));
        centerGrid.setVgap( 8 );
        centerGrid.setHgap( 10 );

        layout = new BorderPane();
        layout.setTop( topGrid );
        layout.setCenter( centerGrid );

        Button addTextBoxButton = new Button("Add Textbox");
        GridPane.setConstraints( addTextBoxButton, 0, 0 );
        addTextBoxButton.setOnAction( e ->
        {
            TextField cell = new TextField();
            GridPane.setConstraints( cell, num_elements % 6, num_elements / 6 );
            centerGrid.getChildren().add( cell );
            num_elements++;
        });

        ChoiceBox<String> connectorSelector = new ChoiceBox<>();
        connectorSelector.getItems().addAll("Dependency", "Association", 
            "Generalization", "Aggregation");
        connectorSelector.setValue("Association");
        GridPane.setConstraints( connectorSelector, 1, 0 );

        Button useConnectorButton = new Button("Use Connector");
        GridPane.setConstraints( useConnectorButton, 2, 0 );
        String currentConnector;
        useConnectorButton.setOnAction( e -> 
            System.out.println( "Use the " + 
            connectorSelector.getValue() + " Connector"));

        topGrid.getChildren().addAll( addTextBoxButton, connectorSelector, 
            useConnectorButton );
        
        Scene scene = new Scene( layout, 1200, 900 );
        window.setTitle("UML Editor");
        window.setScene( scene );
        window.show();
    }
}