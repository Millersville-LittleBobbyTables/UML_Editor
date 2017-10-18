package application;

import gui_tools.DraggableNode;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import uml_class.UMLLayout;
import javafx.scene.input.DragEvent;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
import javafx.scene.text.Text;

public class Main extends Application 
{
    private String currentConnector = "Association";
    private int num_elements = 0;
    private double window_width = 1200;
    private double window_height = 900;

    public static void main(String[] args) 
    {
        launch(args);
    }

    /**
     * establishes the window and menu bar for the UML
     * application
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
            Rectangle cell = new Rectangle( x, y, 200, 100 );
            cell.setOnMouseDragged( event ->
            {
                double centerHeight = window_height - topGrid.getHeight();
                double x_delta = event.getX() - ( cell.getWidth() / 2 );
                double x_pos = 0;
                if ( x_delta >= 0 )
                {
                    if ( x_delta <= window_width )
                    {
                        x_pos = x_delta;
                    }
                    else
                    {
                        x_pos = window_width;
                    }
                }
                cell.setX( x_pos );

                double y_delta = event.getY() - ( cell.getHeight() / 2 );
                double y_pos = 0;
                if ( y_delta >= 0 )
                {
                    if ( y_delta <= centerHeight )
                    {
                        y_pos = y_delta;
                    }
                    else
                    {
                        y_pos = centerHeight;
                    }
                }
                cell.setY( y_pos );
            });
            center.getChildren().add( cell );
            num_elements++;
        });
        GridPane.setConstraints( addTextBoxButton, 0, 0 );

        DraggableNode diagram = new DraggableNode( new UMLLayout().getUMLBox());
        center.getChildren().add( diagram.getView());

        ChoiceBox<String> connectorSelector = new ChoiceBox<>();
        connectorSelector.getItems().addAll("Dependency", "Association", 
            "Generalization", "Aggregation");

        connectorSelector.setValue( currentConnector );
        GridPane.setConstraints( connectorSelector, 1, 0 );

        connectorSelector.getSelectionModel().selectedItemProperty()
            .addListener(( v, oldValue, newValue ) -> 
        {
            if ( oldValue != newValue )
            {
                currentConnector = newValue;
            }
        });
        
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
