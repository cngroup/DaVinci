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
package davinci;

import davinci.data.elem.IElement;

/**
 * IElement Finder is an interface for finding related visual elements on the Display<BR>
 * under a specified location (x, y). Various ways can be used to implement this interface.<Br>
 * For example, it can be simply implemented by traversing all the elements or it can <Br>
 * also be implemented using a quadtree to accelerate the search process.<Br>
 * 
 * @since V1.0
 * @version 1.0
 * @see davinci.IElemFinder
 * @author <a href="http://www.cse.ust.hk/~nancao/index.html">Nan Cao</a>
 *
 */
public interface IElemFinder {

	public void setOwner(Display disp);
	
	public Display getOwner();
	
	public IElement find(double x, double y);
	
}
