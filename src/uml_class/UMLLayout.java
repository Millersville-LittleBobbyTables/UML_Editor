package uml_class;

import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

public class UMLLayout
    {
        private double width = 100;
        private double height = 200;

        private VBox textStack;
        private TextArea top;
        private TextArea mid;
        private TextArea btm;

        public UMLLayout( )
        {
            init();
        }

        public UMLLayout( double width, double height )
        {
            this.width = width;
            this.height = height;
            init();
        }

        private void init()
        {
            top = new TextArea();
            top.setPrefWidth( width );
            top.setPrefHeight( height / 9 );

            mid = new TextArea();
            mid.setPrefWidth( width );
            mid.setPrefHeight( 4 * height / 9 );

            btm = new TextArea();
            btm.setPrefWidth( width );
            btm.setPrefHeight( 4 * height / 9 );

            textStack = new VBox();
            textStack.getChildren().addAll( top, mid, btm );
        }

        public VBox getUMLBox()
        {
            return textStack;
        }
    }