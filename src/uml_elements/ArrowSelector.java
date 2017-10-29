package uml_elements;

import javafx.scene.Scene;
import uml_elements.Arrow;

public class ArrowSelector
{
    public static final String[] ArrowType = {"Association", "Dependency",
        "Generalization", "Realization"};

    public static int getIndex(String str)
    {
        for (int i = 0; i < ArrowType.length; ++i)
        {
            if (str.equals(ArrowType[i])) return i;
        }
        return 0;
    }

    public static Arrow getArrowSelected(int index, Scene scene)
    {
        if      (index == 0)    return new Arrow(scene, false, true, true);
        else if (index == 1)    return new Arrow(scene, true,  true, true);  
        else if (index == 2)    return new Arrow(scene, false, true);
        else                    return new Arrow(scene, true,  true);           
    }
}