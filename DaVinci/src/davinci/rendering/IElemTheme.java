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

import java.awt.Color;
import java.awt.Font;
import java.awt.Stroke;

import davinci.Display;
import davinci.data.elem.IElement;

/**
 * 
 * This interface defines an element theme. An element theme defines the<br>
 * rendering styles such as the filling color and the border thickness of the<br>
 * element. One element should be mapped to one element theme. Different types<br>
 * of element renders should be designed to rendering different types of<br>
 * elements (node, edge and other user defined types).<br>
 * <br>
 * 
 * The mapping of element theme is managed in the Display (see<br>
 * {@link davinci.Display.addElemTheme}). This mapping will be used in the<br>
 * display renders that implements IDisplayRender.
 * 
 * @see davinci.rendering.IElemRender
 * @see davinci.rendering.IDisplayRender
 * @see davinci.Display
 * @author <a href="http://www.cse.ust.hk/~nancao/index.html">Nan Cao</a>
 * 
 */
public interface IElemTheme {

	public Display getOwner();

	public void setOwner(Display disp);

	public Color getFillColor(IElement e);

	public Color getBorderColor(IElement e);

	public Color getLabelColor(IElement e);

	public Color getTextColor(IElement e);

	public Stroke getThickness(IElement e);

	public Font getLabelFont(IElement e);

	public Font getTextFont(IElement e);

}
