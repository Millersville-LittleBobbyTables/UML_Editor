package uml_elements;

import java.util.Vector;
import javafx.scene.layout.Pane;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.Scene;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.paint.Color;
import javafx.scene.Cursor;
import javafx.scene.shape.StrokeType;
import utility.UMath;
import java.lang.Math;
import application.Main;
import interface_elements.SumlToolBar.EditMode;

public class ArrowContainer
{
	public static final String[] ArrowType = {"Association", "Dependency",
        "Generalization", "Realization"};

	private static final double xLength = 50;
	private Vector<Arrow> arrows = new Vector<Arrow>();
	private Scene scene;
    private Pane layout;

    public ArrowContainer(Pane layout, Scene scene)
    {
    	this.layout = layout;
    	this.scene = scene;
    }

	public void addArrow(String arrowType, double x, double y)
	{
		int index = 0;
        for (int i = 0; i < ArrowType.length; ++i)
        {
            if (arrowType.equals(ArrowType[i])) index = i;
        }

        Arrow arrow;
        if      (index == 0)
        {
            arrow = new Arrow(arrows.size(), false, true, true, layout);
        }
        else if (index == 1)
        {
            arrow = new Arrow(arrows.size(), true,  true, true, layout);  
        }
        else if (index == 2)
        {
            arrow = new Arrow(arrows.size(), false, true, false, layout);
        }
        else
        {
            arrow = new Arrow(arrows.size(), true,  true, false, layout);
        }
        arrow.setPosition(x, y, x + xLength, y);
		arrows.add(arrow);
		layout.getChildren().addAll(arrow.getLine(), arrow.getTriangle());
	}

	public void removeArrow(int index)
	{
		layout.getChildren().removeAll(arrows.elementAt(index).getLine(), 
			arrows.elementAt(index).getTriangle());
		arrows.remove(index);
	}

	public void clear()
	{
		arrows.clear();
		layout.getChildren().clear();
	}

	private class Arrow
	{
	    private double triangleHeight = 25;

	    private double X_MIN = triangleHeight;
	    private double Y_MIN = triangleHeight;

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

	    private Pane layout;
	    
	    private int index;

	    /**
	    * Constructs an Arrow object with a scene to allow for cursor changes,
	    *   a isDash boolean to make the line itself dashed, a isTriVisible
	    *   boolean to make the triangle invisible or not, and a isNotClosed
	    *   boolean to make the arrow open like this (--->).
	    */
	    Arrow(int index, boolean isDash, boolean isTriVisible, 
	        boolean isNotClosed, Pane layout)
	    {
	        this.index = index;
	        this.isDash = isDash;
	        this.isTriVisible = isTriVisible;
	        this.isNotClosed = isNotClosed;
	        this.layout = layout;
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

	    public void setPosition (double orgX, double orgY, double endX, double endY)
	    {
	        this.orgX = orgX;
	        this.orgY = orgY;
	        this.endX = endX;
	        this.endY = endY;
	        updateTriangle ();
	        updatePosition ();
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
	     * Clamps the arrow to inside the layout bounds
	     */
	    private void clampToBounds()
	    {
        	double x = getXLength() + X_MIN;
            
            if ( orgX < endX )
            {
                orgX = UMath.clamp(orgX, X_MIN, layout.getWidth() - triangleHeight - getXLength());
                endX = UMath.clamp(endX, x, layout.getWidth() - triangleHeight);
            }
            else
            {
                endX = UMath.clamp(endX, X_MIN, layout.getWidth() - triangleHeight - getXLength());
                orgX = UMath.clamp(orgX, x, layout.getWidth() - triangleHeight);
            }

            double y = getYLength() + Y_MIN;

            if ( orgY < endY )
            {
                orgY = UMath.clamp(orgY, Y_MIN, layout.getHeight() - triangleHeight - getYLength());
                endY = UMath.clamp(endY, y, layout.getHeight() - triangleHeight);
            }
            else
            {
                endY = UMath.clamp(endY, Y_MIN, layout.getHeight() - triangleHeight - getYLength());
                orgY = UMath.clamp(orgY, y, layout.getHeight() - triangleHeight);
            }
            updateTriangle();
            updatePosition();
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

	        line.setOnMouseClicked(e -> 
	        {
	            if (Main.toolBar.currentEditMode() == EditMode.DELETE_MODE)
	            {
	                removeArrow(index);
	            }
	        });

	        line.setOnMousePressed(e->
	        {
	            initX = UMath.clamp(e.getX(), X_MIN, layout.getWidth() - triangleHeight);
	            initY = UMath.clamp(e.getY(), Y_MIN, layout.getHeight() - triangleHeight);
	            moveToFront();
	            e.consume();
	        });

	        line.setOnMouseDragged(e->
	        {
	            double newX = UMath.clamp(e.getX(), X_MIN, layout.getWidth() - triangleHeight);
	            double translateX = newX - initX;
	            initX = newX;
	            orgX += translateX;
	            endX += translateX;

	            double newY = UMath.clamp(e.getY(), Y_MIN, layout.getHeight() - triangleHeight);
	            double translateY = newY - initY;
	            initY = newY;
	            orgY += translateY;
	            endY += translateY;
	            
	            clampToBounds();
	            moveToFront();
	            e.consume();
	        });

	        triangle.setOnMouseClicked(e -> 
	        {
	            if (Main.toolBar.currentEditMode() == EditMode.DELETE_MODE)
	            {
	                removeArrow(index);
	            }
	        });

	        triangle.setOnMouseEntered(e ->
	        {
	            Main.workspaceViewport.setCursor(Cursor.HAND);
	        });

	        triangle.setOnMouseExited(e->
	        {
	            Main.workspaceViewport.setCursor(Cursor.DEFAULT);
	        });
	        
	        triangle.setOnMousePressed(e->
	        {
	            initX = UMath.clamp(e.getX(), X_MIN, layout.getWidth() - triangleHeight);
	            initY = UMath.clamp(e.getY(), Y_MIN, layout.getHeight() - triangleHeight);
	            moveToFront();
	            e.consume();
	        });

	        triangle.setOnMouseDragged(e->
	        {
	            double newX = UMath.clamp(e.getX(), X_MIN, layout.getWidth() - triangleHeight);
	            double newY = UMath.clamp(e.getY(), Y_MIN, layout.getHeight() - triangleHeight);
	            double translateX = newX - initX;
	            double translateY = newY - initY;
	            initX = newX;
	            initY = newY;
	            newX = UMath.clamp(endX + translateX, X_MIN, layout.getWidth() - triangleHeight);
	            newY = UMath.clamp(endY + translateY, Y_MIN, layout.getHeight() - triangleHeight);
	            if (newX != orgX || newY != orgY)
	            {
	                endX = newX;
	                endY = newY;
	                updateTriangle();
	                updatePosition();
	            }
	            moveToFront();
	            e.consume();
	        });
	        
	        layout.widthProperty().addListener( (obs, oldValue, newValue) ->
	        {
	        	clampToBounds();
	        });
	        layout.heightProperty().addListener( (obs, oldValue, newValue) ->
	        {
	        	clampToBounds();
	        });
	        
	        moveToFront();
	    }
	}
}