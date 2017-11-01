package uml_elements.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import uml_elements.UMLLayout;
import javaFX_test_util.JfxTestRunner;


@RunWith (JfxTestRunner.class)
public class UMLLayoutTest
{

	private Pane layout;
	private UMLLayout uml;
	
	@Before
	public void setUpLayout ()
	{
		layout = new Pane ();
		uml = new UMLLayout (layout);
	}
	
	@Test
	public void testCtorAddsRectangle()
	{
		int rectCount = 0;
		ObservableList<Node> children = layout.getChildren(); 
		for (int i = 0; i < children.size(); ++i)
		{
			if (children.get(i) instanceof Rectangle)
			{
				++rectCount;
			}
		}
		assertTrue (rectCount == 1);
	}
	
	@Test
	public void testCtorAddsButton()
	{
		int buttonCount = 0;
		ObservableList<Node> children = layout.getChildren(); 
		for (int i = 0; i < children.size(); ++i)
		{
			if (children.get(i) instanceof Button)
			{
				++buttonCount;
			}
		}
		assertTrue (buttonCount == 1);
	}
	
	@Test
	public void testCtorAddsTextAreas()
	{
		int textCount = 0;
		ObservableList<Node> children = layout.getChildren(); 
		for (int i = 0; i < children.size(); ++i)
		{
			if (children.get(i) instanceof TextArea)
			{
				++textCount;
			}
		}
		assertTrue (textCount == 3);
	}
	
	@Test
	public void testRemoveFromLayout()
	{
		assertTrue (!layout.getChildren().isEmpty());
		uml.removeFromLayout();
		assertTrue (layout.getChildren().isEmpty());
	}

}
