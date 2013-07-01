package javamas.gui;

import java.awt.Canvas;
import javax.swing.JInternalFrame;

/**
 *
 * @author Guillaume Monet
 */
public class JavaMASDraw extends JInternalFrame{

    private static JavaMASDraw instance = null;
    private Canvas canvas = new Canvas();

    private JavaMASDraw(){
	this.getContentPane().add(canvas);
    }  
}
