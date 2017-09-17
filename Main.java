import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.geometry.Insets;
 
public class Main extends Application
{   
    private Stage window;
    private GridPane grid;
    private int num_elements = 0;

    public static void main( String[] args ) 
    {
        launch(args);
    }
    
    @Override
    public void start( Stage primaryStage ) 
    {
        window = primaryStage;
        grid = new GridPane();
        grid.setPadding( new Insets( 10, 10, 10, 10 ));
        grid.setVgap( 8 );
        grid.setHgap( 10 );

        Button btn = new Button("Add Textbox");
        GridPane.setConstraints( btn, 0, 0 );
        btn.setOnAction( e ->
        {
            TextField cell = new TextField();
            GridPane.setConstraints( cell, num_elements % 6, (num_elements / 6) + 1 );
            grid.getChildren().add( cell );
            num_elements++;
        });
        
        grid.getChildren().add( btn );
        Scene scene = new Scene( grid, 1200, 900 );
        window.setTitle("UML Editor");
        window.setScene( scene );
        window.show();  
    }
}