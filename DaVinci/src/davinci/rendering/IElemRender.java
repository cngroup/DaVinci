/*
 * DaVinci V1.0 is a light weighted visualization framework and toolkit. 
 * The design of DaVinci based on the information visualization reference model.
 * Some of the packages are ported from prefuse (http://prefuse.org/). All these packages 
 * keep the original file headers and copyright information. Please read and follow it 
 * if you want to use them.
 * 
 * The original motivation of creating this project is to design a light weighted, 
 * simple and easy to use information visualization framework that facilitates the 
 * Ph.D study of the author. For any other purposes please notify the author through 
 * email.
 * 
 * DaVinci V1.0 is under the MIT opensource license.
 * 
 * Author : Nan Cao
 * Email: nan.cao@gmail.com
 * Homepage: http://www.cse.ust.hk/~nancao/
 * Project Homepage: http://www.cse.ust.hk/~nancao/architecture.html
 * V1 Release Date : 1st June 2010
 */
package davinci.rendering;

import java.awt.Graphics2D;
import java.awt.Shape;

import davinci.Display;
import davinci.data.elem.IElement;

/**
 * 
 * This interface defines a render at the element level. It renders one single<br>
 * visual elements per time. One element should be mapped to one element render.<br>
 * Different types of element renders should be designed to rendering different<br>
 * types of elements (node, edge and other user defined types).<br>
 * <br>
 * 
 * The mapping of element renders is managed in the Display (see<br>
 * {@link davinci.Display.addElemRender}). This mapping will be used in the<br>
 * display renders that implements IDisplayRender.
 * 
 * @see davinci.rendering.IDisplayRender
 * @see davinci.Display
 * @author <a href="http://www.cse.ust.hk/~nancao/index.html">Nan Cao</a>
 * 
 */
public interface IElemRender {
	
	public void setOwner(Display owner);
	
	public void getOwner(Display disp);

	public Shape getRawShape(IElement elem);
	
	public void render(Graphics2D g, IElement elem, IElemTheme theme, boolean highlight);
	
	public boolean locatePoint(double x, double y, IElement e);
	
}
