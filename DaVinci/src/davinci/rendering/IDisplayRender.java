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

import davinci.Display;

/**
 * 
 * This interface defines a render at the Display level. It manages the<br>
 * rendering pipeline and orders the elements to be rendered. The order<br>
 * is defined by users. It could be the simple first in first out order<br>
 * or some complicated orders such as stack order and tree traverse order.<br>
 * <br>
 * One display can support multiple display renders for different purpose.<br>
 * Generally speaking these renders are managed in three layers:
 * <ul>
 * <li>Background Renders: multiple display renders for rendering the background
 * <br>
 * of the visualization display.</li>
 * <li>View Render: a single display render for rendering all the visualization
 * elements.</li>
 * <li>Foreground Renders: multiple display renders for rendering the foreground<br>
 * of the visualization dispay. It is usually added in interactions to draw, for<br>
 * example, a selection box</li>
 * </ul>
 * 
 * @see davinci.rendering.DisplayRender
 * @see davinci.Display
 * @author <a href="http://www.cse.ust.hk/~nancao/index.html">Nan Cao</a>
 * 
 */
public interface IDisplayRender {

	public void setOwner(Display disp);

	public Display getOwner();

	public boolean isEnabled();

	public void setEnable(boolean benable);

	public void render(Graphics2D g);

}
