import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.geometry.Insets;

import javafx.scene.shape.Line;
import java.lang.Math;

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
            new UMLLayout(center, x, y);
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
            center.getChildren().add(new Arrow().getLine());
        });
        GridPane.setConstraints( useConnectorButton, 2, 0 );

        topGrid.getChildren().addAll( addTextBoxButton, connectorSelector, 
            useConnectorButton );
        primaryStage.setScene(scene);
        primaryStage.setTitle("UML Editor");
        primaryStage.show();
    }

    public class Arrow
    {
        private double orgX = 10;
        private double orgY = 10;
        private double endX = 100;
        private double endY = 10;

        private double initX;
        private double initY;

        private static final double X_MIN = 5;
        private static final double Y_MIN = 5;

        private static final double X_MAX = 1100;
        private static final double Y_MAX = 800;

        private boolean isDash = true;

        Line line = new Line();

        Arrow()
        {
            init();
        }

        Arrow(boolean isDash)
        {
            this.isDash = isDash;
            init();
        }

        Arrow(double orgX, double orgY, double endX, double endY)
        {
            this.orgX = orgX;
            this.orgY = orgY;
            this.endX = endX;
            this.endY = endY;
            init();
        }

        Arrow(double orgX, double orgY, double endX, double endY, boolean isDash)
        {
            this.isDash = isDash;
            this.orgX = orgX;
            this.orgY = orgY;
            this.endX = endX;
            this.endY = endY;
            init();
        }

        public Line getLine()
        {
            return line;
        }
        
        private void init()
        {
            updatePosition();
            line.setStrokeWidth(10);
            if (isDash)
            {
                line.getStrokeDashArray().addAll(15d, 20d);
            }

            line.setOnMousePressed(e->
            {
                initX = clamp(e.getX(), X_MIN, X_MAX);
                initY = clamp(e.getY(), Y_MIN, Y_MAX);
            });

            line.setOnMouseDragged(e->
            {
                double newX = clamp(e.getX(), X_MIN, X_MAX);
                double translateX = newX - initX;
                initX = newX;
                double x = getXLength() + X_MIN;
                
                if ( orgX < endX )
                {
                    orgX = clamp(orgX + translateX, X_MIN, X_MAX - getXLength());
                    endX = clamp(endX + translateX, x, X_MAX);
                }
                else
                {
                    endX = clamp(endX + translateX, X_MIN, X_MAX - getXLength());
                    orgX = clamp(orgX + translateX, x, X_MAX);
                }

                double newY = clamp(e.getY(), Y_MIN, Y_MAX);
                double translateY = newY - initY;
                initY = newY;
                double y = getYLength() + Y_MIN;

                if ( orgY < endY )
                {
                    orgY = clamp(orgY + translateY, Y_MIN, Y_MAX - getYLength());
                    endY = clamp(endY + translateY, y, Y_MAX);
                }
                else
                {
                    endY = clamp(endY + translateY, Y_MIN, Y_MAX - getYLength());
                    orgY = clamp(orgY + translateY, y, Y_MAX);
                }
                updatePosition();
            });
        }

        public boolean within(double x, double min, double max)
        {
            if ( x >= min )
            {
                if ( x <= max )
                {
                    return true;
                }
            }
            return false;
        }

        public double clamp(double x, double min, double max)
        {
            if ( x < min )
            {
                return min;
            }
            if ( x > max )
            {
                return max;
            }
            return x;
        }
/*
        private double distFromYBound()
        {
            double xSquared = pow(getXLength(), 2);
            double lengthSquared = xSquared + pow(getYLength(), 2);
            return Math.sqrt(lengthSquared - xSquared);
        }

        public double getLength()
        {
            return Math.sqrt(Math.pow(getXLength(), 2) + Math.pow(getYLength(), 2));
        }
*/
        private double getXLength()
        {
            return Math.abs(orgX - endX);
        }

        private double getYLength()
        {
            return Math.abs(orgY - endY);
        }

        private void updateX()
        {
            line.setStartX(orgX);
            line.setEndX(endX);
        }

        private void updateY()
        {
            line.setStartY(orgY);
            line.setEndY(endY);
        }

        private void updatePosition()
        {
            line.setStartX(orgX);
            line.setEndX(endX);
            line.setStartY(orgY);
            line.setEndY(endY);
        }
    }
}