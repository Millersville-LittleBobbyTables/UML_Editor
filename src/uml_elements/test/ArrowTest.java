package uml_elements.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javaFX_test_util.JfxTestRunner;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import uml_elements.Arrow;

@RunWith (JfxTestRunner.class)
public class ArrowTest
{

	private Scene scene;
	private Pane layout;
	private Arrow arrow;
	
	@Before
	public void setUp()
	{
		layout = new Pane();
		scene = new Scene (layout);
		arrow = new Arrow (scene, 5, 64, 29, 8);
	}
	
	@Test
	public void testLineAfterCtor()
	{
		Line l = arrow.getLine();
		assertTrue (l.getStartX() == 5);
		assertTrue (l.getStartY() == 64);
		assertTrue (l.getEndX() == 29);
		assertTrue (l.getEndY() == 8);
	}
	
	@Test
	public void testXLength()
	{
		assertTrue (arrow.getXLength() == 24);
	}
	
	@Test
	public void testYLength()
	{
		assertTrue (arrow.getYLength() == 56);
	}
	
	@Test
	public void testLength ()
	{
		assertTrue (arrow.getLength () == Math.sqrt (3712));
	}

}
