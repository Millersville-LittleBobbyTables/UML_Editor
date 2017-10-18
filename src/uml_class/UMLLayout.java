package uml_class;

import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

/*
 * class responsible for the main layout of the UML diagram
 * including the textareas, top menu bar, etc. 
 */
public class UMLLayout
    {
        private double width = 100;
        private double height = 200;

        private VBox textStack;
        private TextArea top;
        private TextArea mid;
        private TextArea btm;

        /*
         * initializing the layout
         */
        public UMLLayout( )
        {
            init();
        }

        /*
         * creating an initial height and width for the UML window 
         */
        public UMLLayout( double width, double height )
        {
            this.width = width;
            this.height = height;
            init();
        }

        /*
         * establishing the textareas on the left of the window
         * these text areas should adjust height to text content 
         * (no scroll bar)
         */
        private void init()
        {
            top = new TextArea();
            top.setMinWidth( width );
            top.setPrefHeight( height / 9 );

            mid = new TextArea();
            mid.setMinWidth( width );
            mid.setPrefHeight( 4 * height / 9 );

            btm = new TextArea();
            btm.setMinWidth( width );
            btm.setPrefHeight( 4 * height / 9 );

            textStack = new VBox();
            textStack.getChildren().addAll( top, mid, btm );
        }

        /*
         * gets the box for the UML
         */
        public VBox getUMLBox()
        {
            return textStack;
        }
    }