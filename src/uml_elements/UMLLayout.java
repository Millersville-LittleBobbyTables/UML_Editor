package uml_elements;

import javafx.scene.layout.Pane;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
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

    private final static double rectHeight = 15;
    private final static double mWidth = 100;
    private double width1 = 100;
    private double width2 = 100;
    private double width3 = 100;

    private final static double mHeight = 30;
    private double height1 = 30;
    private double height2 = 30;
    private double height3 = 30;

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
    * @return double max
    */
    public static double maxOfDoubles( double d1 , double d2 )
    {
        if ( d1 >= d2 ) return d1;
        else            return d2;
    }

    public double clamp(double x, double min, double max)
    {
        if ( x < min ) return min;
        if ( x > max ) return max;
        return x;
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
        deleteButton.setLayoutX(x + getMaxWidth() - rectHeight);
        top.setLayoutX(x);
        mid.setLayoutX(x);
        btm.setLayoutX(x);
    }

    /**
    * Set Y position
    */
    private void setYInLayout()
    {
        cell.setY(y);
        deleteButton.setLayoutY(y);
        top.setLayoutY(y + rectHeight);
        mid.setLayoutY(y + height1 + rectHeight);
        btm.setLayoutY(y + height2 + height1 + rectHeight);
    }

    /**
    * Updates the widths 
    */
    private void updateWidths()
    {
        cell.setWidth(getMaxWidth());
        top.setPrefWidth(getMaxWidth());
        mid.setPrefWidth(getMaxWidth());
        btm.setPrefWidth(getMaxWidth());
    }

    private double getMaxHeight()
    {
        return rectHeight + height1 + height2 + height3;
    }

    /**
    * gets the max width so that the widths of the textareas coincide
    * @return double maxWidth
    */
    private double getMaxWidth()
    {
        return maxOfDoubles(width1, maxOfDoubles(width2, width3));
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

        cell = new Rectangle(x,y,mWidth,rectHeight);
        cell.setOnMousePressed(e -> 
        {
            orgX = clamp(e.getX(), X_MIN, X_MAX - getMaxWidth());
            orgY = clamp(e.getY(), Y_MIN, Y_MAX - getMaxHeight());
            moveToFront();
        });
        cell.setOnMouseDragged(e -> 
        {
            double translateX = e.getX() - orgX;
            double translateY = e.getY() - orgY;

            orgX = clamp(e.getX(), X_MIN, X_MAX - getMaxWidth());
            orgY = clamp(e.getY(), Y_MIN, Y_MAX - getMaxHeight());

            x = clamp(x + translateX, X_MIN, X_MAX - getMaxWidth());
            y = clamp(y + translateY, Y_MIN, Y_MAX - getMaxHeight());

            setXInLayout();
            setYInLayout();
        });

        ImageView imageView = new ImageView(new Image("768px-Red_X.svg.png"));
        imageView.setFitWidth(rectHeight - 5);
        imageView.setFitHeight(rectHeight - 5);
        deleteButton = new Button("", imageView);
        deleteButton.setPadding(Insets.EMPTY);
        deleteButton.setPrefHeight(rectHeight);
        deleteButton.setPrefWidth(rectHeight);
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

            width1 = maxOfDoubles(mWidth, currText.getLayoutBounds().getWidth() + 20);
            updateWidths();

            deleteButton.setLayoutX(x + getMaxWidth() - rectHeight);

            height1 = maxOfDoubles(mHeight, currText.getLayoutBounds().getHeight() * 1.08 + 10);
            top.setPrefHeight(height1);

            mid.setLayoutY(y + rectHeight + height1);
            btm.setLayoutY(y + rectHeight + height1 + height2);

            moveToFront();
        });

        mid.setMinWidth( mWidth );
        mid.setPrefWidth( width2 );
        mid.setMinHeight( mHeight );
        mid.setPrefHeight( height2 );
        mid.textProperty().addListener((str, oldValue, newValue) ->
        {
            currText.setText(mid.getText());

            width2 = maxOfDoubles(mWidth, currText.getLayoutBounds().getWidth() + 20);
            updateWidths();

            deleteButton.setLayoutX(x + getMaxWidth() - rectHeight);

            height2 = maxOfDoubles(mHeight, currText.getLayoutBounds().getHeight() * 1.08 + 10);
            mid.setPrefHeight(height2);

            btm.setLayoutY(y + rectHeight + height1 + height2);

            moveToFront();
        });

        btm.setMinWidth( mWidth );
        btm.setPrefWidth( width3 );
        btm.setMinHeight( mHeight );
        btm.setPrefHeight( height3 );
        btm.textProperty().addListener((str, oldValue, newValue) ->
        {
            currText.setText(btm.getText());

            width3 = maxOfDoubles(mWidth, currText.getLayoutBounds().getWidth() + 20);
            updateWidths();

            deleteButton.setLayoutX(x + getMaxWidth() - rectHeight);

            height3 = maxOfDoubles(mHeight, currText.getLayoutBounds().getHeight() * 1.08 + 10);
            btm.setPrefHeight(height3);

            moveToFront();
        });

        setXInLayout();
        setYInLayout();
        addToLayout();
    }
}