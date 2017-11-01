package uml_elements;

import javafx.scene.layout.Pane;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import utility.UMath;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.geometry.Insets;

public class UMLLayout
{
    private double x = 10;
    private double y = 10;

    private double X_MIN = 0;
    private double Y_MIN = 0;
    private double X_MAX = 1200;
    private double Y_MAX = 850;

    private double orgX;
    private double orgY;

    private final static double barHeight = 15;
    private final static double borderThickness = 3;
    private final static double mWidth = 100;
    private double width1 = 100;
    private double width2 = 100;
    private double width3 = 100;

    private final static double mHeight = 30;
    private double height1 = 30;
    private double height2 = 30;
    private double height3 = 30;
    
    private double rectWidth = width1 + (borderThickness * 2);
    private double rectHeight = barHeight + height1
    		                  + height2 + height3
    		                  + (borderThickness * 2);

    private TextArea top = new TextArea();
    private TextArea mid = new TextArea();
    private TextArea btm = new TextArea();
    private Rectangle cell;
    private Button deleteButton;
    private Pane layout;

    private Text currText = new Text();

    /**
    * Constructs a UMLLayout with a pane and default x and y positions
    */
    public UMLLayout(Pane pane)
    {
        layout = pane;
        init();
    }

    /**
    * Constructs a UMLLayout with a pane and specified x and y positions
    */
    public UMLLayout(Pane pane, double x, double y)
    {
        layout = pane;
        this.x = x;
        this.y = y;
        init();
    }

    /**
    * Updates and sets X position for all elements
    */
    public void setX(double x)
    {
        this.x = x;
        setXInLayout();
    }

    /**
    * Updates and sets Y position for all elements
    */
    public void setY(double y)
    {
        this.y = y;
        setYInLayout();
    }

    /**
    * Updates and sets X and Y position for all elements
    */
    public void setPosition(double x, double y)
    {
        this.x = x;
        this.y = y;

        setXInLayout();
        setYInLayout();
    }

    /**
    * @return double x
    */
    public double getX()
    {
        return x;
    }

    /**
    * @return double y
    */
    public double getY()
    {
        return y;
    }

    /**
    * Adds elements to the layout
    */
    public void addToLayout()
    {
        layout.getChildren().addAll(cell, deleteButton, top, mid, btm);
    }

    public void removeFromLayout()
    {
        layout.getChildren().removeAll(cell, deleteButton, top, mid, btm);
        layout = null;
    }

    /**
    * Moves current UMLLayout to the front of the layout
    */
    public void moveToFront()
    {
        cell.toFront();
        top.toFront();
        mid.toFront();
        btm.toFront();
        deleteButton.toFront();
    }

    /**
    * Set X position
    */
    private void setXInLayout()
    {
        cell.setX(x);
        deleteButton.setLayoutX(x + getMaxWidth() + borderThickness - barHeight);
        top.setLayoutX(x + borderThickness);
        mid.setLayoutX(x + borderThickness);
        btm.setLayoutX(x + borderThickness);
    }

    /**
    * Set Y position
    */
    private void setYInLayout()
    {
        cell.setY(y);
        deleteButton.setLayoutY(y + (borderThickness / 2));
        top.setLayoutY(y + barHeight + borderThickness);
        mid.setLayoutY(y + height1 + barHeight + borderThickness);
        btm.setLayoutY(y + height2 + height1 + barHeight + borderThickness);
    }

    /**
    * Updates the widths 
    */
    private void updateWidths()
    {
        cell.setWidth(getMaxWidth() + (borderThickness * 2));
        top.setPrefWidth(getMaxWidth());
        mid.setPrefWidth(getMaxWidth());
        btm.setPrefWidth(getMaxWidth());
    }

    private double getMaxHeight()
    {
        return barHeight + height1 + height2 + height3;
    }

    /**
    * gets the max width so that the widths of the textareas coincide
    * @return double maxWidth
    */
    private double getMaxWidth()
    {
        return UMath.maxOfDoubles(width1, UMath.maxOfDoubles(width2, width3));
    }

    /**
    * private method that is called to initialize the 
    * objects of the class and setup the event behavior
    */
    private void init()
    {
        currText.setFont(Font.font("Verdana", FontWeight.NORMAL, 12));
        top.setFont(Font.font("Verdana", FontWeight.NORMAL, 12));
        mid.setFont(Font.font("Verdana", FontWeight.NORMAL, 12));
        btm.setFont(Font.font("Verdana", FontWeight.NORMAL, 12));

        cell = new Rectangle(x,y,rectWidth,rectHeight);
        cell.setOnMousePressed(e -> 
        {
            orgX = UMath.clamp(e.getX(), X_MIN, X_MAX - getMaxWidth());
            orgY = UMath.clamp(e.getY(), Y_MIN, Y_MAX - getMaxHeight());
            moveToFront();
        });
        cell.setOnMouseDragged(e -> 
        {
            double translateX = e.getX() - orgX;
            double translateY = e.getY() - orgY;

            orgX = UMath.clamp(e.getX(), X_MIN, X_MAX - getMaxWidth());
            orgY = UMath.clamp(e.getY(), Y_MIN, Y_MAX - getMaxHeight());

            x = UMath.clamp(x + translateX, X_MIN, X_MAX - getMaxWidth());
            y = UMath.clamp(y + translateY, Y_MIN, Y_MAX - getMaxHeight());

            setXInLayout();
            setYInLayout();
        });

        ImageView imageView = new ImageView(new Image("768px-Red_X.svg.png"));
        imageView.setFitWidth(barHeight);
        imageView.setFitHeight(barHeight);
        deleteButton = new Button("", imageView);
        deleteButton.setPadding(Insets.EMPTY);
        deleteButton.setPrefHeight(barHeight);
        deleteButton.setPrefWidth(barHeight);
        deleteButton.setOnAction( e ->
        {
            removeFromLayout();
        });

        top.setMinWidth( mWidth );
        top.setPrefWidth( width1 );
        top.setMinHeight( mHeight );
        top.setPrefHeight( height1 );
        top.textProperty().addListener((str, oldValue, newValue) ->
        {
            currText.setText(top.getText());

            width1 = UMath.maxOfDoubles(mWidth, currText.getLayoutBounds().getWidth() + 20);
            updateWidths();

            deleteButton.setLayoutX(x + getMaxWidth() + borderThickness - barHeight);

            height1 = UMath.maxOfDoubles(mHeight, currText.getLayoutBounds().getHeight() * 1.08 + 10);
            top.setPrefHeight(height1);
            cell.setHeight(getMaxHeight() + (borderThickness * 2));

            mid.setLayoutY(y + barHeight + height1 + borderThickness);
            btm.setLayoutY(y + barHeight + height1 + height2 + borderThickness);

            moveToFront();
        });

        mid.setMinWidth( mWidth );
        mid.setPrefWidth( width2 );
        mid.setMinHeight( mHeight );
        mid.setPrefHeight( height2 );
        mid.textProperty().addListener((str, oldValue, newValue) ->
        {
            currText.setText(mid.getText());

            width2 = UMath.maxOfDoubles(mWidth, currText.getLayoutBounds().getWidth() + 20);
            updateWidths();

            deleteButton.setLayoutX(x + getMaxWidth() + borderThickness - barHeight);

            height2 = UMath.maxOfDoubles(mHeight, currText.getLayoutBounds().getHeight() * 1.08 + 10);
            mid.setPrefHeight(height2);
            cell.setHeight(getMaxHeight() + (borderThickness * 2));

            btm.setLayoutY(y + barHeight + height1 + height2 + borderThickness);

            moveToFront();
        });

        btm.setMinWidth( mWidth );
        btm.setPrefWidth( width3 );
        btm.setMinHeight( mHeight );
        btm.setPrefHeight( height3 );
        btm.textProperty().addListener((str, oldValue, newValue) ->
        {
            currText.setText(btm.getText());

            width3 = UMath.maxOfDoubles(mWidth, currText.getLayoutBounds().getWidth() + 20);
            updateWidths();

            deleteButton.setLayoutX(x + getMaxWidth() + borderThickness - barHeight);

            height3 = UMath.maxOfDoubles(mHeight, currText.getLayoutBounds().getHeight() * 1.08 + 10);
            btm.setPrefHeight(height3);
            cell.setHeight(getMaxHeight() + (borderThickness * 2));

            moveToFront();
        });

        setXInLayout();
        setYInLayout();
        addToLayout();
    }
}