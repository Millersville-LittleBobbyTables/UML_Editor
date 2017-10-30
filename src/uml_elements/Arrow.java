package uml_elements;

import javafx.scene.Scene;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.paint.Color;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.Cursor;
import javafx.scene.shape.StrokeType;
import javafx.scene.shape.StrokeLineCap;
import java.lang.Math;

public class Arrow
{
    private double triangleHeight = 25;

    private double X_MIN = triangleHeight;
    private double Y_MIN = triangleHeight;

    private double X_MAX = 1180;
    private double Y_MAX = 830;

    private double orgX = X_MIN;
    private double orgY = Y_MIN;
    private double endX = X_MIN + 50;
    private double endY = Y_MIN;

    private double initX;
    private double initY;

    private double newOrgX;
    private double newOrgY;

    private boolean isDash          = false;
    private boolean isNotClosed     = false;
    private boolean isTriVisible    = false;

    private Line line = new Line();
    private Polygon triangle = new Polygon();

    private Scene scene;

    /**
    * Constructs an Arrow object with a scene to allow for cursor changes
    */
    Arrow(Scene scene)
    {
        this.scene = scene;
        init();
    }

    /**
    * Constructs an Arrow object with a scene to allow for cursor changes
    *   and a dash boolean to make the line itself dashed
    */
    Arrow(Scene scene, boolean isDash)
    {
        this.scene = scene;
        this.isDash = isDash;
        init();
    }

    /**
    * Constructs an Arrow object with a scene to allow for cursor changes,
    *   a isDash boolean to make the line itself dashed, and a isTriVisible
    *   boolean to make the triangle invisible or not
    */
    Arrow(Scene scene, boolean isDash, boolean isTriVisible)
    {
        this.scene = scene;
        this.isDash = isDash;
        this.isTriVisible = isTriVisible;
        init();
    }

    /**
    * Constructs an Arrow object with a scene to allow for cursor changes,
    *   a isDash boolean to make the line itself dashed, a isTriVisible
    *   boolean to make the triangle invisible or not, and a isNotClosed
    *   boolean to make the arrow open like this (--->).
    */
    Arrow(Scene scene, boolean isDash, boolean isTriVisible, 
        boolean isNotClosed)
    {
        this.scene = scene;
        this.isDash = isDash;
        this.isTriVisible = isTriVisible;
        this.isNotClosed = isNotClosed;
        init();
    }
    
    /**
    * Constructs an Arrow object with a scene and line coordinates
    */
    Arrow(Scene scene, double orgX, double orgY, double endX, double endY)
    {
        this.scene = scene;
        this.orgX = orgX;
        this.orgY = orgY;
        this.endX = endX;
        this.endY = endY;
        init();
    }

    /**
    * Constructs an Arrow object with a scene, line coordinates, and a
    * isDash boolean to make the line itself dashed
    */
    Arrow(Scene scene, double orgX, double orgY, double endX, double endY, 
        boolean isDash)
    {
        this.scene = scene;
        this.isDash = isDash;
        this.orgX = orgX;
        this.orgY = orgY;
        this.endX = endX;
        this.endY = endY;
        init();
    }

    /**
    * @return Line object
    */
    public Line getLine()
    {
        return line;
    }

    /**
    * @return Polygon triangle
    */
    public Polygon getTriangle()
    {
        return triangle;
    }

    /**
    * Moves all objects to front
    */
    public void moveToFront()
    {
        line.toFront();
        triangle.toFront();
    }
    /**
    * @return double within min <= x <= max
    */
    public double clamp(double x, double min, double max)
    {
        if ( x < min ) return min;
        if ( x > max ) return max;
        return x;
    }

    /**
    * @return length = sqrt( x^2 + y^2 )
    */
    public double getLength()
    {
        return Math.sqrt(Math.pow(getXLength(), 2) + Math.pow(getYLength(), 2));
    }

    /**
    * @return x vector
    */
    public double getXLength()
    {
        return Math.abs(orgX - endX);
    }

    /**
    * @return y vector
    */
    public double getYLength()
    {
        return Math.abs(orgY - endY);
    }

    /**
    * updates line to proper positions
    */
    private void updatePosition()
    {
        line.setStartX(orgX);
        line.setStartY(orgY);

        if (isTriVisible)
        {
            line.setEndX(newOrgX);
            line.setEndY(newOrgY);
        }
        else
        {
            line.setEndX(endX);
            line.setEndY(endY);
        }
    }

    /**
    * Maintains a triangle with a height of triangleHeight and a base
    *   of 2 * triangleHeight.
    */
    private void updateTriangle()
    {
        double length = getLength();
        double newLength = length - triangleHeight;
        double lengthFactor = newLength / length;

        newOrgX = (lengthFactor * (endX - orgX)) + orgX;
        newOrgY = (lengthFactor * (endY - orgY)) + orgY;

        double vectorX = endX - newOrgX;
        double vectorY = endY - newOrgY;

        double newX1 = -vectorY + newOrgX;
        double newY1 = vectorX + newOrgY;

        double newX2 = vectorY + newOrgX;
        double newY2 = -vectorX + newOrgY;

        triangle.getPoints().setAll(new Double[]
        {
            endX,  endY,
            newX1, newY1,
            newX2, newY2
        });
    }

    /**
    * initializes all objects with properly defined behavior
    */
    private void init()
    {
        updateTriangle();
        triangle.setFill(Color.TRANSPARENT);
        if (isNotClosed)
        {
            triangle.getStrokeDashArray()
                .addAll(Math.sqrt(2) * triangleHeight, 2 * triangleHeight);
        }
        if (isTriVisible)
        {
            triangle.setStrokeType(StrokeType.INSIDE);
            triangle.setStroke(Color.BLACK);
            triangle.setStrokeWidth(5);
        }

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
            moveToFront();
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
            updateTriangle();
            updatePosition();
            moveToFront();
        });

        triangle.setOnMouseEntered(e->
        {
            scene.setCursor(Cursor.HAND);
        });
        triangle.setOnMouseExited(e->
        {
            scene.setCursor(Cursor.DEFAULT);
        });
        
        triangle.setOnMousePressed(e->
        {
            initX = clamp(e.getX(), X_MIN, X_MAX);
            initY = clamp(e.getY(), Y_MIN, Y_MAX);
            moveToFront();
        });

        triangle.setOnMouseDragged(e->
        {
            double newX = clamp(e.getX(), X_MIN, X_MAX);
            double newY = clamp(e.getY(), Y_MIN, Y_MAX);
            double translateX = newX - initX;
            double translateY = newY - initY;
            initX = newX;
            initY = newY;
            newX = clamp(endX + translateX, X_MIN, X_MAX);
            newY = clamp(endY + translateY, Y_MIN, Y_MAX);
            if (newX != orgX || newY != orgY)
            {
                endX = newX;
                endY = newY;
                updateTriangle();
                updatePosition();
            }
            moveToFront();
        });
        moveToFront();
    }
}