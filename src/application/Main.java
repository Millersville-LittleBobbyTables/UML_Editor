package application;

import interface_elements.SumlMenuBar;
import interface_elements.SumlToolBar;
import interface_elements.SumlWorkspace;
import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;

public class Main extends Application 
{	
    public static double window_width = 1200;
    public static double window_height = 900;
    
    public static SumlMenuBar menuBar;
    public static SumlToolBar toolBar;
    public static ScrollPane workspaceViewport;
    public static SumlWorkspace workspace;

    public static Scene scene;

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
        scene = new Scene( layout, window_width, window_height );
        // Fixes cursor bug where the cursor stays in hand mode if dragging the arrow head past
        // the edge of the screen and letting go outside of the window and then returning.
        // Kinda hacky.
        scene.setOnMouseEntered(e -> {
        	workspaceViewport.setCursor(Cursor.DEFAULT);
        });
        
        menuBar = new SumlMenuBar (primaryStage);
        toolBar = new SumlToolBar ();
        // For if we decide on adding tabs
        // TabPane tabBar = new TabPane ();
        //VBox top = new VBox (menuBar, toolBar, tabBar);
        VBox top = new VBox ();
        top.getChildren().addAll(menuBar.getMenuBar(), toolBar.getToolBar());
        
        workspaceViewport = new ScrollPane ();
        workspaceViewport.setPannable(true);
        workspaceViewport.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        workspaceViewport.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        workspaceViewport.setStyle("-fx-background:#555555; -fx-focus-color:transparent;");
        
        workspace = new SumlWorkspace (1200, 825);
        workspaceViewport.setContent(workspace.getWorkspace());
        
        // Set up layout
        layout.setBackground(new Background(
        		new BackgroundFill(Color.DARKGREY, CornerRadii.EMPTY, Insets.EMPTY)));
        layout.setTop(top);
        layout.setCenter(workspaceViewport);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Simple UML");
        primaryStage.show();
    }
}